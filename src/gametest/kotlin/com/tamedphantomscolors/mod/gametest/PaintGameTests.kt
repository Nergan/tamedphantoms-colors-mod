package com.tamedphantomscolors.mod.gametest

import com.tamedphantoms.mod.entity.ModEntities
import com.tamedphantoms.mod.entity.TamedPhantomEntity
import com.tamedphantoms.mod.event.PhantomInteractionHandler
import com.tamedphantomscolors.mod.TamedPhantomsColors
import net.minecraft.core.BlockPos
import net.minecraft.gametest.framework.GameTest
import net.minecraft.gametest.framework.GameTestHelper
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.level.GameType
import net.minecraft.world.phys.Vec3
import net.neoforged.neoforge.gametest.GameTestHolder
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate

/**
 * Клики идут через обработчик Tamed Phantoms, как от настоящего игрока.
 * Только для `runGameTestServer`: в jar этот пакет не попадает.
 */
@GameTestHolder(TamedPhantomsColors.MOD_ID)
@PrefixGameTestTemplate(false)
object PaintGameTests {

    private const val TEMPLATE = "empty"

    private fun spawnPet(helper: GameTestHelper, owner: Player?): TamedPhantomEntity {
        val pet = helper.spawn(ModEntities.TAMED_PHANTOM.get(), BlockPos(2, 2, 2))
        pet.isNoAi = true
        if (owner != null) pet.tameTo(owner)
        return pet
    }

    private fun click(player: Player, pet: TamedPhantomEntity, stack: ItemStack, hit: Vec3? = null) {
        player.setItemInHand(InteractionHand.MAIN_HAND, stack)
        PhantomInteractionHandler.onEntityInteract(player, pet, InteractionHand.MAIN_HAND, hit)
    }

    private fun saddleTop(pet: TamedPhantomEntity): Vec3 = pet.position().add(0.0, pet.bbHeight * 0.95, 0.0)

    private fun check(helper: GameTestHelper, condition: Boolean, message: String) {
        helper.assertTrue(condition, message)
    }

    @GameTest(template = TEMPLATE)
    @JvmStatic
    fun ownerDyesBodyAndSaddleSeparately(helper: GameTestHelper) {
        val owner = helper.makeMockPlayer(GameType.SURVIVAL)
        val pet = spawnPet(helper, owner)
        pet.equipSaddle(ItemStack(Items.SADDLE), null)
        val red = ItemStack(Items.RED_DYE, 2)
        click(owner, pet, red)
        check(helper, pet.bodyDye == DyeColor.RED.id, "body should be red, got ${pet.bodyDye}")
        check(helper, pet.saddleDye == TamedPhantomEntity.NO_DYE, "saddle should stay natural, got ${pet.saddleDye}")
        check(helper, red.count == 1, "one red dye should be spent, ${red.count} left")
        val blue = ItemStack(Items.BLUE_DYE)
        click(owner, pet, blue, saddleTop(pet))
        check(helper, pet.saddleDye == DyeColor.BLUE.id, "saddle should be blue, got ${pet.saddleDye}")
        check(helper, pet.bodyDye == DyeColor.RED.id, "body should stay red, got ${pet.bodyDye}")
        check(helper, blue.isEmpty, "blue dye should be spent")
        helper.succeed()
    }

    @GameTest(template = TEMPLATE)
    @JvmStatic
    fun sameColorIsNotSpentTwice(helper: GameTestHelper) {
        val owner = helper.makeMockPlayer(GameType.SURVIVAL)
        val pet = spawnPet(helper, owner)
        val green = ItemStack(Items.GREEN_DYE, 3)
        click(owner, pet, green)
        click(owner, pet, green)
        check(helper, pet.bodyDye == DyeColor.GREEN.id, "body should be green, got ${pet.bodyDye}")
        check(helper, green.count == 2, "only one dye should be spent, ${green.count} left")
        helper.succeed()
    }

    @GameTest(template = TEMPLATE)
    @JvmStatic
    fun strangerCannotPaint(helper: GameTestHelper) {
        val owner = helper.makeMockPlayer(GameType.SURVIVAL)
        val stranger = helper.makeMockPlayer(GameType.SURVIVAL)
        val pet = spawnPet(helper, owner)
        pet.equipSaddle(ItemStack(Items.SADDLE), null)
        val dye = ItemStack(Items.PURPLE_DYE, 2)
        click(stranger, pet, dye)
        click(stranger, pet, dye, saddleTop(pet))
        val ink = ItemStack(Items.GLOW_INK_SAC)
        click(stranger, pet, ink)
        check(helper, pet.bodyDye == TamedPhantomEntity.NO_DYE, "a stranger must not dye the body, got ${pet.bodyDye}")
        check(helper, pet.saddleDye == TamedPhantomEntity.NO_DYE, "a stranger must not dye the saddle, got ${pet.saddleDye}")
        check(helper, !pet.luminous, "a stranger must not make it glow")
        check(helper, dye.count == 2 && ink.count == 1, "nothing should be spent")
        check(helper, !pet.isDefending(), "a tamed phantom does not get angry at a stranger's dye")
        click(owner, pet, ItemStack(Items.RED_DYE))
        val brush = ItemStack(Items.BRUSH)
        click(stranger, pet, brush)
        check(helper, pet.bodyDye == DyeColor.RED.id, "a stranger must not brush the paint off, got ${pet.bodyDye}")
        check(helper, brush.damageValue == 0, "the stranger's brush should not wear")
        helper.succeed()
    }

