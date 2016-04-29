package de.heikoseeberger.scalatrain

object Time {

  def fromMinutes(minutes: Int): Time = new Time(minutes / 60, minutes % 60)
  def isIncreasing(times: Seq[Time]): Boolean =
    times.length <= 1 || times.sliding(2).forall(times => times.head < times.last)
  //sliding gives pair of (n) and head is a common collection method to get 1st and last element of collection
}

case class Time(hours: Int = 0, minutes: Int = 0) extends Ordered[Time] {
  require(0 <= hours && hours < 24, "hours must be within [0, 24)!")
  require(0 <= minutes && minutes < 60, "minutes must be within [0, 60)!")

  def minus(that: Time): Int = {
    def asMinutes(time: Time) = time.hours * 60 + time.minutes
    asMinutes(this) - asMinutes(that)
  }

  def -(that: Time): Int = this.minus(that)

  override val toString:String = f"${hours}%02d:${minutes}%02d"

  override def compare(that: Time): Int = this-that
}

/*
* use cses
* Time(2,10) > Time(3,10)
* */

/* Trai
case class MyTestClass(time:Time) extends  Ordered[Time] with abc {
  override def compare(that: Time): Int = time-that
}

trait abc {val height:Int=10}*/

