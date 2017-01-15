import sbt._
import Keys._

object HelloBuild extends Build {

  val sampleKeyA = SettingKey[String]("sample-a", "demo key A")
  val sampleKeyB = SettingKey[String]("sample-b", "demo key B")
  val sampleKeyC = SettingKey[String]("sample-c", "demo key C")
  val sampleKeyD = SettingKey[String]("sample-d", "demo key D")

  override lazy val settings = super.settings ++
    Seq(sampleKeyA := "A: in Build.settings in Build.scala", resolvers := Seq())

  lazy val root = Project(id = "hello",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(sampleKeyB := "B: in the root project settings in Build.scala"))
}