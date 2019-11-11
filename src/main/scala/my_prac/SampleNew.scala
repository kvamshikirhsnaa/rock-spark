package my_prac

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object SampleNew {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      master("local").
      appName("my prac").
      getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    import spark.implicits._

    val df = spark.read.option("inferSchema", "true").option("header", "true").
      csv("C:\\Users\\kenche's\\Desktop\\03 Practice\\07 spark\\my\\inputs\\sample.txt")

    df.show

    val days = (i: Int) => i * 60 * 5

    val df2 = df.withColumn("timestamp", unix_timestamp($"eventtime").cast("Long"))

    df2.show

    val spec = Window.partitionBy($"location_point").orderBy($"timestamp").
      rangeBetween(-60 * 5, 0)

    val df3 = df2.withColumn("occ_in_5min", count($"timestamp") over (spec))

    df3.show

    val udf_conv = udf((i: Int) => if (i > 1) true else false)

    val df4 = df3.withColumn("already_occupied", udf_conv($"occ_in_5min"))

    df4.show
  }
}