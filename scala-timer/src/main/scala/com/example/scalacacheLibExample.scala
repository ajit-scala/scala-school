package com.example

import java.io.Serializable

import com.sun.net.httpserver.Authenticator.Success
import net.sf.ehcache.event.CacheEventListener
import net.sf.ehcache.{Cache, CacheManager, Ehcache, Element}

import scalacache.ScalaCache
import scalacache.ehcache.EhcacheCache
import concurrent.duration._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import util.{Failure, Success}

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
    cache.getCacheEventNotificationService.registerListener(abc())

    scalaCache.cache.put("t2", Test("Vinit"), Option(3 seconds))

    println("::Results::")

    var x: Future[Option[Test]] = scalaCache.cache.get[Test]("t2")



 x.map(opt=>opt.get).onSuccess{//best one ? :) map returns Future of test from futtue of option[test}
   case res => println(res)
 }
/*
    Thread.sleep(2 * 1000)


    val yy = for{
      x1<-x if x1.isDefined
    } yield x1.get

    yy.onSuccess{
      case y => println("y:"+y)
    }
    yy.onFailure{
      case y => println("yff:"+y)
    }
    Thread.sleep(2 * 1000)

    x.onSuccess {
      case xx => println(xx.getOrElse("noting "))
    }
    Thread.sleep(2 * 1000)
     x = scalaCache.cache.get[Test]("t2")

    x.onSuccess {
      case xx => println(xx.getOrElse("noting "))
    }

    Thread.sleep(3 * 1000)
    x= scalaCache.cache.get[Test]("t2")

    x.onSuccess {
      case xx => println(xx.getOrElse("noting "))
    }

*/
  }
}
case class abc() extends CacheEventListener {
  override def notifyElementExpired(ehcache: Ehcache, element: Element): Unit = {
    println("notifyElementExpired an element " + element)
  }

  override def notifyElementEvicted(ehcache: Ehcache, element: Element): Unit = {}

  override def notifyRemoveAll(ehcache: Ehcache): Unit = {}

  override def notifyElementPut(ehcache: Ehcache, element: Element): Unit = {
    println("added an element " + element)
  }

  override def dispose(): Unit = {}

  override def notifyElementRemoved(ehcache: Ehcache, element: Element): Unit = {
    println("notifyElementRemoved an element " + element)
  }

  override def notifyElementUpdated(ehcache: Ehcache, element: Element): Unit = {}
}