package com.tamedphantomscolors.mod.loader.neoforge

import com.tamedphantomscolors.mod.TamedPhantomsColors
import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.ModContainer
import net.neoforged.fml.common.Mod

@Mod(TamedPhantomsColors.MOD_ID)
class TamedPhantomsColorsNeoForge(bus: IEventBus, container: ModContainer) {
    init {
        TamedPhantomsColors.init()
    }
}
