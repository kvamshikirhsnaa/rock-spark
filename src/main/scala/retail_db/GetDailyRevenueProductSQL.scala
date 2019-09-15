package retail_db

import com.typesafe.config.ConfigFactory
import org.apache.spark.sql.SparkSession

object GetDailyRevenueProductSQL {
  def main(args: Array[String]): Unit = {

    val props = ConfigFactory.load()
    val envProps = props.getConfig(args(0))

    val spark = SparkSession.builder.
      master(envProps.getString("execution.mode")).
      appName("get daily product revenue").
      getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")
    spark.conf.set("spark.sql.shuffle.partitions","2")

    val inputBaseDir = envProps.getString("input.base.dir")

    val orders = spark.read.json(inputBaseDir + "/orders")
    orders.createOrReplaceTempView("orders")

    val orderItems = spark.read.json(inputBaseDir + "/order_items")
    orderItems.createOrReplaceTempView("orderItems")

    //spark.sql("show tables").show
    //spark.sql("select * from orders").show
   // spark.sql("select * from orderItems").show

    val dailyProductRevenue = spark.sql("select o.order_date, "+
      " oi.order_item_product_id,sum(oi.order_item_subtotal) as "+
      " daily_product_revenue from orders o join orderItems oi "+
      " on o.order_id = oi.order_item_order_id "+
      " where o.order_status in ('COMPLETE','CLOSED') "+
      " group by o.order_date,oi.order_item_product_id "+
      //" having daily_product_revenue >= 1000 "+
      " order by o.order_date,daily_product_revenue")

    //dailyProductRevenue.show

    val outputBaseDir = envProps.getString("output.base.dir")
    dailyProductRevenue.write.json(outputBaseDir + "/dailyProductsRevnueSQL")






  }




}
