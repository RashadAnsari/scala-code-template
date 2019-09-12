name := "scala-code-template"

scalaVersion := "2.12.4"

enablePlugins(JavaAppPackaging)
enablePlugins(DockerPlugin)

dockerBaseImage := "openjdk:8"
// fixme: template
packageName in Docker := "scala-code-template"
version in Docker := (version in ThisBuild).value
bashScriptExtraDefines += """addJava "-Dlogback.configurationFile=${app_home}/../conf/logback.xml""""

resolvers += Resolver.bintrayRepo("rashad", "maven")
resolvers += Resolver.bintrayRepo("beyondthelines", "maven")

lazy val akkaVersion = "2.5.23"
lazy val akka = Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster" % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster-tools" % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster-sharding" % akkaVersion,
  "com.typesafe.akka" %% "akka-slf4j" % akkaVersion
)

lazy val akkaHttpVersion = "10.1.9"
lazy val akkaHttp = Seq(
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion
)

lazy val scalaPB = Seq(
  "io.grpc" % "grpc-netty" % scalapb.compiler.Version.grpcJavaVersion,
  "com.thesamet.scalapb" %% "scalapb-runtime-grpc" % scalapb.compiler.Version.scalapbVersion,
  "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf"
)

lazy val logging = Seq(
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "biz.paluch.logging" % "logstash-gelf" % "1.8.0",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0"
)

lazy val testKits = Seq(
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
  "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion,
  "com.typesafe.akka" %% "akka-multi-node-testkit" % akkaVersion,
  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion,

  "org.scalacheck" %% "scalacheck" % "1.13.4" % "test",
  "org.scalatest" %% "scalatest" % "3.0.4"
)

lazy val management = Seq(
  "com.lightbend.akka.management" %% "akka-management" % "1.0.0",
  "com.lightbend.akka.management" %% "akka-management-cluster-http" % "1.0.0"
)

libraryDependencies ++= Seq(
  "click.rashad" %% "scala-commons" % "1.0.11",
  "click.rashad" %% "akka-scalapb-serialization" % "1.0.3"
) ++ akka ++ akkaHttp ++ scalaPB ++ logging ++ testKits ++ management

PB.targets in Compile := Seq(
  scalapb.gen() -> (sourceManaged in Compile).value
)

licenses := Seq(
  (
    "MIT License",
    url("https://opensource.org/licenses/MIT")
  )
)
