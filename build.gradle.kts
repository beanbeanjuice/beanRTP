import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("com.github.johnrengelman.shadow") version("8.1.1")
    id("java")
}

group = "com.beanbeanjuice"
version = "3.1.0"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()

    maven {
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }

    maven {
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }

    maven {
        url = uri("https://papermc.io/repo/repository/maven-public/")
    }

    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

dependencies {
    // Spigot
    compileOnly("org.spigotmc", "spigot-api", "1.20.6-R0.1-SNAPSHOT")

    // Paper ASYNC
    implementation("io.papermc", "paperlib", "1.0.8")

    // bstats
    implementation("org.bstats", "bstats-bukkit", "3.0.2")

    // Better YAML Support
    implementation("dev.dejvokep", "boosted-yaml", "1.3.5")

    // Lombok
    compileOnly("org.projectlombok", "lombok", "1.18.32")
    annotationProcessor("org.projectlombok", "lombok", "1.18.32")
}

inline fun <reified C> Project.configure(name: String, configuration: C.() -> Unit) {
    (this.tasks.getByName(name) as C).configuration()
}

configure<ProcessResources>("processResources") {
    filesMatching("plugin.yml") {
        expand(project.properties)
    }
}

tasks.withType<ShadowJar> {
    minimize()
    relocate("org.bstats", "com.beanbeanjuice.beanrtp.libs.org.bstats")
    archiveBaseName.set(rootProject.name)
    archiveClassifier.set("")
    archiveVersion.set(version as String)
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
