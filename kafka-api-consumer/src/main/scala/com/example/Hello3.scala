package com.example

import java.net.ServerSocket
import java.util.Properties

import org.apache.kafka.clients.consumer.{ConsumerRecords, KafkaConsumer}
import org.apache.kafka.common.TopicPartition

import scala.collection.JavaConverters._
import java.net.{Socket, ServerSocket}
import java.util.concurrent.{Executors, ExecutorService}
import java.util.Date

object Hello2 {
  def main2(args: Array[String]): Unit = {


    val config = new Properties()
    config.put("bootstrap.servers", "localhost:9092")
    config.put("group.id", "scala-test")
    config.put("enable.auto.commit", "false")
    config.put("session.timeout.ms", "10000")
    config.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    config.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    val consumer = new KafkaConsumer[String, String](config)
    val partitions = consumer.partitionsFor("lists").asScala.map { p => new TopicPartition(p.topic(), p.partition()) }
    consumer.assign(partitions.asJava)
    for {partition <- partitions} consumer.seek(partition,0)

    println("Exit1")

    val hello = new Thread(new Runnable {
      def run() {
        println("xxxx")
        consume(consumer)
      }
    })
    hello.run()
    println("Exit2")

    (new NetworkService(9000, 2)).run()

    println("Exit")
  }


  def consume(consumer:KafkaConsumer[String, String]): Unit ={
    consumer.poll(1000) match {
      case cr:ConsumerRecords[String, String] if cr.count()>0=> {
        println("...got something: " + cr.count())
        for {record <- cr.asScala} println(record)
      }
      case _ => println("None")
    }
    consume(consumer)
  }
}

class NetworkService(port: Int, poolSize: Int) extends Runnable {
  val serverSocket = new ServerSocket(port)
  val pool: ExecutorService = Executors.newFixedThreadPool(poolSize)

  def run() {
    try {
      while (true) {
        // This will block until a connection comes in.
        val socket = serverSocket.accept()
        pool.execute(new Handler(socket))
      }
    } finally {
      pool.shutdown()
    }
  }
}

class Handler(socket: Socket) extends Runnable {
  def message = (Thread.currentThread.getName() + "\n").getBytes

  def run() {
    socket.getOutputStream.write(message)
    socket.getOutputStream.close()
  }
}
