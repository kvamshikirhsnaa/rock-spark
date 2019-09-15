package my_prac

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object SalesNew {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      master("local").
      appName("my prac").
      getOrCreate()

    import spark.implicits._

    spark.sparkContext.setLogLevel("ERROR")

    val df = spark.read.option("inferSchema", "true").
      csv("C:\\Users\\kenche's\\Desktop\\03 Practice\\07 spark\\my\\inputs\\sales.txt").
      toDF("id","price")

    val spec = Window.orderBy($"price")

    val df2 = df.withColumn("amt", lag($"price",2,0) over (spec))

    df2.show

//    val spec2 = Window.orderBy($"amt").
//      rangeBetween(lag($"price",2,0) over (spec), Window.currentRow)




  }

}
