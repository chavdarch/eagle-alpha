import play.PlayScala
import sbt.Keys._

name := """eagle-alpha"""

version := "1.0-SNAPSHOT"

scalaVersion in ThisBuild := "2.11.6"

javacOptions ++= Seq("-source", "1.8", "-target", "1.8", "-Xlint")

resolvers ++= Seq(
  "Sonatype snapshots repository" at "https://oss.sonatype.org/content/repositories/snapshots/"
)

lazy val streaming = project
  .in(file("streaming"))
  .enablePlugins(PlayScala)
  .settings(
    libraryDependencies ++= Seq(
      "org.scalatestplus" %% "play" % "1.1.0" % Test
    )
  )

lazy val jaccard = project
  .in(file("jaccard"))
  .settings(
    libraryDependencies ++= Seq(
      "org.scalatest" % "scalatest_2.11" % "2.2.4" % Test
    )
  )

lazy val sparkStreamProcessing = project
  .in(file("sparkStreamProcessing"))
  .enablePlugins(PlayScala)
  .settings(
    libraryDependencies ++= Seq(
      ws,
      cache,
      "org.pac4j" % "play-pac4j_scala2.11" % "1.3.0",
      "org.pac4j" % "pac4j-oauth" % "1.6.0",
      "org.scalatest" % "scalatest_2.11" % "2.2.4" % Test
    )
  )

