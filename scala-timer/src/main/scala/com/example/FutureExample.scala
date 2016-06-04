package com.example

import scala.concurrent.{Await, Future, future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object FutureExample {

  def main(args: Array[String]) {

    println("Main:"+Thread.currentThread().getId)


    val f= Future{
      println("loc:"+Thread.currentThread().getId)
      Thread.sleep(500)
      1+1
    }

    f.onComplete{
      case Success(x) => {
        println("loc:"+Thread.currentThread().getId)
        println(s"x:$x")
      }
      case Failure(e) => e.printStackTrace
    }

      val npm =longRunningComputation(10)
   npm.onSuccess{
     case x=> {
       println("method0:"+Thread.currentThread().getId)
       //println(s"long:$x")
     }
   }

    npm.onSuccess{
      case x=> {
        sleep(50)
        println("method1:"+Thread.currentThread().getId)
    //    println(s"long:$x")
      }
    }

    longRunningComputation(10).onSuccess{
      case x=> {
        println("method2:"+Thread.currentThread().getId)
      //  println(s"long:$x")
      }
    }

    longRunningComputation(10).onSuccess{
      case x=> {
        println("method3:"+Thread.currentThread().getId)
        //println(s"long:$x")
      }
    }

   Thread.sleep(3 * 1000)

  }

  def longRunningComputation(i: Int): Future[Int] = Future {
    println("method call:"+Thread.currentThread().getId)
    sleep(100)
    i + 1
  }

  def sleep(ml: Int) {
    Thread.sleep(ml)
  }
}
