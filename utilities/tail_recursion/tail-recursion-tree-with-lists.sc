import scala.annotation.tailrec

case class Category(name:String, children: List[Category])

val lists = List(
  Category("1",
    List(Category("1.1",
      List(Category("1.2", Nil))
    ))
  )
  ,Category("2", List(Category("2.1", Nil),Category("2.1.1", List(Category("2.1.2", Nil))))),
  Category("3",
    List(Category("3.1", Nil),Category("3.1.1", Nil))
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
def childCount2(cats: List[Category], acc:Int): Int =
cats match {
  case Nil => acc
  case x :: xs => childCount2(x.children ++ xs, acc + 1)
}



//val count = childCount(lists.toStream, 0)
val count2 = childCount2(lists, 0)


(1 until 5)
(1 to 5)


//
def generate(depth: Int, children: Int): List[Category] = {
  if(depth == 0) Nil
  else (0 until children).map(i => Category("abc", generate(depth - 1, children))).toList
}
val cats = generate(8,3)
//generates 9840 test cats instances in following structur
//List(Category(abc,List(Category(abc,List(Category(abc,List()))))))
//List(Category(abc,List(Category(abc,List()))))
