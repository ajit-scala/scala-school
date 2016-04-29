package de.heikoseeberger.scalatrain

class JourneyPlanner(trains: Set[Train]) {

  def stations: Set[Station] = trains.flatMap(_.stations)

  def trainsAt(station: Station): Set[Train] = trains.filter(_.stations.contains(station))

  def departuresAt(station: Station): Set[(Time, Train)] ={
    for{
      train <- trains
      stop <- train.schedule if stop.station== station
    } yield (stop.departureTime, train)
  }
  def departuresAt_via_patterns(station: Station): Set[(Time, Train)] = for {
    train <- trains
    Stop(`station`, _, departureTime) <- train.schedule
  } yield (departureTime, train)


  /*
  * _matched any 1 occurance of obj
  * _* macthes any occreances to seq in objects
  * `obj` exact matches variable defines in scope
  * */
  def isShortTrip(from: Station, to: Station): Boolean =
    trains.exists { train => train.stations.dropWhile(st => st != from)  match {
      case Seq(fromStation,toStation) => toStation==to
      case Seq(fromStation,_,thirdStation) => thirdStation==to
      case _ => false
      }
    }

  def isShortTrip_Solution_by_Seq_Macthing(from: Station, to: Station): Boolean =
    trains.exists { _.stations.dropWhile(_ != from) match {
      case Seq(_, `to`, _*) => true
      case Seq(_, _, `to`, _*) => true
      case _ => false
    }
    }

  def isShortTrip_by_collection_api(from: Station, to: Station): Boolean =
    trains.exists { _.stations.dropWhile(_ != from).take(3).contains(to) }
}
