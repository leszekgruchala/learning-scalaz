name := "scalaz-exercises"

version := "1.0"

scalaVersion := "2.11.8"

val scalazVersion = "7.2.6"

libraryDependencies ++= Seq(
  "org.scalaz" %% "scalaz-core" % scalazVersion,
  "org.scalaz" %% "scalaz-effect" % scalazVersion,
  "org.scalaz" %% "scalaz-concurrent" % scalazVersion
)

scalacOptions += "-feature"

initialCommands in console := "import scalaz._, Scalaz._"
