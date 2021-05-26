dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    runtimeOnly("mysql:mysql-connector-java")

    api("com.querydsl:querydsl-jpa")
    kapt("com.querydsl:querydsl-apt::jpa")

    annotationProcessor(group = "com.querydsl", name = "querydsl-apt", classifier = "jpa")

    testImplementation("io.kotest:kotest-runner-junit5:4.5.0")
    testImplementation("io.kotest:kotest-extensions-spring:4.4.3")

    testImplementation("io.mockk:mockk:1.9.3.kotlin12")
}
