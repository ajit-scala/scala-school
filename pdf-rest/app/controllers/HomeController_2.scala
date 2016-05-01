package controllers

import javax.inject._
import java.io._

import play.api._
import play.api.libs.json.Json
import play.api.libs.ws.ning.NingWSClient
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController2 @Inject() extends Controller {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

//  def index2 = Action {
//
//    val wsClient = NingWSClient()
//    wsClient
//      .url("http://jsonplaceholder.typicode.com/comments/1")
//      .withQueryString("some_parameter" -> "some_value", "some_other_parameter" -> "some_other_value")
//      .withHeaders("Cache-Control" -> "no-cache")
//      .get()
//      .map { wsResponse =>
//        if (! (200 to 299).contains(wsResponse.status)) {
//          sys.error(s"Received unexpected status ${wsResponse.status} : ${wsResponse.body}")
//        }
//        println(s"OK, received ${wsResponse.body}")
//        println(s"The response header Content-Length was ${wsResponse.header("Content-Length")}")
//      }
//    Ok(Json.obj("test" -> "this is test"))
//  }

}
