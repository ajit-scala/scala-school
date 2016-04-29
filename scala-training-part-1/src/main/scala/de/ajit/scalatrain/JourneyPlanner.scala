package de.ajit.scalatrain

/**
 * Created by achahal on 13/04/16.
 */
class JourneyPlanner(val trains: Set[Train]) {

  def stations: Set[Station] = trains.flatMap(x => x.stations) //but flatmap flats collection of colection to original type
  def stationsMoreConsizeSyntax: Set[Station] = trains.flatMap(_.stations) //_ replaces the type x=> as a  whole as type is known
  def stationsWithMap: Set[Seq[Station]] = trains.map(x => x.stations) //collection of collection

  def trainsAt(station: Station): Set[Train] = trains.filter(x => x.stations.contains(station))
  def trainsAt2(station: Station): Set[Train] = trains.filter(_.stations.contains(station))
}
