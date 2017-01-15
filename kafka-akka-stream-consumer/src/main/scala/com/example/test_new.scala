
/*---Usable code use akka-kafka- then sime processing methods----*/
package com.example

import java.util.concurrent.atomic.AtomicLong

import akka.Done
import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.kafka.{ConsumerSettings, KafkaConsumerActor, Subscriptions}
import akka.kafka.scaladsl.Consumer
import akka.stream.scaladsl.Sink
import akka.stream.{ActorMaterializer, ActorMaterializerSettings}
import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecord, OffsetResetStrategy}
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.{ByteArrayDeserializer, StringDeserializer}

import scala.concurrent.Future

object application extends App {
  implicit val system = ActorSystem("Categorization1")
  implicit val materializer = ActorMaterializer(ActorMaterializerSettings(system))

  import scala.concurrent.ExecutionContext.Implicits.global

  val consumerSettings = ConsumerSettings(system, new ByteArrayDeserializer, new StringDeserializer)
    .withBootstrapServers("localhost:9092")
    .withGroupId("group1")
    .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest")
    .withProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true")
    .withProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000")

  val db = new DB
//  db.loadOffset().foreach { fromOffset =>
   // val partition = 0
////    val subscription = Subscriptions.assignmentWithOffset(
 //     new TopicPartition("test-1", partition) -> fromOffset
////    )
//
//
//
//  }

  val subscription2 = Subscriptions.topics("raw-listings")

  val done =
    Consumer.plainSource(consumerSettings, subscription2)
      .mapAsync(4){//no. of concurrent processes to start
        db.save
      }
      .runWith(Sink.ignore)

  class DB {

    private val offset = new AtomicLong

    def save(record: ConsumerRecord[Array[Byte], String]): Future[Done] = {
      println(s"DB.save: ${record.key()}")
      print("   " + Thread.currentThread().getId()  )
      offset.set(record.offset)
      Future.successful(Done)
    }

    def loadOffset(): Future[Long] =
      Future.successful(offset.get)

    def update(data: String): Future[Done] = {
      println(s"DB.update: $data")
      Future.successful(Done)
    }
  }
}