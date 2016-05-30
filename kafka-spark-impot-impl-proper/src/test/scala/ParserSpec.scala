//import java.util.Arrays._
//
//import app.ListingJsonParser
//import com.amazonaws.auth.BasicAWSCredentials
//import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
//import com.amazonaws.services.dynamodbv2.document.{ DynamoDB, Table }
//import com.amazonaws.services.dynamodbv2.model._
//import org.scalatest._
//
//class ParserSpec extends WordSpec with Matchers with ListingJsonParser {
//  "Listing Parser" should {
//    "Must not Parse test json, capitalized properties" in {
//      val testJson = "{\"ClassifiedGuid\":\"xx-fff-rr-66z-566\",\"ArticleId\":\"1234\",\"color_id\":\"c1\",\"Body_type_id\":\"b5\"}"
//      val result = parseListing(testJson)
//      result.isSuccess should ===(false)
//    }
//    "Parse test json successfully" in {
//      val testJson = "{\"classifiedGuid\":\"xx-fff-rr-66z-566\",\"articleId\":\"1234\",\"color_id\":\"c1\",\"Body_type_id\":\"b5\"}"
//      val result = parseListing(testJson)
//      result.isSuccess should ===(true)
//      result.get.classifiedGuid shouldBe "xx-fff-rr-66z-566"
//      result.get.articleId.getOrElse("not parsed value") shouldBe 1234
//    }
//
//  }
//}
