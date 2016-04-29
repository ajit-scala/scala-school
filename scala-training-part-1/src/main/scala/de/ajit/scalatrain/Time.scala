package de.ajit.scalatrain

/**
 * Created by achahal on 13/04/16.
 */
//case class Time(hours: Int=0, minutes: Int=0) {
case class Time(val hours: Int = 0, val minutes: Int = 0) {
  //"Check preconditions: hours must be within [0, 24)!"
  //"Check preconditions: minutes must be within [0, 60)!"
  require(hours >= 0 && hours < 24, "Hours must be within 0 and 24")
  require(minutes >= 0 && minutes < 60, "minutes must be within 0 and 60")

  def minus(that: Time): Int = {
    val thisMinutes = this.hours * 60 + this.minutes
    val thatMinutes = that.hours * 60 + that.minutes
    thisMinutes - thatMinutes
  }

  //local method
  def minus2(that: Time): Int = {
    def asMinutes(time: Time) = time.hours * 60 + time.minutes
    asMinutes(this) - asMinutes(that)
    // or this.minus(that)
  }

  def -(that: Time): Int = {
    def asMinutes(time: Time) = time.hours * 60 + time.minutes
    asMinutes(this) - asMinutes(that)
  }
}
object Time {
  def fromMinutes(minutes: Int): Time = new Time(minutes / 60, minutes % 60)
}
