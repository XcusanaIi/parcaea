package io.github.xcusanaii.parcaea.render;

import io.github.xcusanaii.parcaea.event.NoteHandler;
import io.github.xcusanaii.parcaea.model.Chart;
import io.github.xcusanaii.parcaea.model.config.CfgGeneral;
import io.github.xcusanaii.parcaea.util.math.Vec2i;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public abstract class AbsHud extends GuiIngame {

    private static final Minecraft mc = Minecraft.getMinecraft();
    public static Vec2i screenCenter = new Vec2i();

    public AbsHud() {
        super(mc);
    }

    public void draw(float partialTicks){
        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
        screenCenter.x = resolution.getScaledWidth() / 2 + CfgGeneral.hudOffsetX;
        screenCenter.y = resolution.getScaledHeight() / 2 + CfgGeneral.hudOffsetY;
        calculateFramePos();
        if (!NoteHandler.isPlaying) partialTicks = 0.0F;
        if (CfgGeneral.enable45S) {
            draw45S();
        }else {
            drawFrame();
            if (Chart.selectedChart != null) {
                drawKeyNote(partialTicks);
                if (CfgGeneral.enableSnake && !Chart.selectedChart.isNoTurn) {
                    drawMouseNote(partialTicks);
                }
            }
        }
    }

    public abstract void draw45S();
    public abstract void calculateFramePos();
    public abstract void drawFrame();
    public abstract void drawKeyNote(float partialTicks);
    public abstract void drawMouseNote(float partialTicks);
    public abstract void setNoteHandler();

    public void drawRectWithBorder(Vec2i center, int halfX, int halfY, int rectColor, int borderSize, int borderColor) {
        halfX = abs(halfX);
        halfY = abs(halfY);
        borderSize = abs(borderSize);
        drawRect(center.x - halfX, center.y - halfY, center.x + halfX, center.y + halfY, rectColor);
        drawRect(center.x - halfX, center.y - halfY - borderSize, center.x + halfX, center.y - halfY, borderColor);
        drawRect(center.x - halfX, center.y + halfY, center.x + halfX, center.y + halfY + borderSize, borderColor);
        drawRect(center.x - halfX - borderSize, center.y - halfY, center.x - halfX, center.y + halfY, borderColor);
        drawRect(center.x + halfX, center.y - halfY, center.x + halfX + borderSize, center.y + halfY, borderColor);
    }

    public void drawStyleNoteWithBorder(Vec2i center, int halfX, int halfY, int noteColor, int borderSize, int borderColor) {
        halfX = abs(halfX);
        halfY = abs(halfY);
        borderSize = abs(borderSize);
        int smoothOffsetX = halfX > halfY ? 1 : 0;
        int smoothOffsetY = halfY > halfX ? 1 : 0;
        drawOrzmicStyleHexagon(center, halfX + borderSize + smoothOffsetX, halfY + borderSize + smoothOffsetY, borderColor);
        drawOrzmicStyleHexagon(center, halfX, halfY, noteColor);
    }

    private void drawOrzmicStyleHexagon(Vec2i center, int halfX, int halfY, int color) {
        float r = (float)(color >> 16 & 255) / 255.0f;
        float g = (float)(color >> 8 & 255) / 255.0f;
        float b = (float)(color & 255) / 255.0f;
        float a = (float)(color >> 24 & 255) / 255.0f;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer buffer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(r, g, b, a);
        buffer.begin(GL11.GL_POLYGON, DefaultVertexFormats.POSITION);
        if (halfX > halfY) {
            buffer.pos(center.x - halfX, center.y, 0.0d).endVertex();
            buffer.pos(center.x - halfX + halfY, center.y + halfY, 0.0d).endVertex();
            buffer.pos(center.x + halfX - halfY, center.y + halfY, 0.0d).endVertex();
            buffer.pos(center.x + halfX, center.y, 0.0d).endVertex();
            buffer.pos(center.x + halfX - halfY, center.y - halfY, 0.0d).endVertex();
            buffer.pos(center.x - halfX + halfY, center.y - halfY, 0.0d).endVertex();
        }else {
            buffer.pos(center.x, center.y + halfY, 0.0d).endVertex();
            buffer.pos(center.x + halfX, center.y + halfY - halfX, 0.0d).endVertex();
            buffer.pos(center.x + halfX, center.y - halfY + halfX, 0.0d).endVertex();
            buffer.pos(center.x, center.y - halfY, 0.0d).endVertex();
            buffer.pos(center.x - halfX, center.y - halfY + halfX, 0.0d).endVertex();
            buffer.pos(center.x - halfX, center.y + halfY - halfX, 0.0d).endVertex();
        }
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    private int abs(int a) {
        return a < 0 ? -a : a;
    }
}
