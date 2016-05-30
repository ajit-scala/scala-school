package util

object option {

  implicit class RichOption[T](o: Option[T]) {
    def contains(t: T) = o match {
      case Some(v) if v == t => true
      case _ => false
    }
  }

  implicit class RichIntOption(o: Option[Int]) {
    def getOrZero = o.getOrElse(0)
  }

  implicit class RichDoubleOption(o: Option[Double]) {
    def getOrZero = o.getOrElse(0)
  }

  implicit class RichStringOption(o: Option[String]) {
    def getOrEmptyStr = o.getOrElse("")
  }

}
