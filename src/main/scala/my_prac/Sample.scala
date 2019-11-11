package my_prac

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

object Myprac {

}


class Test {
  import Sample._
  import spark.implicits._


  val dfnew = spark.read.
    csv("C:\\Users\\kenche's\\Desktop\\03 Practice\\07 spark\\my\\inputs\\emp1.txt")

  dfnew.show

  val sf1 = StructField("name", StringType, nullable = true)
  val sf2 = StructField("sector", StringType, nullable = true)
  val sf3 = StructField("age", IntegerType, nullable = true)

  val fields = List(sf1,sf2,sf3)
  val schema = StructType(fields)



  val row1 = Row("Andy","aaa",20)
  val row2 = Row("Berta","bbb",30)
  val row3 = Row("Joe","ccc",40)

  val data = Seq(row1,row2,row3)

  val df1 = spark.createDataFrame(spark.sparkContext.parallelize(data), schema)

  df1.show

  df1.select($"name",$"sector",$"age")
}


object Sample {
  val spark = SparkSession.builder().
    master("local").
    appName("my prac").
    getOrCreate()

  def main(args: Array[String]): Unit = {
    val test = new Test


    spark.sparkContext.setLogLevel("ERROR")
  }
}


