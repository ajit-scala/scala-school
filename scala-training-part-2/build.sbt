organization := "de.heikoseeberger"
name         := "scala-train"
version      := "1.0.0"

scalaVersion   := "2.11.8"
scalacOptions ++= List(
  "-unchecked",
  "-deprecation",
  "-target:jvm-1.8"
)

libraryDependencies ++= List(
  "org.scalatest" %% "scalatest" % "2.2.6" % "test"
)

import scalariform.formatter.preferences._
scalariformPreferences := scalariformPreferences.value
  .setPreference(AlignSingleLineCaseStatements, true)
  .setPreference(AlignSingleLineCaseStatements.MaxArrowIndent, 100)
  .setPreference(DoubleIndentClassDeclaration, true)

initialCommands := "import de.heikoseeberger.scalatrain._"