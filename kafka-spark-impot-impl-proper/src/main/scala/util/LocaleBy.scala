package util

import java.util.Locale

object LocaleBy {

  def unapply(locale: Locale) = Some(locale.getLanguage, locale.getCountry)

}
