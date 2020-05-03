package part3

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Kill, PoisonPill, Props, Terminated}


  /**
   * Kill Vs PoisonPill
   * PoisonPill is a method that you pass to actor to stop processing when the actor is running
   * Kill will also throw ActorKilledException
   * kill will terminate the actor and will remove the actor in the actor Path and replace the entire actor with a new actor.
   *
   *context.watchChild() register actor to parent, so when the child actor dies parent actor will recieve special actor message with
   * actorRef in a receiver methods.
   *
   */



  object StartingStoppingActors extends App {

    val system = ActorSystem("StoppingActorsDemo")

    object Parent {
      case class StartChild(name: String)
      case class StopChild(name: String)
      case object Stop
    }

    class Parent extends Actor with ActorLogging {
      import Parent._

      override def receive: Receive = withChildren(Map())

      def withChildren(children: Map[String, ActorRef]): Receive = {
        case StartChild(name) =>
          log.info(s"Starting child actor with name $name")
          children + (name -> context.actorOf(Props[Child],name))
          context.become(withChildren(children))
        case StopChild(name) =>
          log.info(s"Stopping child with the name $name")
          val childOption = children.get(name)
          childOption.foreach(childRef => context.stop(childRef))
        case Stop =>
          log.info("Stopping mySelf")
          context.stop(self)
        case message =>
          log.info(message.toString)
      }
    }

    class Child extends Actor with ActorLogging {
      override def receive: Receive = {
        case message => log.info(message.toString)
      }
    }

    /**
     * method #1 - using context.stop (stop child actor recursively, and then stop itself)
     */
    import Parent._
    val parent = system.actorOf(Props[Parent],"Parent")
    parent ! StartChild("1child")
    var child1Ref = system.actorSelection("/user/Parent/1child")
    child1Ref ! "Hello first child"

    /**
     * method #2 - using special messages
     */
    //  val looseActor = system.actorOf(Props[Child])
    //  looseActor ! "hello, loose actor"
    //  looseActor ! PoisonPill
    //  looseActor ! "loose actor, are you still there?"
    //
    //  val abruptlyTerminatedActor = system.actorOf(Props[Child])
    //  abruptlyTerminatedActor ! "you are about to be terminated"
    //  abruptlyTerminatedActor ! Kill
    //  abruptlyTerminatedActor ! "you have been terminated"

    /**
     *  Death watch
     */
    class Watcher extends Actor with ActorLogging {
      import Parent._

      override def receive: Receive = {
        case StartChild(name) =>
          val child = context.actorOf(Props[Child], name)
          log.info(s"Started and watching child $name")
          context.watch(child)
        case Terminated(ref) =>
          log.info(s"the reference that I'm watching $ref has been stopped")
      }
    }

    val watcher = system.actorOf(Props[Watcher], "watcher")
    watcher ! StartChild("watchedChild")
    val watchedChild = system.actorSelection("/user/watcher/watchedChild")
    Thread.sleep(500)
    watchedChild ! PoisonPill
  }