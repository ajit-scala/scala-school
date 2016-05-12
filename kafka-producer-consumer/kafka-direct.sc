import kafka.serializer.StringDecoder

import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka._
import org.apache.spark.SparkConf


val sparkConf = new SparkConf().setAppName("DirectKafkaWordCount")
val ssc = new StreamingContext(sparkConf, Seconds(2))

val Array(brokers, topics) = Array("localhost,9092","test")
val topicsSet = topics.split(",").toSet
val kafkaParams = Map[String, String]("metadata.broker.list" -> brokers)

val messages = KafkaUtils.createDirectStream[String,String,StringDecoder, StringDecoder](ssc, kafkaParams ,topicsSet)

messages map {
  case (key, json) => println(json)
}

val lines = messages.map(_._2)
val words = lines.flatMap(_.split(" "))
val wordCounts = words.map(x => (x, 1L)).reduceByKey(_ + _)
wordCounts.print()

// Start the computation
ssc.start()
