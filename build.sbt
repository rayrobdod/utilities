name := "utilities"

organization := "com.rayrobdod"

organizationHomepage := Some(new URL("http://rayrobdod.name/"))

//version := "20140518"
version := "SNAPSHOT"

scalaVersion := "2.10.5"

crossScalaVersions := Seq("2.10.5", "2.11.7", "2.12.0-M3")

compileOrder := CompileOrder.JavaThenScala

javacOptions ++= Seq("-Xlint:deprecation", "-Xlint:unchecked", "-source", "1.7", "-target", "1.7")

scalacOptions ++= Seq("-unchecked", "-deprecation", "-target:jvm-1.7")



// testing
{
  val scalaTestVersion = Def.setting{ scalaVersion.value.split('.').apply(1) match {
    case "9" => "1.9.2"
    case "10" => "2.2.5"
    case "11" => "2.2.5"
    case "12" => {
      "2.2.5" + scalaVersion.value.split('.').apply(2).drop(1)
    }
  }}
  libraryDependencies += "org.scalatest" %% "scalatest" % scalaTestVersion.value % "test"
}

testOptions in Test += Tests.Argument("-oS")
