package de.ajit.scalatrain
import org.scalatest.{ Matchers, WordSpec }

/**
 * Created by achahal on 13/04/16.
 */
class TrainSpec extends WordSpec with Matchers {
  "Creating a Train" should {
    "throw an IllegalArgumentException for an empty kind" in {
      an[IllegalArgumentException] should be thrownBy Train("", 44, List(
        Stop(Station("st1"), Time(1, 10), Time(2, 0)),
        Stop(Station("st2"), Time(3, 10), Time(4, 0))
      ))

    }
    "station method must return all stations from all stops" in {
      Train("ICE", 44, List(
        Stop(Station("st1"), Time(1, 10), Time(2, 0)),
        Stop(Station("st2"), Time(3, 10), Time(4, 0))
      )).stations shouldBe List(Station("st1"), Station("st2"))
    }

  }
}
