import Dependencies._

ThisBuild / scalaVersion     := "3.4.0"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

resolvers += "jitpack" at "https://jitpack.io"

lazy val root = (project in file("."))
  .settings(
    name := "kursk",
    libraryDependencies ++= Seq(
      "com.github.suprnation.cats-actors" %% "cats-actors" % "2.0.0-RC4",
      "io.github.timwspence" %% "cats-stm" % "0.11.0"
    ),
    Compile / run / fork := true
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
