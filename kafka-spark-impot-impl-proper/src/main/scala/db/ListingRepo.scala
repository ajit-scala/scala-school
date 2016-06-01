package db

import app.Configuration
import com.amazonaws.ClientConfiguration
import model.Listing
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
import com.amazonaws.services.dynamodbv2.document.{ DynamoDB, Item, PutItemOutcome, Table }
import com.google.inject.{ Inject, Provides }
import com.typesafe.scalalogging.LazyLogging
import play.api.libs.json.{ JsValue, Json, Reads }

import scala.util.{ Failure, Success, Try }

/**
 * Created by ajit on 13.05.16.
 */
trait ListingRepo extends LazyLogging with Configuration with DynamoDbClient {

  def writeToListingsTable(listing: Listing, listingJson: String): Unit = {
    val table: Table = dynamoDb.getTable(dynamoDbListingsTable)
    Try(table.putItem(new Item()
      .withPrimaryKey("ClassifiedGuid", listing.classifiedGuid, "ArticleId", listing.articleId.getOrElse(0))
      .withJSON("Attributes", listingJson))) match {
      case Success(_) => logger.debug(s"${listing.classifiedGuid} was successfully inserted to dynamo-db")
      case Failure(f) => logger.error(s"Error while writing Listing to dynamo-db. table-name: '$dynamoDbListingsTable' \n Detailed Error: ${f.getMessage}")
    }
  }
}
