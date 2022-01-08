package com.kenza.clickmachine

import com.kenza.clickmachine.blocks.AutoClickerBlock
import net.minecraft.item.BlockItem
import net.minecraft.block.entity.BlockEntityType
import com.kenza.clickmachine.blocks.AutoClickerBlockEntity
import net.minecraft.screen.ScreenHandlerType
import com.kenza.clickmachine.blocks.AutoClickerGuiDescription
import com.kenza.clickmachine.GuiMod
import net.minecraft.block.Material
import net.minecraft.sound.BlockSoundGroup
import com.kenza.clickmachine.ClickMachine
import net.minecraft.item.ItemGroup
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry
import net.fabricmc.fabric.impl.screenhandler.ExtendedScreenHandlerType
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.Item
import net.minecraft.network.PacketByteBuf
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.registry.Registry

object GuiMod {

    var GUI_BLOCK: AutoClickerBlock? = null
    var NO_BLOCK_INVENTORY_BLOCK: Block? = null
    var GUI_BLOCK_ITEM: BlockItem? = null
    var GUI_BLOCKENTITY_TYPE: BlockEntityType<AutoClickerBlockEntity>? = null

//    @JvmField
//    var GUI_SCREEN_HANDLER_TYPE: ScreenHandlerType<AutoClickerGuiDescription>? = null

    lateinit var GUI_SCREEN_HANDLER_TYPE: ScreenHandlerType<AutoClickerGuiDescription>



    fun test() {
        GUI_BLOCK = AutoClickerBlock(  FabricBlockSettings.of(Material.TNT).sounds(BlockSoundGroup.GRASS) , ::AutoClickerGuiDescription)
        Registry.register(Registry.BLOCK, Identifier(ClickMachine.ID, "auto_clicker"), GUI_BLOCK)
        GUI_BLOCK_ITEM = BlockItem(GUI_BLOCK, Item.Settings().group(ItemGroup.MISC))
        Registry.register(Registry.ITEM, Identifier(ClickMachine.ID, "auto_clicker"), GUI_BLOCK_ITEM)
        GUI_BLOCKENTITY_TYPE = FabricBlockEntityTypeBuilder.create<AutoClickerBlockEntity>(
            FabricBlockEntityTypeBuilder.Factory<AutoClickerBlockEntity> { pos: BlockPos?, state: BlockState? ->
                AutoClickerBlockEntity(
                    pos,
                    state
                )
            }, GUI_BLOCK
        ).build(null)
        Registry.register(Registry.BLOCK_ENTITY_TYPE, Identifier(ClickMachine.ID, "auto_clicker"), GUI_BLOCKENTITY_TYPE)
        //
//        GUI_SCREEN_HANDLER_TYPE = ScreenHandlerRegistry.registerSimple<AutoClickerGuiDescription>(
//            Identifier(
//                ClickMachine.ID, "auto_clicker"
//            ),
//            ScreenHandlerRegistry.SimpleClientHandlerFactory<AutoClickerGuiDescription> { syncId: Int, inventory: PlayerInventory ->
//                AutoClickerGuiDescription(
//                    GUI_SCREEN_HANDLER_TYPE, syncId, inventory, ScreenHandlerContext.EMPTY
//                )
//            })

        GUI_SCREEN_HANDLER_TYPE = ScreenHandlerRegistry.registerExtended<AutoClickerGuiDescription>(
            Identifier(
                ClickMachine.ID, "auto_clicker"
            ),
            ScreenHandlerRegistry.ExtendedClientHandlerFactory<AutoClickerGuiDescription> { syncId: Int, inv: PlayerInventory, buf : PacketByteBuf ->
                AutoClickerGuiDescription(
                    syncId, inv,  ScreenHandlerContext.create(inv.player.world, buf.readBlockPos())
                )
            })




//        GUI_SCREEN_HANDLER_TYPE = ScreenHandlerRegistry.registerExtended( Identifier(ClickMachine.ID, "auto_clicker")) { syncId, inv, buf ->
//            AutoClickerGuiDescription(
//                GUI_SCREEN_HANDLER_TYPE, syncId, inv, ScreenHandlerContext.create(inv.player.world, buf.readBlockPos())
//            )
//        }

    }
}