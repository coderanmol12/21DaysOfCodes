package part4

import akka.actor.{Actor, ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.{BeforeAndAfterAll, WordSpecLike}
import part4.BasicSpec.{Blackhole, LabTestActor, SimpleActor}

import scala.concurrent.duration._
import scala.util.Random

class BasicSpec extends TestKit(ActorSystem("basicSpec")) with ImplicitSender with WordSpecLike with BeforeAndAfterAll
{

  override def afterAll(): Unit = {
    TestKit.shutdownActorSystem(system)
  }

  "A simple actor" should {
    "return the same message" in{
      var simple = system.actorOf(Props[SimpleActor])
      simple ! "Hello Actor"
      expectMsg("Hello Actor")
    }
  }

  "A black Hole Actor" should{
    "send back some message" in{
      var bloackHole = system.actorOf(Props[Blackhole],"blackHole")
      bloackHole ! "Hello Test"
      expectNoMsg(2 second)
    }

  }

  "A Lab Test Actor" should{
    var labActor = system.actorOf(Props[LabTestActor],"lab")
    "convert the string into uppercase" in{
      var msg = "I love Akka"
      labActor ! msg
      val reply = expectMsgType[String]
      assert(reply == "I LOVE AKKA")

    }

    "reply to a greeting" in {
      labActor! "greeting"
      expectMsgAnyOf("hi", "hello")
    }

    "reply with favorite tech" in {
      labActor ! "favoriteTech"
      expectMsgAllOf("Scala", "Akka")
    }
    "reply with cool tech in a different way" in {
      labActor ! "favoriteTech"
      val messages = receiveN(2) // Seq[Any]
      // free to do more complicated assertions
    }
    "reply with cool tech in a fancy way" in {
      labActor ! "favoriteTech"

      expectMsgPF() {   //PF -> PARTIAL FUNCTION
        case "Scala" => // only care that the PF is defined
        case "Akka" =>
      }
    }
  }





}
object BasicSpec {

  class SimpleActor extends Actor {
    override def receive: Receive = {
      case message => sender() ! message
    }
  }


  // Actor which never reply
  class Blackhole extends Actor {
    override def receive: Receive = Actor.emptyBehavior
  }

  class LabTestActor extends Actor {
    val random = new Random()

    override def receive: Receive = {
      case "greeting" =>
        if (random.nextBoolean()) sender() ! "hi" else sender() ! "hello"
      case "favoriteTech" =>
        sender() ! "Scala"
        sender() ! "Akka"
      case message: String => sender() ! message.toUpperCase()
    }
  }
}
