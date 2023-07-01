package io.github.xcusanaii.parcaea.event.handler.tick;

import net.minecraft.client.Minecraft;

public class LandingBlockHandler {

    private static final Minecraft mc = Minecraft.getMinecraft();

    public void onClientTickPost() {
        int guiScale = mc.gameSettings.guiScale;
    }

    public void onClientTickPre() {

    }

}
