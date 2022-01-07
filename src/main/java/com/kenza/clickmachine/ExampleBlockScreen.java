package com.kenza.clickmachine;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class ExampleBlockScreen extends CottonInventoryScreen<ExampleGuiDescription> {
    public ExampleBlockScreen(ExampleGuiDescription gui, PlayerEntity player, Text title) {
        super(gui, player, title);
    }
}
