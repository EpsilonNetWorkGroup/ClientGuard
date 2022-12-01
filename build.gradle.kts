plugins {
  java
  id("org.cadixdev.licenser") version "0.6.1"
  id("net.kyori.blossom") version "1.3.1"
}

java {
  toolchain {
    languageVersion.set(JavaLanguageVersion.of(17))
  }
}

defaultTasks("licenseFormat", "build")

allprojects {
  repositories {
    maven("https://maven.fabricmc.net/")
    maven("https://repo.papermc.io/repository/maven-public/")
    mavenCentral()
    gradlePluginPortal()
  }
}

subprojects { 
  apply {
    plugin("java")
    plugin("org.cadixdev.licenser")
    plugin("net.kyori.blossom")
  }

  license {
    header(rootProject.file("HEADER.txt"))
    include("**/*.java")
    newLine(true)
  }

  tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
  }

  tasks.withType<Jar> {
    manifest {
      attributes["Main-Class"] = "net.playl.clientguard.Main"
    }

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from({
        configurations.runtimeClasspath.get().filter { it.name.startsWith("Shared") }.map { zipTree(it) }
    })

    from("LICENSE")
  }

  group = project.group
  version = project.version
  description = project.description
}
