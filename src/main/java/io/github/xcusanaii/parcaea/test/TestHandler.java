package io.github.xcusanaii.parcaea.test;

import io.github.xcusanaii.parcaea.Parcaea;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class TestHandler {

    @SubscribeEvent
    public void onTickEvent(ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) return;
        if (Parcaea.keyTest.isPressed()){
            EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
            if (player != null) {
                player.playSound("parcaea:wood2", 1.0f, 1.0f);
            }
        }
    }
}
