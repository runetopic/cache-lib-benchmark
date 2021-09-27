plugins {
    kotlin("jvm")
}

java.sourceCompatibility = JavaVersion.VERSION_16
java.targetCompatibility = JavaVersion.VERSION_16

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.runetopic.cache:cache:1.4.8-SNAPSHOT")
    implementation("io.guthix:js5-filestore:0.5.0")
    implementation("com.displee:rs-cache-library:6.8")
}