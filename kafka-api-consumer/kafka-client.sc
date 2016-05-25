import java.util
import java.util.Properties

import scala.collection.JavaConverters._
import org.apache.kafka.clients.consumer.{ConsumerRecords, KafkaConsumer}
import org.apache.kafka.common.PartitionInfo
import org.apache.kafka.common.serialization.StringDeserializer

//val configs = new util.HashMap[String,Object]()
val configs = new Properties()
configs.put("bootstrap.servers", "localhost:9092")
configs.put("group.id", "test")
configs.put("enable.auto.commit", "true")
configs.put("auto.commit.interval.ms", "1000")
configs.put("session.timeout.ms", "30000")

//configs.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
//configs.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")

//val consumer: KafkaConsumer[String, String] = new KafkaConsumer(configs)
val consumer: KafkaConsumer[String, String] = new KafkaConsumer(configs, new StringDeserializer(), new StringDeserializer())

//val partitions: util.List[PartitionInfo] = consumer.partitionsFor("kjhkh")
//
//val scala_parts = partitions.asScala


//consumer.subscribe("lists")
//while (true) {
// val records = consumer.poll(100)
// for(record <- records.asScala) {
//  println(record)
// }
//cd ka
//
//}