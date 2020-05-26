import akka.Done
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Sink, Source}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future}
import scala.util.Success

object FaultTolerance extends App {

  implicit val system = ActorSystem("faultTolerance")
  implicit val materializer = ActorMaterializer()


  def tryMethod(mgs:List[String]):Future[Done] ={
    Source(mgs).map(item => {
      Future.successful(Done)
    }).async
      .runWith(Sink.foreach(e=> e))
  }
  tryMethod(List("a","b","c")).onComplete(
    {
      case Success(value) => println("the value got is"+ value)
    }
  )
}