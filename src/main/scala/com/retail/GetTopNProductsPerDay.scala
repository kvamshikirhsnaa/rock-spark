package com.retail

import com.typesafe.config.ConfigFactory
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.expressions._

object GetTopNProductsPerDay {
  def main(args: Array[String]): Unit = {

    val props = ConfigFactory.load()
    val envProps = props.getConfig(args(0))
    val topN = args(1).toInt

    val spark = SparkSession.builder.
      master(envProps.getString("execution.mode")).
      appName("get " +topN+ " products per day").
      getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")
    spark.conf.set("spark.sql.shuffle.partitions","2")

    import spark.implicits._

    val inputBaseDir = envProps.getString("input.base.dir")
    val orders = spark.read.json(inputBaseDir + "/orders")
    val orderItems = spark.read.json(inputBaseDir + "/order_items")

    val dailyProductsRevenue = orders.
      where($"order_status" isin ("COMPLETE","CLOSED")).
      join(orderItems, $"order_id" === $"order_item_order_id").
      groupBy($"order_date",$"order_item_product_id").
      agg(sum($"order_item_subtotal") as "daily_product_revenue")

    val spec = Window.partitionBy($"order_date").
      orderBy($"daily_product_revenue"  desc)

    val getTopNProducts = dailyProductsRevenue.
      withColumn("rnk", rank() over(spec)).
      where($"rnk" <= topN).
      orderBy($"order_date",$"rnk")

    val outputBaseDir = envProps.getString("output.base.dir")

    getTopNProducts.write.json(outputBaseDir + "/getTopNproductsPerDay")

  }

}
