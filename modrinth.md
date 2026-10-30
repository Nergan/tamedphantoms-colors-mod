Dye your tamed phantoms. An addon for [Tamed Phantoms](https://github.com/Nergan/tamedphantoms-mod) on Minecraft 1.21.1, for NeoForge and Fabric. Install one loader, not both.

## What it does

- **Dye.** Right-click your tamed phantom with any dye. The body takes the color, the eyes keep their own glow, and the trail from its wings turns the same color.
- **Saddle.** Use a dye on the saddle to color the saddle alone. The body and the saddle are dyed separately.
- **Glow.** A glow ink sac turns the phantom into a light source with light level 15, like a lamp. It is drawn at full brightness, so a dyed phantom glows in its own color. Light on the blocks around it needs [LambDynamicLights](https://modrinth.com/mod/lambdynamiclights) on the client, and that light is white: Minecraft light has no color.
- **Brush.** A brush washes the paint and the glow off. The phantom gives a dry shake, and the brush loses one point of durability.
- **Released phantoms.** Only a tamed phantom can be painted. Try a dye or a glow ink sac on a released one, and it bristles, growls, and attacks you for a while.
- **Release.** A painted phantom that is set free shakes the paint off in a spray of that color and returns to its natural look. Its saddle loses the dye too.
- **Guidebook.** With Patchouli, the Tamed Phantoms guidebook gets a *Colors* page.

Only the owner can paint or clean a phantom. The addon is needed on the server and on every client.

## NeoForge

Required: `tamedphantomscolors-neoforge-1.21.1-1.0.0.jar`, Tamed Phantoms 1.0.5 or newer, and Kotlin for Forge 5.8.0 (`kotlinforforge-neoforge`). Optional: LambDynamicLights 4.8.11+1.21.1 for real light, Patchouli `1.21.1-93-neoforge` for the guidebook page.

## Fabric

Required: `tamedphantomscolors-fabric-1.21.1-1.0.0.jar`, Tamed Phantoms `1.0.5-fabric` or newer, Fabric API `0.116.17+1.21.1`, Fabric Language Kotlin `1.13.2+kotlin.2.1.20`, and Cloth Config `15.0.140` for Tamed Phantoms. Optional: LambDynamicLights 4.8.11+1.21.1 for real light, Patchouli `1.21.1-93-fabric` for the guidebook page.

The source code and every release jar are on [GitHub](https://github.com/Nergan/tamedphantoms-colors-mod). License: MPL-2.0.
