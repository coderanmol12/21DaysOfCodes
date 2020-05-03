package part3

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import part3.ChildActor.Parent.{CreateChild, TellChild}

object ChildActor extends App{

  // Objective : actor can create actor (childActors)

  object Parent{
    case class CreateChild(name:String)
    case class TellChild(message:String)
  }


  class Parent extends Actor{
    import Parent._
    override def receive: Receive = {
      case CreateChild(name) => {
        println(s"child actor will be created with name $name")
        var childRef=context.actorOf(Props[ChildActor], name)
        context.become(withChildRef(childRef),false)
      }
    }

    def withChildRef(childRef:ActorRef):Receive={
      case TellChild(message) => childRef forward message
    }
  }

  class ChildActor extends  Actor{
    override def receive: Receive = {
      case message:String => println(s"i got message $message")
    }
  }


  var actorSystem = ActorSystem("childActor")
  var parent = actorSystem.actorOf(Props[Parent],"parent")
  parent ! CreateChild("child1")
  parent ! TellChild("child created")


  // If parent owns child then who owns parent ???
  // TOP-LEVEL GUARDIAN ACTOR
  //  - SYSTEM (SYSTEM - LEVEL GUARDIAN)
  //  - USER (USER-LEVEL GUARDIAN)
  //  - /(ROOT LEVEL GUARDIAN)

}
