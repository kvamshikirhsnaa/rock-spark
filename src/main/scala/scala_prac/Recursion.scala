package scala_prac

object Recursion {
  def main(args: Array[String]): Unit = {

    println(fact(5))
    println(fact(50))
    println(fact(500))
    println(fact(5000))

  }

  def fact(num: BigInt): BigInt = {
    require(num > 0, "num should not be negative")
    if (num == 1) 1
    else num * fact(num - 1)
  }

}
