package controllers

import java.util.{Calendar, Date}
import javax.inject._

import play.api._
import play.api.mvc._
import play.api.cache._

import scala.concurrent.duration._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject() (cache:CacheApi) extends Controller {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action {
    println(cache.get("date"))

    val cd = cache.getOrElse[Date]("date", 10.seconds) {
      println("getting new date.....")
      val ld = Calendar.getInstance().getTime
      cache.set("date", ld, 10.seconds)
      ld
    }


    Ok(views.html.index("Your new application is ready." + cd))
  }

}


