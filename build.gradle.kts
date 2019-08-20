import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.41"
    application
}

repositories {
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
