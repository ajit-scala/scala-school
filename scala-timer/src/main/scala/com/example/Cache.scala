package com.example

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

case class Test(name:String)


object CacheExample {
  def main(args: Array[String]): Unit = {
    println("Hello, world!")
    //1. Create a cache manager
    val cm:CacheManager  = CacheManager.getInstance()

    //2. Create a cache called "cache1"
    //    cm.addCache("cache1")

    //3. Get a cache called "cache1"
    val cache:Cache  = cm.getCache("cache1")

    //4. Put few elements in cache
    cache.put(new Element("t1",Option(Test("ajit"))))
    val ele:Element  = cache.get("t1")

    println(ele)
    val t = ele.getObjectValue.asInstanceOf[Test]
    println(t.name)

    Thread.sleep(5*1000)
    val ele2:Element  = cache.get("t1")

    println(ele2)
    val t2 = ele2.getObjectValue.asInstanceOf[Test]
    println(t2.name)

    Thread.sleep(5*1000)
    val ele3:Element  = cache.get("t1")//will be null here as timeout is 10 sec in config file

    println(ele3)
    val t3 = ele3.getObjectValue.asInstanceOf[Test]
    println(t3.name)
  }
}