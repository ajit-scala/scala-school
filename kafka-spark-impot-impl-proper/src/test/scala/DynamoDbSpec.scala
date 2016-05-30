//import java.util.Arrays._
//
//import app.Configuration
//import com.amazonaws.auth.BasicAWSCredentials
//import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
//import com.amazonaws.services.dynamodbv2.document.{ DynamoDB, Table }
//import com.amazonaws.services.dynamodbv2.model._
//import db.{ DynamoDbClient, DynamoDbEmbeddedClient }
//import org.scalatest._
//
//class DynamoDbSpec extends WordSpec with Matchers with Configuration with DynamoDbClient { //todo run these tests locally only via sbt task
//  /*"DynamoDb" should {
//    "Create Table '$dynamoDbListingsTable at localhost:8000" in {
//      val retStr = createTableWithSortKey(dynamoDbListingsTable, true)
//      retStr shouldBe "ACTIVE"
//    }
//    "Create Table '$dynamoDbKafkaPartitionOffsetTable at localhost:8000" in {
//      val retStr = createTableWithSortKey("CarHistoryKafkaPartitionOffsets", true)
//      retStr shouldBe "ACTIVE"
//    }
//    "Delete and Create Table 'TestTable' at localhost:8000" in {
//      val retStr = createTable("TestTable", true)
//      retStr shouldBe "ACTIVE"
//    }
//  }*/
//  def createKafkaTable(tableName: String, del: Boolean = false): String = {
//    if (del) {
//      try {
//        val table: Table = dynamoDb.getTable(tableName)
//        table.delete()
//      } catch {
//        case ex: Exception => "error deleting table" + ex.getMessage
//      }
//    }
//    val newTable: Table = dynamoDb.createTable(
//      tableName,
//      asList(
//        new KeySchemaElement("Topic", KeyType.HASH),
//        new KeySchemaElement("PartitionId", KeyType.RANGE)
//    ),
//      asList(
//        new AttributeDefinition("Topic", ScalarAttributeType.S),
//        new AttributeDefinition("PartitionId", ScalarAttributeType.N)
//    ),
//      new ProvisionedThroughput(1L, 1L)
//    )
//    newTable.waitForActive()
//    newTable.getDescription().getTableStatus()
//  }
//  def createTable(tableName: String, del: Boolean = false): String = {
//    if (del) {
//      try {
//        val table: Table = dynamoDb.getTable(tableName)
//        table.delete()
//      } catch {
//        case ex: Exception => "error deleting table" + ex.getMessage
//      }
//    }
//    val newTable: Table = dynamoDb.createTable(
//      tableName,
//      asList(
//        new KeySchemaElement("ClassifiedGuid", KeyType.HASH)
//      ),
//      asList(
//        new AttributeDefinition("ClassifiedGuid", ScalarAttributeType.S)
//      ),
//      new ProvisionedThroughput(10L, 10L)
//    )
//    newTable.waitForActive()
//    newTable.getDescription().getTableStatus()
//  }
//  def createTableWithSortKey(tableName: String, del: Boolean = false): String = {
//    val client: AmazonDynamoDBClient = new AmazonDynamoDBClient(new BasicAWSCredentials("fake", "fake"))
//      .withEndpoint("http://localhost:8000")
//
//    val dynamoDB: DynamoDB = new DynamoDB(client)
//    if (del) {
//      try {
//        val table: Table = dynamoDB.getTable(tableName)
//        table.delete()
//      } catch {
//        case ex: Exception => "error deleting table"
//      }
//    }
//    val newTable: Table = dynamoDB.createTable(
//      tableName,
//      asList(
//        new KeySchemaElement("ClassifiedGuid", KeyType.HASH), //Partition key
//        new KeySchemaElement("ArticleId", KeyType.RANGE)
//      ), //Sort key
//      asList(
//        new AttributeDefinition("ClassifiedGuid", ScalarAttributeType.S),
//        new AttributeDefinition("ArticleId", ScalarAttributeType.N)
//      ),
//      new ProvisionedThroughput(10L, 10L)
//    )
//    newTable.waitForActive()
//    newTable.getDescription().getTableStatus()
//  }
//}
//
