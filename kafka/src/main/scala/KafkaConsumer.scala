import java.util.Collections
import java.util.Properties
import scala.collection.JavaConversions._
import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer}


object KafkaConsumer {
  def main(args: Array[String]): Unit = {
    val props = new Properties();

    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "1");

    val consumer = new KafkaConsumer[String, String] (props);
    consumer.subscribe(Collections.singletonList("first_topic"));

    while(true) {
      val records = consumer.poll(500);
      for(record <- records.iterator()) {
        println("Received message: (" + record.key() + ", " + record.value() + ") at offset " + record.offset());
      }
    }
  }
}
