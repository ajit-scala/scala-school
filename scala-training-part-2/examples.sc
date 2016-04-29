import de.heikoseeberger.scalatrain._
import de.heikoseeberger.scalatrain.Data._

/*val planner = new JourneyPlanner(Set(ice42, ic41))

planner.trainsAt(muc).foreach(println)*/

/*
val Thing = new AnyRef{
  def apply(){1+1}
}
Thing()*/

1.to(2)
1 to 2

for {
  x <- 1 to 2
}yield x

//for is just snytax for map and flatmap filters
/*
val jp = new JourneyPlanner(Set(ic41,ice42))

jp.departuresAt2(muc).foreach(println)


Time(2,1)

Time(2,10) > Time(3,10)
Time(2,10) < Time(3,10)

Vector(1,2,3,4,5).sliding(2).foreach(println)
Vector(1,2,3,4,5).sliding(2,3).foreach(println)
*/
ice42

val xx:Time = ic41.departureTime(muc)

xx

ic43wifi.stations