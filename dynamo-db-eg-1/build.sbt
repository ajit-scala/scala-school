name := """dynamo-db-eg-1"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
)

libraryDependencies += "com.amazonaws" % "aws-java-sdk" % "1.11.49"
libraryDependencies += "com.amazonaws" % "aws-java-sdk-dynamodb" % "1.11.49"
