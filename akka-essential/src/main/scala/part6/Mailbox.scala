package part6

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.dispatch.{PriorityGenerator, UnboundedPriorityMailbox}
import com.typesafe.config.{Config, ConfigFactory}
import akka.dispatch._

object Mailbox extends App{


class SimpleActor extends Actor with ActorLogging {
  override def receive: Receive = {
    case message => log.info(message.toString)
  }
}

  /**
   * Custom priority Mailbox
   * p0 - most important
   * p1
   * p2
   * p3
   * p4
   */

  class SupportTicketPriorityMailbox(settings: ActorSystem.Settings, config: Config)
    extends UnboundedPriorityMailbox(
      PriorityGenerator {
        case message: String if message.startsWith("[P0]") => 0
        case message: String if message.startsWith("[P1]") => 1
        case message: String if message.startsWith("[P2]") => 2
        case message: String if message.startsWith("[P3]") => 3
        case _ => 4
      })

  val system = ActorSystem("MailboxDemo", ConfigFactory.load().getConfig("mailbox"))

  case object ManagementTicket


  val supportTicketLogger = system.actorOf(Props[SimpleActor])
  supportTicketLogger ! "[P3] this thing would be nice to have"
  supportTicketLogger ! "[P0] this needs to be solved NOW!"
  supportTicketLogger ! "[P1] do this when you have the time"

  val controlAwareActor = system.actorOf(Props[SimpleActor].withMailbox("control-mailbox"))
  //  controlAwareActor ! "[P0] this needs to be solved NOW!"
  //  controlAwareActor ! "[P1] do this when you have the time"
  //  controlAwareActor ! ManagementTicket

  // method #2 - using deployment config
  val altControlAwareActorss = system.actorOf(Props[SimpleActor], "altControlAwareActor")
  altControlAwareActorss ! "[P0] this needs to be solved NOW!"
  altControlAwareActorss ! "[P1] do this when you have the time"
  altControlAwareActorss ! ManagementTicket


  // control-aware mailbox (message needs to be processed before processing)
  /**
   * UnboundedControlAwareMailbox
   */

  val controlAwareActors = system.actorOf(Props[SimpleActor].withMailbox("control-mailbox"))
  // method #2 - using deployment config
  val altControlAwareActor = system.actorOf(Props[SimpleActor], "altControlAwareActor")
  altControlAwareActor ! "[P0] this needs to be solved NOW!"
  altControlAwareActor ! "[P1] do this when you have the time"
  altControlAwareActor ! ManagementTicket



}


