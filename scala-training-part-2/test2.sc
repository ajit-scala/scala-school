val list = 1 +: 2 +: 3 +: 4 +: Nil

def checkY(y: Int) = {
  for {
    x <- Seq(99, 100, 101)
  } {
    val str = x match {
      case `y` => "found y!"
      case i: Int => "int: "+i
    }
    println(str)
  }
}
checkY(100)