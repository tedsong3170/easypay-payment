plugins {
  id("java")
  id("org.springframework.boot") version "3.2.5"
  id("io.spring.dependency-management") version "1.1.4"
}

group = "song.pg.application"
version = "0.0.1-SNAPSHOT"

repositories {
  mavenCentral()
}

dependencies {
  implementation(project(":domain"))
  implementation(project(":utils"))

  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.data:spring-data-commons")

  compileOnly("org.projectlombok:lombok:1.18.34")

  testImplementation("io.mockk:mockk:1.13.10")
  testImplementation("org.assertj:assertj-core:3.26.0")

  testImplementation(platform("org.junit:junit-bom:5.10.0"))
  testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
  useJUnitPlatform()
}
