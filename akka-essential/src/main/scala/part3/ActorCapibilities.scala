package part3

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

object ActorCapibilities extends App{


  var actorSystem = ActorSystem("SimpleActorSystem")
  case class ForwardMessages(ref: ActorRef, messages: Int)
  class SimpleActor extends Actor{
    override def receive: Receive = {
      case message:String => println(s"$message got")
      case ForwardMessages(ref,msg) => {
        println(s"recieved int value $msg")
        context.self forward msg.toString
      }
    }
  }

  var simpleActor = actorSystem.actorOf(Props[SimpleActor],"actor")
  simpleActor ! ForwardMessages(simpleActor, 42)


  // Properties of message
  // - messages must be serializable
  // - message must be immutable


  // Replying Back to actor

  case class Money(amount:Int)
  class Bank extends  Actor{
    override def receive: Receive = {
      case Money(amount) => sender() ! s"$amount money deposited"
    }
  }
  case class DepositMoney(ref: ActorRef, messages: Int)

  class Person extends Actor{
    override def receive: Receive = {
      case DepositMoney(ref, msg) => ref ! Money(msg)
      case message: String => println(s"$message")
    }
  }

  var person = actorSystem.actorOf(Props[Person],"person")
  var bank = actorSystem.actorOf(Props[Bank],"bank")
  person ! DepositMoney(bank,5000)


}


