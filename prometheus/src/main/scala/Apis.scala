import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import kamon.Kamon
import kamon.prometheus.PrometheusReporter
import kamon.system.SystemMetrics

import scala.concurrent.ExecutionContext
import scala.io.StdIn

object Apis extends App {

  val host = "0.0.0.0"
  val port = "9000"

  Kamon.addReporter(new PrometheusReporter())
  implicit val system: ActorSystem = ActorSystem("helloworld")
  implicit val executor: ExecutionContext = system.dispatcher
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  def route = path("hello") {  get {
    Metrics.counter("H101_W")
    println("hello")
    complete("Hello, Worlds!") }

  }
  val bindingFuture = Http().bindAndHandle(route, "localhost", 9100)

  println(s"Server online at http://localhost:9100/\nPress RETURN to stop...")
  StdIn.readLine() // let it run until user presses return
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => system.terminate()) // and shutdown when done
}