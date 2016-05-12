package com.example

import kafka.serializer.StringDecoder
import org.apache.log4j.Logger
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.log4j._
import java.io._

import kafka.common.TopicAndPartition
import kafka.message.MessageAndMetadata
import org.apache.spark.rdd.RDD
case class Response1(name:String)
object Hello1 {

  def messageHandler(messageAndMetadata: MessageAndMetadata[String,String]):Response = {
    Response(messageAndMetadata.toString())
  }

  def getContext(): StreamingContext = {
    println("Hello, world!")
    //Logger.getLogger(StreamingContext.getClass).setLevel(Level.OFF)

    val Array(brokers, topics) = Array("localhost:9092","test2")
    val topicsSet = topics.split(",").toSet
    val kafkaParams = Map[String, String]("metadata.broker.list" -> brokers)

    println(topics)
    println(kafkaParams)

    val sparkConf = new SparkConf().setAppName("DirectKafkaWordCount").setMaster("local[*]")
    val ssc = new StreamingContext(sparkConf, Seconds(5))
    KafkaUtils.createDirectStream[String,String,StringDecoder, StringDecoder](ssc, kafkaParams ,topicsSet) foreachRDD {
      rdd=> rdd.foreach(r=>writeToFile(r))
    }


//    val lines = messages.map(_._2)
//    val words = lines.flatMap(_.split(" "))
//    val wordCounts = words.map(x => (x, 1L)).reduceByKey(_ + _)
    println("printing word count   ")
//    wordCounts.print()
    println("end word count   ")

    // Start the computation
//    ssc.start()
  //  ssc.awaitTermination()
    println("Hello, world - finish!")
    ssc
  }
def writeToFile(r:Response): Unit = {
    val writer = new FileWriter("/home/ajit/tmp/test123.txt",true)
    writer.write(r.name)
    writer.close()
  }
  def writeToFile(r:(String,String)): Unit = {
    val writer = new FileWriter("/home/ajit/tmp/test123.txt",true)
    writer.write(s"${r._1}  ${r._2}\n")
    writer.close()
  }
  def main(args: Array[String]): Unit = {
    val ssc = StreamingContext.getOrCreate("~/tmp/cats-spark", getContext)
    ssc.start()
    ssc.awaitTermination()
  }
}
