package de.ajit.scalatrain

/**
 * Created by achahal on 13/04/16.
 */
case class Station(name: String) {
  require(name.nonEmpty, "Empty name")
}
