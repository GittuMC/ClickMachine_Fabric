package com.kenza.clickmachine;

import com.kenza.clickmachine.blocks.AutoClickerBlock;
import com.kenza.clickmachine.blocks.AutoClickerBlockEntity;
import com.kenza.clickmachine.blocks.AutoClickerGuiDescription;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.Block;
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

public class GuiMod {

    public static AutoClickerBlock GUI_BLOCK;
    public static Block NO_BLOCK_INVENTORY_BLOCK;
    public static BlockItem GUI_BLOCK_ITEM;
    public static BlockEntityType<AutoClickerBlockEntity> GUI_BLOCKENTITY_TYPE;
    public static ScreenHandlerType<AutoClickerGuiDescription> GUI_SCREEN_HANDLER_TYPE;



    public static void test() {

        GUI_BLOCK = new AutoClickerBlock(FabricBlockSettings.of(Material.TNT).sounds(BlockSoundGroup.GRASS));
        Registry.register(Registry.BLOCK, new Identifier(ID, "auto_clicker"), GUI_BLOCK);
        GUI_BLOCK_ITEM = new BlockItem(GUI_BLOCK, new Item.Settings().group(ItemGroup.MISC));
        Registry.register(Registry.ITEM, new Identifier(ID, "auto_clicker"), GUI_BLOCK_ITEM);
        GUI_BLOCKENTITY_TYPE = FabricBlockEntityTypeBuilder.create(AutoClickerBlockEntity::new, GUI_BLOCK).build(null);

        Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(ID, "auto_clicker"), GUI_BLOCKENTITY_TYPE);
//
        GUI_SCREEN_HANDLER_TYPE = ScreenHandlerRegistry.registerSimple(new Identifier(ID, "auto_clicker"), (int syncId, PlayerInventory inventory) -> {
            return new AutoClickerGuiDescription(GUI_SCREEN_HANDLER_TYPE, syncId, inventory, ScreenHandlerContext.EMPTY);
        });



    }
}
