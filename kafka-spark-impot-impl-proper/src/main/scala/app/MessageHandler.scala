package app

import com.typesafe.scalalogging.LazyLogging
import db.ListingRepo
import kafka.message.MessageAndMetadata
import model.Listing

import scala.util.{ Failure, Success }

/**
 * Created by ajit on 17.05.16.
 */
trait MessageHandler extends MessageProcessor with LazyLogging with ListingRepo {
  def handleKafkaMessage(message: (String, String)): Unit = {
    message match {
      case (key, json) =>
        val mayBeJson = processMessage(json)
        mayBeJson match {
          case Success(_) => {
            logger.debug(s"$key was successfully processed")
            writeToListingsTable(mayBeJson.get, json)
          }
          case Failure(f) => logger.error(s"$key processing failed with $f ${f.getMessage}")
        }
    }
  }
  def handleKafkaMessage(messageAndMetadata: MessageAndMetadata[String, String]): (Option[Listing], String) = {
    val message = (messageAndMetadata.key(), messageAndMetadata.message())

    message match {
      case (key, json) =>
        val mayBeJson = processMessage(json)
        mayBeJson match {
          case Success(_) => {
            logger.debug(s"$key was successfully processed")
            (Some(mayBeJson.get), json)
          }
          case Failure(f) => {
            logger.error(s"$key processing-failed with $f ${f.getMessage}")
            (None, json)
          }
        }
    }
  }
}
