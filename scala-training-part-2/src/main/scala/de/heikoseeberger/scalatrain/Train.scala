package de.heikoseeberger.scalatrain

case class Station(name: String) {
  require(name.nonEmpty, "name must not be empty!")
}

case class Stop(station: Station, arrivalTime: Time, departureTime: Time) {
  // "Check precondition: arrivalTime must be before departureTime!"
  require(arrivalTime < departureTime)
}

case class Train(info:TrainInfo, schedule: Seq[Stop]) {
  //require(kind.nonEmpty, "kind must not be empty!")
  require(schedule.size >= 2, "schedule must have at least two stops!")
  require(schedule.distinct == schedule, "schedule must not contain duplicate stations!")

  // "Check precondition: schedule must be increasing in time!"
  require(Time.isIncreasing(schedule.flatMap(stop => List(stop.arrivalTime, stop.departureTime))),
    "schedule must be increasing in time!")


  def stations: Seq[Station] = schedule.map(_.station)

  override def toString = info match {
    case InterCityExpress(number, hasWifi) => if( hasWifi) s"ICE $number (WIFI)" else s"ICE $number"
    case InterCity(number) =>  s"IC $number"
    case RegionalExpress(number) =>  s"RE $number"
  }
  def departureTime(at: Station): Option[Time] =
    schedule.collectFirst( {
      case Stop(`at`, _, departureTime) => departureTime //partial / anonymous function
  })

}

abstract class TrainInfo() {
  def number:Int
}
case class InterCityExpress(number: Int, hasWifi: Boolean=false) extends TrainInfo//promoted number param overrides number def automatically
case class InterCity(number: Int)extends TrainInfo
case class RegionalExpress(number: Int)extends TrainInfo

