package scalaplayground.part4

import scalaplayground.part4.TypeClass2.User

object TypeClassPart3 extends App {

  trait Equality[T] {
    def equal(a: T, b: T): Boolean
  }

  object Equality {
    def apply[T](a: T, b: T)(implicit instance: Equality[T]) = instance.equal(a, b)
  }


  implicit class NameEqualityRicher[T](a:T){
    def === (other:T)(implicit equality: Equality[T]):Boolean= Equality[T](a,other)
  }

  implicit object NameEquality extends Equality[User] {
    override def equal(a: User, b: User): Boolean = a.name == b.name
  }

  var john1 = User("John", 2, "a@gmail.com")
  var john2 = User("John", 2, "a@gmail.com")

  println(Equality[User](john1, john2))
  var john3 = User("Johns", 2, "a@gmail.com")

  println(john1 === john3)



  trait Serializer[T]{
    def serialize(value:T):String
  }

  object Serializer{
    def apply[T](value:T)(implicit insance:Serializer[T])= insance.serialize(value)
  }

  implicit class HTMLRicher[T](value:T){
    def serialize(implicit serializer: Serializer[T]):String=serializer.serialize(value)
  }

  implicit object HTMLSerializer extends Serializer[User] {
    override def serialize(value: User): String = s"<div> Hello my name is ${value.name} my age is ${value.age}"
  }

  println(Serializer[User](john1))
  println(john1 serialize(HTMLSerializer))
}
