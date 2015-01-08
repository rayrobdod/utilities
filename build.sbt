name := "utilities"

organization := "com.rayrobdod"

organizationHomepage := Some(new URL("http://rayrobdod.name/"))

//version := "20140518"
version := "SNAPSHOT"

scalaVersion := "2.10.4"

crossScalaVersions ++= Seq("2.11.4", "2.10.4", "2.9.3", "2.9.2", "2.9.1")

//exportJars := true

javacOptions ++= Seq("-Xlint:deprecation", "-Xlint:unchecked")

scalacOptions ++= Seq("-unchecked", "-deprecation" )



scalaVersion in Test := "2.10.3"

libraryDependencies += "org.scalatest" % "scalatest_2.10" % "2.0" % "test"

// testOptions in Test += Tests.Argument("-oS")

