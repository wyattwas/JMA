plugins {
    kotlin("jvm") version "2.0.10"
}

group = "org.opencodespace"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.9.1")
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("org.amshove.kluent:kluent:1.73")

    testImplementation(kotlin("test"))
    testImplementation("junit:junit:4.12")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.0")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.11.0")
    testImplementation("org.assertj:assertj-core:3.26.3")
    testImplementation("org.mockito:mockito-core:4.0.0")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.11.0")

    testCompileOnly("org.junit.jupiter:junit-jupiter:5.11.0")
    testCompileOnly("com.github.sys1yagi:kmockito:0.1.2") {
        exclude(group = "org.mockito", module = "mockito-core")
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib")
    }
}

tasks.test {
    useJUnitPlatform()
}