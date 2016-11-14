/*---this one tried to use actors with akka-streams - is of no use and creates a bottleneck at ask pattern---*/

package com.example

import java.util.concurrent.atomic.AtomicLong

import akka.Done
import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.kafka.{ConsumerSettings, KafkaConsumerActor, Subscriptions}
import akka.kafka.scaladsl.Consumer
import akka.stream.scaladsl.Sink
import akka.stream.{ActorMaterializer, ActorMaterializerSettings}
import com.example.application2.MyDbActor.Start
import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecord, OffsetResetStrategy}
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.{ByteArrayDeserializer, StringDeserializer}
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._

import scala.concurrent.Future

object application2 extends App {
  implicit val system = ActorSystem("Categorization2")
  //val system2 = ActorSystem("Categorization21")
  implicit val materializer = ActorMaterializer(ActorMaterializerSettings(system))
  val dbActor = system.actorOf(Props[MyDbActor])

  import scala.concurrent.ExecutionContext.Implicits.global

  val consumerSettings = ConsumerSettings(system, new ByteArrayDeserializer, new StringDeserializer)
    .withBootstrapServers("localhost:9092")
    .withGroupId("group1")
    .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest")
    .withProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true")
    .withProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000")

//  db.loadOffset().foreach { fromOffset =>
////    val partition = 0
////    val subscription = Subscriptions.assignmentWithOffset(
////      new TopicPartition("test-1", partition) -> fromOffset
////    )
//
//
//
//  }

  val subscription2 = Subscriptions.topics("test")
  implicit val timeout    = Timeout(5.seconds)//worker actor should come back in 5 seconds or time out exception occurs

  val done =
    Consumer.plainSource(consumerSettings, subscription2)
//      .mapAsync(1)(db.save)
      .mapAsync(4){ message =>
          println(message.value())
          println(dbActor)
          dbActor ? Start(message)
      }
      .runWith(Sink.ignore)

  class MyDbActor extends Actor {
    override def receive: Receive = {
      case Start(record:ConsumerRecord[Array[Byte], String]) =>
      //  println(s"DB.save: sleeping")
       // Thread.sleep(2000)
        println(s"DB actor .save: ${record.value}")
        sender ! 2
    }
  }
  object MyDbActor {
    case class Start(record: ConsumerRecord[Array[Byte], String])
  }

}