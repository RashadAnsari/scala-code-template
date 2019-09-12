resolvers += Resolver.bintrayRepo("rashad","maven")

resolvers += Resolver.bintrayRepo("rashad","sbt-plugins")

resolvers += Resolver.bintrayRepo("thesamet", "sbt-plugins")

addSbtPlugin("click.rashad" % "scala-code-style" % "1.1.8")

addSbtPlugin("org.foundweekends" % "sbt-bintray" % "0.5.4")

addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.3.10")

addSbtPlugin("com.thesamet" % "sbt-protoc" % "0.99.18")

libraryDependencies += "com.thesamet.scalapb" %% "compilerplugin" % "0.7.1"