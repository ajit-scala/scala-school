package de.ajit.scalatrain
import scala.collection.immutable.Seq

/**
 * Created by achahal on 13/04/16.
 */
case class Train(val kind: String, val number: Int, schedule: Seq[Stop]) {
  //this is my constructor body, everthin gets executed on instantiation of this class
  // kind must not be empty
  //Check precondition: schedule must be increasing in time!

  require(kind.nonEmpty, "Kind is empty") //first statement that will be executed
  require(schedule.size >= 2, "Minimum stations must be 2")
  require(schedule.distinct == schedule, "Must not contain duplicates")
  //require(schedule.foreach(x=>x.))

  def stations: Seq[Station] = schedule.map(x => x.station)

}
