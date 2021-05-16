dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    runtimeOnly("mysql:mysql-connector-java")

    testImplementation("io.kotest:kotest-runner-junit5:4.5.0")
    testImplementation("io.kotest:kotest-extensions-spring:4.4.3")
}
