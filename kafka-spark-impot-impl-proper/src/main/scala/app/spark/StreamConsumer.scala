package app.spark

import kafka.common.TopicAndPartition
import kafka.message.MessageAndMetadata
import kafka.serializer.StringDecoder
import model.Listing
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.{ DStream, InputDStream }
import org.apache.spark.streaming.kafka._

trait StreamConsumer {

  //new messages from all partitions and topics
  def getStream(kafkaParams: Map[String, String], topics: Set[String])(implicit sc: StreamingContext): DStream[(String, String)] = {
    KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](sc, kafkaParams, topics)
  }

  //all messages from offset provided in topicsMap and from all partitions provided
  def getStream(kafkaParams: Map[String, String], topicsMap: Map[TopicAndPartition, Long],
    messageHandler: (MessageAndMetadata[String, String] => (Listing, String)))(implicit sc: StreamingContext): InputDStream[(Listing, String)] = {
    KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder, (Listing, String)](sc, kafkaParams, topicsMap, x => messageHandler(x))
  }

}
