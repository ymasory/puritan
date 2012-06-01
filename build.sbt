//basic project info
name := "puritan"

organization := "com.yuvimasory"

version := "0.0.1-SNAPSHOT"

//scala versions and options
scalaVersion := "2.9.2"

crossScalaVersions := Seq()

scalacOptions ++= Seq("-deprecation", "-unchecked")

javacOptions ++= Seq("-Xlint:unchecked", "-Xlint:deprecation")

//dependencies
libraryDependencies ++= Seq (
  "org.scalaz" %% "scalaz-core" % "7.0-SNAPSHOT",
  "org.scalacheck" %% "scalacheck" % "1.9" % "test"
)

//improve REPL
initialCommands := ""

//only uncomment if you need dependencies from the snapshots repo
//resolvers += ScalaToolsSnapshots

//sbt behavior
logLevel in compile := Level.Warn

traceLevel := 5

//dependecy-graph-plugin
net.virtualvoid.sbt.graph.Plugin.graphSettings
