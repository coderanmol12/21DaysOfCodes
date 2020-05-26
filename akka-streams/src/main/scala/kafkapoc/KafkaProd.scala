package kafkapoc

import akka.Done
import akka.actor.ActorSystem
import akka.kafka.{ProducerMessage, ProducerSettings}
import akka.stream.Supervision.{Resume, Stop}
import akka.stream.scaladsl.{Sink, Source}
import akka.stream.{ActorAttributes, ActorMaterializer}
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.errors.{InvalidCommitOffsetSizeException, InvalidPartitionsException, TopicAuthorizationException}
import org.apache.kafka.common.serialization.StringSerializer

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

object KafkaProducerWithCallbacks extends App {
  implicit val system = ActorSystem("faultTolerance")
  implicit val materializer = ActorMaterializer()
  val config = system.settings.config.getConfig("akka.kafka.producer")
  val producerSettings: ProducerSettings[String, String] = ProducerSettings(system, new StringSerializer, new StringSerializer).withBootstrapServers("localhost:9092")
  var topic = "first_topic"

  def check(message: List[String]): Future[Done] = {
      Source(message).mapAsync(4)(item => {
      if (item == "abc2") throw new InvalidPartitionsException(item+"_metrics")
      Future.successful(ProducerMessage.Message(new ProducerRecord[String, String](topic, "valuehdhd", item + ""), null))
    }).log("supervision").withAttributes(ActorAttributes.supervisionStrategy {
          case e:Throwable => {
            println("error occurred"+e.getMessage())
            Resume}
        }).async.
      via(akka.kafka.scaladsl.Producer.flexiFlow(producerSettings, producerSettings.createKafkaProducer()))
      .runWith(Sink.foreach(e=> println("hello world")))
  }


  check(List("abc1", "abc2", "abc3", "abc4")).onComplete {
    case Success(value) => println(value)
    case Failure(exception) => println(exception.getStackTrace())
  }
}