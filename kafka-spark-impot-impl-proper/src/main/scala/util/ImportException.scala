package util

sealed trait ImportContext

case object AGENDA_GROUPS extends ImportContext

case object RULES extends ImportContext

case object EXPECTATIONS extends ImportContext

case object LISTINGS_IMPORT extends ImportContext

case object LISTINGS_JSON_PARSE extends ImportContext

case object GENERAL extends ImportContext

class ImportException(context: ImportContext, msg: String, throwable: Throwable = null) extends RuntimeException(msg, throwable) {

  override def getMessage = {
    val wrappedMsg = if (throwable != null) throwable.getMessage else ""

    s"ClassifiedImport EXCEPTION / $context -> ${super.getMessage} \n  $wrappedMsg"
  }

}
