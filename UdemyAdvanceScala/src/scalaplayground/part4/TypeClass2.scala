package scalaplayground.part4

object TypeClass2 extends App {


  case class User(name: String, age: Int, email: String)

  trait Serializer[T, R] {
    def serialize(value: T): R
  }

  object UserSerialzier extends Serializer[User, String] {
    override def serialize(value: User): String = s"<div> ${value.name} is ${value.age} yo and email is ${value.email}"
  }

  var john = User("John", 20, "john123@gmail.com")
  println(UserSerialzier.serialize(john))

  // PART 2   TYPE CLASSES ARE AWESOME

  class HTMLSerializer {
    def serialize[T](value: T)(implicit serializer: HTMLSerializer): String = serializer.serialize(value)
  }

  implicit object IntSerializer extends HTMLSerializer {
    override def serialize[T](value: T)(implicit serializer: HTMLSerializer): String = s"<div> ${value}"
  }


  println(new HTMLSerializer().serialize(42))


  // part2 exercise

  trait Equality[T] {
    def equal(a: T, b: T): Boolean
  }

  object Equality {
    def apply[T](a: T, b: T)(implicit instance: Equality[T]) = instance.equal(a, b)
  }

  implicit object AgeEquality extends Equality[User] {
    override def equal(a: User, b: User): Boolean = a.name == b.name
  }

  var john1 = User("John", 21, "a@gmail.com")
  var john2 = User("John", 21, "a@gmail.com")

  println(Equality[User](john2, john1))


  class Hello {
    def sayHello(name: String)(implicit languageCode: String): String = {
      s"Hii my name is ${name} in language ${languageCode}"
    }
  }


  object Hello {
    implicit val defaultLanguageCode = "english"
  }

  import Hello._
  println(new Hello().sayHello("Anmol"))

  // Defining My Type Class
  trait Converter[F,T]{
    def convert(value: F): T
  }

  object Converter{
    def apply[F,T](value:F)(implicit instance: Converter[F,T]):T= instance.convert(value)
  }


  implicit object StringConverter extends Converter[Int,String] {
    override def convert(value: Int): String = value+"converted to string"
  }


  println(Converter[Int,String](42))


}