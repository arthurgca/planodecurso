name := "planodecurso"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache
)

libraryDependencies += "postgresql" % "postgresql" % "9.1-901-1.jdbc4"  

libraryDependencies += "org.mindrot" % "jbcrypt" % "0.3m"

play.Project.playJavaSettings
