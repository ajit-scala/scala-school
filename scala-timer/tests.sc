import scala.concurrent.{Await, Future}
import concurrent.duration._

println(fib(1, 2))

    def fib(prevPrev: Int, prev: Int) {

      val x = Future {
        prevPrev + prev
      }
      /*.onSuccess {
          case x: Int => {
            println(x)
            fib(prev, x)
          }
        }*/
      Await.ready(x, 10.minutes).onSuccess {
        case x: Int => {
          println(x)
        }
      }
      fib(prev, 5)
    }