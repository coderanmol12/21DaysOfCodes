package scalaplayground.part4



object OrganiseImplicit  extends App{

  // sorted is by default taking implicit ordering in ascending order.

///  implicit val reverseOrdering:Ordering[Int]= Ordering.fromLessThan(_>_)
  println(List(1,4,5,11,10).sorted)

  //so overwritten the implicit value.
  //Implicit can only be applied to
  //   -val/var
  //   object
  //   defs with no parenthesis

  // Exercise
  case class Person(name:String, age:Int)

  val persons = List(Person("John",20),Person("Ammy",40),Person("Demo",66))
//  implicit val alphabeticOrdering:Ordering[Person] = Ordering.fromLessThan(_.age>_.age)
  import AgeOrdering._
  println(persons.sorted)



  // Good implicit value can be define inside companion objects.
  object  AgeOrdering{
    implicit val ageOrdering:Ordering[Person] = Ordering.fromLessThan(_.age>_.age)
  }

  //Scope
  // -LOCAL
  // - Imported
  //  - Companion Object



}
