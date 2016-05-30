package app.spark

import app.{ Configuration, MessageHandler, MessageProcessor }
import model.Listing
import com.typesafe.scalalogging.LazyLogging
import db.ListingRepo
import kafka.message.MessageAndMetadata
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{ Seconds, StreamingContext }

object Application extends StreamConsumer with Configuration with MessageHandler {

  def getContext(): StreamingContext = {

    implicit val ssc = new StreamingContext(sparkConf, Seconds(batchDurationSeconds))

    logger.info(s"Starting the processing of topics: $inputTopics in brokers: $brokers")
    // *** subscribe to new messages only
    getStream(kafkaParams, inputTopics) foreachRDD {
      rdd => rdd.foreach(messageAndMetadata => handleKafkaMessage(messageAndMetadata))
    }

    //ssc.checkpoint(checkpointPath)
    ssc
  }

  def main_old(args: Array[String]): Unit = {
    val ssc = StreamingContext.getOrCreate(checkpointPath, getContext)
    ssc.start()
    ssc.awaitTermination()
  }
  def messageHandler(messageAndMetadata: MessageAndMetadata[String, String]): Listing = {
    Listing(messageAndMetadata.toString())
  }
}

