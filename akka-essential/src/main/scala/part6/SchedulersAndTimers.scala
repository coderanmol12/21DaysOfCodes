package part6

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}

import scala.concurrent.duration._

object SchedulersAndTimers extends App{

  class SimpleActor extends Actor with ActorLogging {
    override def receive: Receive = {
      case message:String => log.info("Remained logged")
    }
  }
  var system = ActorSystem("timers")
  var simple = system.actorOf(Props[SimpleActor],"simpleActor")
  system.scheduler.schedule(1 seconds, 2 seconds){
    simple ! "hello"
  }(system.dispatcher)
}