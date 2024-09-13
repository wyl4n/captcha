plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.0"
}

group = "com.redeverse"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()

    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven ("https://oss.sonatype.org/content/groups/public/")
    maven ("https://repo.codemc.org/repository/maven-public")

    maven( "https://jitpack.io")

}

dependencies {
    // Spigot
    compileOnly ("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")
    compileOnly ("com.github.azbh111:craftbukkit-1.8.8:R")

    // Lombok
    compileOnly ("org.projectlombok:lombok:1.18.20")
    annotationProcessor ("org.projectlombok:lombok:1.18.20")

    // InventoryFramework
    implementation("com.github.DevNatan.inventory-framework:inventory-framework:7c95ccca6c84fdb8ecff6375b0ef1add09e6440c")

    // Intellij
    implementation("org.jetbrains:annotations:24.0.0")

}

tasks {

    java {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }
    compileJava { options.encoding = "UTF-8"}

    java {
        shadowJar {
            archiveFileName.set("captcha.jar")

            relocate(
                "me.saiintbrisson.minecraft",
                "com.redeverse.captcha.libs.inventory"
            )
        }
    }
}