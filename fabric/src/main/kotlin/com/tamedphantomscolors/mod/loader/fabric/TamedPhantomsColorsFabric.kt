package com.tamedphantomscolors.mod.loader.fabric

import com.tamedphantomscolors.mod.TamedPhantomsColors
import net.fabricmc.api.ModInitializer

class TamedPhantomsColorsFabric : ModInitializer {
    override fun onInitialize() {
        TamedPhantomsColors.init()
    }
}
