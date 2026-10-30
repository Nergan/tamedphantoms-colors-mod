import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.Properties
import org.gradle.api.tasks.Sync

plugins {
    kotlin("jvm") version "2.4.0"
    id("net.neoforged.moddev") version "2.0.148"
}

fun prop(name: String): String = project.property(name) as String

fun parentProp(name: String): String {
    val props = Properties()
    file("../../gradle.properties").reader(Charsets.UTF_8).use { props.load(it) }
    return props.getProperty(name) ?: error("Missing $name in the 1.21.1 gradle.properties")
}

fun rewriteFor26(line: String): String {
    var s = line
    val pairs = listOf(
        "net.minecraft.resources.ResourceLocation" to "net.minecraft.resources.Identifier",
        "ResourceLocation" to "Identifier",
        // 26.3 keeps the dye color in a data component, DyeItem no longer exposes it.
        "stack.item is DyeItem -> PaintRules.Tool.DYE" to
            "stack.get(net.minecraft.core.component.DataComponents.DYE) != null -> PaintRules.Tool.DYE",
        "val dye = (stack.item as? DyeItem)?.dyeColor" to
            "val dye = stack.get(net.minecraft.core.component.DataComponents.DYE)",
        "stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand))" to "stack.hurtAndBreak(1, player, hand)",
        "DustParticleOptions(Vec3.fromRGB24(rgb).toVector3f(), 1.2f)" to "DustParticleOptions(rgb, 1.2f)",
        // LambDynamicLights 4.13 dropped the old initializer method.
        "    @Deprecated(\"Старая точка входа LambDynamicLights. Новая получает весь контекст.\")" to "",
        "    override fun onInitializeDynamicLights(itemLightSourceManager: ItemLightSourceManager) = Unit" to "",
    )
    for ((from, to) in pairs) {
        s = s.replace(from, to)
    }
    return s
}

extra["rewriteFor26"] = { line: String -> rewriteFor26(line) }

val modId = parentProp("mod_id")
val modVersion = parentProp("mod_version")
val modGroupId = parentProp("mod_group_id")
val modName = parentProp("mod_name")
val modLicense = parentProp("mod_license")
val modAuthors = parentProp("mod_authors")
val modDescription = parentProp("mod_description")
val tamedPhantomsVersion = parentProp("tamedphantoms_version")
val tamedPhantomsVersionRange = parentProp("tamedphantoms_version_range")
val minecraftVersion = prop("minecraft_version")
val minecraftVersionRange = prop("minecraft_version_range")
val neoVersion = prop("neo_version")
val kffVersion = prop("kff_version")
val kffVersionRange = prop("kff_version_range")

version = modVersion
group = modGroupId

base {
    archivesName.set("$modId-neoforge-$minecraftVersion")
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(25))

val sharedKotlin by tasks.registering(Sync::class) {
    from(file("../../src/main/kotlin"))
    from(file("../../src/neoforge/kotlin"))
    into(layout.buildDirectory.dir("shared-kotlin"))
    inputs.property("rewriteStamp", "init-1")
    filter { line -> rewriteFor26(line) }
}

kotlin {
    jvmToolchain(25)
    sourceSets.named("main") {
        kotlin.srcDir(sharedKotlin)
    }
    sourceSets.named("test") {
        kotlin.srcDir(file("../../src/test/kotlin"))
    }
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_25)
        freeCompilerArgs.add("-Xjvm-default=all")
    }
}

sourceSets.main {
    resources.srcDir("src/main/resources")
    resources.srcDir(file("../../src/main/resources"))
}

repositories {
    maven {
        name = "Kotlin for Forge"
        url = uri("https://thedarkcolour.github.io/KotlinForForge/")
    }
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
    mavenCentral()
}

// -Ptamedphantoms_local=<каталог tamedphantoms-mod> собирает против локального jar, пока релиз не вышел.
val tamedPhantoms: Any = findProperty("tamedphantoms_local")?.let {
    files("$it/versions/26.3/build/libs/tamedphantoms-neoforge-$minecraftVersion-$tamedPhantomsVersion.jar")
} ?: "minecraft-$minecraftVersion-neoforge:tamedphantoms-neoforge-$minecraftVersion:$tamedPhantomsVersion@jar"

neoForge {
    enable {
        version = neoVersion
        // CI sets this automatically. Locally it skips Vineflower, which needs more RAM than this machine has.
        setDisableRecompilation(true)
    }

    runs {
        create("client") {
            client()
            jvmArguments.addAll(listOf("-Xms256m", "-Xmx1024m", "-XX:+UseSerialGC"))
        }
        create("server") {
            server()
            programArguments.addAll(listOf("--nogui", "--port", "25567"))
            jvmArguments.addAll(listOf("-Xms256m", "-Xmx768m", "-XX:+UseSerialGC"))
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
    compileOnly("dev.lambdaurora.lambdynamiclights:lambdynamiclights-api:${prop("lambdynamiclights_version")}@jar")
    compileOnly("dev.yumi.commons:yumi-commons-event:${prop("yumi_event_version")}") { isTransitive = false }
    testImplementation(platform("org.junit:junit-bom:5.10.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

val generateModMetadata by tasks.registering(ProcessResources::class) {
    val replaceProperties = mapOf(
        "minecraft_version_range" to minecraftVersionRange,
        "neo_version" to neoVersion,
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

tasks.processResources {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.jar {
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
