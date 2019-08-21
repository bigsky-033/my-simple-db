import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.41"
    id("kotlinx-serialization") version "1.3.41"
    application
}

repositories {
    jcenter()
    mavenCentral()
}

application {
    group = "kr.bigsky033.study"
    version = "0.0.1"
    applicationName = "my-simple-db"
    mainClassName = "kr.bigsky033.study.mysimpledb.AppKt"
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("gradle-plugin"))
    implementation(kotlin("serialization"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.11.1")
    implementation("org.junit.jupiter:junit-jupiter:5.5.1")
}

tasks {

    named<Test>("test") {
        useJUnitPlatform()
    }

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "12"
    }

    withType<Jar> {
        manifest {
            attributes["Main-Class"] = application.mainClassName
        }

        from(configurations.compileClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    }

}
