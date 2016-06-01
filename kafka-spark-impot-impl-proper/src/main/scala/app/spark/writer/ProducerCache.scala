package app.spark.writer

import java.util.Properties

import org.apache.kafka.clients.producer.KafkaProducer

import scala.collection.mutable

object ProducerCache {

  private val producers = new mutable.HashMap[Properties, Any]()

  def getProducer[K, V](config: Properties): KafkaProducer[K, V] = {
    producers.getOrElse(config, {
      val producer = new KafkaProducer[K, V](config)
      producers(config) = producer
      producer
    }).asInstanceOf[KafkaProducer[K, V]]
  }
}
