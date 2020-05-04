package part6

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}

import scala.concurrent.{ExecutionContext, Future}


/**
 * Throughput defines the number of msgs needs to be processed per actor before thread jumps to another actor.
 */
import scala.util.Random

object Dispatchers extends App{

  class Counter extends Actor with ActorLogging {
    var count = 0
    override def receive: Receive = {
      case message =>
        count += 1
        log.info(s"[$count] $message")
    }
  }

  var system = ActorSystem("dispatcher")
  val actors = for (i <- 1 to 10) yield system.actorOf(Props[Counter].withDispatcher("my-dispatcher"), s"counter_$i")
    val r = new Random()
    for (i <- 1 to 1000) {
      actors(r.nextInt(10)) ! i
    }

//  val rtjvmActor = system.actorOf(Props[Counter], "rtjvm")
//
//  /**
//   * Dispatchers implement the ExecutionContext trait
//   */
//
//  class DBActor extends Actor with ActorLogging {
//    // solution #1
//    implicit val executionContext: ExecutionContext = context.system.dispatchers.lookup("my-dispatcher")
//    // solution #2 - use Router
//
//    override def receive: Receive = {
//      case message => Future {
//        // wait on a resource
//        Thread.sleep(5000)
//        log.info(s"Success: $message")
//      }
//    }
//  }
//
//  val dbActor = system.actorOf(Props[DBActor])
//  //  dbActor ! "the meaning of life is 42"
//
//  val nonblockingActor = system.actorOf(Props[Counter])
//  for (i <- 1 to 1000) {
//    val message = s"important message $i"
//    dbActor ! message
//    nonblockingActor ! message
//  }
}