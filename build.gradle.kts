import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlinVersion: String by project
val jvmVersion: String by project
val projectGroup: String by project
val projectVersion: String by project

plugins {
	kotlin("jvm") apply true
	id("org.springframework.boot") apply false
	id("io.spring.dependency-management")  apply false
	id("org.jetbrains.kotlin.plugin.spring")  apply false
}


allprojects {
	group = projectGroup
	version = projectVersion
	apply(plugin = "org.jetbrains.kotlin.jvm")
	apply(plugin = "org.jetbrains.kotlin.plugin.spring")
	apply(plugin = "io.spring.dependency-management")

	repositories {
		mavenCentral()
		maven("https://plugins.gradle.org/m2/")
		maven("https://repo.spring.io/snapshot")
	}
	tasks.withType<Test> {
		useJUnitPlatform()
	}

	tasks.withType<KotlinCompile> {
		kotlinOptions.jvmTarget = jvmVersion
	}

	the<DependencyManagementExtension>().apply {
		imports {
			mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES) {
				bomProperty("kotlin.version", kotlinVersion)
			}
		}
	}

	dependencies {
		runtimeOnly("org.springframework.boot:spring-boot-starter-actuator")

		implementation("io.micrometer:context-propagation")
		implementation("io.micrometer:micrometer-observation")
		implementation("io.micrometer:micrometer-tracing-bridge-brave")

		implementation("org.springframework.boot:spring-boot-starter")
		implementation("org.springframework.boot:spring-boot-starter-webflux")

		implementation("io.projectreactor.kotlin:reactor-kotlin-extensions:1.0.2.RELEASE")

		implementation("io.github.microutils:kotlin-logging:1.7.9") {
			exclude("org.jetbrains.kotlin")
		}
		testImplementation("io.kotest:kotest-assertions-core:5.3.1")
		testImplementation("org.springframework.boot:spring-boot-starter-test")
	}

}