package com.example

object TimerExample {
  def main1(args: Array[String]): Unit = {
    println("Hello, world!")
    Timer(2000) { println("Timer went off") }
    println("Hello, world end!")
    while(true )  {
        Thread.sleep(1000)
      }
  }
}


class timer1{

}
object Timer{
  def apply(interval: Int, repeats: Boolean = true)(op: => Unit) {
    val timeOut = new javax.swing.AbstractAction() {
      def actionPerformed(e : java.awt.event.ActionEvent) = op
    }
    val t = new javax.swing.Timer(interval, timeOut)
    t.setRepeats(repeats)
    t.start()
  }
}