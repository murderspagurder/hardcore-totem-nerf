plugins {
    id("dev.isxander.modstitch.base") version "0.5.12"
    id("dev.kikugie.j52j") version "2.0"
    id("dev.kikugie.postprocess.jsonlang") version "2.1-beta.4"
    id("me.modmuss50.mod-publish-plugin") version "0.8.4"
}

fun prop(name: String, consumer: (prop: String) -> Unit) {
    (findProperty(name) as? String?)
        ?.let(consumer)
}

val baseVersion = property("mod.version") as String
val minecraft = property("deps.minecraft") as String;
val loader = when {
    modstitch.isLoom -> "fabric"
    modstitch.isModDevGradleRegular -> "neoforge"
    modstitch.isModDevGradleLegacy -> "forge"
    else -> throw IllegalStateException("Unsupported Loader")
}
val midnightlibVersion = property("deps.midnightlib") as String;

j52j {
    params {
        prettyPrinting = true
    }
}

jsonlang {
    languageDirectories = listOf("assets/hardcoretotemnerf/lang")
    prettyPrint = true
}

modstitch {
    minecraftVersion = minecraft

    // Alternatively use stonecutter.eval if you have a lot of versions to target.
    // https://stonecutter.kikugie.dev/stonecutter/guide/setup#checking-versions
    var jvm1206 = 17
    if (loader == "neoforge") {
        jvm1206 = 21
    }
    javaTarget = when (minecraft) {
        "1.20.1" -> 17
        "1.20.2" -> 17
        "1.20.4" -> 17
        "1.20.6" -> jvm1206
        "1.21.1" -> 21
        "1.21.3" -> 21
        "1.21.4" -> 21
        "1.21.5" -> 21
        "1.21.6" -> 21
        else -> throw IllegalArgumentException("Please store the java version for ${property("deps.minecraft")} in build.gradle.kts!")
    }

    // If parchment doesnt exist for a version yet you can safely
    // omit the "deps.parchment" property from your versioned gradle.properties
    parchment {
        prop("deps.parchment") { mappingsVersion = it }
    }

    // This metadata is used to fill out the information inside
    // the metadata files found in the templates folder.
    metadata {
        modId = "hardcoretotemnerf"
        modName = "Hardcore Totem Nerf"
        modVersion = "${baseVersion}+${minecraft}-${loader}"
        modGroup = "dev.spagurder"
        modAuthor = "murder_spagurder"
        modDescription = "Hardcore-inspired Nerfs and Debuffs for Totems of Undying"
        modLicense = "MIT"

        fun <K, V> MapProperty<K, V>.populate(block: MapProperty<K, V>.() -> Unit) {
            block()
        }

        replacementProperties.populate {
            // You can put any other replacement properties/metadata here that
            // modstitch doesn't initially support. Some examples below.
            put("mod_issue_tracker", "https://github.com/murderspagurder/hardcore-totem-nerf/issues")
            put("pack_format", when (property("deps.minecraft")) {
                "1.20.1" -> 15
                "1.20.2" -> 18
                "1.20.4" -> 22
                "1.20.6" -> 32
                "1.21.1" -> 34
                "1.21.3" -> 42
                "1.21.4" -> 46
                "1.21.5" -> 55
                "1.21.6" -> 63
                else -> throw IllegalArgumentException("Please store the resource pack version for ${property("deps.minecraft")} in build.gradle.kts! https://minecraft.wiki/w/Pack_format")
            }.toString())
        }
    }

    // Fabric Loom (Fabric)
    loom {
        // It's not recommended to store the Fabric Loader version in properties.
        // Make sure its up to date.
        fabricLoaderVersion = "0.16.14"

        // Configure loom like normal in this block.
        configureLoom {

        }
    }

    // ModDevGradle (NeoForge, Forge, Forgelike)
    moddevgradle {
        enable {
            prop("deps.forge") { forgeVersion = it }
            prop("deps.neoform") { neoFormVersion = it }
            prop("deps.neoforge") { neoForgeVersion = it }
            prop("deps.mcp") { mcpVersion = it }
        }

        // Configures client and server runs for MDG, it is not done by default
        defaultRuns()

        // This block configures the `neoforge` extension that MDG exposes by default,
        // you can configure MDG like normal from here
        configureNeoforge {
            runs.all {
                disableIdeRun()
            }
        }
    }

    mixin {
        // You do not need to specify mixins in any mods.json/toml file if this is set to
        // true, it will automatically be generated.
        addMixinsToModManifest = true

        configs.register("hardcoretotemnerf")

        // Most of the time you wont ever need loader specific mixins.
        // If you do, simply make the mixin file and add it like so for the respective loader:
        // if (isLoom) configs.register("examplemod-fabric")
        // if (isModDevGradleRegular) configs.register("examplemod-neoforge")
        // if (isModDevGradleLegacy) configs.register("examplemod-forge")
    }
}

// Stonecutter constants for mod loaders.
// See https://stonecutter.kikugie.dev/stonecutter/guide/comments#condition-constants
var constraint: String = name.split("-")[1]
stonecutter {
    consts(
        "fabric" to constraint.equals("fabric"),
        "neoforge" to constraint.equals("neoforge"),
        "forge" to constraint.equals("forge"),
        "vanilla" to constraint.equals("vanilla")
    )
}

// All dependencies should be specified through modstitch's proxy configuration.
// Wondering where the "repositories" block is? Go to "stonecutter.gradle.kts"
// If you want to create proxy configurations for more source sets, such as client source sets,
// use the modstitch.createProxyConfigurations(sourceSets["client"]) function.
dependencies {
    modstitch.loom {
        val fabricApiVersion = property("deps.fabric-api") as String;
        modstitchModImplementation("net.fabricmc.fabric-api:fabric-api:${fabricApiVersion}")
    }

    // Anything else in the dependencies block will be used for all platforms.
    modstitchModImplementation("maven.modrinth:midnightlib:${midnightlibVersion}")
    modstitchJiJ("maven.modrinth:midnightlib:${midnightlibVersion}")
}

tasks.register<Copy>("buildAndCollect") {
    group = "build"
    from(modstitch.finalJarTask.map { it.archiveFile })
    into(rootProject.layout.buildDirectory.file("libs/${project.property("mod.version")}"))
    dependsOn("build")
}

val onlyVersion = rootProject.findProperty("onlyVersion") as String?
val additionalVersionsStr = findProperty("publish.additionalVersions") as String?
val additionalVersions: List<String> = additionalVersionsStr
    ?.split(",")
    ?.map { it.trim() }
    ?.filter { it.isNotEmpty() }
    ?: emptyList()
if (onlyVersion == null || onlyVersion == project.name) {
    publishMods {
        version = "${baseVersion}+${minecraft}"
        displayName = "Hardcore Totem Nerf $baseVersion for $loader $minecraft"
        file = modstitch.finalJarTask.flatMap { it.archiveFile }
        type = STABLE
        modLoaders.add(loader)
        changelog = rootProject.file("CHANGELOG.md").readText()

        modrinth {
            accessToken = providers.environmentVariable("MODRINTH_API_KEY")
            projectId = "FcCGemui"
            minecraftVersions.add(minecraft)
            minecraftVersions.addAll(additionalVersions)
        }
    }
}