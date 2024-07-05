plugins {
  id("java")
  id("org.springframework.boot") version "3.2.5"
  id("io.spring.dependency-management") version "1.1.4"
  id("org.asciidoctor.jvm.convert") version "3.3.2"
  id("com.epages.restdocs-api-spec") version "0.18.4"
}

group = "song.pg"
version = "0.0.1-SNAPSHOT"

repositories {
  mavenCentral()
}

extra["snippetsDir"] = file("build/generated-snippets")

dependencies {
  implementation(project(":domain"))
  implementation(project(":utils"))
  implementation(project(":application:application-payment"))
  implementation(project(":persistence:jpa"))

  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-security")
  implementation("org.springframework.boot:spring-boot-starter-validation")
  implementation("org.springframework.boot:spring-boot-starter-actuator")

  //OpenAPI Converter
  implementation("org.springdoc:springdoc-openapi-ui:1.6.11")
  implementation("org.springdoc:springdoc-openapi-webmvc-core:1.6.11")

  //Jackson
  implementation("com.fasterxml.jackson.core:jackson-databind")
  implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
  implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8:2.15.2")

  //JWT
  implementation("io.jsonwebtoken:jjwt-api:0.11.2")
  implementation("io.jsonwebtoken:jjwt-impl:0.11.2")
  implementation("io.jsonwebtoken:jjwt-jackson:0.11.2")

  compileOnly("org.projectlombok:lombok:1.18.34")

  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
  testImplementation("org.springframework.security:spring-security-test")

  //OpenAPI Converter
  testImplementation("com.epages:restdocs-api-spec:0.18.4")
  testImplementation("com.epages:restdocs-api-spec-mockmvc:0.18.4")

  testImplementation(platform("org.junit:junit-bom:5.10.0"))
  testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
  useJUnitPlatform()

  outputs.dir(project.extra["snippetsDir"]!!)
}

tasks.asciidoctor {
  inputs.dir(project.extra["snippetsDir"]!!)
  dependsOn(tasks.test)
}

openapi3 {
  this.setServer("http://localhost:8080")
  this.title = "테스트PG 결제시스템 API"
  this.description = "테스트 PG 결제시스템 API 명세서"
  this.version = "1.0.0"
  this.format = "yaml"
}
