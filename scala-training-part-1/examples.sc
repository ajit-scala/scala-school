import de.ajit.scalatrain._
/*
val t1 = new Train("ICE",44)
  val t2 = new Train("IC",45)

  f"${t1.kind} ${t1.number}"

  {
    val t1 = new Train("ICE",44)
    val t2 = new Train("IC",45)
    t1+"   "+t2
  }

  new Time(2,10) - (new Time(1,0))

  val x1 = new Time(2,10)
  val x2 = (new Time(1,0))

  x1 - x2
  x1 minus2 x2

  val np = new Time(minutes = 20)
  np.hours
  np.minutes
  class abc( name:String) {
    def test(): String = {
      name + "ss"
    }
  }
new abc("aaa").test
val rr = Time.fromMinutes(20)
rr.hours
rr.minutes

 Train("ICE",5)

val x = new Time(5,200)
val y = new Time(5,200)*/
Train("ICE", 44, List(
  Stop( Station("st1"),Time(1,10),Time(2,0)),
  Stop( Station("st2"),Time(3,10),Time(4,0))
)).stations

val t1 = Train("ICE", 44, List(
  Stop( Station("st1"),Time(1,10),Time(2,0)),
  Stop( Station("st2"),Time(3,10),Time(4,0))
))
val t3 = Train("ICE3", 44, List(
  Stop( Station("st1"),Time(1,10),Time(2,0)),
  Stop( Station("sx2"),Time(3,10),Time(4,0))
))
val t2 = Train("ICE2", 44, List(
  Stop( Station("st21"),Time(1,10),Time(2,0)),
  Stop( Station("st22"),Time(3,10),Time(4,0))
))
new JourneyPlanner(Set(t1,t2)).stations
new JourneyPlanner(Set(t1,t2)).stationsWithMap
new JourneyPlanner(Set(t1,t2, t3)).trainsAt(Station("st1"))