package scala_prac

object Test1 {
  def main(args: Array[String]): Unit = {

    val result = sayHello("Ganga Reddy")
    println(result)

  }

  def sayHello(username: String) = {
    def greet(msg: String): String = {
      s"hello ${username}, ${msg}"
    }
    greet("Good morning")
  }

}
