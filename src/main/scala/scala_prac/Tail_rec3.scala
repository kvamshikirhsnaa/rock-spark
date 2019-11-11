package scala_prac

import scala.annotation.tailrec


object Tail_rec3 {
  def main(args: Array[String]): Unit = {

    println(fact(5))
    println(fact(50))

    println(fact2(50))
  }
  def fact(num: BigInt): BigInt = {
    require (num > 0, "num should not be negative")
    facttail (1, num)
  }

  @tailrec
  private def facttail (acc: BigInt, num: BigInt): BigInt = {
    if (num == 1) acc
    else facttail (acc * num, num - 1)
  }


// also can do like this
  def fact2(num: BigInt): BigInt = {
    require (num > 0, "num should not be negative")
    @tailrec
    def facttail (acc: BigInt, num: BigInt): BigInt = {
      if (num == 1) acc
      else facttail (acc * num, num - 1)
    }
    facttail (1, num)
  }





}








//converting recursion to tail recursion rules:
//---------------------------------------------
//-> keep the original method(recursion) signature same
//-> create a second method by
//-> copying original method
//-> giving it a new name
//-> make it as a private
//-> giving it a new accumulator input parameter, and
//-> adding @tailrec annotation to it
//-> modify second method algorithm so it uses new accumulator
//-> call the second function from inside the first function,
//when we do this, give second method's accumulator parameter an initial value,
//for sum it should be 0, for multiply it should be 1, ...etc

