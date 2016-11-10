package com.example

import kamon.Kamon

//object Hello {
//  def main(args: Array[String]): Unit = {
//    println("Hello, world!")
//
//  }
//}
object GetStarted extends App {
  Kamon.start()

  val someHistogram = Kamon.metrics.histogram("some-histogram")
  val someCounter = Kamon.metrics.counter("some-counter",Map("tag1"->"1234","tag2"->"7890"))

  someHistogram.record(42)
  someHistogram.record(50)
  someCounter.increment()

  // This application wont terminate unless you shutdown Kamon.
  //Kamon.shutdown()
}