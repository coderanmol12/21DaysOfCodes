package scalaplayground.part4

object TypeClassPractise extends App{

  case class User(name:String)
   // TYPE CLASS
  trait Reader[T]{
    def render(value:T):String
  }

  object Reader{
    def apply[T](value:T)(implicit instance:Reader[T]):String= instance.render(value)
  }

  implicit class Render[T](value:T){
    def render(implicit reader: Reader[T]):String= Reader[T](value)
  }


   implicit object JsonRender extends Reader[User] {
    override def render(value: User): String = s"{" +
      s"user.name= ${value.name} }"
  }


  var john = User("John")
  println(john render)






  trait Anchor[T]{
    def anchors(value:T):String
  }

  object Anchor{
    def apply[T](value:T)(implicit instance:Anchor[T])= instance.anchors(value)
  }

  implicit object FemaleAnchor extends Anchor[User] {
    override def anchors(value: User): String = s"Female is anchoring the show and the award goes to ${value.name}"
  }

  implicit class FemaleAnchors[T](value:T){
    def host(implicit anchor: Anchor[T]):String=Anchor[T](value)
  }




  println("+++++")
  println(john host)




  // Sampple Example

  implicit class Doctor(clinincName:String){

    def printName(implicit isAyurvedic:Boolean): Unit ={
      println("the clicname is & is Ayurvedic"+ clinincName+ "   ::"+ isAyurvedic)
    }
  }





  import ISAYURVEDIC._
  "clinicName" printName

  object ISAYURVEDIC{
    implicit var defaultVae:Boolean= false
  }



  // Pimp Library practise

  implicit  class IntRicher(x:Int){
    def isEven: Boolean= x%2==0
    def isOdd: Boolean = x%2!=0
  }
  println(2 isEven)
  println(2 isOdd)



  // Type class problem

  trait Serializer[T]{
    def serialize(value:T):String
  }

  object Serializer{
    def apply[T](value:T)(implicit instance: Serializer[T])= instance.serialize(value)
  }

  implicit class HTMLSerializer[T](value:T){
    def doIt(implicit serializer: Serializer[T]):String= Serializer[T](value)
  }

  implicit object HtmlRender extends Serializer[User]{
    override def serialize(value: User): String = "html render done"
  }

  println(john doIt)




}
