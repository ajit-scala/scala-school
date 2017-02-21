package com.abc.example

import org.slf4j.LoggerFactory

/**
  * Created by achahal on 21/02/2017.
  */
object Hello {
  def main(args: Array[String]): Unit = {

    val log = LoggerFactory.getLogger(getClass)
    val log1 = LoggerFactory.getLogger("myLogName")
    log.error("test 1234 78")
    log.info("test info 1234...")
    log1.error("test 1234 567 789")


    val jsonLogger = LoggerFactory.getLogger("JsonLogger")
    jsonLogger.info("{'name':'this is example json'}")

    println("Hello, world!")
  }
}
