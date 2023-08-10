plugins {
    java
    id("org.springframework.boot") version "2.7.14"
    id("io.spring.dependency-management") version "1.1.2"
    id("maven-publish")
    id("me.champeau.jmh") version "0.7.1"
}

group = "me.yevgnenll"
version = "1.0.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_11
}

repositories {
    mavenCentral()
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:2.7.14")
        mavenBom("org.projectlombok:lombok:1.18.24")
        mavenBom("com.google.guava:guava-bom:32.1.2-jre")
        mavenBom("org.junit:junit-bom:5.6.2")
    }
}

dependencies {
    implementation("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")
    implementation("com.google.guava:guava")
    implementation("org.slf4j:slf4j-api:2.0.3")
}

dependencies {
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.hamcrest:hamcrest-all:1.3")

    // assertj & mockito
    testImplementation("org.assertj:assertj-core:3.23.1")
    testImplementation("org.mockito:mockito-core:4.8.0")

    // junit
    testImplementation("com.google.code.findbugs:jsr305:3.0.2")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testRuntimeOnly("org.junit.platform:junit-platform-commons")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

dependencies {
    jmh("org.openjdk.jmh:jmh-core:1.35")
    jmh("org.openjdk.jmh:jmh-generator-annprocess:1.35")
}

jmh {
    // https://github.com/melix/jmh-gradle-plugin
    zip64 = true
    fork = 1
    iterations = 3
    warmupIterations = 1
}

tasks.withType<Test> {
    useJUnitPlatform()
}
