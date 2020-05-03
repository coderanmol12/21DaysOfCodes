package part3

import akka.actor.{Actor, ActorSystem, Props}

object AkkaBehaviour extends App{

  var actorSystem = ActorSystem("MyFirstActorSystem")
  println(actorSystem.name)

  class WordCounter extends Actor {
    override def receive: Receive = {
      case message:String=> println(s"$message received")
      case _ => "Unexpected error occurred"
    }}

  var wordActor = actorSystem.actorOf(Props[WordCounter],"wordactor")
  wordActor ! "hii i am sending my first message"
  }

