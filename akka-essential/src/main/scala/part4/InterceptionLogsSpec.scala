package part4

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.testkit.{EventFilter, ImplicitSender, TestActorRef, TestKit}
import com.typesafe.config.ConfigFactory
import org.scalatest.{BeforeAndAfterAll, WordSpecLike}
import part4.InterceptionLogsSpec.LogActor


class InterceptionLogsSpec extends TestKit(ActorSystem("interceptingLogs",ConfigFactory.load().getConfig("interceptingLogMessages"))) with WordSpecLike with ImplicitSender with BeforeAndAfterAll{


  "A LogManager actor" should{
    "print a sent message in logs" in{
      EventFilter.info(pattern = s"hello logss", occurrences = 1) intercept {
        // our test code
        var logger = system.actorOf(Props[LogActor],"logactor")
        logger ! "hello logss"
      }
    }

    "should increment the value of count" in{
      var logger = TestActorRef[LogActor](Props[LogActor])    // accessing the state of Actor
      logger ! "hello logss"
      assert(logger.underlyingActor.count==1)
    }
  }
}


object InterceptionLogsSpec{
  class LogActor extends Actor with ActorLogging{
    var count =0
    override def receive: Receive = {
      case message:String=>{
        count+=1
        log.info(s"$message received")
        sender() ! message+"demo"
      }
    }
  }

}
