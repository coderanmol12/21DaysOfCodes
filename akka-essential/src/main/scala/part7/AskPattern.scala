package part7

import scala.util.{Try, Success, Failure}

import akka.actor.{Actor, ActorSystem, Props}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import akka.pattern.{ask, pipe}
import akka.util.Timeout

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext
import scala.util.Try


object AskPattern extends App{




  class DummyActor extends Actor{
    implicit val executionContext: ExecutionContext = context.dispatcher
    override def receive: Receive = {
      case message: String => {
        var responseFuture = Future{Thread.sleep(3000)
          "Helllo i am from future"
        }
        responseFuture.pipeTo(sender())
      }
    }
  }
  implicit val timeout: Timeout = Timeout(5 second)

  var system = ActorSystem("ask")
  var dummy = system.actorOf(Props[DummyActor],"dummy")
  var future = dummy ? "hello"
  println("the future is"+ future)
  future.onComplete {
    case Success(result) => println("the result is"+ result)
    case Failure(failure) => println("the failure is"+failure)
  }
}
