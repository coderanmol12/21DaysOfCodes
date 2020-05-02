package scalaplayground.part4

object PimpMyLibrary extends App {

  // objective is 2.isPrime


  implicit class RicherInt(value:Int){
    def isEven:Boolean= value%2==0
    def getSqrt:Double= Math.sqrt(value)

  }

  println(42.isEven)
  println(43.getSqrt)// see magic

  class Person(name:String){
    def likes (name2:String):String= s"$name likes $name2"
  }
  var obj = new Person("Anmol")
  println(obj likes "Scala")
}
