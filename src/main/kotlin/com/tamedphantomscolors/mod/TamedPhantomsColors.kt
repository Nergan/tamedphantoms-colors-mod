package com.tamedphantomscolors.mod

import com.tamedphantomscolors.mod.paint.PhantomPaint
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object TamedPhantomsColors {
    const val MOD_ID = "tamedphantomscolors"

    val LOGGER: Logger = LoggerFactory.getLogger(MOD_ID)

    fun init() {
        PhantomPaint.register()
        LOGGER.info("Tamed Phantoms Colors: краска, свечение и кисть подключены.")
    }
}
