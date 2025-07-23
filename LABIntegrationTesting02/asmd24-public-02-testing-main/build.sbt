ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.12" // Ensure you're using Scala 2.13

lazy val root = (project in file("."))
  .settings(
    name := "asmd-testing",
    libraryDependencies ++= Seq(
      "net.aichler" % "jupiter-interface" % "0.9.1" % Test, // Specify an actual version
      "org.mockito" % "mockito-core" % "3.+" % Test,
      "org.seleniumhq.selenium" % "selenium-java" % "4.20.0",
      "io.github.bonigarcia" % "webdrivermanager" % "5.8.0",
      "io.cucumber" % "cucumber-junit-platform-engine" % "8.16.1 " % Test,
      "io.cucumber" % "cucumber-java" % "8.16.1"  % Test,
      "org.scalatest" %% "scalatest" % "3.2.18" % Test,
      "junit" % "junit" % "4.13.2" % Test
    )
  )
