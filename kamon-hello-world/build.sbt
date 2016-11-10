name := """kamon-hello-world"""

version := "1.0"

scalaVersion := "2.11.7"
val kamonVersion = "0.6.2"


val kamonDependencies = Seq(
  "io.kamon" %% "kamon-core" % kamonVersion,
  "io.kamon" %% "kamon-akka" % kamonVersion,
  "io.kamon" %% "kamon-jmx" % kamonVersion,
  "io.kamon" %% "kamon-datadog" % kamonVersion,
  "io.kamon" %% "kamon-log-reporter" % kamonVersion
)

libraryDependencies ++= Seq(
//  "io.dropwizard.metrics" % "metrics-core" % "3.1.2",
  "org.scalatest" %% "scalatest" % "3.0.0"  % "test",
  "org.joda" % "joda-convert" % "1.8.1"

)  ++ kamonDependencies

// Uncomment to use Akka
//libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.11"

