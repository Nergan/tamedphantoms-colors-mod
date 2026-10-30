import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.Properties
import org.gradle.api.tasks.Sync

plugins {
    id("net.fabricmc.fabric-loom") version "1.18.4"
    kotlin("jvm")
}

fun prop(name: String): String = project.property(name) as String

fun parentProp(name: String): String {
    val props = Properties()
    rootProject.file("../../gradle.properties").reader(Charsets.UTF_8).use { props.load(it) }
    return props.getProperty(name) ?: error("Missing $name in the 1.21.1 gradle.properties")
}

val minecraftVersion = prop("minecraft_version")
val tamedPhantomsVersion = parentProp("tamedphantoms_version")

version = parentProp("mod_version")
group = parentProp("mod_group_id")

base {
    archivesName.set("${parentProp("mod_id")}-fabric-$minecraftVersion")
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(25))

val rewriteFor26 = rootProject.extra["rewriteFor26"] as (String) -> String

val sharedKotlin by tasks.registering(Sync::class) {
    from(rootProject.file("../../src/main/kotlin"))
    from(rootProject.file("../../fabric/src/main/kotlin"))
    into(layout.buildDirectory.dir("shared-kotlin"))
    inputs.property("rewriteStamp", "init-1")
    filter { line -> rewriteFor26(line) }
}

kotlin {
    jvmToolchain(25)
    sourceSets.named("main") {
        kotlin.srcDir(sharedKotlin)
    }
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_25)
        freeCompilerArgs.add("-Xjvm-default=all")
    }
}

sourceSets.named("main") {
    resources.srcDir("src/main/resources")
    resources.srcDir(rootProject.file("src/main/resources"))
    // fabric/ is a subproject of versions/26.3, so the mod root is two levels up.
    resources.srcDir(rootProject.file("../../src/main/resources"))
}

repositories {
    ivy {
        name = "Tamed Phantoms releases"
        url = uri("https://github.com/Nergan/tamedphantoms-mod/releases/download")
        patternLayout { artifact("[organisation]/[module]-[revision].[ext]") }
        metadataSources { artifact() }
        content { includeGroupByRegex("minecraft-.*") }
    }
    maven {
        name = "LambDynamicLights"
        url = uri("https://maven.gegy.dev/releases")
        content { includeGroup("dev.lambdaurora.lambdynamiclights") }
    }
    maven {
        name = "Shedaniel"
        url = uri("https://maven.shedaniel.me/")
    }
    mavenCentral()
}

val tamedPhantoms: Any = rootProject.findProperty("tamedphantoms_local")?.let {
    files("$it/versions/26.3/fabric/build/libs/tamedphantoms-fabric-$minecraftVersion-$tamedPhantomsVersion.jar")
} ?: "minecraft-$minecraftVersion-fabric:tamedphantoms-fabric-$minecraftVersion:$tamedPhantomsVersion@jar"

dependencies {
    minecraft("com.mojang:minecraft:$minecraftVersion")
    implementation("net.fabricmc:fabric-loader:${prop("fabric_loader_version")}")
    implementation("net.fabricmc.fabric-api:fabric-api:${prop("fabric_api_version")}")
    implementation("net.fabricmc:fabric-language-kotlin:${prop("fabric_kotlin_version")}")
    implementation(tamedPhantoms)
    runtimeOnly("me.shedaniel.cloth:cloth-config-fabric:${prop("cloth_config_version")}")
    compileOnly("dev.lambdaurora.lambdynamiclights:lambdynamiclights-api:${prop("lambdynamiclights_version")}@jar")
    compileOnly("dev.yumi.commons:yumi-commons-event:${prop("yumi_event_version")}") { isTransitive = false }
}

tasks.processResources {
    val version = project.version.toString()
    inputs.property("version", version)
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    filesMatching("fabric.mod.json") {
        filter { line -> line.replace("\${version}", version) }
    }
}

loom {
    runs {
        named("server") {
            programArgs("--nogui", "--port", "25567")
            vmArgs("-Xms128m", "-Xmx512m", "-XX:+UseSerialGC")
        }
        named("client") {
            vmArgs("-Xms256m", "-Xmx1024m", "-XX:+UseSerialGC")
        }
    }
}
