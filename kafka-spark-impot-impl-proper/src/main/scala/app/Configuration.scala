package app

import com.typesafe.config.ConfigFactory
import org.apache.spark.SparkConf
import com.typesafe.config.{ ConfigRenderOptions, ConfigException }

import scala.collection.JavaConverters._
import scala.util.Try

trait Configuration {
  lazy val config = ConfigFactory.load()

  lazy val port = config.getInt("server.port")

  lazy val batchDurationSeconds = config.getLong("spark.batchDurationSeconds")
  lazy val maxRate = config.getInt("spark.maxRate")
  lazy val checkpointPath: String = config.getString("spark.checkpointPath")
  lazy val appName: String = config.getString("spark.appName")
  lazy val webUiPort: String = config.getString("spark.webUiPort")

  lazy val brokers: String = java.net.InetAddress.getAllByName(config.getString("kafka.host")).map(a => s"${a.getHostAddress}:${config.getInt("kafka.port")}").mkString(",")
  lazy val brokersHost: String = config.getString("kafka.host")
  lazy val inputTopics: Set[String] = config.getStringList("kafka.inputTopics").asScala.toSet
  lazy val kafkaPort: Int = config.getInt("kafka.port")
  lazy val kafkaClientName: String = config.getString("kafka.client.name")

  lazy val standardOutputPath: String = config.getString("spark.standardOutputPath")
  lazy val headersOutputPath: String = config.getString("spark.headersOutputPath")

  lazy val localeDefault: String = config.getString("locale.default")

  val sparkConf = new SparkConf().setAppName(appName)
    .setIfMissing("spark.master", "local[*]")
    .set("spark.ui.port", webUiPort)
    .set("spark.streaming.kafka.maxRatePerPartition", maxRate.toString)

  val kafkaParams = Map[String, String](
    "metadata.broker.list" -> brokers,
    "auto.offset.reset" -> "smallest"
  )

  lazy val dynamoDbEndPoint = config.getString("dynamodb.endpoint")
  lazy val dynamoDbListingsTable = config.getString("dynamodb.listings-table")
  lazy val useDummyAwsCredentials = Try(config.getBoolean("aws.use_dummy_credentials"))
    .recover { case e: ConfigException.Missing => false }.get
}