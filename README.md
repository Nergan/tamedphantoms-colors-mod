# Tamed Phantoms Colors

**[English](README.md)** · **[Русский](README.ru.md)**

An addon for [Tamed Phantoms](https://github.com/Nergan/tamedphantoms-mod) on **Minecraft 1.21.1**, for NeoForge and Fabric. It lets the owner dye a tamed phantom and its saddle, make it glow, and wash it clean again. Install one loader, not both. The NeoForge build uses [Kotlin for Forge](https://modrinth.com/mod/kotlin-for-forge), the Fabric build uses [Fabric Language Kotlin](https://modrinth.com/mod/fabric-language-kotlin).

## Features

- **Dye.** The owner right-clicks a tamed phantom with any dye. The body takes the color, the eyes keep their own, and the trail from its wings turns the same color. A dye on the saddle colors only the saddle, so the body and the saddle are dyed separately. The same color twice does not spend the dye.
- **Glow.** A glow ink sac makes the phantom a light source with light level 15, like a lamp. The phantom is drawn at full brightness, so a dyed phantom glows in its own color. Light on blocks around it needs [LambDynamicLights](https://modrinth.com/mod/lambdynamiclights) on the client. Minecraft light has no color, so the light on blocks stays white.
- **Brush.** A brush washes the paint and the glow off the phantom and its saddle. The phantom gives a dry shake without droplets, and the brush loses one point of durability.
- **Released phantoms.** Only a tamed phantom can be painted. A dye or a glow ink sac on a released phantom makes it angry: angry villager clouds, a growl, and it attacks that player for a while, the same way it defends itself.
- **Release.** When the owner releases a painted phantom, it shakes the paint off in a spray of that color and returns to its natural color. The saddle loses its dye as well. A saddle removed with shears also loses its dye.
- **Guidebook.** With [Patchouli](https://modrinth.com/mod/patchouli), the Tamed Phantoms guidebook gets a *Colors* page in the taming chapter.

## Downloads

Jars live on [GitHub Releases](https://github.com/Nergan/tamedphantoms-colors-mod/releases/latest). A push to `main` updates one release page per loader. Each page includes `all-jars.zip`. Download one set and put those files in the `mods` folder. Do not install `*-sources.jar`.

### NeoForge

| File | Required | What it is |
| --- | --- | --- |
| `tamedphantomscolors-neoforge-1.21.1-1.0.0.jar` | Yes | this addon, NeoForge 1.21.1 |
| `tamedphantoms-neoforge-1.21.1-1.0.5.jar` | Yes | [Tamed Phantoms](https://github.com/Nergan/tamedphantoms-mod) 1.0.5 or newer |
| `kotlinforforge-5.8.0-all.jar` | Yes | [Kotlin for Forge](https://modrinth.com/mod/kotlin-for-forge) |
| `Patchouli-1.21.1-93-NEOFORGE.jar` | No | [Patchouli](https://modrinth.com/mod/patchouli), only for the guidebook |

### Fabric

| File | Required | What it is |
| --- | --- | --- |
| `tamedphantomscolors-fabric-1.21.1-1.0.0.jar` | Yes | this addon, Fabric 1.21.1 |
| `tamedphantoms-fabric-1.21.1-1.0.5.jar` | Yes | [Tamed Phantoms](https://github.com/Nergan/tamedphantoms-mod) 1.0.5 or newer |
| `fabric-api-0.116.17+1.21.1.jar` | Yes | [Fabric API](https://modrinth.com/mod/fabric-api) |
| `fabric-language-kotlin-1.13.2+kotlin.2.1.20.jar` | Yes | [Fabric Language Kotlin](https://modrinth.com/mod/fabric-language-kotlin) |
| `cloth-config-15.0.140-fabric.jar` | Yes | [Cloth Config](https://modrinth.com/mod/cloth-config), required by Tamed Phantoms |
| `Patchouli-1.21.1-93-FABRIC.jar` | No | [Patchouli](https://modrinth.com/mod/patchouli), only for the guidebook |

LambDynamicLights is not bundled: install it from [Modrinth](https://modrinth.com/mod/lambdynamiclights) (4.8.11 or newer for 1.21.1) if you want real light.

## Requirements

| Component | Version |
| --- | --- |
| Minecraft | 1.21.1 |
| Tamed Phantoms | 1.0.5 or newer, same loader |
| NeoForge | 21.1.209 or newer |
| Kotlin for Forge | 5.8.0, **NeoForge** build |
| Fabric Loader | 0.16.10 or newer |
| Fabric API | 0.116.17+1.21.1 |
| Fabric Language Kotlin | 1.13.2+kotlin.2.1.20 |
| Java | 21 |
| LambDynamicLights | optional, client only, 4.8.11+1.21.1 |
| Patchouli | optional, 1.21.1-93 for the same loader |

## Installation

1. Install Tamed Phantoms 1.0.5 or newer with its requirements.
2. Put the addon jar for the same loader into `mods`.
3. Optionally add LambDynamicLights on each client for real light, and Patchouli for the guidebook page.

The addon is required on both the server and the clients.

## Configuration

The addon has no settings.

## License

The code is [MPL-2.0](LICENSE). The look of the phantom (dye, saddle dye, glow) is stored and drawn by Tamed Phantoms; this addon only decides what a click does.
