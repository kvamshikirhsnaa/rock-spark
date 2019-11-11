package scala_new

abstract class Hotel {
  def bookingPrice: Int
  def facilities: List[String]
  def availability: Int
  def book(noOfRooms: Int)
}

class Standard extends Hotel {

  private var _availability = 20
  def bookingPrice = 1000
  def facilities = List("Bed", "Chair", "Tv", "Table", "Fan")
  def availability = _availability
  def book(noOfRooms: Int) = {
    require(_availability - noOfRooms >= 0, "Rooms not available")
    _availability = _availability - noOfRooms
  }
}

class Deluxe extends Hotel {

  private var _availability = 10
  def bookingPrice = 5000
  def facilities = List("QueenBed", "Chair", "LED Tv", "Table", "AC")
  def availability = _availability
  def book(noOfRooms: Int) = {
    require(_availability - noOfRooms >= 0, "Rooms not available")
    _availability = _availability - noOfRooms
  }
}

class SuperDeluxe extends Hotel {

  private var _availability = 5
  def bookingPrice = 10000
  def facilities = List("KingBed", "Sofa", "Chair", "Smart Tv", "Dining Table", "Table", "AC")
  def availability = _availability
  def book(noOfRooms: Int) = {
    require(_availability - noOfRooms >= 0, "Rooms not available")
    _availability = _availability - noOfRooms
  }
}


object Hotel {
  def main(args: Array[String]): Unit = {

    val s = Hotel(0)

    println(s.bookingPrice)
    println(s.availability)
    println(s.facilities)
    s.book(2)
    println(s.availability)
    s.book(18)
    println(s.availability)
    s.book(1)
  }

  val STANDARD = 0
  val DELUXE = 1
  val SUPER_DELUXE = 2

  def apply(roomType: Int): Hotel = {
    roomType match {
      case SUPER_DELUXE => new SuperDeluxe
      case DELUXE => new Deluxe
      case STANDARD => new Standard
    }
  }
}