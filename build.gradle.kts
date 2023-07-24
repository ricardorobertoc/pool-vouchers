import com.adarshr.gradle.testlogger.theme.ThemeType.MOCHA
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import org.gradle.api.logging.LogLevel.QUIET
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.0"
    kotlin("plugin.spring") version "1.6.10"

    id("org.springframework.boot") version "2.6.8"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("io.gitlab.arturbosch.detekt") version "1.20.0"
    id("com.adarshr.test-logger") version "3.1.0"
    id("jacoco")
    id("org.barfuin.gradle.jacocolog") version "2.0.0"
    id("com.avast.gradle.docker-compose") version "0.14.9"
}

group = "com.getnet.pooldevouchers"
version = "1.0"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

val mockkVersion = "1.13.2"
val springCloudVersion = "2021.0.0"
val testcontainersVersion = "1.17.4"
val springDocVersion = "1.6.13"
val detektFormattingVersion = "1.21.0"
val kotlinLoggingVersion = "3.0.4"
val micrometerVersion = "1.9.3"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator:2.7.12")
    implementation("org.springframework.boot:spring-boot-starter-web:3.0.6") {
        exclude(module = "tomcat-embed-core")
    }
    implementation("org.springframework.boot:spring-boot-starter-aop:2.6.15")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.glassfish:javax.json:1.0.4")
    implementation("javax.json:javax.json-api:1.1")
    implementation("org.springdoc:springdoc-openapi-ui:$springDocVersion")
    implementation("org.springdoc:springdoc-openapi-webmvc-core:$springDocVersion")
    implementation("org.springdoc:springdoc-openapi-kotlin:$springDocVersion")
    implementation("com.github.tomakehurst:wiremock-jre8:2.35.0") {
        exclude(module = "commons-fileupload")
    }
    implementation("net.logstash.logback:logstash-logback-encoder:7.2")
    implementation("org.eclipse.jetty.http2:http2-server:9.4.48.v20220622")
    implementation("io.github.microutils:kotlin-logging-jvm:$kotlinLoggingVersion")
    implementation("io.micrometer:micrometer-core:$micrometerVersion")
    implementation("org.jeasy:easy-rules-core:4.1.0")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:$detektFormattingVersion")

    testImplementation("org.testcontainers:junit-jupiter:1.17.4")
    testImplementation("org.springframework.boot:spring-boot-starter-test:2.6.15")
    testImplementation("io.mockk:mockk:1.12.4")
    testImplementation("com.ninja-squad:springmockk:3.1.1")
    testImplementation("org.testcontainers:mongodb:1.17.6")
}

dependencyManagement {
    imports {
        mavenBom("org.testcontainers:testcontainers-bom:$testcontainersVersion")
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
    }
}

configurations.all {
    resolutionStrategy.eachDependency {
        if (requested.group == "org.yaml" && requested.name == "snakeyaml") {
            useVersion("1.32")
            because("SNYK-JAVA-ORGYAML-3113851")
        }
        if (requested.group == "org.springframework" && requested.name == "spring-webmvc") {
            useVersion("5.3.26")
            because("SNYK-JAVA-ORGSPRINGFRAMEWORK-3369852")
        }
        if (requested.group == "net.minidev" && requested.name == "json-smart") {
            useVersion("2.4.9")
            because("SNYK-JAVA-NETMINIDEV-3369748")
        }
        if (requested.group == "com.fasterxml.jackson.core" && requested.name == "jackson-databind") {
            useVersion("2.13.5")
            because("SNYK-JAVA-COMFASTERXMLJACKSONCORE-3038424")
        }
        if (requested.group == "org.scala-lang" && requested.name == "scala-library") {
            useVersion("2.13.9")
            because("SNYK-JAVA-ORGSCALALANG-3032987")
        }
        if (requested.group == "org.springframework.boot" && requested.name == "spring-boot-autoconfigure") {
            useVersion("2.5.15")
            because("SNYK-JAVA-ORGSPRINGFRAMEWORKBOOT-5564390")
        }
        if (requested.group == "org.springframework.boot" && requested.name == "spring-boot-actuator-autoconfigure") {
            useVersion("2.5.15")
            because("SNYK-JAVA-ORGSPRINGFRAMEWORKBOOT-5441321")
        }
        if (requested.group == "com.google.guava" && requested.name == "guava") {
            useVersion("32.0.0-jre")
            because("SNYK-JAVA-COMGOOGLEGUAVA-5710356")
        }
        if (requested.group == "org.springframework.data" && requested.name == "spring-data-mongodb") {
            useVersion("3.3.5")
            because("SNYK-JAVA-ORGSPRINGFRAMEWORKDATA-2932975")
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<JacocoReport> {
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
    afterEvaluate {
        classDirectories.setFrom(files(classDirectories.files.map {
            fileTree(it).apply {
                exclude( "Application.kt")
            }
        }))
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    setForkEvery(0)
    filter {
        isFailOnNoMatchingTests = false
    }
    finalizedBy(tasks.jacocoTestReport)
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

tasks.withType<Detekt>().configureEach {

    autoCorrect = true

    reports {
        xml.required.set(false)
        html.required.set(false)
        txt.required.set(false)
        sarif.required.set(false)
    }
}

dockerCompose {
    useComposeFiles.add("docker-compose.yml")
    projectName = "pool-de-vouchers"
}

dockerCompose.isRequiredBy(tasks.bootRun)

val detektGenerateBaseline by tasks.registering(DetektCreateBaselineTask::class) {
    description = "Overrides current baseline."
    buildUponDefaultConfig.set(true)
    ignoreFailures.set(true)
    parallel.set(true)
    setSource(files(projectDir))
    config.setFrom(files("$projectDir/config/detekt/detekt.yml"))
    baseline.set(file("$projectDir/config/detekt/baseline.xml"))
    include("**/*.kt")
    include("**/*.kts")
    exclude("**/resources/**")
    exclude("**/build/**")
}

testlogger {
    theme = MOCHA
    showPassed = false
    logLevel = QUIET
}
