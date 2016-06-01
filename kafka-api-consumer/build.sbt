name := """kafka-api-consumer"""

version := "1.0"
scalaVersion := "2.11.8"
libraryDependencies ++= Seq(
  "org.apache.kafka" % "kafka-clients" % "0.9.0.1",
  "org.slf4j" % "slf4j-simple" % "1.7.21"
)
