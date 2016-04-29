case class Name(first: String, last: String)
case class Duck(name: String)
object Dagobert
val gladstone = "Gladstone"

object MyPattern {
  def unapply(v : AnyRef): Option[String] =
    if (v == "Andreas")
      Some(v.toString)
    else
      None
}

def whoIs(any: Any): String = any match {
  case MyPattern(s) => s"That's $s!"
  case Duck(name) => s"A duck called $name"
  case "Donald"    => "An angry duck"
  case Dagobert    => "A very rich duck"
  case `gladstone` => "A very lucky duck"
  case age: Int => if (age >= 100) "Pretty old" else "Still young"
  case (name: String, _) =>
    s"Two things, the first named $name with length ${name.length}"
  case _ => "Unknown creature"
}

val n = Name("Donald", "Duck")
Name.unapply(n)

whoIs(Duck("Donald"))
whoIs(("Andreas", 12))
whoIs(99)
whoIs(100)
whoIs("Donald")
whoIs(Dagobert)
whoIs("Gladstone")
whoIs("Andreas")