//basic project info
name := "puritan"

organization := "com.yuvimasory"

version := "0.0.1-SNAPSHOT"

//scala versions and options
scalaVersion := "2.9.2"

crossScalaVersions := Seq("2.9.1-1", "2.9.1", "2.9.0-1", "2.9.0")

scalacOptions ++= Seq("-deprecation", "-unchecked")

javacOptions ++= Seq("-Xlint:unchecked", "-Xlint:deprecation")

//dependencies
libraryDependencies ++= Seq (
  "org.scalaz" %% "scalaz-effect" % "7.0-SNAPSHOT"
  // "org.scalacheck" %% "scalacheck" % "1.9" % "test"
)

//improve REPL
initialCommands := ""

//sbt behavior
logLevel in compile := Level.Warn

traceLevel := 5

//dependecy-graph-plugin
net.virtualvoid.sbt.graph.Plugin.graphSettings

//publish
publishMavenStyle := true

publishTo <<= version { (v: String) =>
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT")) Some(
    "snapshots" at nexus + "content/repositories/snapshots"
  )
  else Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

pomExtra := (
  <url>https://github.com/ymasory/puritan</url>
  <licenses>
    <license>
      <name>Scala License</name>
      <url>https://github.com/ymasory/puritan/blob/master/LICENSE</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git@github.com:ymasory/puritan.git</url>
    <connection>scm:git:git@github.com:ymasory/puritan.git</connection>
  </scm>
  <developers>
    <developer>
      <id>ymasory</id>
      <name>Yuvi Masory</name>
      <email>ymasory@gmail.com</email>
      <url>http://yuvimasory.com</url>
    </developer>
  </developers>
)

pgpPassphrase := Some("".toCharArray)

pgpSecretRing := file("")

pgpPublicRing := file("")

pgpSigningKey := Some(0L)

credentials += Credentials(
  "Sonatype Nexus Repository Manager",
  "oss.sonatype.org",
  "ymasory@gmail.com",
  IO read file("")
)
