package app

import model.{ Listing }

import scala.util.Try

trait MessageProcessor extends ListingJsonParser {

  def processMessage(json: String): Try[Listing] = {
    parseListing(json)
  }

}
