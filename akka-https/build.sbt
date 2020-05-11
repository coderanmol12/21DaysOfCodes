
name := "akka-https"
organization := "21DaysOfCode"
version := "0.1"
scalaVersion := "2.12.6"
scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

val akkaStreamVersion = "2.5.26"
val akkaHttpVersion = "10.1.3"
val scalaTestV = "3.0.4"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-jackson" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaStreamVersion, // or whatever the latest version is
  "com.typesafe.akka" %% "akka-actor" % akkaStreamVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.11"

)


