dependencies {
    implementation("com.velocitypowered:velocity-api:${providers.gradleProperty("velocity_api").get()}")
    annotationProcessor("com.velocitypowered:velocity-api:${providers.gradleProperty("velocity_api").get()}")

    implementation("net.md-5:bungeecord-config:1.19-R0.1-SNAPSHOT")
    implementation(project(":Shared"))
}

tasks.withType<Jar> {
    from({
        configurations.runtimeClasspath.get().filter { it.name.startsWith("bungeecord-config") }.map { zipTree(it) }
    })
}

blossom{
    val file = "src/main/java/net/playl/clientguard/ClientGuardVelocity.java"
    replaceToken("{version}", project.version, file)
    replaceToken("{description}",  project.description, file)
    replaceToken("{github_url}",  providers.gradleProperty("githubUrl").get(), file)
}
