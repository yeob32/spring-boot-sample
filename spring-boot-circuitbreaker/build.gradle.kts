extra["springCloudVersion"] = "2021.0.0"

dependencies {
	// https://github.com/resilience4j/resilience4j/issues/1151
	// spring boot 2.3.4 에서는 아래와 같이 충돌 회피
//	implementation("io.github.resilience4j:resilience4j-circuitbreaker:1.5.0")
//	implementation("io.github.resilience4j:resilience4j-spring-boot2:${property("resilience4jVersion")}") // 1.7.1

	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-resilience4j")
	implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
	runtimeOnly("io.micrometer:micrometer-registry-prometheus")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}