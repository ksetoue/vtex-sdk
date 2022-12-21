import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.21"
    application
    `maven-publish`
}

group = "ksetoue"
version = "1.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot:3.0.0")
    implementation("com.fasterxml.jackson.core:jackson-core:2.14.0")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.14.0")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/ksetoue/vtex-sdk")
            credentials {
                username = System.getenv("GITHUB_PACKAGES_USERID")
                // Safe to share the password since it is a `read:package` scoped token.
                password = System.getenv("GITHUB_VTEX_SDK_PUBLISH")
            }
        }
    }
    publications {
        register("gprRelease", MavenPublication::class) {
            groupId = group.toString()
            artifactId = artifactId.toString()
            version = version.toString()

            from(components["java"])
        }
    }
}