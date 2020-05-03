//package part3
//
//import java.io.File
//
//import akka.actor.SupervisorStrategy.Stop
//import akka.actor.{Actor, ActorLogging, ActorSystem, OneForOneStrategy, Props}
//import akka.pattern.{Backoff, BackoffSupervisor}
//
//import scala.concurrent.duration._
//import scala.io.Source
//
//
//object BackOffSupervisionStrategy extends App{
//
//
//
//  object BackoffSupervisorPattern extends App {
//
//    case object ReadFile
//    class FileBasedPersistentActor extends Actor with ActorLogging {
//      var dataSource: Source = null
//
//      override def preStart(): Unit =
//        log.info("Persistent actor starting")
//
//      override def postStop(): Unit =
//        log.warning("Persistent actor has stopped")
//
//      override def preRestart(reason: Throwable, message: Option[Any]): Unit =
//        log.warning("Persistent actor restarting")
//
//      override def receive: Receive = {
//        case ReadFile =>
//          if (dataSource == null)
//            dataSource = Source.fromFile(new File("src/main/resources/testfiles/IMPORTANT.txt"))
//          log.info("I've just read some IMPORTANT data: " + dataSource.getLines().toList)
//      }
//    }
//
//    val system = ActorSystem("BackoffSupervisorDemo")
//    //  val simpleActor = system.actorOf(Props[FileBasedPersistentActor], "simpleActor")
//    //  simpleActor ! ReadFile
//
//    val simpleSupervisorProps = BackoffSupervisor.props(
//      Backoff.onFailure(
//        Props[FileBasedPersistentActor],
//        "simpleBackoffActor",
//        3 seconds, // then 6s, 12s, 24s
//        30 seconds,
//        0.2
//      )
//    )
//
//
//    val stopSupervisorProps = BackoffSupervisor.props(
//      Backoff.onStop(
//        Props[FileBasedPersistentActor],
//        "stopBackoffActor",
//        3 seconds, // min and max delays
//        30 seconds,
//        0.2   // randomness factor added so that every actor wont start on same time.
//      ).withSupervisorStrategy(
//        OneForOneStrategy() {
//          case _ => Stop
//        }
//      )
//    )
//
//    //  val stopSupervisor = system.actorOf(stopSupervisorProps, "stopSupervisor")
//    //  stopSupervisor ! ReadFile
//
//    class EagerFBPActor extends FileBasedPersistentActor {
//      override def preStart(): Unit = {
//        log.info("Eager actor starting")
//        dataSource = Source.fromFile(new File("src/main/resources/testfiles/important_data.txt"))
//      }
//    }
//
//    // ActorInitializationException => STOP
//
//    val repeatedSupervisorProps = BackoffSupervisor.props(
//      Backoff.onStop(
//        Props[EagerFBPActor],
//        "eagerActor",
//        1 second,
//        30 seconds,
//        0.1
//      )
//    )
//    val repeatedSupervisor = system.actorOf(repeatedSupervisorProps, "eagerSupervisor")
//
//
//
//
//  }
//
//}
