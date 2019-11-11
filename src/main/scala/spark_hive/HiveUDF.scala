package spark_hive

import org.apache.hadoop.hive.ql.exec.UDF

class HiveUDF extends UDF {

  def evaluate(str: String): String = {
    if (str == null) null
    else {
      val res = str.substring(2, 5)
      res
    }
  }
}
