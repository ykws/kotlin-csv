plugins {
    id("org.jetbrains.kotlin.jvm").version("1.3.41")
    `maven-publish`
    signing
}

group = "com.github.doyaaaaaken"
version = "0.1.0-SNAPSHOT"

repositories {
    jcenter()
}

val test by tasks.getting(Test::class) {
    useJUnitPlatform { }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.3.2")
}


//publishing settings
//https://docs.gradle.org/current/userguide/publishing_maven.html
val sourcesJar = task<Jar>("sourcesJar") {
    from(sourceSets.main.get().allSource)
    archiveClassifier.set("sources")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = "kotlin-csv"
            from(components["java"])
            artifact(sourcesJar)
        }
    }
    repositories {
        maven {
            url = uri("https://oss.sonatype.org/content/repositories/snapshots")
            credentials {
                val nexusUsername: String? by project
                val nexusPassword: String? by project
                username = nexusUsername
                password = nexusPassword
            }
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
}
