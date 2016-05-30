resolvers += "Typesafe repository" at "https://repo.typesafe.com/typesafe/releases/"

// Asset pipeline plugins
addSbtPlugin("com.typesafe.sbt" % "sbt-digest" % "1.1.0")
addSbtPlugin("com.typesafe.sbt" % "sbt-gzip" % "1.0.0")
addSbtPlugin("com.typesafe.sbt" % "sbt-uglify" % "1.0.3")

// Build Version Plugins
addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.5.0")
addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.0.6")
addSbtPlugin("io.spray" % "sbt-revolver" % "0.8.0")
addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.8.2")
addSbtPlugin("org.scalariform" % "sbt-scalariform" % "1.6.0")
addSbtPlugin("com.localytics" % "sbt-dynamodb" % "1.4.1")
