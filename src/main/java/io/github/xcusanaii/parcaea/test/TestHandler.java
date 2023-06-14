package io.github.xcusanaii.parcaea.test;

import io.github.xcusanaii.parcaea.Parcaea;
import io.github.xcusanaii.parcaea.model.KeyBinds;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class TestHandler {

    private static final Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onTickEvent(ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END || mc.thePlayer == null) return;
        if (KeyBinds.keyTest.isPressed()){
            Parcaea.LOGGER.info("Key Pressed");
        }
    }
}
