case class Duck(name:String)

val namePattern = """(\w+)\s(\w+)""".r

def whoIs(any: Any): String = any match {
  case Duck(namePattern(first, last)) =>
    s"A duck with full name $first $last"
}

whoIs(Duck("abc"))