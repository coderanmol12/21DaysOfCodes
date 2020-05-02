package scalaplayground.part4

object TypeClass1 extends App {


  case class User(name: String, age: Int, email: String)

  trait Serializer[T,R] {
    def serialize(value: T):R
  }

  object UserSerialzier extends Serializer[User,String] {
    override def serialize(value: User): String = s"<div> ${value.name} is ${value.age} yo and email is ${value.email}"
  }
  var john = User("John",20,"john123@gmail.com")
  println(UserSerialzier.serialize(john))


  // Exercise

  trait Equal[T]{
    def action(a:T,b:T):Boolean
  }

  object NameEqual extends Equal[User] {
    override def action(value: User, user:User): Boolean = value.name == user.name
  }




}
