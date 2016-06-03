name := """scala-timer"""

version := "1.0"

scalaVersion := "2.11.7"

// Change this to another test framework if you prefer
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4" % "test"

// Uncomment to use Akka
//libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.11"

// http://mvnrepository.com/artifact/net.sf.ehcache/ehcache-core
//libraryDependencies += "net.sf.ehcache" % "ehcache-core" % "2.4.2"
libraryDependencies += "net.sf.ehcache" % "ehcache" % "2.9.0"

libraryDependencies +=  "org.slf4j" % "slf4j-simple" % "1.7.21"
libraryDependencies += "com.github.cb372" %% "scalacache-ehcache" % "0.9.1"