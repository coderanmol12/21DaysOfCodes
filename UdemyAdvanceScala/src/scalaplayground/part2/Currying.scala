package scalaplayground.part2

object Currying extends App {

  //Basic Curry Functions
  def curryFunc: (Int) => (Int) => Int = (x) => (y) => x+y
  println("The result of curry function is "+curryFunc(3)(4))

  /**
   * Transforming a Function to method called Lifting
   */

  /**
   * ETA-EXPANSION OR LIFTING(wrapping a function to extra layer by preserving identity)
   */
  var curryObj=curryFunc(3)
  var liftCurry: Int => Int = curryObj
  println("The result of LiftCurry function is "+liftCurry(4))


  /**
   * Exercise
   */

  var simpleAddFunction = (x:Int, y:Int) => x+y
  def simpleAddMethod(x:Int, y:Int):Int = x+y
  def simpleCurriedFunction(x:Int)(y:Int):Int ={x+y}
  var add7: Int => Int = simpleCurriedFunction(3)
  println("the curried version of Add7: "+add7(4))
//
//  var add7_1 = simpleAddFunction.curried(7)
//  var add7_2 = (x:Int) => simpleAddMethod(7,_)
//  var add7_3 = (x:Int)=> simpleAddMethod(7,x)
//  var add7_4 = simpleCurriedFunction(7,_:Int)


  /**
   * Exercise
   *  - Process a List of number and return their string representation with different formats
   *   use %4.2f, %8.6f, %14.12f with a curried formatter functions
   *
   */


  def curriedFormatter(formatter:String)(number:Double):String = {
    formatter.format(number)
  }

  var doubleList:List[Double]= List(Math.PI,Math.E)
  var withFormat = curriedFormatter("%4.20f") _
  print(doubleList.map(x=> withFormat(x)))


  // Call By Name and Call by Method


  def name(str:String):Unit ={
    print(str)
  }

  def byMethod(f: (String) => String, str:String):Unit={
    println("the doubler method worked "+f(str))
  }


  def byName(f: => String):Unit={
    println(f)
  }

  def doubler(str:String):String= str.concat(str)
  byMethod(doubler,"abc")
  byName("calling by name"+doubler("abc"))
}
