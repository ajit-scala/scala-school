package controllers

import javax.inject._

import com.gargoylesoftware.htmlunit.WebClient
import com.gargoylesoftware.htmlunit.html.HtmlPage
import play.api._
import play.api.mvc._
import java.io._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject() extends Controller {

  val js = "$(function(){$(\"#container\").highcharts({title:{text:\"Monthly Average Temperature\",x:-20},subtitle:{text:\"Source: WorldClimate.com\",x:-20},xAxis:{categories:[\"Jan\",\"Feb\",\"Mar\",\"Apr\",\"May\",\"Jun\",\"Jul\",\"Aug\",\"Sep\",\"Oct\",\"Nov\",\"Dec\"]},yAxis:{title:{text:\"Temperature (°C)\"},plotLines:[{value:0,width:1,color:\"#808080\"}]},tooltip:{valueSuffix:\"°C\"},legend:{layout:\"vertical\",align:\"right\",verticalAlign:\"middle\",borderWidth:0},series:[{name:\"Tokyo\",data:[7,6.9,9.5,14.5,18.2,21.5,25.2,26.5,23.3,18.3,13.9,9.6]},{name:\"New York\",data:[-.2,.8,5.7,11.3,17,22,24.8,24.1,20.1,14.1,8.6,2.5]},{name:\"Berlin\",data:[-.9,.6,3.5,8.4,13.5,17,18.6,17.9,14.3,9,3.9,1]},{name:\"London\",data:[3.9,4.2,5.7,8.5,11.9,15.2,17,16.6,14.2,10.3,6.6,4.8]}]})});";
  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def index2 = Action {

    val  wc = new WebClient()
    wc.getOptions.setJavaScriptEnabled(true);
    //val page:HtmlPage = wc.getPage("http://www.highcharts.com/demo/line-basic")

    val page:HtmlPage = wc.getPage("http://localhost:9000/chart")

    wc.waitForBackgroundJavaScript(3000);

    //page.executeJavaScript(js);

    //Thread.sleep(5000)

    val file:File = File.createTempFile("HtmlUnit", ".html" );

    file.delete(); // Delete is needed, because page.save can't overwrite it
    page.save(file)

    //Ok(views.html.index2(file.getAbsolutePath))//.as("text/plain")
    //Ok(views.html.index2(page.asXml()))//.as("text/plain")
    Ok(views.html.index2(page.asText()))//.as("text/plain")
  }

  def chart = Action {
    Ok(views.html.chart("Your new application is ready."))

  }

}
