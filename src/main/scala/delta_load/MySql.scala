package delta_load

import org.apache.spark.sql.SparkSession

object MySql {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      master("local").
      appName("jdbc app").getOrCreate()

    import spark.implicits._

    spark.sparkContext.setLogLevel("ERROR")
    spark.conf.set("spark.sql.shuffle.partitions", 2)

    val df = spark.read.option("header", "false").
      csv("/spark/control/control1.csv").
      toDF("tname", "lastsink")


    val rdd = df.rdd.map(x => x.toString.replace("[", "").replace("]", ""))

    val rdd2 = rdd.map(x => (x.split(",")(0), x.split(",")(1) ) )

    val pair = rdd2.collectAsMap()

    val empDF = spark.read.
      format("jdbc").
      option("url", "jdbc:mysql://localhost:3306/kvk").
      option("dbtable", "employee").
      option("user", "root").option("password", "root").
      load

    val empDF2 = spark.read.
      format("jdbc").
      option("url", "jdbc:mysql://localhost:3306/kvk").
      option("dbtable", "employee").
      option("user", "root").option("password", "root").load.
      where($"sink" >= pair.get("table1"))

    if (pair.get("table1") == "null") {
      empDF.write.saveAsTable("empnew")
    }
    else {
      empDF2.write.mode("append").
        insertInto("empnew")
    }

//    val df2 = df.
//      select($"tname", when($"lastsink" isNull, current_timestamp()))




  }

}
