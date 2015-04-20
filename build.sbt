import play.PlayScala

name := """hello-play-scala"""

version := "1.0-SNAPSHOT"

lazy val streaming = project
  .in(file("streaming"))
  .enablePlugins(PlayScala)
  .settings(
    libraryDependencies ++= Seq(
      "org.scalatestplus" %% "play" % "1.1.0" % "test"
    )
  )
