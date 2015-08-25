name := """Diagnosis"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.6"

//resolvers += "Local Maven Repository" at "file:///Users/kiwanglee/Documents/Project/Phone/repository"
resolvers += "Spring Snapshots" at "http://maven.springframework.org/snapshot"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  "org.springframework" % "spring-context" % "4.2.0.RELEASE",
  "org.springframework.guice" % "spring-guice" % "1.0.0.BUILD-SNAPSHOT",
  "commons-io" % "commons-io" % "2.4",
  "jexcelapi" % "jxl" % "2.6"
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
