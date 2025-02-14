package com.kenza.clickmachine.utils

import com.kenza.clickmachine.ClickMachine
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry
import net.minecraft.block.Block
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.client.util.SpriteIdentifier
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.nbt.NbtCompound
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.screen.PlayerScreenHandler
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.text.MutableText
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import net.minecraft.util.math.ChunkPos
import net.minecraft.util.math.Direction


val EMPTY_INT_ARRAY = intArrayOf()

fun identifier(id: String) = Identifier(ClickMachine.ID, id)

fun blockSpriteId(id: String) = SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, identifier(id))

fun Identifier.block(block: Block): Identifier {
    Registry.register(Registries.BLOCK, this, block)
    return this
}



fun Identifier.item(item: Item): Identifier {
    Registry.register(Registries.ITEM, this, item)
    return this
}

fun Identifier.blockEntityType(entityType: BlockEntityType<*>): Identifier {
    Registry.register(Registries.BLOCK_ENTITY_TYPE, this, entityType)
    return this
}

//fun itemSettings(): FabricItemSettings = FabricItemSettings().group(IndustrialRevolution.MOD_GROUP)

fun <T : ScreenHandler> Identifier.registerScreenHandler(
    f: (Int, PlayerInventory, ScreenHandlerContext) -> T
): ExtendedScreenHandlerType<T> =
    ScreenHandlerRegistry.registerExtended(this) { syncId, inv, buf ->
        f(syncId, inv, ScreenHandlerContext.create(inv.player.world, buf.readBlockPos()))
    } as ExtendedScreenHandlerType<T>


fun ChunkPos.toNbt() = NbtCompound().also {
    it.putInt("x", x)
    it.putInt("z", z)
}

fun getChunkPos(nbt: NbtCompound) = ChunkPos(nbt.getInt("x"), nbt.getInt("z"))


fun pack(dirs: Collection<Direction>): Byte {
    var i = 0
    dirs.forEach { dir -> i = i or (1 shl dir.id) }
    return i.toByte()
}

fun unpack(byte: Byte): List<Direction> {
    val i = byte.toInt()
    return DIRECTIONS.filter { dir -> i and (1 shl dir.id) != 0 }
}

private val DIRECTIONS = Direction.values()

//val Fluid?.rawId: Int
//    get() = Registry.FLUID.getRawId(this)

fun literal(text: String): MutableText = Text.literal(text)