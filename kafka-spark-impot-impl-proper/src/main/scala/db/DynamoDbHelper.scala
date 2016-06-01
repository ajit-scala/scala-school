package db

import app.Configuration
import com.amazonaws.ClientConfiguration
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.google.inject.Provides

/**
 * Created by ajit on 19.05.16.
 */
object DynamoDbHelper extends Configuration {

  def dynamoDb(): DynamoDB = {
    val client =
      if (useDummyAwsCredentials)
        new AmazonDynamoDBClient(new BasicAWSCredentials("fake", "fake"))
      else
        new AmazonDynamoDBClient(new ClientConfiguration().withMaxConnections(500))

    client.setEndpoint(dynamoDbEndPoint)
    new DynamoDB(client)
  }
}

trait DynamoDbClient {
  val dynamoDb = DynamoDbHelper.dynamoDb()
}
