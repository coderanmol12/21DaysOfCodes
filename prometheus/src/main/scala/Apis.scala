import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import kamon.Kamon
import kamon.prometheus.PrometheusReporter

import scala.concurrent.ExecutionContext
import scala.io.StdIn


/**
 * @author Anmol Gupta
 */
object Apis extends App {

  val host = "localhost"
  val port = "9100"

  Kamon.addReporter(new PrometheusReporter())
  implicit val system: ActorSystem = ActorSystem("metricsystem")
  implicit val executor: ExecutionContext = system.dispatcher
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  def route = path("push"/ "metrics") {  get {
    Kamon.counter("DATA_INGESTED").increment()   // Metrics
    complete("Hello, Worlds!") }

  }
  val bindingFuture = Http().bindAndHandle(route, host, port)

  println(s"Server online at http://localhost:9100/\nPress RETURN to stop...")
  StdIn.readLine() // let it run until user presses return
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => system.terminate()) // and shutdown when done
}