package com.tamedphantomscolors.mod.paint

import com.tamedphantoms.mod.api.TamedPhantomsApi
import com.tamedphantoms.mod.entity.TamedPhantomEntity
import net.minecraft.core.particles.DustParticleOptions
import net.minecraft.core.particles.ParticleOptions
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.stats.Stats
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.BrushItem
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.DyeItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.phys.Vec3

/**
 * Краситель, светящиеся чернила и кисть по фантому. Родитель зовёт клик только на сервере,
 * а с седла — когда седок смотрит на своего фантома с этим предметом в руке.
 */
object PhantomPaint {

    fun register() {
        TamedPhantomsApi.registerInteraction(::onInteract)
        TamedPhantomsApi.registerReleaseListener(::onReleased)
        TamedPhantomsApi.registerRiderTool { stack -> toolOf(stack) != null }
    }

    private fun onInteract(
        player: Player,
        phantom: TamedPhantomEntity,
        hand: InteractionHand,
        stack: ItemStack,
        part: TamedPhantomsApi.Part,
    ): InteractionResult? {
        val tool = toolOf(stack) ?: return null
        val level = phantom.level() as? ServerLevel ?: return null
        val dye = (stack.item as? DyeItem)?.dyeColor
        val action = PaintRules.decide(
            tool = tool,
            tamed = phantom.tamed,
            owner = phantom.isOwnedBy(player),
            onSaddle = part == TamedPhantomsApi.Part.SADDLE,
            dye = dye?.id ?: PaintRules.NO_DYE,
            look = lookOf(phantom),
        )
        when (action) {
            PaintRules.Action.PASS -> return null
            PaintRules.Action.PROVOKE -> {
                phantom.provokeBy(player)
                ColorAdvancements.grant(player, ColorAdvancements.Kind.REFUSED)
            }
            PaintRules.Action.PAINT_BODY -> {
                phantom.bodyDye = dye!!.id
                spend(player, stack)
                puff(level, phantom, dust(dye.textureDiffuseColor), saddle = false)
                sound(level, phantom, SoundEvents.DYE_USE)
                ColorAdvancements.grant(player, ColorAdvancements.Kind.DYE)
            }
            PaintRules.Action.PAINT_SADDLE -> {
                phantom.saddleDye = dye!!.id
                spend(player, stack)
                puff(level, phantom, dust(dye.textureDiffuseColor), saddle = true)
                sound(level, phantom, SoundEvents.DYE_USE)
                ColorAdvancements.grant(player, ColorAdvancements.Kind.DYE)
            }
            PaintRules.Action.GLOW -> {
                phantom.luminous = true
                spend(player, stack)
                puff(level, phantom, ParticleTypes.GLOW, saddle = false)
                sound(level, phantom, SoundEvents.GLOW_INK_SAC_USE)
                ColorAdvancements.grant(player, ColorAdvancements.Kind.GLOW)
            }
            PaintRules.Action.CLEAN -> {
                wipe(phantom)
                phantom.startShake(TamedPhantomEntity.SHAKE_DRY, sound = false)
                sound(level, phantom, SoundEvents.BRUSH_GENERIC)
                player.awardStat(Stats.ITEM_USED.get(stack.item))
                stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand))
                ColorAdvancements.grant(player, ColorAdvancements.Kind.BRUSH)
            }
        }
        return InteractionResult.SUCCESS
    }

    /** Краску и свечение фантом стряхивает цветными брызгами. Седло родитель роняет следом. */
    private fun onReleased(phantom: TamedPhantomEntity, previousOwner: Player?) {
        val spray = PaintRules.releaseSpray(
            bodyRgb = rgbOf(phantom.bodyDye),
            saddleRgb = rgbOf(phantom.saddleDye),
            luminous = phantom.luminous,
        ) ?: return
        wipe(phantom)
        phantom.startShake(spray)
        ColorAdvancements.grant(previousOwner, ColorAdvancements.Kind.SHAKE_OFF)
    }

    private fun toolOf(stack: ItemStack): PaintRules.Tool? = when {
        stack.item is DyeItem -> PaintRules.Tool.DYE
        stack.`is`(Items.GLOW_INK_SAC) -> PaintRules.Tool.GLOW_INK
        stack.item is BrushItem -> PaintRules.Tool.BRUSH
        else -> null
    }

    private fun lookOf(phantom: TamedPhantomEntity) =
        PaintRules.Look(phantom.bodyDye, phantom.saddleDye, phantom.luminous)

    private fun wipe(phantom: TamedPhantomEntity) {
        phantom.bodyDye = TamedPhantomEntity.NO_DYE
        phantom.saddleDye = TamedPhantomEntity.NO_DYE
        phantom.luminous = false
    }

    private fun rgbOf(dye: Int): Int? =
        if (dye == TamedPhantomEntity.NO_DYE) null else DyeColor.byId(dye).textureDiffuseColor and 0xFFFFFF

    private fun spend(player: Player, stack: ItemStack) {
        player.awardStat(Stats.ITEM_USED.get(stack.item))
        if (!player.abilities.instabuild) stack.shrink(1)
    }

    private fun dust(rgb: Int): ParticleOptions = DustParticleOptions(Vec3.fromRGB24(rgb).toVector3f(), 1.2f)

    private fun puff(level: ServerLevel, phantom: TamedPhantomEntity, particle: ParticleOptions, saddle: Boolean) {
        val height = phantom.bbHeight.toDouble()
        val y = if (saddle) phantom.y + height * 0.9 else phantom.y + height * 0.5
        val spread = if (saddle) 0.25 else phantom.bbWidth * 0.35
        level.sendParticles(particle, phantom.x, y, phantom.z, 14, spread, height * 0.2, spread, 0.02)
    }

    private fun sound(level: ServerLevel, phantom: TamedPhantomEntity, event: SoundEvent) {
        level.playSound(null, phantom.x, phantom.y, phantom.z, event, SoundSource.PLAYERS, 1.0f, 1.0f)
    }
}
