package com.kenza.clickmachine.blocks

import blue.endless.jankson.annotation.Nullable
import com.kenza.clickmachine.ClickMachine.Companion.FAKE_PLAYER_BUILDER
import com.kenza.clickmachine.GuiMod
import net.minecraft.block.AirBlock
import net.minecraft.util.math.BlockPos
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.client.MinecraftClient
import net.minecraft.screen.NamedScreenHandlerFactory
import net.minecraft.util.collection.DefaultedList
import net.minecraft.item.ItemStack
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.text.LiteralText
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.nbt.NbtCompound
import net.minecraft.inventory.Inventories
import net.minecraft.server.world.ServerWorld
import net.minecraft.text.Text
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Direction.*
import java.util.logging.Logger

class AutoClickerBlockEntity(pos: BlockPos?, state: BlockState?) :
    ImplementedInventory, BlockEntity(GuiMod.GUI_BLOCKENTITY_TYPE, pos, state),
    NamedScreenHandlerFactory {


    private val fakePlayer by lazy { FAKE_PLAYER_BUILDER.create(world!!.server, world as ServerWorld, "AutoClickerBlockEntity") }

    private var items = DefaultedList.ofSize(INVENTORY_SIZE, ItemStack.EMPTY)
    private var tickCounter = 0


    override fun markDirty() {}

    override fun canPlayerUse(player: PlayerEntity): Boolean {
        return pos.isWithinDistance(player.blockPos, 4.5)
    }

    override fun getItems(): DefaultedList<ItemStack> {
        return items
    }

    override fun getDisplayName(): Text {
        return LiteralText("test title")
    }

    @Nullable
    override fun createMenu(syncId: Int, inv: PlayerInventory, player: PlayerEntity): ScreenHandler? {
        return AutoClickerGuiDescription(
            GuiMod.GUI_SCREEN_HANDLER_TYPE,
            syncId,
            inv,
            ScreenHandlerContext.create(world, pos)
        )
    }

    override fun readNbt(nbt: NbtCompound) {
        super.readNbt(nbt)
        Inventories.readNbt(nbt, items)
    }

    public override fun writeNbt(nbt: NbtCompound) {
        Inventories.writeNbt(nbt, items)
    }

    fun tick(facing: Direction?) {

        if (world?.isClient == true) return

        val itemStack = items.firstOrNull()



        fakePlayer.inventory.selectedSlot = 0
        fakePlayer.inventory.addPickBlock(itemStack)

        if (itemStack == null || itemStack.isEmpty) {
            return
        }



        val blockPos = pos.offset(facing)
        val block = world?.getBlockState(blockPos)

        if(block?.isAir == true){
            tickCounter = 0
            return
        }


        tickCounter++

        val interactionManager = MinecraftClient.getInstance().server?.getPlayerInteractionManager(fakePlayer)
//        fakePlayer.setPos(blockPos.x.toDouble() + 1, blockPos.y.toDouble() + 1, blockPos.z.toDouble() + 1)


        val stateToBreak: BlockState = world!!.getBlockState(blockPos)
        val blockHardness: Float = stateToBreak.getHardness(world, blockPos)



        if (!canBreak(stateToBreak, blockHardness)) {
            tickCounter = 0
            world?.setBlockBreakingInfo(fakePlayer.id, blockPos, -1)
            return
        }

        val breakSpeed = (itemStack.item)?.getMiningSpeedMultiplier(itemStack, stateToBreak) ?: 0.1f
        val destroyProgress =  tickCounter * 0.001 *  (breakSpeed / blockHardness  ) * 150

        if (destroyProgress >= 10) {
            interactionManager?.tryBreakBlock(blockPos)
            fakePlayer.inventory.clear()
            tickCounter = 0
            world?.setBlockBreakingInfo(fakePlayer.id, blockPos,-1)
            return
        }

        world?.setBlockBreakingInfo(fakePlayer.id, blockPos, destroyProgress.toInt())
        fakePlayer.inventory.clear()

    }

    private fun canBreak(stateToBreak: BlockState, blockHardness: Float): Boolean {
        return isBreakable(stateToBreak, blockHardness)
    }

    private fun isBreakable(stateToBreak: BlockState, blockHardness: Float): Boolean {
        return !(stateToBreak.material.isLiquid || stateToBreak.block is AirBlock
                || blockHardness == -1f)
    }

    companion object {
        const val INVENTORY_SIZE = 1
    }
}