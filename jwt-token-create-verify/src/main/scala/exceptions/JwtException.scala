package exceptions

case class JwtException(message: String, cause: Throwable) extends Exception(message, cause)
