package part3

import akka.actor.{Actor, ActorLogging, ActorSystem, PoisonPill, Props}

object ActorLifecycle extends App {


  /**
   * 5 Lifecycle of Actors
   * 1 start  : Creates a new actor with new actor path.
   * 2 suspended  : actorRef will enqueue but will not process any messages.
   * 3 resume  :  actorRef will process the enqueue messages
   * 4 restarted  :  1: suspend 2: swapping the actor instances(OLD INSTANCE CALLED PreStart, replace actor instances and new instances called PostRestart) 3: resume
   * 5 stopped : calling postStop() lifecycle method and then all Terminated(ref)
   */


  // IF A ACTOR THROW EXCEPTION IT WILL GET RESTARTED, DEFAULT SUPERVISON STRATEGY

  class LifeCycleActor extends Actor with ActorLogging {

    override def preStart(): Unit = log.info("In a pre start Lifecycle method")

    override def postStop(): Unit = log.info("In a post stop lifecycle method")

    override def receive: Receive = {
      case message: String => log.info("actor recieved message")
    }
  }


  var actorSystem = ActorSystem("lifecycle")
  var parent = actorSystem.actorOf(Props[LifeCycleActor], "lifeCycleActor")
  parent ! "hello"
  parent ! PoisonPill


  object FAIL
  object CHECK

  class Child extends Actor with ActorLogging {

    override def preStart(): Unit = log.info("supervised In a pre start Lifecycle method")

    override def postStop(): Unit = log.info("supervised In a post stop lifecycle method")

    override def preRestart(reason: Throwable, message: Option[Any]): Unit = log.info("supervised actor starting ")

    override def postRestart(reason: Throwable): Unit = {

      log.info("supervised actor post restarted")
    }


    override def receive: Receive = {

      case FAIL => {
        log.warning("child will fail now")
        throw new RuntimeException("I failed")
      }
      case CHECK=> log.info("aliving and kicking")
      }
  }
  object FailChild
  object CheckChild

  class Parent extends Actor {
    private val child = context.actorOf(Props[Child], "supervisedChild")

    override def receive: Receive = {
      case FailChild => child ! FAIL
      case CheckChild => child ! CHECK
    }
  }
}
