package com.kenza.clickmachine

import com.kenza.clickmachine.ClickMachine.Companion.GUI_SCREEN_HANDLER_TYPE
import com.kenza.clickmachine.blocks.AutoClickerGuiDescription
import net.fabricmc.api.ClientModInitializer
import com.kenza.clickmachine.common.IRInventoryScreen
import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry
import net.minecraft.client.gui.screen.ingame.HandledScreens
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text

class ClickMachineClient : ClientModInitializer {
    override fun onInitializeClient() {

        ScreenRegistry.register<AutoClickerGuiDescription, CottonInventoryScreen<AutoClickerGuiDescription>>(
            GUI_SCREEN_HANDLER_TYPE
        ) { description: AutoClickerGuiDescription, inventory: PlayerInventory?, title: Text? ->
            CottonInventoryScreen(
                description,
                inventory,
                title
            )
        }

//        HandledScreens.register(ClickMachine.GUI_SCREEN_HANDLER_TYPE) { controller, inv, _ -> IRInventoryScreen(controller, inv.player) }



    }
}