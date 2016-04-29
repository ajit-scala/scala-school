package de.ajit.scalatrain

/**
 * Created by achahal on 13/04/16.
 */
object Data {
  val t1 = Train("ICE1", 44, List(
    Stop(Station("Munich"), Time(1, 10), Time(2, 0)),
    Stop(Station("Fankfurt"), Time(3, 10), Time(4, 0))
  ))
  val t2 = Train("ICE2", 45, List(
    Stop(Station("Hamburg"), Time(5, 10), Time(6, 0)),
    Stop(Station("Berlin"), Time(7, 10), Time(8, 0))
  ))

  val t3 = Train("ICE3", 46, List(
    Stop(Station("Munich"), Time(9, 10), Time(10, 0)),
    Stop(Station("Hamburg"), Time(11, 10), Time(12, 0))
  ))

  val muc = Station("München")
  val ing = Station("Ingolstad")
  var nur = Station("Nürnberg")
  var wur = Station("Würzburg")
  var fra = Station("Frankfurt")
  var aug = Station("Augsburg")

  val ice42Schedule = List(
    Stop(muc, Time(9, 0), Time(9, 10)),
    Stop(nur, Time(10, 5), Time(10, 10)),
    Stop(wur, Time(11, 55), Time(12, 5)),
    Stop(fra, Time(14, 15), Time(14, 30))
  )

  val ice42 = Train("ICE", 42, ice42Schedule)

  val ic41Schedule = List(
    Stop(muc, Time(8, 30), Time(8, 40)),
    Stop(nur, Time(9, 35), Time(9, 40)),
    Stop(wur, Time(11, 50), Time(11, 55))
  )

  val ic41 = Train("IC", 41, ic41Schedule)

}
