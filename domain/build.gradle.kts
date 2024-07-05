import com.google.protobuf.gradle.id

plugins {
  id("java")
  id("com.google.protobuf") version "0.9.4"
}

group = "song.pg.domain"
version = "0.0.1-SNAPSHOT"

repositories {
  mavenCentral()
  google()
}

val grpcVersion = "3.19.4"
val grpcKotlinVersion = "1.2.1"
val grpcProtoVersion = "1.44.1"

dependencies {
  implementation(project(":utils"))

  implementation("io.grpc:grpc-stub:$grpcProtoVersion")
  implementation("io.grpc:grpc-protobuf:$grpcProtoVersion")
  implementation("io.grpc:grpc-netty-shaded:$grpcProtoVersion")
  implementation("com.google.protobuf:protobuf-java-util:3.25.1")
  implementation("com.google.protobuf:protobuf-java:3.25.1")

  compileOnly("org.projectlombok:lombok:1.18.34")

  testImplementation(platform("org.junit:junit-bom:5.10.0"))
  testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
  useJUnitPlatform()
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
