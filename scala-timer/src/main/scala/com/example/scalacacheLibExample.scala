package com.example

import com.sun.net.httpserver.Authenticator.Success
import net.sf.ehcache.{Cache, CacheManager}

import scalacache.ScalaCache
import scalacache.ehcache.EhcacheCache
import concurrent.duration._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by ajit on 03.06.16.
  */
case class Test(name:String)


object scalacacheLibExample {
  def main(args: Array[String]): Unit = {
    val cm:CacheManager  = CacheManager.getInstance()

    //3. Get a cache called "cache1"
    val cache:Cache  = cm.getCache("cache1")
    implicit val scalaCache = ScalaCache(EhcacheCache(cache))

    scalaCache.cache.put("t2", Test("Vinit"), Option(15 seconds))
    println("::Results::")

    var x: Future[Option[Test]] = scalaCache.cache.get[Test]("t2")


    x.onSuccess {
      case xx => println(xx.getOrElse("noting "))
    }
    Thread.sleep(10 * 1000)
     x = scalaCache.cache.get[Test]("t2")

    x.onSuccess {
      case xx => println(xx.getOrElse("noting "))
    }
Thread.sleep(10 * 1000)
    x= scalaCache.cache.get[Test]("t2")

    x.onSuccess {
      case xx => println(xx.getOrElse("noting "))
    }


  }
}
