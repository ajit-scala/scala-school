package util

import java.nio.file.{ Path, Paths }

import scala.language.implicitConversions

object PathConversions {

  implicit def string2Path(path: String): Path = Paths.get(getClass.getClassLoader.getResource(path).toURI)

}
