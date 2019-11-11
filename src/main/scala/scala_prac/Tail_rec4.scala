package scala_prac

object Tail_rec4 {
  def main(args: Array[String]): Unit = {

    println(fibo(40))
    println(fibotail(1,40))

  }

  // this is recursion only
  def fibo(n: Int): Int = {
    //require(n > 0, "n should be positive")
    if (n > 1) fibo(n - 1) + fibo(n - 2)
    else n
  }

  // this is recursion only
  def fibotail(acc: Int, n: Int): Int = {
    if (n > 1) fibotail(acc + 1,(n - 1)) + fibotail(acc + 1,(n - 2))
    else acc
  }



}
