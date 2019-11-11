package my_prac

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object Sales {
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

    val spec = Window.orderBy("price").
      rowsBetween(-2, Window.currentRow)

    val df2 = df.select($"id", $"price", sum($"price") over (spec) as "amt")

    df2.show

    val window1 = Window.orderBy($"price")
    val window2 = Window
      .orderBy($"price")
      .rowsBetween(Window.unboundedPreceding, Window.currentRow)

    df.withColumn("counter", when((lag($"price", 2,0).over(window1)) === 0, $"price")
      .otherwise(sum($"price").over(window2))).show

    //df.withColumn("counter", sum($"price").over(window2)).show


  }

}
