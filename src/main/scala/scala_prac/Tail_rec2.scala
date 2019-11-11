package scala_prac

import scala.annotation.tailrec


object Tail_rec2 {
  def main(args: Array[String]): Unit = {

    println(fact(1, 500))

  }

  @tailrec
  def fact(acc: BigInt, num: BigInt): BigInt = {
    require(num > 0, "num should not be negative")

    if (num == 1) acc
    else fact(acc * num, num - 1)
  }

}


