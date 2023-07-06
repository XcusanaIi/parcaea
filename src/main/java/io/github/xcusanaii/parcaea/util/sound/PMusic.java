package io.github.xcusanaii.parcaea.util.sound;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.ITickableSound;
import net.minecraft.util.ResourceLocation;

public class PMusic implements ITickableSound {

    private final ResourceLocation musicResource;
    private final float volume;

    private static final Minecraft mc = Minecraft.getMinecraft();

    public PMusic(ResourceLocation musicResource, float volume) {
        this.musicResource = musicResource;
        this.volume = volume;
    }

    @Override
    public boolean isDonePlaying() {
        return false;
    }

    @Override
    public ResourceLocation getSoundLocation() {
        return musicResource;
    }

    @Override
    public boolean canRepeat() {
        return false;
    }

    @Override
    public int getRepeatDelay() {
        return 0;
    }

    @Override
    public float getVolume() {
        return volume;
    }

    @Override
    public float getPitch() {
        return 1.0F;
    }

    @Override
    public float getXPosF() {
        return mc.thePlayer != null ? (float) mc.thePlayer.posX : 0.0F;
    }

    @Override
    public float getYPosF() {
        return mc.thePlayer != null ? (float) mc.thePlayer.posY : 0.0F;
    }

    @Override
    public float getZPosF() {
        return mc.thePlayer != null ? (float) mc.thePlayer.posZ : 0.0F;
    }

    @Override
    public AttenuationType getAttenuationType() {
        return ISound.AttenuationType.LINEAR;
    }

    @Override
    public void update() {

    }
}
