package scala_prac

import scala.annotation.tailrec

object Tail_rec {
  def main(args: Array[String]): Unit = {

    val names = "vamshikrishna,saikrishna,narahari,prakash,aravind"
    splitNew(names, ",").foreach(println)

  }

  def splitNew(xs: String, del: String): Array[String] = {
    var arr = Array.empty[String]
    @tailrec
    def split(s: String, del: String): Array[String] = {
      if (!s.contains(del)) {
        arr :+= s
        arr
      }
      else {
        val s2 = s.substring(0, s.indexOf(del))
        arr :+= s2
        split(s.drop(s.indexOf(del) + 1), del)
      }
    }
    split(xs, del)
  }

}
