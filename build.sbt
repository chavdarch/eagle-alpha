import play.PlayScala
import sbt.Keys._

name := """eagle-alpha"""

version := "1.0-SNAPSHOT"

scalaVersion in ThisBuild := "2.11.6"

javacOptions ++= Seq("-source", "1.8", "-target", "1.8", "-Xlint")

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

