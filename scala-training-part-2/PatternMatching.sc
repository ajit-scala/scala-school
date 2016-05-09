
class Dagobert(val name:String) {}
object Dagobert {
  def apply(name: String): Dagobert = new Dagobert(name)
}
val gladstone = "Gladstone"
def whoIs(any: Any): String = any match {
  case "Donald"    => "An angry duck"
  case   Dagobert    => "A very rich duck"
  case d:Dagobert    => s"A very-2 rich duck ${d.name}"
  case `gladstone` => "A very lucky duck"
  case i:Int => s"a €$i duck"
}
whoIs(Dagobert("Obama"))
whoIs(500)

def whoIs2(any123: Any): String = any123 match {
  case (name: String, _) =>
    s"Two things, the first named $name with length ${name.length}"
}

whoIs2(("22",33))

case class Address(street: String, city: String, country: String)
case class Person(name: String, age: Int, address: Address)
val alice = Person("Alice", 25, Address("1 Scala Lane", "Chicago", "USA"))
val alice2 = Person("Alice", 25, Address("1 Scala Lane", "Chicago", "USA"))
val bob = Person("Bob", 29, Address("2 Java Ave.", "Miami", "USA"))
val charlie = Person("Charlie", 32, Address("3 Python Ct.", "Boston", "USA"))

for (person <- Seq(alice,alice2, bob, charlie)) {
  person match {
    case Person("Alice", 25, Address(_, "Chicago", _)) => println("Hi Alice!")
    case Person("Bob", 29, Address("2 Java Ave.", "Miami", "USA")) =>
      println("Hi Bob!")
    case Person(name, age, _) =>
      println(s"Who are you, $age year-old person named $name?")
  }
}

case class Duck(name:String)
def whoIsx(any: Any): String = any match {
  //case Duck(name) => s"A duck called $name"
  case d:Duck => s"A duck called ${d.name}"
}
whoIsx(Duck("dduucckk"))

def whoIs3(any: Any): String = any match {
  case Seq(Duck, _*) => s"Some things, a duck called noname first"
  case Seq(Duck(name), _*) => s"Some things, a duck called $name first"
}

whoIs3(Seq(Duck("Abc"),Duck("Abc1"),Duck("Abc2")))
whoIs3(Seq(Duck,Duck("Abc"),Duck("Abc1")))
//whoIs3("sdfsda")

def whoIs4(any: Any): String = any match {
  case duck @ Duck(name) if name.endsWith("Duck") =>
          s"${duck} called Duck"
}
whoIs4(Duck("myDuck"))

///--patterns in for--
for (pair <- Map(1 -> "a", 2 -> "b") if pair._1 == 2)
  yield pair._2
for ((2, value) <- Map(1 -> "a", 2 -> "b"))
  yield value