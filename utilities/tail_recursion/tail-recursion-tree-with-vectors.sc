import scala.annotation.tailrec

case class Category(name:String, children: Vector[Category])

val lists = Vector(
  Category("1",
    Vector(Category("1.1",
      Vector(Category("1.2", Vector.empty))
    ))
  )
  ,Category("2", Vector(Category("2.1", Vector.empty),Category("2.1.1", Vector(Category("2.1.2", Vector.empty))))),
  Category("3",
    Vector(Category("3.1", Vector.empty),Category("3.1.1", Vector.empty))
  )
)


//@tailrec
//def childCount(cats:Stream[Category], acc:Int):Int =
//  cats match {
//    case Stream.Empty => acc
//    case head #:: tail =>
//      //println(head.name)
//      childCount(tail ++ head.children, acc+1)
//  }
@tailrec
def childCount2(cats: Vector[Category], acc:Int): Int =
cats match {
  case x +: xs => childCount2(x.children ++ xs, acc + 1)
  case _ => acc
}



//val count = childCount(lists.toStream, 0)
val count2 = childCount2(lists, 0)


