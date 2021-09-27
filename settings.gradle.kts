rootProject.name = "cache-lib-benchmark"

pluginManagement {
    plugins {
        kotlin("jvm") version "1.5.21"
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven {
            url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
        }
    }
}