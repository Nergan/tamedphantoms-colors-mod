package com.tamedphantomscolors.mod.paint

import com.tamedphantomscolors.mod.paint.PaintRules.Action
import com.tamedphantomscolors.mod.paint.PaintRules.Look
import com.tamedphantomscolors.mod.paint.PaintRules.NO_DYE
import com.tamedphantomscolors.mod.paint.PaintRules.Tool
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class PaintRulesTest {

    private val natural = Look(NO_DYE, NO_DYE, false)
    private val red = 14
    private val blue = 11

    private fun decide(
        tool: Tool,
        tamed: Boolean = true,
        owner: Boolean = true,
        onSaddle: Boolean = false,
        dye: Int = NO_DYE,
        look: Look = natural,
    ) = PaintRules.decide(tool, tamed, owner, onSaddle, dye, look)

    @Test
    fun `owner paints the body and the saddle separately`() {
        assertEquals(Action.PAINT_BODY, decide(Tool.DYE, dye = red))
        assertEquals(Action.PAINT_SADDLE, decide(Tool.DYE, dye = red, onSaddle = true))
        val redBody = Look(red, NO_DYE, false)
        assertEquals(Action.PAINT_SADDLE, decide(Tool.DYE, dye = red, onSaddle = true, look = redBody))
        assertEquals(Action.PAINT_BODY, decide(Tool.DYE, dye = blue, look = redBody))
    }

    @Test
    fun `the same color again is not spent`() {
        assertEquals(Action.PASS, decide(Tool.DYE, dye = red, look = Look(red, NO_DYE, false)))
        assertEquals(Action.PASS, decide(Tool.DYE, dye = blue, onSaddle = true, look = Look(NO_DYE, blue, false)))
        assertEquals(Action.PASS, decide(Tool.GLOW_INK, look = Look(NO_DYE, NO_DYE, true)))
    }

    @Test
    fun `only the owner of a tamed phantom paints it`() {
        assertEquals(Action.PASS, decide(Tool.DYE, owner = false, dye = red))
        assertEquals(Action.PASS, decide(Tool.GLOW_INK, owner = false))
        assertEquals(Action.PASS, decide(Tool.BRUSH, owner = false, look = Look(red, NO_DYE, true)))
    }

    @Test
    fun `a released phantom is angered by dye and ink but not by a brush`() {
        assertEquals(Action.PROVOKE, decide(Tool.DYE, tamed = false, owner = false, dye = red))
        assertEquals(Action.PROVOKE, decide(Tool.GLOW_INK, tamed = false, owner = false))
        assertEquals(Action.PASS, decide(Tool.BRUSH, tamed = false, owner = false))
    }

    @Test
    fun `the brush cleans only a painted or glowing phantom`() {
        assertEquals(Action.PASS, decide(Tool.BRUSH))
        assertEquals(Action.CLEAN, decide(Tool.BRUSH, look = Look(red, NO_DYE, false)))
        assertEquals(Action.CLEAN, decide(Tool.BRUSH, look = Look(NO_DYE, blue, false)))
        assertEquals(Action.CLEAN, decide(Tool.BRUSH, look = Look(NO_DYE, NO_DYE, true)))
    }

    @Test
    fun `release spray takes the body color first`() {
        assertEquals(0xB02E26, PaintRules.releaseSpray(0xB02E26, 0x3C44AA, true))
        assertEquals(0x3C44AA, PaintRules.releaseSpray(null, 0x3C44AA, true))
        assertEquals(PaintRules.GLOW_RGB, PaintRules.releaseSpray(null, null, true))
        assertNull(PaintRules.releaseSpray(null, null, false))
    }
}
