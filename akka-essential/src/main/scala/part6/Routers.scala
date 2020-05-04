package part6

import akka.actor.{Actor, ActorLogging, ActorSystem, Props, Terminated}
import akka.routing.{ActorRefRoutee, Broadcast, FromConfig, RoundRobinPool, RoundRobinRoutingLogic, Router}
import com.typesafe.config.ConfigFactory

object Routers extends App {


  /** ___Types Of Routers___:
   *
   * Round-Robin: seen that cycle is between beauty's.
   *
   * Random: which is not very smart.
   *
   * Smallest Mailbox: as a load balancing your mistake, It always sends the next message to the actor with the fewest messages in the queue.
   *
   * Broadcast: as a redundancy measure.This sends the same message to all the routines.
   *
   * Scatter gather first: for example broadcasts so sends to everyone and waits for the first reply and all the next replies are discarded.
   *
   * Tail chopping: which forwards the next message to each actor sequentially until the first reply was received and all the other replies are discarded.
   *
   * Consistent hashing:  in which all the messages with the same hash get to the same actor
   *
   * _________________ DIFF BW GROUP ROUTERS VS POOL ROUTERS___________________
   *
   *  When a routee terminates within a pool, the router detects this and removes the routee from the pool. A group router doesn't support this. When a routee terminates, the group router will still send messages to the routee.
   */
  class Master extends Actor {
    var slaves = for (elem <- (1 to 5)) yield {
      val slave = context.actorOf(Props[Slaves], s"slave_$elem")
      context.watch(slave)
      ActorRefRoutee(slave)
    }

    private val router = Router(RoundRobinRoutingLogic(), slaves)

    override def receive: Receive = {

      case Terminated(ref) => {
        router.removeRoutee(ref)
        val newSlave = context.actorOf(Props[Slaves])
        context.watch(newSlave)
        router.addRoutee(newSlave)
      }

      case message => router.route(message, sender())


    }
  }

  val system = ActorSystem ("routerDemo",ConfigFactory.load().getConfig("routersDemo"))
 // val master = system.actorOf (Props[Master], "master")

//  for (i <- 1 to 10) {
//    master ! s"Hello World $i"
//  }

  // 2.2 from configuration
  val poolMaster2 = system.actorOf(FromConfig.props(Props[Slaves]), "poolMaster2")
//    for (i <- 1 to 10) {
//      poolMaster2 ! s"[$i] Hello from the world"
//    }

  val slaveList = (1 to 5).map(i => system.actorOf(Props[Slaves], s"slave_$i")).toList


  val groupMaster2 = system.actorOf(FromConfig.props(), "groupMaster2")   // need to create slavesList first.
//  for (i <- 1 to 10) {
//    groupMaster2 ! s"[$i] Hello from the world"
//  }


  groupMaster2 ! Broadcast("hello, everyone")


}

class Slaves extends Actor with ActorLogging {
  override def receive: Receive = {
    case message: String => log.info("Slave request received")
  }
}


