package com.retail

import org.apache.spark.sql.SparkSession

object mysql {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      master("local").
      appName("jdbc app").getOrCreate()

    val df = spark.read.format("jdbc").
      option("url","jdbc:mysql://localhost:3306/sakila").
      option("dbtable","actor").
      option("user","root").
      option("password","Kenche@21").load()

    df.show
  }

}
