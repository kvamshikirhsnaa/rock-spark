package com.retail

import org.apache.spark.sql.SparkSession
import com.typesafe.config.ConfigFactory

object GetTopNProductsPerDaySQL {
  def main(args: Array[String]): Unit = {

    val props = ConfigFactory.load()
    val envProps = props.getConfig(args(0))
    val topN = args(1).toInt

    val spark = SparkSession.builder.
      master(envProps.getString("execution.mode")).
      appName("get "+topN +" products per day").
      getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")
    spark.conf.set("spark.sql.shuffle.partitions","2")

    val inputBaseDir = envProps.getString("input.base.dir")
    val orders = spark.read.json(inputBaseDir +"/orders")
    orders.createOrReplaceTempView("orders")

    val orderItems = spark.read.json(inputBaseDir +"/order_items")
    orderItems.createOrReplaceTempView("orderItems")

    val dailyProductsRevenue = spark.sql("select o.order_date, "+
      " oi.order_item_product_id,sum(oi.order_item_subtotal) as "+
      " daily_product_revenue from orders o join orderItems oi "+
      " on o.order_id = oi.order_item_order_id "+
      " where o.order_status in ('COMPLETE','CLOSED') "+
      " group by o.order_date,oi.order_item_product_id ")

    //dailyProductsRevenue.show

    dailyProductsRevenue.
      createOrReplaceTempView("daily_products")

    //spark.sql("select *,rank() over "+
    //" (partition by order_date order by daily_product_revenue desc) "+
    //" as rnk from daily_products").show


    val topNProductsPerDay = spark.sql("select * from "+
      "(select order_date,order_item_product_id,daily_product_revenue,"+
      "rank() over (partition by order_date "+
      "order by daily_product_revenue desc) as rnk from daily_products) "+
      "q where rnk <= " +topN + " order by order_date,rnk")

    //topNProductsPerDay.show

    val outputBaseDir = envProps.getString("output.base.dir")
    topNProductsPerDay.write.json(outputBaseDir +"/topNProductsPerDaySQL")


  }

}
