package com.kenza.clickmachine;

import com.kenza.clickmachine.blocks.GuiBlock;
import com.kenza.clickmachine.blocks.GuiBlock2;
import com.kenza.clickmachine.blocks.GuiBlockEntity;
import com.kenza.clickmachine.blocks.TestDescription;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static com.kenza.clickmachine.ClickMachine.ID;

public class Test {

    public static GuiBlock GUI_BLOCK;
    public static Block NO_BLOCK_INVENTORY_BLOCK;
    public static BlockItem GUI_BLOCK_ITEM;
    public static BlockEntityType<GuiBlockEntity> GUI_BLOCKENTITY_TYPE;
    public static ScreenHandlerType<TestDescription> GUI_SCREEN_HANDLER_TYPE;
//    public static ScreenHandlerType<ReallySimpleDescription> REALLY_SIMPLE_SCREEN_HANDLER_TYPE;



    public static void test() {

        GUI_BLOCK = new GuiBlock2(FabricBlockSettings.of(Material.TNT).sounds(BlockSoundGroup.GRASS));
        Registry.register(Registry.BLOCK, new Identifier(ID, "auto_clicker"), GUI_BLOCK);
        GUI_BLOCK_ITEM = new BlockItem(GUI_BLOCK, new Item.Settings().group(ItemGroup.MISC));
        Registry.register(Registry.ITEM, new Identifier(ID, "auto_clicker"), GUI_BLOCK_ITEM);
////        NO_BLOCK_INVENTORY_BLOCK = new NoBlockInventoryBlock(AbstractBlock.Settings.copy(Blocks.STONE));
////        Registry.register(Registry.BLOCK, new Identifier(ID, "no_block_inventory"), NO_BLOCK_INVENTORY_BLOCK);
//        Registry.register(Registry.ITEM, new Identifier(ID, "no_block_inventory"), new BlockItem(NO_BLOCK_INVENTORY_BLOCK, new Item.Settings().group(ItemGroup.MISC)));
        GUI_BLOCKENTITY_TYPE = FabricBlockEntityTypeBuilder.create(GuiBlockEntity::new, GUI_BLOCK).build(null);
        Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(ID, "auto_clicker"), GUI_BLOCKENTITY_TYPE);
//
        GUI_SCREEN_HANDLER_TYPE = ScreenHandlerRegistry.registerSimple(new Identifier(ID, "auto_clicker"), (int syncId, PlayerInventory inventory) -> {
            return new TestDescription(GUI_SCREEN_HANDLER_TYPE, syncId, inventory, ScreenHandlerContext.EMPTY);
        });



    }
}
