import sbt._
import Keys._

import sbtassembly.Plugin._
import AssemblyKeys._

object ApplicationBuild extends Build {

  lazy val buildSettings = Defaults.defaultSettings ++ Seq(
    organization := "brycea",
    version := "0.0.1-SNAPSHOT",
    scalaVersion in ThisBuild := "2.10.3",
    libraryDependencies += scalatest % "test"
  )

  val main = Project("http_parser", 
                    new File("."),
                    settings = buildSettings ++ assemblySettings
    ).settings(
      mainClass in assembly := Some("http_parser.Main"),
      mergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) =>
        {
          case x if x.endsWith("MANIFEST.MF")     => 
            println("Found : " + x.getClass)
            MergeStrategy.discard
          case x if x.endsWith("rootdoc.txt")     => 
            println("Found : " + x)
            MergeStrategy.concat
          case x => old(x)
        }
      }
    )
   .dependsOn(http4s)

   lazy val http4s = ProjectRef(file("../../Documents/GitHub/http4s"), "core")
   
   lazy val scalatest  = "org.scalatest"  %% "scalatest" % "2.0.RC3"
}
