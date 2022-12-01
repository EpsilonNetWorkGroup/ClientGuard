plugins {
	id("fabric-loom") version "1.0-SNAPSHOT"
}

dependencies {
	// To change the versions see the gradle.properties file
	minecraft("com.mojang:minecraft:1.19.2")
	mappings("net.fabricmc:yarn:1.19.2+build.8:v2")
	modImplementation("net.fabricmc:fabric-loader:0.14.9")

	// Fabric API. This is technically optional, but you probably want it anyway.
	modImplementation("net.fabricmc.fabric-api:fabric-api:0.60.0+1.19.2")

	implementation(project(":Shared"))
}

blossom{
	val src = "src/main/java/net/playl/clientguard/ClientGuardFabric.java"
	replaceToken("{version}", project.version, src)

	// just not work
//	val file = "src/main/resources/fabric.mod.json"
//    replaceToken("\${version}", project.version, file)
//    replaceToken("\${description}",  project.description, file)
//    replaceToken("\${github_url}",  providers.gradleProperty("githubUrl").get(), file)
}

tasks {
	processResources {
		val props = mapOf(
			"version" to project.version,
			"github_url" to providers.gradleProperty("githubUrl").get(),
			"description" to project.description,
		)
		inputs.properties(props)
		filesMatching("fabric.mod.json") {
			// filter manually to avoid trying to replace $Initializer in initializer class name...
			filter { string ->
				var result = string
				for ((key, value) in props) {
					result = result.replace("\${$key}", value.toString())
				}
				result
			}
		}

	}
}
