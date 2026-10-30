package com.tamedphantomscolors.mod.compat

import com.tamedphantoms.mod.entity.ModEntities
import dev.lambdaurora.lambdynlights.api.DynamicLightsContext
import dev.lambdaurora.lambdynlights.api.DynamicLightsInitializer
import dev.lambdaurora.lambdynlights.api.entity.EntityLightSourceManager
import dev.lambdaurora.lambdynlights.api.item.ItemLightSourceManager

/**
 * Точка входа LambDynamicLights. Класс грузится только на клиенте, где этот мод стоит.
 * Свет движка Minecraft одноцветный, поэтому цвет краски на нём не виден.
 */
class ColorsDynamicLights : DynamicLightsInitializer {

    override fun onInitializeDynamicLights(context: DynamicLightsContext) {
        context.entityLightSourceManager().onRegisterEvent().register(
            EntityLightSourceManager.OnRegister { registration ->
                registration.register(ModEntities.TAMED_PHANTOM.get(), GlowInkLuminance)
            },
        )
    }

    @Deprecated("Старая точка входа LambDynamicLights. Новая получает весь контекст.")
    override fun onInitializeDynamicLights(itemLightSourceManager: ItemLightSourceManager) = Unit
}
