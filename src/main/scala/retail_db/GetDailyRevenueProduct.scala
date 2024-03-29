package retail_db

import com.typesafe.config.ConfigFactory
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._



object GetDailyRevenueProduct {
  def main(args: Array[String]): Unit = {
    val props = ConfigFactory.load()
    val envProps = props.getConfig(args(0))

    val spark = SparkSession.
      builder().
      master(envProps.getString("execution.mode")).
      appName("get daily product revenue").
      getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")
    spark.conf.set("spark.sql.shuffle.partitions","2")

    import spark.implicits._

    val inputBaseDir = envProps.getString("input.base.dir")
    val orders = spark.read.json(inputBaseDir + "/orders")
    val orderItems = spark.read.json(inputBaseDir + "/order_items")

    val dailyProductRevenue = orders.
      where($"order_status" isin ("COMPLETE","CLOSED")).
      join(orderItems, $"order_id" === $"order_item_order_id").
      groupBy($"order_date",$"order_item_product_id").
      agg(sum($"order_item_subtotal") as "daily_revenue").
      orderBy($"order_date",$"daily_revenue" desc)

    val outputBaseDir = envProps.getString("output.base.dir")
    dailyProductRevenue.write.json(outputBaseDir + "/daily_revenue_json")

  }

}
