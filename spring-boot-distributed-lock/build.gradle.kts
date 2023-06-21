dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.redisson:redisson-spring-boot-starter:3.17.7")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")

    runtimeOnly("com.h2database:h2")
}