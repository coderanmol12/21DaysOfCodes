package scalaplayground.part2

object PartialsFunction extends App{

  var aFussyFunction =(x:Int)=>(y:Int)=> x match {
    case 1 => 42
    case 2 => 54
    case 3 => 999
    case _ => throw new NoSuchElementException
  }
  //print(aFussyFunction(1)(20))


  /**
   * A partialFunction Example which internally works on pattern matching scala...
   * A PartialFunction can have only parameter type
   */
  var aPartialFunction:PartialFunction[Any,String]={
    case x if(x.isInstanceOf[String]) => "Hi its a string"
    case x if(x.isInstanceOf[Int]) => "Hi its a Int"
    case x if(x.isInstanceOf[List[String]]) => "Hi its a list of a string"
  }
  println("This shoudl return a int type "+aPartialFunction(12))
  var aNicerPartialFunction = aPartialFunction.lift
  println("This should return a None"+ aNicerPartialFunction(("John Deo",23)))


  /**
   * Exercise
   * A manual PartialFunction
   * - Dumb Chatbot
   */

  //Implementing a Dumb Chatbot
  var aBotParitalFunction: PartialFunction[String,String]={
    case "hii" => "Hello How are you??"
    case "Bye" => "Bye, See you tomorrow"
    case _ => "Not Understood!!!!"
  }
  //scala.io.Source.stdin.getLines().foreach(line => println("Bot: "+aBotParitalFunction(line)))

  // Implementing A manual Partial Functions Q2

  val aManualPartialFunction = new PartialFunction[Int,Int] {
    override def isDefinedAt(x: Int): Boolean = (x == 1) || (x == 2)

    override def apply(v1: Int): Int = v1 match {
      case 1 => 100
      case 2 => 200
      case _ => -1
    }
  }


  var dummy:PartialFunction[Any, Unit]={
    case value:Int => println("the value provided is int: "+value )
    case value:String=> println("the value provided is string"+value)
    case _ => println("Undefined value provided")
  }

  println(dummy(10))



}
