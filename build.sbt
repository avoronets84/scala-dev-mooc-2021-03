import sbt.Keys.libraryDependencies

scalacOptions += "-Ymacro-annotations"

lazy val root = (project in file("."))
  .settings(
    name := "scala-dev-mooc-2021-03",
    version := "0.1",
    scalaVersion := "2.13.3"
  )

libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.5"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.5" % "test"