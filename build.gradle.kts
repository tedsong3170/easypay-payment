import com.google.protobuf.gradle.*

plugins {
  java
  id("org.springframework.boot") version "3.2.5"
  id("io.spring.dependency-management") version "1.1.4"
  id("org.asciidoctor.jvm.convert") version "3.3.2"
  id("com.epages.restdocs-api-spec") version "0.18.4"
  id("com.google.protobuf") version "0.9.4"
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
  google()
}

extra["snippetsDir"] = file("build/generated-snippets")

val grpcVersion = "3.19.4"
val grpcKotlinVersion = "1.2.1"
val grpcProtoVersion = "1.44.1"

sourceSets{
  getByName("main"){
    java {
      srcDirs(
        "build/generated/source/proto/main/java",
        "build/generated/source/proto/main/grpc"
      )
    }
  }
}

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

  implementation("io.grpc:grpc-stub:$grpcProtoVersion")
  implementation("io.grpc:grpc-protobuf:$grpcProtoVersion")
  implementation("io.grpc:grpc-netty-shaded:$grpcProtoVersion")
  implementation("com.google.protobuf:protobuf-java-util:3.25.1")
  implementation("com.google.protobuf:protobuf-java:3.25.1")
  implementation("net.devh:grpc-client-spring-boot-starter:2.15.0.RELEASE")
  compileOnly("org.apache.tomcat:annotations-api:6.0.53")

  implementation("io.jsonwebtoken:jjwt-api:0.11.2")
  implementation("io.jsonwebtoken:jjwt-impl:0.11.2")
  implementation("io.jsonwebtoken:jjwt-jackson:0.11.2")

  implementation("org.springframework.kafka:spring-kafka")

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
  this.title = "테스트PG 결제시스템 API"
  this.description = "테스트 PG 결제시스템 API 명세서"
  this.version = "1.0.0"
  this.format = "yaml"
}

protobuf {
  protoc {
    artifact = "com.google.protobuf:protoc:$grpcVersion"
  }

  plugins {
    id("grpc") {
      artifact = "io.grpc:protoc-gen-grpc-java:$grpcProtoVersion"
    }
  }
  generateProtoTasks {
    all().forEach {
      it.plugins {
        id("grpc")
      }
    }
  }
}
