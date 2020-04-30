package scalaplayground.part3

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

object FutureAndPromises extends App {


  // basic case....
  def calcluateMeaningOfLife(): Int = {
    Thread.sleep(2000)
    42
  }

  var result = Future {
    calcluateMeaningOfLife()
  }
  println(result.value) //should be empty
  result.onComplete(_ match {
    case Success(message) => println("the message is " + message)
    case Failure(exception) => println("Failure happend" + exception)
  })

  Thread.sleep(3000)
}
