package scalaplayground.part5

object FBoundedPolyMorphism extends App{


  // NAIVE SOLUTION

//  trait Animal{
//    def breed:List[Animal]
//  }
//  class Dog extends Animal {
//    override def breed: List[Animal] = ???
//  }
//  class Cat extends Animal {
//    override def breed: List[Animal] = ???
//  }

// Limitation of F-BOUNDED Polymorphism is its failed after 1 level hirerchy
  // F-Bounded Polymorphism is used to put a restriction on return types of Generic classes.

  trait Animal[T <: Animal[T]]{ self: T =>
    def breed:List[Animal[T]]
  }

  class Dog extends Animal[Dog] {
    override def breed: List[Animal[Dog]] = ???
  }

  class Cat extends Animal[Cat] {
    override def breed: List[Animal[Cat]] = ???
  }









}
