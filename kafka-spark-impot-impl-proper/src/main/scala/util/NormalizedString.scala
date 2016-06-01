package util

object normalizedString {

  import java.text.Normalizer.{ normalize => jnormalize, _ }

  private[util] val replacements = Seq(
    """[\p{InCombiningDiacriticalMarks}\p{IsM}\p{IsLm}\p{IsSk}]+""".r -> "",
    """'s""".r -> "",
    """ß""".r -> "ss",
    """ø""".r -> "o",
    """[^a-zA-Z0-9-]+""".r -> "-",
    """-+""".r -> "-"
  )

  trait PreProcess extends (String => String)

  private[util] object NonePreProcess extends PreProcess {

    def apply(s: String) = s

  }

  private[util] def jnorm(s: String) = jnormalize(s.trim.toLowerCase, Form.NFD)

  implicit class NormalizedString(s: String) {

    def normalize(implicit preProcess: PreProcess = NonePreProcess): String = {
      replacements.foldLeft(jnorm(preProcess(s))) {
        case (n, (r, rpl)) =>
          r.replaceAllIn(n, rpl)
      }.stripSuffix("-")
    }

    def normalizedContains(other: String): Boolean = s.normalize contains other.normalize

  }

}

