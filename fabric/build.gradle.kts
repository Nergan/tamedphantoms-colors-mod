import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("fabric-loom") version "1.9.2"
    kotlin("jvm") version "2.1.20"
}

fun prop(name: String): String = project.property(name) as String

val minecraftVersion = prop("minecraft_version")
val tamedPhantomsVersion = prop("tamedphantoms_version")

version = prop("mod_version")
group = prop("mod_group_id")

base {
    archivesName.set("${prop("mod_id")}-fabric-$minecraftVersion")
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(21))

kotlin {
    jvmToolchain(21)
    sourceSets.named("main") {
        kotlin.srcDir(rootProject.file("src/main/kotlin"))
    }
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
        freeCompilerArgs.add("-Xjvm-default=all")
    }
}

sourceSets.named("main") {
    resources.srcDir(rootProject.file("src/main/resources"))
}

repositories {
    ivy {
        name = "Tamed Phantoms releases"
        url = uri("https://github.com/Nergan/tamedphantoms-mod/releases/download")
        patternLayout { artifact("[organisation]/[module]-[revision].[ext]") }
        metadataSources { artifact() }
        content { includeGroupByRegex("minecraft-.*") }
    }
    exclusiveContent {
        forRepository {
            maven {
                name = "Modrinth"
                url = uri("https://api.modrinth.com/maven")
            }
        }
        filter { includeGroup("maven.modrinth") }
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

val tamedPhantoms: Any = findProperty("tamedphantoms_local")?.let {
    files("$it/fabric/build/libs/tamedphantoms-fabric-$minecraftVersion-$tamedPhantomsVersion.jar")
} ?: "minecraft-$minecraftVersion-fabric:tamedphantoms-fabric-$minecraftVersion:$tamedPhantomsVersion@jar"

dependencies {
    minecraft("com.mojang:minecraft:$minecraftVersion")
    mappings(loom.officialMojangMappings())
    modImplementation("net.fabricmc:fabric-loader:${prop("fabric_loader_version")}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${prop("fabric_api_version")}")
    modImplementation("net.fabricmc:fabric-language-kotlin:${prop("fabric_kotlin_version")}")
    modImplementation(tamedPhantoms)
    modRuntimeOnly("me.shedaniel.cloth:cloth-config-fabric:${prop("cloth_config_version")}") {
        exclude(group = "net.fabricmc.fabric-api")
    }
    // API собран Loom 1.16, Loom 1.9.2 его не переназначает. Имена в dev — Mojang, как у варианта mojmap.
    compileOnly("dev.lambdaurora.lambdynamiclights:lambdynamiclights-api:${prop("lambdynamiclights_version")}:mojmap@jar")
    compileOnly("dev.yumi.commons:yumi-commons-event:1.0.0") { isTransitive = false }
    // Только для dev-запуска: книга Tamed Phantoms со страницей аддона.
    // Loom не берёт JiJ из jar Patchouli, поэтому Fiber подключён отдельно.
    modRuntimeOnly("maven.modrinth:patchouli:${prop("patchouli_fabric_version")}")
    modRuntimeOnly("me.zeroeightsix:fiber:0.23.0-2")
}

tasks.processResources {
    val version = project.version.toString()
    inputs.property("version", version)
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    filesMatching("fabric.mod.json") {
        filter { line -> line.replace("\${version}", version) }
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}
