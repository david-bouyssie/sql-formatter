import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}
import ReleaseTransformations._

scalaVersion in ThisBuild       := "2.12.11"
crossScalaVersions in ThisBuild := Seq("2.11.12", scalaVersion.value, "2.13.1")
organization in ThisBuild       := "com.github.takayahilton"

onChangedBuildSource in Global := ReloadOnSourceChanges

lazy val root = project
  .in(file("."))
  .settings(moduleName := "root")
  .settings(publishingSettings)
  .settings(noPublishSettings)
  .aggregate(sql_formatterJVM, sql_formatterJS)
  .dependsOn(sql_formatterJVM, sql_formatterJS)

lazy val sql_formatter = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Full)
  .in(file("."))
  .settings(
    moduleName := "sql-formatter",
    sharedSettings,
    publishingSettings
  )

lazy val sql_formatterJVM = sql_formatter.jvm
lazy val sql_formatterJS = sql_formatter.js

lazy val commonScalacOptions = Def.setting {
  Seq(
    "-deprecation",
    "-encoding",
    "UTF-8",
    "-feature",
    "-language:_",
    "-unchecked",
    "-Xlint",
    "-Xlint:-nullary-unit",
    "-Ywarn-numeric-widen",
    "-Ywarn-value-discard"
  ) ++ {
    CrossVersion.partialVersion(scalaVersion.value) match {
      case Some((2, v)) if v >= 13 =>
        Seq(
          "-Ymacro-annotations"
        )
      case _ =>
        Seq(
          "-Xfatal-warnings",
          "-Yno-adapted-args",
          "-Ypartial-unification",
          "-Xfuture"
        )
    }
  }
}

lazy val sharedSettings = Seq(
  scalacOptions ++= commonScalacOptions.value,
  (scalacOptions in Test) ~= (_.filterNot(_ == "-Xfatal-warnings")),
  libraryDependencies ++= Seq(
    "org.scalatest" %%% "scalatest" % "3.1.1" % Test
  )
)

lazy val publishingSettings = Seq(
  name                    := "sql-formatter",
  description             := "SQL Formatter for Scala",
  publishMavenStyle       := true,
  publishArtifact in Test := false,
  pomIncludeRepository    := { _ => false },
  publishTo := Some(
    if (isSnapshot.value)
      Opts.resolver.sonatypeSnapshots
    else
      Opts.resolver.sonatypeStaging
  ),
  licenses := Seq("MIT" -> url("http://opensource.org/licenses/MIT")),
  homepage := Some(
    url("https://github.com/takayahilton/sql-formatter")
  ),
  scmInfo := Some(
    ScmInfo(
      url("https://github.com/takayahilton/sql-formatter"),
      "scm:git@github.com:takayahilton/sql-formatter.git"
    )
  ),
  developers := List(
    Developer(
      id = "takayahilton",
      name = "Tanaka Takaya",
      email = "takayahilton@gmail.com",
      url = url("https://github.com/takayahilton")
    )
  )
) ++ sharedReleaseProcess

lazy val sharedReleaseProcess = Seq(
  releaseProcess := Seq[ReleaseStep](
    checkSnapshotDependencies,
    inquireVersions,
    runClean,
    releaseStepCommandAndRemaining("check"),
    runTest,
    setReleaseVersion,
    commitReleaseVersion,
    tagRelease,
    releaseStepCommandAndRemaining("+publishSigned"),
    setNextVersion,
    commitNextVersion,
    releaseStepCommand("sonatypeReleaseAll"),
    pushChanges
  )
)

lazy val noPublishSettings = Seq(
  publish         := {},
  publishLocal    := {},
  publishArtifact := false
)

addCommandAlias("check", ";scalafmtCheckAll;scalafmtSbtCheck")
addCommandAlias("fmt", ";scalafmtAll;scalafmtSbt")