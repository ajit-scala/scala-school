package app.kafkaclient

import app.Configuration
import com.typesafe.scalalogging.LazyLogging
import kafka.api.{ TopicMetadata, TopicMetadataResponse }
import kafka.api.TopicMetadataRequest
import kafka.common.TopicAndPartition
import kafka.consumer.SimpleConsumer

import scala.collection.immutable.Map

/**
 * Created by ajit on 18.05.16.
 */
trait KafkaMetaData extends Configuration with LazyLogging {

  def getTopics(offset: Long): Map[TopicAndPartition, Long] = {
    val consumer = new SimpleConsumer(
      brokersHost,
      kafkaPort,
      100000,
      64 * 1024, "test1234"
    )

    val request = new TopicMetadataRequest(inputTopics.toList, 0)
    val resp: TopicMetadataResponse = consumer.send(request)

    val topicsAndPartitions: Map[TopicAndPartition, Long] = (resp.topicsMetadata flatMap {
      topicMeta =>
        topicMeta.partitionsMetadata map {
          partition => (TopicAndPartition(topicMeta.topic, partition.partitionId), offset)
        }
    })(collection.breakOut)

    /*topicsAndPartitions match {
      case x: Map[TopicAndPartition, Long] => logger.debug(s" Topic(s) successfully found for kafka, Host:$brokersHost Topics:${inputTopics.mkString(" ")}")
      case _ => logger.error(s" Topic(s) not found for kafka, Host:$brokersHost Topics:${inputTopics.mkString(" ")}")
    }*/

    topicsAndPartitions
  }
}
