import controllers.HomeController
import org.scalatestplus.play.PlaySpec
import play.api.test.FakeRequest
import play.api.mvc.Result
import play.api.test.Helpers._

import scala.concurrent.Future

/**
  * Created by achahal on 21/12/2016.
  */
class UnitTestsSpec extends PlaySpec{

  "home controller test" should{
    "test home action" in  {
      val hc = new HomeController()
      val result: Future[Result] = hc.index().apply(FakeRequest())
      val bodyText: String = contentAsString(result)
//      bodyText mustBe "ok"
      contentAsString(result) must include ("Your new application is ready.")

    }
    "test json action" in {
      val hc = new HomeController()
      val result: Future[Result] = hc.postIndex2().apply(FakeRequest())
      val bodyText: String = contentAsString(result)
    }
  }

}
