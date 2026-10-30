package com.tamedphantomscolors.mod.compat

import com.tamedphantoms.mod.entity.TamedPhantomEntity
import com.tamedphantomscolors.mod.TamedPhantomsColors
import dev.lambdaurora.lambdynlights.api.entity.luminance.EntityLuminance
import dev.lambdaurora.lambdynlights.api.item.ItemLightSourceManager
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.Entity

/** Фантом в светящихся чернилах светит, как лампа. Остальные ручные фантомы не светят. */
object GlowInkLuminance : EntityLuminance {

    /** Уровень лампы и светокамня. Факел даёт 14. */
    const val LIGHT = 15

    private val TYPE: EntityLuminance.Type = EntityLuminance.Type.registerSimple(
        ResourceLocation.fromNamespaceAndPath(TamedPhantomsColors.MOD_ID, "glow_ink"),
        this,
    )

    override fun type(): EntityLuminance.Type = TYPE

    override fun getLuminance(itemLightSourceManager: ItemLightSourceManager, entity: Entity): Int =
        if ((entity as? TamedPhantomEntity)?.luminous == true) LIGHT else 0
}
