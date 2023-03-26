plugins {
    kotlin("jvm") version "1.8.0"
    application
    id("io.qameta.allure") version "2.11.2"
}

allure {
    version.set("2.8.0")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("io.rest-assured:kotlin-extensions:5.3.0")
    implementation("com.google.code.gson:gson:2.10.1")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}