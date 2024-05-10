plugins {
  java
  id("org.springframework.boot") version "3.2.5"
  id("io.spring.dependency-management") version "1.1.4"
  id("org.asciidoctor.jvm.convert") version "3.3.2"
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
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.flywaydb:flyway-core")
  compileOnly("org.projectlombok:lombok")
  runtimeOnly("com.h2database:h2")
  runtimeOnly("org.postgresql:postgresql")
  annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
  annotationProcessor("org.projectlombok:lombok")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
  testImplementation("org.springframework.security:spring-security-test")
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
