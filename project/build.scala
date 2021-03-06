import sbt._
import Keys._
import java.util.zip.{ZipInputStream, ZipOutputStream, ZipEntry}

object JsonBuild extends Build {
	val JavaDoc = config("genjavadoc") extend Compile
	
	val javadocSettings = inConfig(JavaDoc)(Defaults.configSettings) ++ Seq(
		libraryDependencies += compilerPlugin("com.typesafe.genjavadoc" %%
			"genjavadoc-plugin" % "0.9" cross CrossVersion.full),
		scalacOptions <+= target map (t => "-P:genjavadoc:out=" + (t / "java")),
		packageDoc in Compile <<= packageDoc in JavaDoc,
		sources in JavaDoc <<=
			(target, compile in Compile, sources in Compile) map ((t, c, s) =>
				(t / "java" ** "*.java").get ++ s.filter(_.getName.endsWith(".java"))),
		javacOptions in JavaDoc <<= (javaSource in Compile).map{(javaSrc:File) => Seq(
			"-overview", javaSrc.toString + "/overview.html",
			"-link", "http://docs.oracle.com/javase/7/docs/api/",
		//	"-linksource", "-sourcetab", "4",
			"-use"
		)},
		artifactName in packageDoc in JavaDoc :=
			((sv, mod, art) => "" + mod.name + "_" + sv.binary + "-" + mod.revision + "-javadoc.jar")
	)
	
	
	lazy val root = Project(
			id = "utilities",
			base = file("."),
			settings = Defaults.coreDefaultSettings 
	//			++ javadocSettings
	).configs(JavaDoc)
}

