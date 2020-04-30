package scalaplayground.part3

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

object FutureAndPromises extends App {


  /**
   * Future is a placeholder of the actual result
   * Promise is a value which futures refers for actual result.
   *
   * diff bw parallel and concurrent
   * A system is said to be concurrent if it can support two or more actions **in progress** at the same time.
   * A system is said to be parallel if it can support two or more actions executing simultaneously.
   * @return
   */

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
