package com.example

import java.util.Properties

import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.TopicPartition
import scala.collection.JavaConverters._

object Hello {
  def main(args: Array[String]): Unit = {


    val config = new Properties()
    config.put("bootstrap.servers", "10.0.146.85:9092, 10.0.133.239:9092, 10.0.190.200:9092, 10.0.204.212:9092, 10.0.216.163:9092")
    config.put("group.id", "scala-test")
    config.put("enable.auto.commit", "false")
    config.put("session.timeout.ms", "10000")
    config.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    config.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    val consumer = new KafkaConsumer[String, String](config)
    val partitions = consumer.partitionsFor("lists").asScala.map { p => new TopicPartition(p.topic(), p.partition()) }
    println(partitions)
    consumer.assign(partitions.asJava)
    for {partition <- partitions} consumer.seekToBeginning(partition)
    while (true) {
      val result = consumer.poll(1000)
      println("...got something: " + result.count())
      for {record <- result.asScala} println(record)
      Thread.sleep(5*1000)
    }

  }
}
