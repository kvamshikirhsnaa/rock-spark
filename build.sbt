name := "rock-spark"

version := "0.1"

scalaVersion := "2.11.8"

libraryDependencies += "com.typesafe"%"config"%"1.3.2"
libraryDependencies += "org.apache.spark"%%"spark-core"%"2.1.1"
libraryDependencies += "org.apache.spark"%%"spark-sql"%"2.1.1"
// https://mvnrepository.com/artifact/mysql/mysql-connector-java
libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.11"

unmanagedJars in Compile += file("C:\\Users\\kenche's\\Desktop\\03 Practice\\hive-exec-1.2.1.jar")
// https://mvnrepository.com/artifact/org.apache.hive/hive-exec
//libraryDependencies += "org.apache.hive" % "hive-exec" % "1.2.1"

