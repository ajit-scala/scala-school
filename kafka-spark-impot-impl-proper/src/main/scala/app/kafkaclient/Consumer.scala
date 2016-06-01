package app.kafkaclient

import java.io.PrintStream
import java.util

import kafka.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.{ ConsumerRecord, ConsumerRecords, KafkaConsumer }

import scala.collection.JavaConverters._
import scala.collection.immutable.Iterable;

/**
 * Created by ajit on 18.05.16.
 */
class Consumer {
  val configs = Map(
    "bootstrap.servers" -> "localhost:9092",
    "group.id" -> "test",
    "enable.auto.commit" -> "true",
    "auto.commit.interval.ms" -> "1000",
    "session.timeout.ms" -> "30000",
    "key.deserializer" -> "org.apache.kafka.common.serialization.StringDeserializer",
    "value.deserializer" -> "org.apache.kafka.common.serialization.StringDeserializer"
  ).asInstanceOf[java.util.Map[String, Object]]

  //  val consumer: KafkaConsumer[String, String] = new KafkaConsumer(configs)
  //
  //    consumer.subscribe("test2")
  //    while (true) {
  //      val records: util.Map[String, ConsumerRecords[String, String]] = consumer.poll(100)
  //      for(record <- records) {
  //        println(record)
  //      }
  //  }
}
