package com.tamedphantomscolors.mod.paint

/** Что делает клик инструментом по фантому. Без Minecraft, чтобы проверять тестами. */
object PaintRules {

    /** Как у [com.tamedphantoms.mod.entity.TamedPhantomEntity.NO_DYE]: краски нет. */
    const val NO_DYE = -1

    /** Цвет брызг светящихся чернил, если фантом больше ничем не окрашен. */
    const val GLOW_RGB = 0x5CE6CC

    enum class Tool { DYE, GLOW_INK, BRUSH }

    enum class Action { PASS, PAINT_BODY, PAINT_SADDLE, GLOW, CLEAN, PROVOKE }

    data class Look(val bodyDye: Int, val saddleDye: Int, val luminous: Boolean) {
        val painted: Boolean get() = bodyDye != NO_DYE || saddleDye != NO_DYE || luminous
    }

    /**
     * @param dye id красителя для [Tool.DYE], иначе [NO_DYE]
     * @param onSaddle клик пришёлся в надетое седло
     */
    fun decide(tool: Tool, tamed: Boolean, owner: Boolean, onSaddle: Boolean, dye: Int, look: Look): Action {
        if (!tamed) return if (tool == Tool.BRUSH) Action.PASS else Action.PROVOKE
        if (!owner) return Action.PASS
        return when (tool) {
            Tool.DYE -> when {
                dye == NO_DYE -> Action.PASS
                onSaddle -> if (look.saddleDye == dye) Action.PASS else Action.PAINT_SADDLE
                else -> if (look.bodyDye == dye) Action.PASS else Action.PAINT_BODY
            }
            Tool.GLOW_INK -> if (look.luminous) Action.PASS else Action.GLOW
            Tool.BRUSH -> if (look.painted) Action.CLEAN else Action.PASS
        }
    }

    /**
     * Цвет краски, которую фантом стряхивает при освобождении, или null, если стряхивать нечего.
     * Тело важнее седла, седло важнее свечения.
     */
    fun releaseSpray(bodyRgb: Int?, saddleRgb: Int?, luminous: Boolean): Int? =
        bodyRgb ?: saddleRgb ?: if (luminous) GLOW_RGB else null
}
