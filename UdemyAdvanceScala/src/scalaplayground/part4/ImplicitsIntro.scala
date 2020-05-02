package scalaplayground.part4

object ImplicitsIntro extends App {


  // EXAMPLE 1
  case class Person(name: String) {

    def greet(): String = {
      s"Hello my name is $name"
    }
  }

  implicit def fromStringToPerson(name: String): Person = Person(name)

  println("John Deo".greet())


  // EXAMPLE 2


  implicit var defaultAmount:Int=20
  def addAmount(x: Int)(implicit amount: Int): Unit = {
     println(x+amount)
  }


  // The magic of implicit functions.
  addAmount(30)(30)
}