//package db
//
//import app.Configuration
//import app.Configuration
//import com.amazonaws.services.dynamodbv2.document.DynamoDB
//import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded
//
///*
//  * Created by ajit on 19.05.16.
//  */
//object DynamoDbEmbeddedHelper extends Configuration {
//
//  def dynamoDb(): DynamoDB = {
//    val client = DynamoDBEmbedded.create()
//
//    //client.setEndpoint(dynamoDbEndPoint)
//    new DynamoDB(client)
//  }
//}
//
//trait DynamoDbEmbeddedClient {
//  val dynamoDb = DynamoDbEmbeddedHelper.dynamoDb()
//}
//
