package com.kenza.clickmachine;

import com.kenza.clickmachine.blocks.TestDescription;
import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

import static com.kenza.clickmachine.Test.GUI_SCREEN_HANDLER_TYPE;

public class ClickMachineClient implements ClientModInitializer {


    @Override
    public void onInitializeClient() {

        ScreenRegistry.<TestDescription, CottonInventoryScreen<TestDescription>>register(
                GUI_SCREEN_HANDLER_TYPE,
                CottonInventoryScreen::new
        );

    }
}
