package scalaplayground.part3

import scala.concurrent.{Future, Promise}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}


object RevisionFuturePromises extends App{


  def sayHello(langCode:String):Future[String]= Future{
    Thread.sleep(3000)
    s"Hello in language code $langCode"
  }


  sayHello("EN").onComplete{
    case Success(value) => println(value)
    case Failure(exception) => exception.printStackTrace()
  }
  Thread.sleep(5000)

  var promise = Promise[Int]
  var future = promise.future

  future.onComplete{
    case Success(value)=> println(value)
  }

  var producer = new Thread(new Runnable {
    override def run(): Unit =
      Thread.sleep(2000)
      promise.success(42)
  })



  Thread.sleep(4000)













}
