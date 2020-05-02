package scalaplayground.part5

object AdvanceInheritance extends App{

// Diamond Inheritance Problem

  trait Animal{def name:String}
  trait Lion extends Animal {
    override def name: String = "Lion"}
  trait Tiger extends Animal{
    override def name: String = "Tiger"}
  class Mutant extends Lion with Tiger

  var p = new Mutant
  println(p.name)  // since mutant doesnt implemented name method so what should be the value of p.name??? so In diamond problem it
  // it will always picksup the value of the last override hence this will print TIGER








}
