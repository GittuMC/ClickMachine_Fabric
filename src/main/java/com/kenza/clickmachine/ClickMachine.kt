package com.kenza.clickmachine

import com.kenza.clickmachine.common.UpdateAutoClickerPacket
import com.kenza.clickmachine.utils.identifier
import com.kenza.clickmachine.utils.openLastWorldOnInit
import dev.cafeteria.fakeplayerapi.server.FakePlayerBuilder
import dev.cafeteria.fakeplayerapi.server.FakeServerPlayer
import net.fabricmc.api.ModInitializer
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvent
import org.apache.logging.log4j.LogManager

class ClickMachine : ModInitializer {




    override fun onInitialize() {
        LOGGER.info("Hello Fabric world!")

//        SCREEN_HANDLER_TYPE = ScreenHandlerRegistry.registerSimple(MOD_ID) { syncId: Int, inventory: PlayerInventory? ->
//            ExampleGuiDescription(
//                syncId,
//                inventory,
//                ScreenHandlerContext.EMPTY
//            )
//        }

        openLastWorldOnInit()

        GuiMod.test()

        UpdateAutoClickerPacket.register()


    }

    companion object {

//        lateinit var SCREEN_HANDLER_TYPE: ScreenHandlerType<ExampleGuiDescription>

        @JvmField
        val ID = "clickmachine"

        @JvmField
        val MOD_ID = identifier(ID)


        // This logger is used to write text to the console and the log file.
        // It is considered best practice to use your mod id as the logger's name.
        // That way, it's clear which mod wrote info, warnings, and errors.
        @JvmField
        val LOGGER = LogManager.getLogger("click_machine")

        val FAKE_PLAYER_BUILDER = FakePlayerBuilder(identifier("default_fake_player")) { builder, server, world, profile ->
            object : FakeServerPlayer(builder, server, world, profile) {
                override fun isCreative(): Boolean = false
                override fun isSpectator(): Boolean = false
                override fun playSound(sound: SoundEvent?, volume: Float, pitch: Float) {}
                override fun playSound(event: SoundEvent?, category: SoundCategory?, volume: Float, pitch: Float) {}
            }
        }
    }
}

fun Any.debug(msg: String) {
    ClickMachine.LOGGER.debug(msg)
}