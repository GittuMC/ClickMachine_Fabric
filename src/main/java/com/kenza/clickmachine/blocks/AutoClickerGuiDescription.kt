package com.kenza.clickmachine.blocks

import com.kenza.clickmachine.GuiMod.GUI_SCREEN_HANDLER_TYPE
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.ScreenHandlerContext
import io.github.cottonmc.cotton.gui.SyncedGuiDescription
import io.github.cottonmc.cotton.gui.widget.WGridPanel
import io.github.cottonmc.cotton.gui.widget.WItemSlot
import io.github.cottonmc.cotton.gui.widget.WButton
import net.minecraft.text.LiteralText
import net.minecraft.util.math.BlockPos

class AutoClickerGuiDescription(
    syncId: Int,
    playerInventory: PlayerInventory,
    context: ScreenHandlerContext
) : SyncedGuiDescription(
    GUI_SCREEN_HANDLER_TYPE,
    syncId,
    playerInventory,
    getBlockInventory(context, AutoClickerBlockEntity.INVENTORY_SIZE),
    null
) {

    init {
        val root = getRootPanel() as WGridPanel

        val slot = WItemSlot.of(blockInventory, 0, 1, 1)
        root.add(slot, root.insets.right / 2 + 1, 1)


//        val buttonB = WButton(LiteralText("Show Warnings"))


//        buttonB.onClick = Runnable { slot.icon = TextureIcon(Identifier("libgui-test", "saddle.png")) }
//        root.add(buttonB, 5, 3, 4, 1)

        val t1 = blockInventory.getStack(0)

        val x1 = WButton(LiteralText("Button C"))

        x1.setOnClick {
            val t3 = blockInventory.getStack(0)
            x1.label = LiteralText("Button D1")

            AutoClickerBlockEntity.sendValueUpdatePacket(true, context)
        }

        root.add(x1, root.insets.right / 2 - 1, 3, 5, 1)

//        root.add(WTextField(LiteralText("Type something...")).setMaxLength(64), 0, 7, 5, 1)
//        root.add(WLabel(LiteralText("Large slot:")), 0, 9)
//        root.add(WItemSlot.outputOf(blockInventory, 0), 4, 8)
//        root.add(WItemSlot.of(blockInventory, 7).setIcon(TextureIcon(Identifier("libgui-test", "saddle.png"))), 7, 9)


        root.add(createPlayerInventoryPanel(), 0, 5)


//        println(root.toString())

        getRootPanel().validate(this)

//        ScreenNetworking.of(this, NetworkSide.SERVER)
//            .receive(TEST_MESSAGE) { buf: PacketByteBuf? -> println("Received on the server!") }
//        try {
//            slot.onHidden()
//            slot.onShown()
//        } catch (t: Throwable) {
//            throw AssertionError("ValidatedSlot.setVisible crashed", t)
//        }
    }
}