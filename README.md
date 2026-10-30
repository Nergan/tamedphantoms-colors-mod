# Tamed Phantoms Colors

**[English](README.md)** · **[Русский](README.ru.md)**

An addon for [Tamed Phantoms](https://github.com/Nergan/tamedphantoms-mod) on **Minecraft 1.21.1 and 26.3**, for NeoForge and Fabric. It lets the owner dye a tamed phantom and its saddle, make it glow, and wash it clean again. Install one loader, not both. The NeoForge builds use [Kotlin for Forge](https://modrinth.com/mod/kotlin-for-forge), the Fabric builds use [Fabric Language Kotlin](https://modrinth.com/mod/fabric-language-kotlin).

## Features

- **Dye.** The owner right-clicks a tamed phantom with any dye. The body takes the color, the eyes keep their own, and the trail from its wings turns the same color. A dye on the saddle colors only the saddle, so the body and the saddle are dyed separately. The same color twice does not spend the dye.
- **Glow.** A glow ink sac makes the phantom a light source with light level 15, like a lamp. The phantom is drawn at full brightness, so a dyed phantom glows in its own color. Light on blocks around it needs [LambDynamicLights](https://modrinth.com/mod/lambdynamiclights) on the client. Minecraft light has no color, so the light on blocks stays white.
- **Brush.** A brush washes the paint and the glow off the phantom and its saddle. The phantom gives a dry shake without droplets, and the brush loses one point of durability.
- **Owner only.** Only the owner can dye, light up, or brush a tamed phantom and its saddle. Another player's dye, ink, or brush does nothing and is not spent.
- **Released phantoms.** Only a tamed phantom can be painted. A dye or a glow ink sac on a released phantom makes it angry: angry villager clouds, a growl, and it attacks that player for a while, the same way it defends itself.
- **Release.** When the owner releases a painted phantom, it shakes the paint off in a spray of that color and returns to its natural color. The saddle loses its dye as well. A saddle removed with shears also loses its dye.
- **Advancements.** “Now We're Different” for the first dye, “A Light in the Dark” for the glow ink, “What's Natural Is Right” for the brush, “Free of Labels” when a released phantom shakes the paint off, and “I'm Not Wearing That!” for trying to paint a released phantom. They sit next to the Tamed Phantoms advancements.
- **Guidebook.** With [Patchouli](https://modrinth.com/mod/patchouli) on 1.21.1, the Tamed Phantoms guidebook gets a *Colors* page in the taming chapter. Patchouli has no 26.3 build.

## Downloads

Jars live on [GitHub Releases](https://github.com/Nergan/tamedphantoms-colors-mod/releases/latest). A push to `main` updates one release page per Minecraft version and loader. Each page includes `all-jars.zip`. Download one set and put those files in the `mods` folder. Do not install `*-sources.jar`.

### Minecraft 1.21.1, NeoForge

| File | Required | What it is |
| --- | --- | --- |
| `tamedphantomscolors-neoforge-1.21.1-1.1.0.jar` | Yes | this addon |
| `tamedphantoms-neoforge-1.21.1-1.0.6.jar` | Yes | [Tamed Phantoms](https://github.com/Nergan/tamedphantoms-mod) 1.0.6 or newer |
| `kotlinforforge-5.8.0-all.jar` | Yes | [Kotlin for Forge](https://modrinth.com/mod/kotlin-for-forge) |
| `Patchouli-1.21.1-93-NEOFORGE.jar` | No | [Patchouli](https://modrinth.com/mod/patchouli), only for the guidebook |

### Minecraft 1.21.1, Fabric

| File | Required | What it is |
| --- | --- | --- |
| `tamedphantomscolors-fabric-1.21.1-1.1.0.jar` | Yes | this addon |
| `tamedphantoms-fabric-1.21.1-1.0.6.jar` | Yes | [Tamed Phantoms](https://github.com/Nergan/tamedphantoms-mod) 1.0.6 or newer |
| `fabric-api-0.116.17+1.21.1.jar` | Yes | [Fabric API](https://modrinth.com/mod/fabric-api) |
| `fabric-language-kotlin-1.13.2+kotlin.2.1.20.jar` | Yes | [Fabric Language Kotlin](https://modrinth.com/mod/fabric-language-kotlin) |
| `cloth-config-15.0.140-fabric.jar` | Yes | [Cloth Config](https://modrinth.com/mod/cloth-config), required by Tamed Phantoms |
| `Patchouli-1.21.1-93-FABRIC.jar` | No | [Patchouli](https://modrinth.com/mod/patchouli), only for the guidebook |

### Minecraft 26.3, NeoForge

| File | Required | What it is |
| --- | --- | --- |
| `tamedphantomscolors-neoforge-26.3-1.1.0.jar` | Yes | this addon |
| `tamedphantoms-neoforge-26.3-1.0.6.jar` | Yes | [Tamed Phantoms](https://github.com/Nergan/tamedphantoms-mod) 1.0.6 or newer |
| `kotlinforforge-6.4.0-all.jar` | Yes | [Kotlin for Forge](https://modrinth.com/mod/kotlin-for-forge) |

### Minecraft 26.3, Fabric

| File | Required | What it is |
| --- | --- | --- |
| `tamedphantomscolors-fabric-26.3-1.1.0.jar` | Yes | this addon |
| `tamedphantoms-fabric-26.3-1.0.6.jar` | Yes | [Tamed Phantoms](https://github.com/Nergan/tamedphantoms-mod) 1.0.6 or newer |
| `fabric-api-0.162.0+26.3.jar` | Yes | [Fabric API](https://modrinth.com/mod/fabric-api) |
| `fabric-language-kotlin-1.14.2+kotlin.2.4.21.jar` | Yes | [Fabric Language Kotlin](https://modrinth.com/mod/fabric-language-kotlin) |
| `cloth-config-fabric-26.3.159.jar` | Yes | [Cloth Config](https://modrinth.com/mod/cloth-config), required by Tamed Phantoms |

LambDynamicLights is not bundled: install it from [Modrinth](https://modrinth.com/mod/lambdynamiclights) if you want real light (4.8.11+1.21.1 or newer on 1.21.1, 4.13.0+26.3 or newer on 26.3).

## Requirements

| Component | 1.21.1 | 26.3 |
| --- | --- | --- |
| Tamed Phantoms | 1.0.6 or newer, same loader | 1.0.6 or newer, same loader |
| NeoForge | 21.1.209 or newer | 26.3.0.76 or newer |
| Kotlin for Forge | 5.8.0, **NeoForge** build | 6.4.0 |
| Fabric Loader | 0.16.10 or newer | 0.19.5 or newer |
| Fabric API | 0.116.17+1.21.1 | 0.162.0+26.3 |
| Fabric Language Kotlin | 1.13.2+kotlin.2.1.20 | 1.14.2+kotlin.2.4.21 |
| Java | 21 | 25 |
| LambDynamicLights | optional, client only, 4.8.11+1.21.1 | optional, client only, 4.13.0+26.3 |
| Patchouli | optional, 1.21.1-93 for the same loader | none for 26.3 |

## Installation

1. Install Tamed Phantoms 1.0.6 or newer with its requirements for your Minecraft version and loader.
2. Put the addon jar for the same version and loader into `mods`.
3. Optionally add LambDynamicLights on each client for real light, and on 1.21.1 Patchouli for the guidebook page.

The addon is required on both the server and the clients.

## Configuration

The addon has no settings.

## License

The code is [MPL-2.0](LICENSE). The look of the phantom (dye, saddle dye, glow) is stored and drawn by Tamed Phantoms; this addon only decides what a click does.
