import java.util.Properties

import org.apache.kafka.clients.consumer.KafkaConsumer

import scala.collection.JavaConverters._


val config = new Properties()
config.put("bootstrap.servers", "localhost:9092")
config.put("group.id", "scals-test")
config.put("enable.auto.commit", "false")
config.put("session.timeout.ms", "10000")
config.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
config.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")

val consumer = new KafkaConsumer[String, String](config)

val partitions = consumer.partitionsFor("test").asScala
for (partition <- partitions) {
  println(partition.toString)
}