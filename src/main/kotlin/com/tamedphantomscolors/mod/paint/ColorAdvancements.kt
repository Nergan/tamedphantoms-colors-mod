package com.tamedphantomscolors.mod.paint

import com.tamedphantomscolors.mod.TamedPhantomsColors
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.player.Player

/** Достижения аддона: критерий `minecraft:impossible`, выдаются кодом, как у Tamed Phantoms. */
object ColorAdvancements {

    enum class Kind(val path: String, val criterion: String) {
        DYE("dye_phantom", "dyed"),
        GLOW("glow_phantom", "glowed"),
        BRUSH("brush_phantom", "brushed"),
        SHAKE_OFF("shake_off_paint", "shook_off"),
        REFUSED("refused_paint", "refused"),
    }

    fun grant(player: Player?, kind: Kind) {
        val target = player as? ServerPlayer ?: return
        val server = target.level().server ?: return
        val id = ResourceLocation.fromNamespaceAndPath(TamedPhantomsColors.MOD_ID, kind.path)
        val holder = server.advancements.get(id)
        if (holder == null) {
            TamedPhantomsColors.LOGGER.warn("Достижение {} не загружено.", id)
            return
        }
        target.advancements.award(holder, kind.criterion)
    }
}
