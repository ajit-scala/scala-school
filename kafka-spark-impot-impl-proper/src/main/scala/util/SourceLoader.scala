package util

import java.nio.file.{ Files, Path }

import scala.io.Source
import scala.util.Try

trait SourceLoader {

  def loadSource(path: Path): Try[String] = Try {
    val is = Files.newInputStream(path)
    try {
      Source.fromInputStream(is)("UTF-8").getLines().mkString("\n")
    } finally {
      is.close()
    }

  }

  // TODO monadic failure transformtation
  //  def loadSource(uri: URI): Try[String] = Try {
  //    try {
  //      Source.fromURI(uri).getLines().mkString("\n")
  //    } catch {
  //      case e: Throwable => throw new CategorizationException(GENERAL, s"Couldn't load $uri", e)
  //    }
  //  }

}