    @GameTest(template = TEMPLATE)
    @JvmStatic
    fun everyPaintStepGrantsItsAdvancement(helper: GameTestHelper) {
        val owner = helper.makeMockServerPlayerInLevel()
        val pet = spawnPet(helper, owner)
        click(owner, pet, ItemStack(Items.RED_DYE))
        click(owner, pet, ItemStack(Items.GLOW_INK_SAC))
        click(owner, pet, ItemStack(Items.BRUSH))
        click(owner, pet, ItemStack(Items.BLUE_DYE))
        pet.release()
        click(owner, pet, ItemStack(Items.YELLOW_DYE))
        val server = helper.level.server
        for (path in listOf("dye_phantom", "glow_phantom", "brush_phantom", "shake_off_paint", "refused_paint")) {
            val id = ResourceLocation.fromNamespaceAndPath(TamedPhantomsColors.MOD_ID, path)
            val holder = server.advancements.get(id)
            check(helper, holder != null, "advancement $id should be loaded")
            check(helper, owner.advancements.getOrStartProgress(holder!!).isDone, "advancement $id should be granted")
        }
        helper.succeed()
    }

    @GameTest(template = TEMPLATE)
    @JvmStatic
    fun glowInkThenBrushCleansEverything(helper: GameTestHelper) {
        val owner = helper.makeMockPlayer(GameType.SURVIVAL)
        val pet = spawnPet(helper, owner)
        pet.equipSaddle(ItemStack(Items.SADDLE), null)
        click(owner, pet, ItemStack(Items.ORANGE_DYE))
        click(owner, pet, ItemStack(Items.CYAN_DYE), saddleTop(pet))
        val ink = ItemStack(Items.GLOW_INK_SAC, 2)
        click(owner, pet, ink)
        check(helper, pet.luminous, "glow ink should make it luminous")
        check(helper, ink.count == 1, "one ink sac should be spent, ${ink.count} left")
        val brush = ItemStack(Items.BRUSH)
        click(owner, pet, brush)
        check(helper, pet.bodyDye == TamedPhantomEntity.NO_DYE, "brush should clean the body, got ${pet.bodyDye}")
        check(helper, pet.saddleDye == TamedPhantomEntity.NO_DYE, "brush should clean the saddle, got ${pet.saddleDye}")
        check(helper, !pet.luminous, "brush should remove the glow")
        check(helper, brush.damageValue == 1, "brush should lose one point, damage ${brush.damageValue}")
        check(helper, pet.shakeTicks > 0, "the phantom should shake after the brush")
        check(helper, pet.isSaddled, "the brush must not take the saddle")
        helper.succeed()
    }

    @GameTest(template = TEMPLATE)
    @JvmStatic
    fun releasedPhantomGetsAngryAtDye(helper: GameTestHelper) {
        val owner = helper.makeMockPlayer(GameType.SURVIVAL)
        val pet = spawnPet(helper, owner)
        pet.release()
        check(helper, !pet.tamed, "the phantom should be released")
        val painter = helper.makeMockPlayer(GameType.SURVIVAL)
        val dye = ItemStack(Items.YELLOW_DYE)
        click(painter, pet, dye)
        check(helper, pet.bodyDye == TamedPhantomEntity.NO_DYE, "a released phantom must not be dyed, got ${pet.bodyDye}")
        check(helper, dye.count == 1, "the dye must not be spent")
        check(helper, pet.isDefending(), "a released phantom should defend itself after a dye")
        helper.succeed()
    }

    @GameTest(template = TEMPLATE)
    @JvmStatic
    fun releaseShakesThePaintOff(helper: GameTestHelper) {
        val owner = helper.makeMockPlayer(GameType.SURVIVAL)
        val pet = spawnPet(helper, owner)
        pet.equipSaddle(ItemStack(Items.SADDLE), null)
        click(owner, pet, ItemStack(Items.MAGENTA_DYE))
        click(owner, pet, ItemStack(Items.LIME_DYE), saddleTop(pet))
        click(owner, pet, ItemStack(Items.GLOW_INK_SAC))
        check(helper, pet.bodyDye == DyeColor.MAGENTA.id && pet.saddleDye == DyeColor.LIME.id && pet.luminous, "setup failed")
        pet.release()
        check(helper, pet.bodyDye == TamedPhantomEntity.NO_DYE, "release should wash the body, got ${pet.bodyDye}")
        check(helper, pet.saddleDye == TamedPhantomEntity.NO_DYE, "release should wash the saddle, got ${pet.saddleDye}")
        check(helper, !pet.luminous, "release should remove the glow")
        check(helper, pet.shakeTicks > 0, "the phantom should shake the paint off")
        check(helper, !pet.isSaddled, "the saddle drops on release")
        helper.succeed()
    }

    @GameTest(template = TEMPLATE)
    @JvmStatic
    fun shearedSaddleLosesItsDye(helper: GameTestHelper) {
        val owner = helper.makeMockPlayer(GameType.SURVIVAL)
        val pet = spawnPet(helper, owner)
        pet.equipSaddle(ItemStack(Items.SADDLE), null)
        click(owner, pet, ItemStack(Items.BLACK_DYE), saddleTop(pet))
        check(helper, pet.saddleDye == DyeColor.BLACK.id, "saddle should be black, got ${pet.saddleDye}")
        click(owner, pet, ItemStack(Items.SHEARS))
        check(helper, !pet.isSaddled, "shears should take the saddle off")
        pet.equipSaddle(ItemStack(Items.SADDLE), null)
        check(helper, pet.saddleDye == TamedPhantomEntity.NO_DYE, "a new saddle starts natural, got ${pet.saddleDye}")
        helper.succeed()
    }
}
