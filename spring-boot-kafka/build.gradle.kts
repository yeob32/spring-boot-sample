dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.kafka:spring-kafka")
    runtimeOnly("com.h2database:h2")
    testImplementation("org.springframework.kafka:spring-kafka-test")
}
