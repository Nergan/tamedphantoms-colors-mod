import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.1.20"
    id("net.neoforged.moddev") version "2.0.146"
    idea
}

fun prop(name: String): String = project.property(name) as String

val modId = prop("mod_id")
val modVersion = prop("mod_version")
val modGroupId = prop("mod_group_id")
val modName = prop("mod_name")
val modLicense = prop("mod_license")
val modAuthors = prop("mod_authors")
val modDescription = prop("mod_description")
val minecraftVersion = prop("minecraft_version")
val minecraftVersionRange = prop("minecraft_version_range")
val neoVersion = prop("neo_version")
val loaderVersionRange = prop("loader_version_range")
val parchmentMinecraftVersion = prop("parchment_minecraft_version")
val parchmentMappingsVersion = prop("parchment_mappings_version")
val kffVersion = prop("kff_version")
val kffVersionRange = prop("kff_version_range")
val tamedPhantomsVersion = prop("tamedphantoms_version")
val tamedPhantomsVersionRange = prop("tamedphantoms_version_range")
val patchouliVersion = prop("patchouli_version")
val dynamicLightsVersion = prop("lambdynamiclights_version")

version = modVersion
group = modGroupId

base {
    archivesName.set("$modId-neoforge-$minecraftVersion")
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(21))

kotlin {
    jvmToolchain(21)
    sourceSets.named("main") {
        kotlin.srcDir(file("src/neoforge/kotlin"))
        kotlin.srcDir(file("src/gametest/kotlin"))
    }
}

sourceSets.main {
    resources.srcDir(file("src/gametest/resources"))
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
        freeCompilerArgs.add("-Xjvm-default=all")
    }
}

repositories {
    maven {
        name = "Kotlin for Forge"
        url = uri("https://thedarkcolour.github.io/KotlinForForge/")
    }
    // Tamed Phantoms выходит на страницах GitHub Release: тег страницы — группа, имя jar без версии — модуль.
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
    mavenCentral()
}

// -Ptamedphantoms_local=<каталог tamedphantoms-mod> собирает против локального jar, пока релиз не вышел.
val tamedPhantoms: Any = findProperty("tamedphantoms_local")?.let {
    files("$it/build/libs/tamedphantoms-neoforge-$minecraftVersion-$tamedPhantomsVersion.jar")
} ?: "minecraft-$minecraftVersion-neoforge:tamedphantoms-neoforge-$minecraftVersion:$tamedPhantomsVersion@jar"

neoForge {
    enable {
        version = neoVersion
        // CI sets this automatically. Locally it skips Vineflower, which needs more RAM than this machine has.
        setDisableRecompilation(true)
    }

    parchment {
        mappingsVersion = parchmentMappingsVersion
        minecraftVersion = parchmentMinecraftVersion
    }

    runs {
        create("client") {
            client()
        }
        create("server") {
            server()
            programArgument("--nogui")
        }
        create("gameTestServer") {
            type = "gameTestServer"
            systemProperty("neoforge.enabledGameTestNamespaces", modId)
        }
        configureEach {
            systemProperty("forge.logging.markers", "REGISTRIES")
            logLevel.set(org.slf4j.event.Level.INFO)
        }
    }

    mods {
        create(modId) {
            sourceSet(sourceSets.main.get())
        }
    }
}

dependencies {
    implementation("thedarkcolour:kotlinforforge-neoforge:$kffVersion")
    implementation(tamedPhantoms)
    compileOnly("dev.lambdaurora.lambdynamiclights:lambdynamiclights-api:$dynamicLightsVersion:mojmap@jar")
    compileOnly("dev.yumi.commons:yumi-commons-event:1.0.0") { isTransitive = false }
    // Только для dev-запуска: книга Tamed Phantoms со страницей аддона.
    runtimeOnly("maven.modrinth:patchouli:$patchouliVersion")

    testImplementation(platform("org.junit:junit-bom:5.10.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

val generateModMetadata by tasks.registering(ProcessResources::class) {
    val replaceProperties = mapOf(
        "minecraft_version" to minecraftVersion,
        "minecraft_version_range" to minecraftVersionRange,
        "neo_version" to neoVersion,
        "loader_version_range" to loaderVersionRange,
        "mod_id" to modId,
        "mod_name" to modName,
        "mod_license" to modLicense,
        "mod_version" to modVersion,
        "mod_authors" to modAuthors,
        "mod_description" to modDescription,
        "kff_version_range" to kffVersionRange,
        "tamedphantoms_version_range" to tamedPhantomsVersionRange,
    )
    inputs.properties(replaceProperties)
    from("src/main/templates")
    into("build/generated/sources/modMetadata")
    filteringCharset = "UTF-8"
    filter { line ->
        var result = line
        for ((key, value) in replaceProperties) {
            result = result.replace("\${$key}", value)
        }
        result
    }
}

sourceSets.main.get().resources.srcDir(generateModMetadata)
neoForge.ideSyncTask(generateModMetadata)

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

tasks.jar {
    // Игровые тесты и их пустая структура нужны только runGameTestServer.
    exclude("com/tamedphantomscolors/mod/gametest/**", "data/tamedphantomscolors/structure/**")
    manifest {
        attributes(
            "Specification-Title" to modId,
            "Specification-Vendor" to modAuthors,
            "Specification-Version" to "1",
            "Implementation-Title" to modName,
            "Implementation-Version" to modVersion,
            "Implementation-Vendor" to modAuthors,
        )
    }
}

idea {
    module {
        isDownloadSources = true
        isDownloadJavadoc = true
    }
}
