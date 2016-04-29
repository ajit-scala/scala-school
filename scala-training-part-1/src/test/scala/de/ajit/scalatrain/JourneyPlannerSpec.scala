package de.ajit.scalatrain

import org.scalatest.{ Matchers, WordSpec }

/**
 * Created by achahal on 13/04/16.
 */
class JourneyPlannerSpec extends WordSpec with Matchers {
  import de.ajit.scalatrain.Data._ //test data from data singleton object, all members are imported

  "Creating a JourneyPlanner" should {
    "Give all trains of a station" in {
      new JourneyPlanner(Set(t1, t2, t3)).trainsAt(Station("Munich")) shouldBe Set(t1, t3)
    }
    "-ve of Give all trains of a station" in {
      new JourneyPlanner(Set(t1, t2, t3)).trainsAt(Station("Munich")) should not contain t2
    }
  }
  val fullPlanner = new JourneyPlanner(Set(ice42, ic41))

  "calling stations" should {
    "When called with ICE 42 and IC 41 return all stations" in {
      fullPlanner.stations shouldBe Set(muc, ing, nur, wur, fra)
    }
    "When called with IC 41 does not return frankfurt" in {
      val planner = new JourneyPlanner(Set(ic41))
      planner.stations should not contain fra
    }
  }
  "calling trainAt" should {
    "for a full journey planner" should {
      "return IC 41 when called with ingolstadt" in {
        fullPlanner.trainsAt(ing) shouldBe Set(ic41)
      }
    }
  }
}

import org.scalatest.{ Matchers, WordSpec }
import de.ajit.scalatrain.Data._

class JourneyPlannerSpec2 extends WordSpec with Matchers {
  def fullPlanner = new JourneyPlanner(Set(ic41, ice42))

  "Calling stations" should {
    "when called with ICE 42 and IC 41 return all stations" in {
      fullPlanner.stations shouldBe Set(muc, ing, nur, wur, fra)
    }

    "when called with IC 41 does not return frankfurt" in {
      val planner = new JourneyPlanner(Set(ic41))

      planner.stations should not contain fra
    }
  }

  "Calling trainsAt" should {

    "for a full journey planner" should {
      "return IC 41 when called with Ingolstad" in {
        fullPlanner.trainsAt(ing) shouldBe Set(ic41)
      }

      "return ICE 42 when called with Frankfurt" in {
        fullPlanner.trainsAt(fra) shouldBe Set(ice42)
      }

      "return ICE 42 and IC 41 when called with München" in {
        fullPlanner.trainsAt(muc) shouldBe Set(ice42, ic41)
      }

      "returns empty set when called with Augsburg" in {
        fullPlanner.trainsAt(aug) shouldBe Set.empty
      }
    }
  }
}