package de.heikoseeberger.scalatrain

object Data {
  val muc = Station("München")
  val ing = Station("Ingolstad")
  var nur = Station("Nürnberg")
  var wur = Station("Würzburg")
  var fra = Station("Frankfurt")
  val aug = Station("Augsburg")

  val ice42schedule = List(
    Stop(muc, Time(9, 0), Time(9, 10)),
    Stop(nur, Time(10, 5), Time(10, 10)),
    Stop(wur, Time(11, 55), Time(12, 5)),
    Stop(fra, Time(14, 15), Time(14, 30))
  )

  val ice42 = Train(InterCityExpress(42), ice42schedule)

  val ic41schedule = List(
    Stop(muc, Time(8, 30), Time(8, 40)),
    Stop(ing, Time(9, 10), Time(9, 15)),
    Stop(nur, Time(9, 35), Time(9, 40)),
    Stop(wur, Time(11, 50), Time(11, 55))
  )

  val ic41 = Train(InterCity(41), ic41schedule)
  val ic43wifi = Train(InterCityExpress(41,true), ic41schedule)

}
