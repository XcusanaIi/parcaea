package io.github.xcusanaii.parcaea.event;

import io.github.xcusanaii.parcaea.model.config.CfgGeneral;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class SoundHandler {

    public static void playSoundEffect(int keySlot) {
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if (!CfgGeneral.enableSoundEffect || player == null || player.worldObj == null) return;
        switch (keySlot) {
            case 0:
                player.playSound("parcaea:s1", 1.0f, 1.0f);
                break;
            case 1:
                player.playSound("parcaea:s2", 1.0f, 1.0f);
                break;
            case 2:
                player.playSound("parcaea:s3", 1.0f, 1.0f);
                break;
            case 3:
                player.playSound("parcaea:s4", 1.0f, 1.0f);
                break;
            case 4:
                player.playSound("parcaea:s5", 1.0f, 1.0f);
                break;
            case 5:
                player.playSound("parcaea:s6", 1.0f, 1.0f);
                break;
            case 6:
                player.playSound("parcaea:s7", 1.0f, 1.0f);
                break;
        }
    }
}
