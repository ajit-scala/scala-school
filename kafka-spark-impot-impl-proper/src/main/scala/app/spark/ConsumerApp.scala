package app.spark

import app.kafkaclient.KafkaMetaData
import app.{ Configuration, MessageHandler }
import com.typesafe.scalalogging.LazyLogging
import db.ListingRepo
import kafka.common.TopicAndPartition
import kafka.message.MessageAndMetadata
import kafka.serializer.StringDecoder
import model.Listing
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{ Seconds, StreamingContext }

object ConsumerApp extends MessageHandler with ListingRepo with Configuration with LazyLogging with KafkaMetaData {

  def getContext(): StreamingContext = {

    implicit val ssc = new StreamingContext(sparkConf, Seconds(batchDurationSeconds))

    logger.info("Input topics:" + inputTopics.mkString(" "))
    val topicsMetada = getTopics(0L)
    logger.info(s"topicsMetada: ${topicsMetada.mkString(" ")}")

    KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder, (Option[Listing], String)](ssc, kafkaParams, topicsMetada,
      (messageAndMetadata: MessageAndMetadata[String, String]) => handleKafkaMessage(messageAndMetadata)) foreachRDD {
        rdd =>
          rdd.foreach { tuple2 =>
            tuple2 match {
              case (listing: Some[Listing], originalJson) => writeToListingsTable(listing.get, originalJson)
              case (None, originalJson) => logger.debug("skipping writing to dynamo-db due to missing listing.")
              case _ => logger.debug("skipping writing to dynamo-db due to missing listing or JSON.")
            }
          }
      }

    //ssc.checkpoint(checkpointPath)
    ssc
  }

  def main(args: Array[String]): Unit = {
    val ssc = StreamingContext.getOrCreate(checkpointPath, getContext) //todo from config
    ssc.start()
    ssc.awaitTermination()
  }
}
