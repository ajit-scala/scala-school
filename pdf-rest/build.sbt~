name := """pdf-rest"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala) //dependsOn(sPdf)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

//libraryDependencies += "io.github.cloudify" %% "spdf" % "1.3.1"
//libraryDependencies += "io.github.cloudify" %% "spdf" % "1.3.3"



