/*
 */

plugins {
    id( "java-library" )
    id ("maven-publish")
}

repositories {
    mavenLocal()
    maven {
        url = uri("http://clojars.org/repo")
    }

    maven {
        url = uri("https://repo.maven.apache.org/maven2")
    }

    maven {
        url = uri("https://oss.sonatype.org/content/repositories/snapshots")
    }
}

dependencies {
    implementation( group="junit", name="junit", version="4.12")
    // implementation( group="org.junit.jupiter", name="junit-jupiter-api", version="5.5.1")
    
    implementation( group="bultitude", name="bultitude", version="0.2.8")
    // bultitude may be replaceable by clojure.tools.namespace at some point
    
    implementation( group="org.clojure", name="clojure", version="1.10.1")

    implementation(group="org.apache.logging.log4j", name="log4j-api", version="2.12.0")
    implementation(group="org.apache.logging.log4j", name="log4j-core", version="2.12.0")

    implementation(group="io.github.classgraph", name="classgraph", version="4.8.43")

}

group = "net.mikera"
version = "0.6.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

sourceSets {
    test {
        resources {
            setSrcDirs(listOf("src/test/clojure", "src/main/clojure"))
        }
    }
}

publishing {
    publications {
        register("maven", MavenPublication::class) {
            from(components["java"])

            pom {
                name.set("cljunit")
                description.set("JUnit wrapper for Clojure test code")
                url.set("https://github.com/babeloff/cljunit.git")
                licenses {
                    license {
                        name.set("The MIT License")
                        url.set("https=//opensource.org/licenses/MIT")
                    }
                }
                developers {
                    developer {
                        id.set("mike.ra")
                        name.set("Mike Ra")
                        email.set("mikera@github.com")
                    }
                    developer {
                        id.set("fred.eisele")
                        name.set("Fred Eisele")
                        email.set("phreed@github.com")
                    }
                }
                scm {
                    connection.set("scm:git:git@github.com:babeloff/cljunit.git")
                    developerConnection.set("scm:git:git@github.com:babeloff/cljunit.git")
                    url.set("https://github.com/babeloff/cljunit.git")
                }
            }
        }
    }
}

tasks {
    javadoc {
        options.encoding = "UTF-8"
    }
    compileJava {
        options.encoding = "UTF-8"
    }
    compileTestJava {
        options.encoding = "UTF-8"
    }

    wrapper {
        gradleVersion = "5.5.1"
        distributionType = Wrapper.DistributionType.ALL
    }
}
