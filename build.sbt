ThisBuild / version := "0.1.0"
ThisBuild / scalaVersion := "2.13.6"
ThisBuild / organization := "com.learncats"
ThisBuild / scalacOptions += "-Ymacro-annotations"

addCompilerPlugin("org.typelevel" % "kind-projector" % "0.13.0" cross CrossVersion.full)

val scalaTest = "org.scalatest" %% "scalatest" % "3.2.7"
val gigahorse = "com.eed3si9n" %% "gigahorse-okhttp" % "0.5.0"
val playJson  = "com.typesafe.play" %% "play-json" % "2.9.2"

lazy val sayHello = taskKey[Unit]("Says hello")

lazy val hello = (project in file("."))
  .aggregate(helloCore)
  .dependsOn(helloCore)
  .enablePlugins(JavaAppPackaging)
  .settings(
    name := "LearnCats",
    libraryDependencies ++= Seq("org.typelevel" %% "cats-core" % "2.3.0", "org.typelevel" %% "simulacrum" % "1.0.0", scalaTest % Test),
    )

lazy val helloCore = (project in file("core"))
  .settings(
    name := "LearnCatsCore",
    libraryDependencies ++= Seq(gigahorse, playJson),
    libraryDependencies += scalaTest % Test,    
    )

sayHello := {println("Hellos")}