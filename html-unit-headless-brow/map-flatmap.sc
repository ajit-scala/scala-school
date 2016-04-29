val maps = Vector(1,2,3,4)

maps.map(x=>x*2)

maps.map(x=>s"2$x")

maps.flatMap(x=>Vector(x*1,x*3,x+4))

val v = Vector("Hello", "world")
v.flatMap(x=>x.toLowerCase)

val c = "Ajit"(0)
c.toLower

val s="AJit"
s.toLowerCase

val v1 = Vector('H','E')
v1.flatMap(x=>Vector(x.toLower))


((n:Int)=>n+1)(77)
val cc= (n:Int)=>n+1

cc(44)

class Decorator(left: String, right: String) {
  def layout[A](x: A) = left + x.toString() + right
}
object FunTest extends App {
  def apply(f: Int => String, v: Int) = f(v)
  val decorator = new Decorator("[", "]")
  println(apply(decorator.layout, 7))
}
