/*
LGPL v3.0
Copyright (C) 2019 Pedro Agullo Soliveres
p.agullo.soliveres@gmail.com

KPointers is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 3 of the License, or (at your option) any later version.

KPointers is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program; if not, write to the Free Software Foundation,
Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "com.softwarementors.${rootProject.name}"
version = "beta-1-SNAPSHOT"

val log4j2Ver = "2.11.2"
val log4jKotlinApiVer = "1.0.0"

buildscript {
   repositories {
      jcenter()
      mavenCentral()
   }
}

repositories {
   jcenter()
   mavenCentral()
}


plugins {
   val kotlinVer = "1.3.41"

   java
   application
   kotlin("jvm") version kotlinVer
   kotlin("kapt") version kotlinVer
}

dependencies {
   implementation(kotlin("stdlib-jdk8"))
   implementation(kotlin("reflect"))
   implementation(kotlin("stdlib-common"))

   implementation("org.apache.logging.log4j:log4j-api:$log4j2Ver")
   implementation("org.apache.logging.log4j:log4j-api-kotlin:$log4jKotlinApiVer")
}

// List of examples: we create a runXxx task to run every example
// and set the default run task to run the first example in the list
// See usage of exampleMainClass()
val examples = listOf( "Pointers", "SumFunction")   

fun exampleMainClass( exampleName : String) : String {
   return "com.softwarementors.kpointers.examples.${exampleName}ExampleKt"
}

application {
   // First example in examples list will be the one to be run by 'run' taks 
   mainClassName = exampleMainClass( examples[0])
}

tasks {
   // Generate a 'runXxx' for every 'xxx' example class
   examples.forEach {
      val example = it
      register<JavaExec>("run${example}Example") {
         group = "Application"
         val mainClass = exampleMainClass(example)
         description = "Run example class $mainClass"
         classpath = sourceSets["main"].runtimeClasspath
         main = mainClass
      }
   }
   
   withType<KotlinCompile> {
      // Add arg to remove the '"inline classes" is experimental' warning
      var args = mutableListOf("-XXLanguage:+InlineClasses")
      args.addAll(kotlinOptions.freeCompilerArgs)
      kotlinOptions.freeCompilerArgs = args
   }

   withType<Wrapper> {
      gradleVersion = "5.5.1"
   }

}