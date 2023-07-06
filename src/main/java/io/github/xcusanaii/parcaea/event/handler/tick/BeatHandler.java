package io.github.xcusanaii.parcaea.event.handler.tick;

import io.github.xcusanaii.parcaea.model.config.CfgGeneral;
import io.github.xcusanaii.parcaea.util.sound.SoundUtil;
import net.minecraft.client.Minecraft;

public class BeatHandler {

    public static boolean onBeat = false;

    public static int tickI = -1;

    private static final Minecraft mc = Minecraft.getMinecraft();

    public void onClientTickPre() {
        if (!CfgGeneral.enableChart || !CfgGeneral.enableBeat) {
            onBeat = false;
            return;
        }
        tickI++;
        if (tickI > CfgGeneral.beatInterval - 2) {
            tickI = -1;
            if (CfgGeneral.enableSoundEffect) {
                SoundUtil.playSound(SoundUtil.getSoundName("beat"), (float) CfgGeneral.soundEffectVolume);
            }
        }
        onBeat = tickI <= 0;
    }

}
