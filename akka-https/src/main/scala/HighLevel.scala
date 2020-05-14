import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.actor._
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.routing.RouterEnvelope

object HighLevel extends App{


  implicit val system = ActorSystem("highLevelIntro")
  implicit val materializer = ActorMaterializer()
  import system.dispatcher

  import akka.http.scaladsl.server.Directives._

  var routes:Route = {


    path("hello" / Segment) { charClass =>
      // DIRECTIVES (Building of High Level akka http server)
      complete(StatusCodes.OK, charClass.toString)
    } ~
      pathPrefix("mya") {
        (path(Segment) & post) { name =>
          complete(StatusCodes.OK,name)
        }
      }
  }

  val compactExtractRequestRoute = (path("controlEndpoint") & extractRequest & extractLog) { (request, log) => {

    post {
      log.info(s"I got the http request: $request")
      complete(StatusCodes.OK, "post request")
    }

    get {
      log.info(s"I got the http request: ${request}")
      complete(StatusCodes.OK, "get request")
    }
  }
    }


  val dummy:Route={

    routes ~ compactExtractRequestRoute

  }

  Http().bindAndHandle(dummy,"localhost",9990)

}
