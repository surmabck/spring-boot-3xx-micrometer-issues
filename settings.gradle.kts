pluginManagement {
	val kotlinVersion: String by settings
	val springBootVersion: String by settings
	val springDependencyManagementVersion: String by settings

	plugins {
		kotlin("jvm") version kotlinVersion apply false
		id("org.springframework.boot") version springBootVersion apply false
		id("io.spring.dependency-management") version springDependencyManagementVersion apply false
		id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion apply false
	}

	repositories {
		maven { url = uri("https://repo.spring.io/snapshot") }

		gradlePluginPortal()
	}
	resolutionStrategy {
		eachPlugin {
			if (requested.id.id == "org.springframework.boot") {
				useModule("org.springframework.boot:spring-boot-gradle-plugin:${springBootVersion}")
			}
		}
	}
}

rootProject.name = "demo"
rootProject.buildFileName = "build.gradle.kts"
