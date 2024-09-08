plugins {
    id("org.springframework.boot") version "3.3.3"
    id("io.spring.dependency-management") version "1.1.6"
    kotlin("jvm") version "1.9.25"

    /* kotlin("plugin.spring") 역할
    @Component, @Transaction 등 이 붙은 클래스나 함수를 open 해줌 -> 클래스를 상속 가능, 함수를 오버라이드 가능하게함
    Q. 왜 open 을 해줘야 할까?
    -> 예: 함수에 @Transaction 붙어 있다면, 해당 클래스를 상속받은 프록시 객체에서 해당 함수를 오버라이드 하여 트랜잭션이 적용되기 때문에
    */
    kotlin("plugin.spring") version "1.9.25"

    /* kotlin("plugin.jpa") 역할
    @Entity, @MappedSuperclass, @Embedded 객체에 기본 생성자를 자동으로 만들어줌
    -> 리플렉션을 활용해서 객체를 인스턴스화 할때 기본 생성자가 필요하기 때문에
    */
    kotlin("plugin.jpa") version "1.9.25"
    kotlin("kapt") version "1.9.25"
}

group = "com.yanggang"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")

    // QueryDSL
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")
    kapt("jakarta.annotation:jakarta.annotation-api")
    kapt("jakarta.persistence:jakarta.persistence-api")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    runtimeOnly("com.h2database:h2")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
