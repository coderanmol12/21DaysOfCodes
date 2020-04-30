package scalaplayground.part3

import scala.concurrent.Promise
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Success

object FuturePromise3 extends App{

  var promise = Promise[Int]()
  var future = promise.future


  // Callback Function
  future.onComplete(_ match {
    case Success(value) => println("the value got is"+ value)
  })

  var producer = new Thread(new Runnable {
    override def run(): Unit = {
      Thread.sleep(500)
      promise.success(42)
    }
  })

  producer.start()
  Thread.sleep(1000)







}
