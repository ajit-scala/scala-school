name := """kafka-producer-consumer"""

version := "1.0"

scalaVersion := "2.11.8"
val awsVersion = "1.10.50"

// Change this to another test framework if you prefer
//libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4" % "test"
val sparkStreamingDependencies = Seq(
  ("org.apache.spark" %% "spark-core" % "1.6.0" % "provided"),
  "org.apache.spark" %% "spark-streaming" % "1.6.0",
  "org.apache.spark" %% "spark-streaming-kafka" % "1.6.1"
)

// Uncomment to use Akka
//libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.11"
val awsDependencies = Seq(
  "com.amazonaws" % "aws-java-sdk-s3" % awsVersion
)

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  "org.slf4j" % "slf4j-api" % "1.7.14", // logging
  //  "ch.qos.logback" % "logback-classic" % "1.1.3",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0",
  "org.scalatest" %% "scalatest" % "2.2.6" % "test", // test
  "com.sun.xml.bind" % "jaxb-xjc" % "2.2.4-1",
  "net.jcazevedo" %% "moultingyaml" % "0.2", // yaml parsing
  "org.scalactic" %% "scalactic" % "2.2.6",
  "com.typesafe" % "config" % "1.3.0",
  "org.mockito" % "mockito-all" % "1.10.19" % "test"
) ++ awsDependencies ++ sparkStreamingDependencies

dependencyOverrides ++= Set(
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.4.4"
)