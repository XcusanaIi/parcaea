package io.github.xcusanaii.parcaea.render;

import io.github.xcusanaii.parcaea.util.math.Vec2d;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import java.util.ArrayList;
import java.util.List;

public class InfoHud extends GuiIngame {

    public static List<InfoDisplay> infoDisplayList = new ArrayList<InfoDisplay>();

    public InfoHud(Minecraft mcIn) {
        super(mcIn);
    }

    public void draw() {
        for (InfoDisplay infoDisplay : infoDisplayList) {
            drawResizedCenteredString(infoDisplay.posPercent, infoDisplay.info, infoDisplay.color, infoDisplay.scale);
        }
    }

    private void drawResizedCenteredString(Vec2d posPercent, String str, int color, float scale) {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        GlStateManager.pushMatrix();
        GlStateManager.scale(scale, scale, scale);
        drawCenteredString(getFontRenderer(), str, (int) (scaledResolution.getScaledWidth() * posPercent.x / scale), (int) (scaledResolution.getScaledHeight() * posPercent.y / scale), color);
        GlStateManager.popMatrix();
    }

    public static class InfoDisplay {

        public final Vec2d posPercent;
        public final String info;
        public final int color;
        public int life;
        public final float scale;

        public InfoDisplay(Vec2d posPercent, String info, int color, int life, float scale) {
            this.posPercent = posPercent;
            this.info = info;
            this.color = color;
            this.life = life;
            this.scale = scale;
        }
    }
}
