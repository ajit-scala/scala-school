package com.example
object UserType extends Enumeration {
  type UserType = Value
  val Private = "P"
  val Dealer = "D"
}





object Hello {
  def main(args: Array[String]): Unit = {
    println("Hello, world!")
    object WeekDay extends Enumeration {
      type WeekDay = Value
      val Mon, Tue, Wed, Thu, Fri, Sat, Sun = Value
    }
    import WeekDay._

    def isWorkingDay(d: WeekDay) = ! (d == Sat || d == Sun)

    WeekDay.values filter isWorkingDay foreach println
    WeekDay.values.map(x => println(x.toString))


    println(WeekDay.withName("Mon")+"-")

    import UserType._
    println(UserType.values(Private))
  }
}
