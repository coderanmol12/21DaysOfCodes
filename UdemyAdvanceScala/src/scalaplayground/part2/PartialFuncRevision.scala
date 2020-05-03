package scalaplayground.part2

object PartialFuncRevision extends App{

  case class Person(name:String, age:Int)


   var matcher:PartialFunction[Person,Unit]={
    case Person(something,22) => println(s"the age provided i $something")
    case person:Person=> println(s" the name provided is ${person.age} and ${person.name}")
    case _ => println("nothing is matched")
  }

   var john = new Person("John",22)
  matcher(john)


  var superAdder= (x:Int)=>(y:Int)=> x+y


  def curriedAdder(x:Int)(y:Int):Int = x+y

  var j:Int=> Int= curriedAdder(4)  // Lifting OR ETA-Expension
  var obj1= curriedAdder(10) _
  println(j(20))

  def add(x:Int,y:Int)=x+y
 var demo= add(10,_:Int)
  println(demo(20))

}
