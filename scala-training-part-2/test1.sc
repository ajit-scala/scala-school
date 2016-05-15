

Option("Donald").map( x=> x.length)

(for {
  name <- Option("Donald") if name.length < 8
  last <- Option("Duck")
} yield s"$name $last").getOrElse("no Duck")

