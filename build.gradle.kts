plugins {
    id("java")
}

group = "io.github.tongnamuu"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.temporal:temporal-sdk:1.36.0")
    runtimeOnly("org.slf4j:slf4j-simple:1.7.36")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

}

tasks.test {
    useJUnitPlatform()
}

tasks.register<JavaExec>("runGreeting") {
    group = "application"
    description = "Greeting 클라이언트를 실행합니다."
    classpath = sourceSets.main.get().runtimeClasspath
    mainClass.set("client.GreetingClient")
}
