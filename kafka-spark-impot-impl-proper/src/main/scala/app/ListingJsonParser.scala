package app

import cats.data.Xor
import cats.data.Xor.{ Left, Right }
import io.circe._
import io.circe.generic.auto._
import model._
import util.ImportException

import scala.util._

trait ListingJsonParser {

  def parse(json: String): Xor[Error, Listing] = jawn.decode[Listing](json)

  def parseListing(json: String): Try[Listing] = parse(json) match {
    case Left(error) => Failure(new ImportException(util.LISTINGS_JSON_PARSE, diagnoseErrorMsg(error.getMessage, json), error))
    case Right(listing) => Success(listing)
  }

  // does some primitive diagnostics to give better feedback
  private def diagnoseErrorMsg(msg: String, json: String = ""): String = {
    val resultMsg: StringBuilder = new StringBuilder("Listing could not be parsed")

    val diagnosis = msg match {
      case s if s.contains("control char (10) in string") => ": unclosed string?"
      case s if s.contains("expected } or , got") => ": '}' or comma missing?"
      case s if s.contains("expected \" got ,") => ": duplicate comma?"
      case _ => ""
    }

    (resultMsg ++= diagnosis).toString() + "\nOriginal JSON:\n" + json + "\n"
  }
  //def parseListing(json: String): Try[Listing] = {
  //    Success(Listing.empty)
  //  }
}