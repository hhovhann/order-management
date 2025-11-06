plugins {
    java
    id("org.springframework.boot") version "3.5.7"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "am.hhovhann"
version = "0.1.0"
description = "Clean Hexagonal Architecture for Order Management"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}


dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.5")
    implementation("org.apache.camel.springboot:camel-spring-boot-starter:4.15.0")
    implementation("org.apache.camel.springboot:camel-log-starter:4.15.0")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.14")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-database-postgresql")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    testImplementation("org.mockito:mockito-core:5.5.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}


