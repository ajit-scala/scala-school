name := """kafka-akka-stream-consumer"""

version := "1.0"

scalaVersion := "2.11.8"

// Change this to another test framework if you prefer
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4" % "test"
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.5.10"

// Uncomment to use Akka
//libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.11"
libraryDependencies += "com.typesafe.akka" %% "akka-stream-kafka" % "0.13"


