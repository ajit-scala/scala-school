name := """sbt-projects-train"""

version := "1.0"

scalaVersion := "2.11.7"

// Change this to another test framework if you prefer
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4" % "test"

// Uncomment to use Akka
//libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.11"

sampleKeyC in ThisBuild := "C: in build.sbt scoped to ThisBuild"

sampleKeyD := "D: in build.sbt"

val sst = TaskKey[String]("usrHome","get user home from sys")


sst := System.getProperty("user.home")



val ss = taskKey[String]("test")

ss := {
  sst.value
}

//System.getenv().toString
