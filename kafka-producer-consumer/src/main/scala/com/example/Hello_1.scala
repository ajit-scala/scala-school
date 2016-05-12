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
    KafkaUtils.createDirectStream[String,String,StringDecoder, StringDecoder, Response](ssc, kafkaParams ,Map(TopicAndPartition(topics,1) ->1L),
      x=>messageHandler(x)).foreachRDD((r,t)=>writeToFile(r,t)) //saveAsTextFiles("pp","txt")
//    messages map {
//      //case (key, json) => writeToFile(json)
//      case r:Response => writeToFile(r.toString())
//      case _ => writeToFile("none found")
//    }


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
def writeToFile(r:RDD[Response], t:org.apache.spark.streaming.Time): Unit = {
  val writer = new FileWriter("/home/ajit/tmp/test123.txt",true)
  r.map(x=>writer.write(x.name))
    writer.close()
  }
  def main1(args: Array[String]): Unit = {
    val ssc = StreamingContext.getOrCreate("~/tmp/cats-spark", getContext)
    ssc.start()
    ssc.awaitTermination()
  }
}
