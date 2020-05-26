import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Sink, Source}

object FirstPrinciple  extends App{


  /**
   * Source ->Upstream, and a publisher(emits an element)
   * Sink  -> downstream and a subscriber
   * Flow is bidirectional and a processor
   *
   * connecting Source-> Flow-> Sink is known as graph
   */

  implicit val system = ActorSystem("firstPrinciple")
  implicit val materializer = ActorMaterializer()
  var dummy = (value:Int) => println(value*2)
  val source = Source(1 to 10)
  val sink = Sink.foreach(dummy)
  var graph = source.to(sink)
  graph.run()

  // introducing flow

  var flowFunctions:Int => Int = (x) => {
    println(x*3)
    x
  }
  val flow = Flow[Int].map(x=> flowFunctions(x))
  var sourceWithFlow = source.via(flow)
  var graphWithFlow = sourceWithFlow.to(sink)
  graphWithFlow.run()
}
