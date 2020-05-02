package scalaplayground.part1

object PatternMatching extends App{

  case class Or [A,B](a:A,b:B)
  var either =new Or[Int,String](2,"two")
  var result = either match {
    case int Or string => println(s"$int is written as $string")
  }



}
