package controllers

import javax.inject._

import play.api._
import play.api.mvc._
import io.github.cloudify.scala.spdf._
import java.io._
import java.net._

import play.twirl.api.Html

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject() extends Controller {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def pdf=Action {
    val pdf = Pdf(new PdfConfig {
      //orientation := Portrait
      //pageSize := "Letter"
      //marginTop := "1in"
      //marginBottom := "1in"
      //marginLeft := "1in"
      //marginRight := "1in"
      javascriptDelay := 2000
      //pageHeight := "1920"
      //pageWidth := "1080"
      useXServer := Option(true)
      viewportSize := "1280x1024"
    })

    val page = <html><body><h1>Hello World</h1></body></html>

    // Save the PDF generated from the above HTML into a Byte Array
    val outputStream = new ByteArrayOutputStream
    pdf.run(page, outputStream)


    // Save the PDF of Google's homepage into a file
    //pdf.run(new URL("http://www.google.com"), new File("google.pdf"))
    pdf.run(new URL("http://localhost:9000/chart"), new File("google.pdf"))
    Ok.sendFile(new java.io.File("google.pdf"))
//Ok(Html("works"))

  }

  def chart = Action {
    Ok(views.html.chart())
  }
}
