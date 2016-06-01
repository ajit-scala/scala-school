organization := "com.autoscout24"

name := "carhistory-classified-import"

version := Option(System.getenv("GO_PIPELINE_LABEL")).getOrElse("1.0-SNAPSHOT")

scalaVersion := "2.11.8"

unmanagedBase in Test := baseDirectory.value / "test/lib"
javaOptions in(ThisBuild, Test) ++= Seq(
  s"-Djava.library.path=${(unmanagedBase in Test).value}",
  "-Dconfig.resource=test.conf"
)

//autoCompilerPlugins := true

//addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

scalacOptions in ThisBuild ++= Seq("-unchecked", "-deprecation", "-feature", "-Xfatal-warnings")

javacOptions ++= Seq("-source", "1.8", "-target", "1.8", "-Xlint")

val awsVersion = "1.10.50"
val droolsVersion = "6.3.0.Final"

val awsDependencies = Seq(
  "com.amazonaws" % "aws-java-sdk-s3" % awsVersion,
  "com.amazonaws" % "aws-java-sdk-dynamodb" % awsVersion
)

val sparkStreamingDependencies = Seq(
  ("org.apache.spark" %% "spark-core" % "1.6.0" % "provided"),
  "org.apache.spark" %% "spark-streaming" % "1.6.0",
  "org.apache.spark" %% "spark-streaming-kafka" % "1.6.0"
)
val droolsDependencies = Seq(
  "knowledge-api",
  "drools-core",
  "drools-compiler",
  "drools-jsr94",
  "drools-decisiontables",
  "drools-templates"
).map("org.drools" % _ % droolsVersion) ++ Seq("org.kie" % "kie-api" % droolsVersion, "org.jbpm" % "jbpm-runtime-manager" % droolsVersion)

val circeDependencies = Seq(
  "io.circe" %% "circe-core" % "0.4.0",
  "io.circe" %% "circe-generic" % "0.4.0",
  "io.circe" %% "circe-parser" % "0.4.0",
  "io.circe" %% "circe-java8" % "0.4.0"
)

val dynamoDbLocal = Seq(
  "com.amazonaws" % "DynamoDBLocal" % "1.9.33" % "test" excludeAll(
    ExclusionRule(organization = "com.amazonaws", name = "aws-java-sdk-dynamodb"),
    ExclusionRule(organization = "org.mockito"),
    ExclusionRule(organization = "org.eclipse.jetty.orbit", name = "javax.servlet")
    ),
  "com.almworks.sqlite4java" % "sqlite4java" % "0.282",
  "com.almworks.sqlite4java" % "libsqlite4java-osx" % "0.282" artifacts(Artifact("libsqlite4java-osx", "jnilib", "jnilib"))
)

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  "org.slf4j" % "slf4j-api" % "1.7.14", // logging
  //  "ch.qos.logback" % "logback-classic" % "1.1.3",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0",
  "org.scalatest" %% "scalatest" % "2.2.6" % "it,test", // test
  "com.sun.xml.bind" % "jaxb-xjc" % "2.2.4-1",
  "net.jcazevedo" %% "moultingyaml" % "0.2", // yaml parsing
  "org.scalactic" %% "scalactic" % "2.2.6",
  "com.typesafe" % "config" % "1.3.0",
  "org.mockito" % "mockito-all" % "1.10.19" % "test",
  "com.typesafe.play" %% "play-json" % "2.3.4",
  "org.apache.kafka" % "kafka-clients" % "0.10.0.0"
)  ++ droolsDependencies ++ awsDependencies ++ circeDependencies ++ sparkStreamingDependencies ++ dynamoDbLocal

dependencyOverrides ++= Set(
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.4.4"
)

mainClass in assembly := Some("app.spark.ConsumerApp")

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", "eclipse.inf") => MergeStrategy.discard
  case PathList("com", "esotericsoftware", "minlog", xa@_*) => MergeStrategy.first
  case PathList("com", "google", "common", "base", xb@_*) => MergeStrategy.first
  case PathList("javax", "xml", xc@_*) => MergeStrategy.first
  case PathList("org", "apache", "commons", xd@_*) => MergeStrategy.first
  case PathList("org", "apache", "hadoop", "yarn", xe@_*) => MergeStrategy.first
  case PathList("org", "apache", "spark", "unused", xf@_*) => MergeStrategy.first
  case PathList("org", "slf4j", "impl", xg@_*) => MergeStrategy.first
  case PathList("org", "w3c", "dom", xh@_*) => MergeStrategy.first
  case PathList("org", "xmlpull", "v1", xi@_*) => MergeStrategy.first
  case x =>
    val baseStrategy = (assemblyMergeStrategy in assembly).value
    baseStrategy(x)
}

test in assembly := {}

fork := true

resolvers += "JBoss public" at "http://repository.jboss.org/nexus/content/groups/public/"
resolvers += Resolver.sonatypeRepo("snapshots")



lazy val root = (project in file("."))
  //.dependsOn(moultingyaml)
  .enablePlugins(JavaAppPackaging)
  .configs(IntegrationTest)
  .settings(Defaults.itSettings: _*)
  .settings(unmanagedResourceDirectories in IntegrationTest += baseDirectory.value / "src" / "main" / "resources")


lazy val killjetty = taskKey[Unit]("Execute the shell script to kill jetty running spark for each compile")
killjetty := ("./killjetty.sh" !)
compile in Compile <<= (compile in Compile).dependsOn(killjetty)

dynamoDBLocalSharedDB := true