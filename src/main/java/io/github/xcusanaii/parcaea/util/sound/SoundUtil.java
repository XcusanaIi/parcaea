package io.github.xcusanaii.parcaea.util.sound;

import io.github.xcusanaii.parcaea.Parcaea;
import io.github.xcusanaii.parcaea.model.config.CfgGeneral;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;

public class SoundUtil {

    private static final Minecraft mc = Minecraft.getMinecraft();

    public static void playSoundEffect(int keySlot) {
        if (!CfgGeneral.enableSoundEffect) return;
        playSound(getSoundName("s" + (keySlot + 1)), (float) CfgGeneral.soundEffectVolume);
    }

    public static PMusic playSound(String name, float volume) {
        EntityPlayerSP player = mc.thePlayer;
        if (player == null) return null;
        PlaySoundAtEntityEvent event = ForgeEventFactory.onPlaySoundAtEntity(player, name, volume, 1.0F);
        if (event.isCanceled() || event.name == null) return null;
        name = event.name;
        volume = event.newVolume;
        PMusic pMusic = new PMusic(new ResourceLocation(name), volume);
        mc.getSoundHandler().playSound(pMusic);
        return pMusic;
    }

    public static void stopSound(PMusic pMusic) {
        if (pMusic != null) {
            mc.getSoundHandler().stopSound(pMusic);
        }
    }

    public static String getSoundName(String id) {
        return Parcaea.MODID + ":" + id;
    }
}
