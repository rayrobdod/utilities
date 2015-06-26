name := "utilities"

organization := "com.rayrobdod"

organizationHomepage := Some(new URL("http://rayrobdod.name/"))

//version := "20140518"
version := "SNAPSHOT"

scalaVersion := "2.10.5"

crossScalaVersions := Seq("2.9.3", "2.10.5", "2.11.6")

compileOrder := CompileOrder.JavaThenScala

javacOptions ++= Seq("-Xlint:deprecation", "-Xlint:unchecked")

scalacOptions ++= Seq("-unchecked", "-deprecation" )



// testing
libraryDependencies += {
	if (scalaVersion.value == "2.9.3") {
		"org.scalatest" %% "scalatest" % "1.9.2" % "test"
	} else {
		"org.scalatest" %% "scalatest" % "2.2.1" % "test"
	}
}

testOptions in Test += Tests.Argument("-oS")
