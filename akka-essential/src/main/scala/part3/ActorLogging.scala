package part3

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}

object ActorLogging extends App{


  class LoggerActor extends Actor with ActorLogging{
    override def receive: Receive = {
      case message:String => log.info(s"i got message $message")
    }
  }



  var actorSystem = ActorSystem("logger")
  var logger = actorSystem.actorOf(Props[LoggerActor],"actorlogger")
  logger ! "check my info level logs"

}
