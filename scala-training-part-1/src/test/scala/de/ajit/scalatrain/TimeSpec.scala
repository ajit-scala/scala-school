package de.ajit.scalatrain

import org.scalatest.{ Matchers, WordSpec }

/**
 * Created by achahal on 13/04/16.
 */
class TimeSpec extends WordSpec with Matchers {
  "Calling fromMinutes" should {
    "return Time(0, 10) for 10 minutes" in {
      Time.fromMinutes(10) shouldBe Time(0, 10)
    }

    "return Time(1, 10) for 70 minutes" in {
      Time.fromMinutes(70) shouldBe Time(1, 10)
    }
  }

  "Calling minus or -" should {

    "return 0 for equal Times" in {
      Time(20, 20) - Time(20, 20) shouldBe 0
    }

    "return 60 for Time(1, 10) and Time(0, 10)" in {
      Time(1, 10) - Time(0, 10) shouldBe 60
    }
  }

  "just testing matchers" should {

    val numbers = List(1,2,3)
    val numbersE = List()

    "1 should not be 2" in {
      "hello" shouldBe "hello"
      1 should not be 2
      "hello" should startWith("hell")
      numbersE shouldBe empty
      numbers should contain allOf(1, 2, 3)
      numbers should contain inOrder(1, 2, 3)
      an[Exception] should be thrownBy sys.error("BOOM")
    }

  }

}