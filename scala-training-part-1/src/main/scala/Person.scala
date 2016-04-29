class Person(val firstName: String, val lastName: String) {

  def changeLastName(newLastName: String): Person =
    new Person(firstName, newLastName)
}

//every block is an expression and the last expression in a block is the value it returns
/*e.g.
{
  val t1 = new de.ajit.scalatrain.Train("ICE",44)
  val t2 = new de.ajit.scalatrain.Train("IC",45)
  t1+"   "+t2
}*/

//method definition
//with and wihtout ()
//

object Person {

  final val TheBoss = new Person("Martin", "Odersky") // Constant

  private val namePattern = """(\w+)\s(\w+)""".r

  def fromName(name: String): Person = {
    val namePattern(first, last) = name // pat.mat. is covered later
    new Person(first, last)
  }
}