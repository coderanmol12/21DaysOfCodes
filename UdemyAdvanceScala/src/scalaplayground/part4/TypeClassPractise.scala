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


}
