package part7

import akka.actor.{Actor, ActorLogging, ActorSystem, Props, Stash}

object StashDemo extends App{

  class ResourceActor extends Actor with ActorLogging with Stash {
    private var innerData: String = ""

    override def receive: Receive = closed

    def closed: Receive = {
      case Open =>
        log.info("Open Resource")
        unstashAll()
        context.become(open)
      case message =>
        log.info(s"Stashing $message because I can't handle it in the closed state")
        // step 2 - stash away what you can't handle
        stash()
    }

    def open: Receive = {
      case Read =>
        // do some actual computation
        log.info(s"I have read $innerData")
      case Write(data) =>
        log.debug(s"writing $data")
        innerData = data
      case Close =>
        log.info("Closing resources")
        unstashAll()
        context.become(closed)
      case message =>
        log.info(s"Stashing $message because I can't handle it in open state")
        stash()
    }

  }
  case object Open
  case object Close
  case object Read
  case class Write(data: String)
  val system = ActorSystem("StashDemos")
  val resourceActor = system.actorOf(Props[ResourceActor])
  resourceActor ! Read
  resourceActor ! Open
  resourceActor ! Open
  resourceActor ! Write("I love stashing")
  resourceActor ! Close
  resourceActor ! Read
}

