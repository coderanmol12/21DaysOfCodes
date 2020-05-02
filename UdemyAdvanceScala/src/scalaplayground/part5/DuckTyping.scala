package scalaplayground.part5

object DuckTyping extends App{



  // Duck typing As longs a the type on right hand side is exactly equal to the structure defined as left hand side this is called
  // Static Duck typing
  type SoundMaker ={
  def makeSound():Unit;
  }

  class Dog{
    def makeSound():Unit =println("bark")
  }
  class Cat{
    def makeSound():Unit = println("meooo")
  }

  var sound:SoundMaker= new Dog


  trait CBL[+T]{
    def head: T
    def tail: CBL[T]
  }


}
