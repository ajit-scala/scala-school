package com.example

import java.util.concurrent.atomic.AtomicLong

import akka.Done
import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.kafka.{ConsumerSettings, KafkaConsumerActor, Subscriptions}
import akka.kafka.scaladsl.Consumer
import akka.stream.scaladsl.Sink
import akka.stream.{ActorMaterializer, ActorMaterializerSettings}
import com.example.application.MyDbActor.Start
import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecord, OffsetResetStrategy}
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.{ByteArrayDeserializer, StringDeserializer}

import scala.concurrent.Future

object application extends App {
  implicit val system = ActorSystem("Categorization1")
  implicit val materializer = ActorMaterializer(ActorMaterializerSettings(system))
  val dbActor = system.actorOf(Props[MyDbActor])

  import scala.concurrent.ExecutionContext.Implicits.global

  val consumerSettings = ConsumerSettings(system, new ByteArrayDeserializer, new StringDeserializer)
    .withBootstrapServers("localhost:9092")
    .withGroupId("group1")
    .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest")
    .withProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true")
    .withProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000")

  val db = new DB
//  db.loadOffset().foreach { fromOffset =>
////    val partition = 0
////    val subscription = Subscriptions.assignmentWithOffset(
////      new TopicPartition("test-1", partition) -> fromOffset
////    )
//
//
//
//  }

  val subscription2 = Subscriptions.topics("test-1")

  val done =
    Consumer.plainSource(consumerSettings, subscription2)
//      .mapAsync(1)(db.save)
      .mapAsync(1){ message =>
        Future{
          dbActor ! message
        }
      }
      .runWith(Sink.ignore)

  class DB {

    private val offset = new AtomicLong

    def save(record: ConsumerRecord[Array[Byte], String]): Future[Done] = {
      println(s"DB.save: ${record.value}")
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

  class MyDbActor extends Actor {
    override def receive: Receive = {
      case Start(record:ConsumerRecord[Array[Byte], String]) =>
      //  println(s"DB.save: sleeping")
       // Thread.sleep(2000)
        println(s"DB actor .save: ${record.value}")
    }
  }
  object MyDbActor {
    case class Start(record: ConsumerRecord[Array[Byte], String])
  }

}