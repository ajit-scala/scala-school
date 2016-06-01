package util

import scala.language.implicitConversions
import scala.util.{ Failure, Success, Try }
import scala.reflect.runtime.universe._

object TryConversions {

  implicit def mapTryValues2TryOfMap[A, B](xs: Map[A, Try[B]]): Try[Map[A, B]] = {
    val (ss, fs): (Map[A, Try[B]], Map[A, Try[B]]) = xs.partition { case (a, b) => b.isSuccess }
    if (fs.isEmpty) {
      Success(ss.map { case (a, b) => (a, b.get) })
    } else {
      val (_, b) = fs.head
      Failure[Map[A, B]](b.failed.get)
    }
  }

  implicit def mapSeqOfTryToTrySeq[T](xs: Seq[Try[T]]): Try[Seq[T]] = {
    val (ss: Seq[Success[T]] @unchecked, fs: Seq[Failure[T]] @unchecked) = xs.partition(_.isSuccess)
    if (fs.isEmpty) Success(ss map (_.get))
    else Failure[Seq[T]](fs.head.exception)
  }

  implicit def mapOptionToTry[T: TypeTag](option: Option[T]): Try[T] = option match {
    case Some(t) => Success(t)
    case None => Failure(new NoSuchElementException(s"value of type '${typeOf[T]}' is not available"))
  }

  implicit def mapEitherToTry[T](e: Throwable Either T): Try[T] = e match {
    case Right(r) => Success(r)
    case Left(t) => Failure(t)
  }

}
