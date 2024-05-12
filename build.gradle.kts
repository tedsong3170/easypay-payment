import com.epages.restdocs.apispec.gradle.OpenApi3Extension

plugins {
  java
  id("org.springframework.boot") version "3.2.5"
  id("io.spring.dependency-management") version "1.1.4"
  id("org.asciidoctor.jvm.convert") version "3.3.2"
  id("com.epages.restdocs-api-spec") version "0.18.4"
}

group = "song.pg"
version = "0.0.1-SNAPSHOT"

java {
  sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
  compileOnly {
    extendsFrom(configurations.annotationProcessor.get())
  }
}

repositories {
  mavenCentral()
}

extra["snippetsDir"] = file("build/generated-snippets")

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-security")
  implementation("org.springframework.boot:spring-boot-starter-validation")
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.flywaydb:flyway-core")

  implementation("org.springdoc:springdoc-openapi-ui:1.6.11")
  implementation("org.springdoc:springdoc-openapi-webmvc-core:1.6.11")

  implementation("com.fasterxml.jackson.core:jackson-databind")
  implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
  implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8:2.15.2")

  compileOnly("org.projectlombok:lombok")
  runtimeOnly("com.h2database:h2")
  runtimeOnly("org.postgresql:postgresql")
  annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
  annotationProcessor("org.projectlombok:lombok")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
  testImplementation("org.springframework.security:spring-security-test")

  testImplementation("com.epages:restdocs-api-spec:0.18.4")
  testImplementation("com.epages:restdocs-api-spec-mockmvc:0.18.4")
}

tasks.withType<Test> {
  useJUnitPlatform()
}

tasks.test {
  outputs.dir(project.extra["snippetsDir"]!!)
}

tasks.asciidoctor {
  inputs.dir(project.extra["snippetsDir"]!!)
  dependsOn(tasks.test)
}

openapi3 {
  this.setServer("http://localhost:8080")
  this.title = "Post Service API"
  this.description = "Post Service API description"
  this.version = "1.0.0"
  this.format = "yaml"
}
