package com.kenza.clickmachine.blocks

import blue.endless.jankson.annotation.Nullable
import com.kenza.clickmachine.Test
import net.minecraft.util.math.BlockPos
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.screen.NamedScreenHandlerFactory
import net.minecraft.util.collection.DefaultedList
import net.minecraft.item.ItemStack
import com.kenza.clickmachine.blocks.AutoClickerBlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.text.LiteralText
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.ScreenHandler
import com.kenza.clickmachine.blocks.AutoClickerGuiDescription
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.nbt.NbtCompound
import net.minecraft.inventory.Inventories
import net.minecraft.text.Text
import net.minecraft.util.math.Direction
import java.util.logging.Logger

class AutoClickerBlockEntity(pos: BlockPos?, state: BlockState?) :
    ImplementedInventory, BlockEntity(Test.GUI_BLOCKENTITY_TYPE, pos, state),
    NamedScreenHandlerFactory {


    private var items = DefaultedList.ofSize(INVENTORY_SIZE, ItemStack.EMPTY)

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
            Test.GUI_SCREEN_HANDLER_TYPE,
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
        Logger.getLogger("").info("")
    }

    companion object {
        const val INVENTORY_SIZE = 1
    }
}