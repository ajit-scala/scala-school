import scala.annotation.tailrec


val l = List(1,2,3,4,5)
def sum(nums:List[Int]):Int = {
  @tailrec
  def sumNums(l: List[Int], acc: Int): Int = {
    if (l == Nil)
      acc
    else
      sumNums(l.tail, acc + l.head)
  }

  sumNums(l, 0)
}
sum(l)