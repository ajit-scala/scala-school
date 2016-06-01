package app.spark.writer

import java.util.Properties

import org.apache.kafka.clients.producer.{ KafkaProducer, ProducerRecord }
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.dstream.DStream
import scala.language.implicitConversions

object implicits {

  implicit class RichRDD[T](rdd: RDD[T]) {

    def writeToKafka[K, V](config: Properties, serializer: (T) => ProducerRecord[K, V]): Unit = {
      rdd.foreachPartition { events =>
        val producer: KafkaProducer[K, V] = ProducerCache.getProducer(config)
        events.foreach(e => producer.send(serializer(e)))
      }
    }

  }

  implicit class RichDStream[T](dstream: DStream[T]) {

    def writeToKafka[K, V](config: Properties, serializer: T => ProducerRecord[K, V]): Unit = dstream.foreachRDD(_.writeToKafka(config, serializer))

  }

}
