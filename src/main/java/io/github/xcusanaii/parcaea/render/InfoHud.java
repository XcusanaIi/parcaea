package io.github.xcusanaii.parcaea.render;

import io.github.xcusanaii.parcaea.model.color.ColorGeneral;
import io.github.xcusanaii.parcaea.model.config.CfgGeneral;
import io.github.xcusanaii.parcaea.util.math.Vec2d;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import java.util.ArrayList;
import java.util.List;

public class InfoHud extends GuiIngame {

    public static final InfoDisplay debugInfo = new InfoDisplay(new Vec2d(0.1D, 0.1D), "", CfgGeneral.themeColor, -1, 2.0F, false);

    public static List<InfoDisplay> infoDisplayList = new ArrayList<InfoDisplay>();

    public InfoHud(Minecraft mcIn) {
        super(mcIn);
//        infoDisplayList.add(debugInfo);
    }

    public void draw() {
        for (InfoDisplay infoDisplay : infoDisplayList) {
            drawResizedString(infoDisplay.posPercent, infoDisplay.info, infoDisplay.color, infoDisplay.scale, infoDisplay.centered);
        }
    }

    private void drawResizedString(Vec2d posPercent, String str, int color, float scale, boolean centered) {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        GlStateManager.pushMatrix();
        GlStateManager.scale(scale, scale, 1.0F);
        if (centered) {
            drawCenteredString(getFontRenderer(), str, (int) (scaledResolution.getScaledWidth() * posPercent.x / scale), (int) (scaledResolution.getScaledHeight() * posPercent.y / scale), color);
        }else {
            drawString(getFontRenderer(), str, (int) (scaledResolution.getScaledWidth() * posPercent.x / scale), (int) (scaledResolution.getScaledHeight() * posPercent.y / scale), color);
        }
        GlStateManager.popMatrix();
    }

    public static class InfoDisplay {

        public Vec2d posPercent;
        public String info;
        public int color;
        public int life;
        public float scale;
        public boolean centered;

        public InfoDisplay(Vec2d posPercent, String info, int color, int life, float scale) {
            this(posPercent, info, color, life, scale, true);
        }

        public InfoDisplay(Vec2d posPercent, String info, int color, int life, float scale, boolean centered) {
            this.posPercent = posPercent;
            this.info = info;
            this.color = color;
            this.life = life;
            this.scale = scale;
            this.centered = centered;
        }
    }
}
