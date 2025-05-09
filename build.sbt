name := """play-book-simple"""
organization := "id.book"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.16"

libraryDependencies ++= Seq(guice,
  javaJpa,
  "org.hibernate" % "hibernate-core" % "6.6.10.Final",
  "org.postgresql" % "postgresql" % "42.7.3",
)

PlayKeys.externalizeResourcesExcludes += baseDirectory.value / "conf" / "META-INF" / "persistence.xml"
