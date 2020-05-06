import java.util.Properties

import org.apache.kafka.clients.producer.{Callback, KafkaProducer, ProducerConfig, ProducerRecord, RecordMetadata}

object KafkaProducerWithCallbacks extends App {


  var topic = "first_code_topic"

  val props = new Properties();
  props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
  props.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaProducer");
  props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
  props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
  props.put(ProducerConfig.ACKS_CONFIG, "all") // can be 1 and 0

  val producer = new KafkaProducer[String, String](props)
  sendAsync("hello from scala to kafka!!!")   // key is passing, and passing same key is always leads to same partition

  def sendAsync(value: String):Unit = {
    val record = new ProducerRecord[String, String](topic, value,"Hii checking new topic")
    producer.send(record, new Callback {
      override def onCompletion(metadata: RecordMetadata, exception: Exception): Unit = {
        println("in a callback method with offset value : "+ metadata.offset() +"and partition is :"+ metadata.partition())
      }
    })
  }
  producer.flush()
  producer.close()
}
