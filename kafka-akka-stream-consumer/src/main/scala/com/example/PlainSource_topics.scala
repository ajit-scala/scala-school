package com.example

import java.util.concurrent.atomic.AtomicLong

import akka.Done
import akka.actor.{Actor, ActorSystem, Props}
import akka.kafka.ConsumerMessage.CommittableMessage
import akka.kafka.scaladsl.Consumer
import akka.kafka.{ConsumerSettings, Subscriptions}
import akka.stream.scaladsl.Sink
import akka.stream.{ActorMaterializer, ActorMaterializerSettings}
import com.example.MyDbActor1.Start
import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecord}
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.{ByteArrayDeserializer, StringDeserializer}

import scala.concurrent.Future

object PlainSource_topics {
  def main(args: Array[String]): Unit = {
    println("Starting app.....")
//    val properties = new Properties()
//    properties.put("bootstrap.servers", "10.0.146.85:9092, 10.0.133.239:9092, 10.0.190.200:9092, 10.0.204.212:9092, 10.0.216.163:9092")
//    properties.put("group.id", "scala-test")
//    properties.put("enable.auto.commit", "false")
//    properties.put("session.timeout.ms", "10000")
//    properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
//    properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")

    implicit val system = ActorSystem("Categorization")
    implicit val materializer = ActorMaterializer(ActorMaterializerSettings(system))
    import scala.concurrent.ExecutionContext.Implicits.global

    val consumerSettings = ConsumerSettings(system, new ByteArrayDeserializer, new StringDeserializer)
      .withBootstrapServers("localhost:9092")
      .withGroupId("group1")
      .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest")


    val dbActor1 = system.actorOf(Props[MyDbActor1])

//    val db = new DB
//    db.loadOffset().foreach { fromOffset =>
//      val partition = 0
//      val subscription = Subscriptions.assignmentWithOffset(
//        new TopicPartition("raw-listings", partition) -> fromOffset
//      )

      val done: Future[Done] =
        Consumer.committableSource(consumerSettings, Subscriptions.topics("test"))
//          .mapAsync(1)(db.save)
          .mapAsync(1){
          message =>
            Future {
               dbActor1 ! Start(message) //processor.processMessage(message.record.key, message.record.value)
             }

        }
          .runWith(Sink.ignore)

      //done.map(x=>x)
    }
  }

//  class DB {
//
//    private val offset = new AtomicLong

//    def save(record: ConsumerRecord[Array[Byte], String]): Future[Done] = {
//
//      println(s"DB.save: sleeping")
//      Thread.sleep(2000)
//      println(s"DB.save: ${record.value}")
//      offset.set(record.offset)
//      Future.successful(Done)
//    }
//
//    def loadOffset(): Future[Long] =
//      Future.successful(offset.get)
//
//    def update(data: String): Future[Done] = {
//      println(s"DB.update: $data")
//      Future.successful(Done)
//    }
//  }

  class MyDbActor1 extends Actor {
    override def receive: Receive = {
      case Start(record:CommittableMessage[Array[Byte], String]) =>
        println(s"DB.save: sleeping")
        Thread.sleep(2000)
        println(s"DB.save: ${record.record.value}")
//        offset.set(record.offset)
        Future.successful(Done)
    }
  }
  object MyDbActor1 {
    case class Start(record: CommittableMessage[Array[Byte], String])
  }

