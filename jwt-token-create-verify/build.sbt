
name := """jwt-token-create-verify"""

version := "1.0"

scalaVersion := "2.11.7"


libraryDependencies ++= Seq(
  "ch.qos.logback"  % "logback-classic" % "1.1.7",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
  "com.nimbusds" % "nimbus-jose-jwt" % "4.34.2",
  "org.scalaj" %% "scalaj-http" % "2.3.0",
  "net.codingwell" %% "scala-guice" % "4.0.1",
  "commons-codec" % "commons-codec" % "1.10",

  "org.scalatest" %% "scalatest" % "2.2.4" % "test" ,
  "org.mockito" % "mockito-core" % "2.7.21" %  "test"
)

javaOptions in Test ++= Seq(
  "-Dconfig.resource=test.conf",
  "-Dlogger.resource=as24local-logger.xml"
)

// Uncomment to use Akka
//libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.11"

