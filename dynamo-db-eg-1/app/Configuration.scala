package app

import com.typesafe.config.{ConfigException, ConfigFactory}

import scala.util.Try

trait Configuration {
  lazy val config = ConfigFactory.load()

  lazy val dynamoDbEndPoint = config.getString("dynamodb.endpoint")
  lazy val dynamoDbCarsTable = config.getString("dynamodb.cars-table")
  lazy val useDummyAwsCredentials = Try(config.getBoolean("aws.use_dummy_credentials"))
    .recover { case e: ConfigException.Missing => false }.get
}
