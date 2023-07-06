package io.github.xcusanaii.parcaea.render;

import io.github.xcusanaii.parcaea.event.handler.tick.NoteHandler;
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

        screenCenter.x = mc.displayWidth / 2 + CfgGeneral.hudOffsetX;
        screenCenter.y = mc.displayHeight / 2 + CfgGeneral.hudOffsetY;

        int guiScale = mc.gameSettings.guiScale == 0 ? 3 : mc.gameSettings.guiScale;

        calculateFramePos();
        if (!NoteHandler.isPlaying) partialTicks = 0.0F;

        GlStateManager.pushMatrix();
        GlStateManager.scale(1.0F / guiScale, 1.0F / guiScale, 1.0F);

        drawFrame();
        if (Chart.selectedChart != null) {
            drawKeyNote(partialTicks);
            if (CfgGeneral.enableSnake && !Chart.selectedChart.isNoTurn) {
                drawMouseNote(partialTicks);
            }
        }

        GlStateManager.popMatrix();
    }

    public abstract void calculateFramePos();
    public abstract void drawFrame();
    public abstract void drawKeyNote(float partialTicks);
    public abstract void drawMouseNote(float partialTicks);
    public abstract void setNoteHandler();

    public void drawStyledLine(Vec2i v1, Vec2i v2, int width, int color) {
        int x1 = v1.x;
        int x2 = v2.x;
        int y1 = v1.y;
        int y2 = v2.y;
        double mX = (x1 + x2) / 2.0;
        double mY = (y1 + y2) / 2.0;
        double k = (double) (y2 - y1) / (double) (x2 - x1);
        double theta = Math.atan(k);
        double length = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
        double sinTheta = Math.sin(theta);
        double cosTheta = Math.cos(theta);
        Vec2i a1 = new Vec2i((int)(mX - cosTheta * length / 2.0 - sinTheta * (double) width / 2.0),
                (int)(mY - sinTheta * length / 2.0 + cosTheta * (double) width / 2.0));
        Vec2i a2 = new Vec2i((int)(mX + cosTheta * length / 2.0 - sinTheta * (double) width / 2.0),
                (int)(mY + sinTheta * length / 2.0 + cosTheta * (double) width / 2.0));
        Vec2i a3 = new Vec2i((int)(mX + cosTheta * length / 2.0 + sinTheta * (double) width / 2.0),
                (int)(mY + sinTheta * length / 2.0 - cosTheta * (double) width / 2.0));
        Vec2i a4 = new Vec2i((int)(mX - cosTheta * length / 2.0 + sinTheta * (double) width / 2.0),
                (int)(mY - sinTheta * length / 2.0 - cosTheta * (double) width / 2.0));
        drawPolygon(new Vec2i[]{a1, a2, a3, a4}, color);
    }

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
        drawOrzmicStyleHexagon(center, halfX + borderSize + 2, halfY + borderSize, borderColor);
        drawOrzmicStyleHexagon(center, halfX, halfY, noteColor);
    }

    private void drawOrzmicStyleHexagon(Vec2i center, int halfX, int halfY, int color) {
        Vec2i[] vs = new Vec2i[6];
        if (halfX > halfY) {
            vs[0] = new Vec2i(center.x - halfX, center.y);
            vs[1] = new Vec2i(center.x - halfX + halfY, center.y + halfY);
            vs[2] = new Vec2i(center.x + halfX - halfY, center.y + halfY);
            vs[3] = new Vec2i(center.x + halfX, center.y);
            vs[4] = new Vec2i(center.x + halfX - halfY, center.y - halfY);
            vs[5] = new Vec2i(center.x - halfX + halfY, center.y - halfY);
        }else {
            vs[0] = new Vec2i(center.x, center.y + halfY);
            vs[1] = new Vec2i(center.x + halfX, center.y + halfY - halfX);
            vs[2] = new Vec2i(center.x + halfX, center.y - halfY + halfX);
            vs[3] = new Vec2i(center.x, center.y - halfY);
            vs[4] = new Vec2i(center.x - halfX, center.y - halfY + halfX);
            vs[5] = new Vec2i(center.x - halfX, center.y + halfY - halfX);
        }
        drawPolygon(vs, color);
    }

    protected void drawPolygon(Vec2i[] vs, int color) {
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
        for (Vec2i v : vs) {
            buffer.pos(v.x, v.y, 0.0d).endVertex();
        }
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawRect(int left, int top, int right, int bottom, int color) {
        if (left < right) {
            int i = left;
            left = right;
            right = i;
        }
        if (top < bottom) {
            int j = top;
            top = bottom;
            bottom = j;
        }
        float f3 = (float)(color >> 24 & 255) / 255.0F;
        float f = (float)(color >> 16 & 255) / 255.0F;
        float f1 = (float)(color >> 8 & 255) / 255.0F;
        float f2 = (float)(color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer buffer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f, f1, f2, f3);
        buffer.begin(7, DefaultVertexFormats.POSITION);
        buffer.pos(left, bottom, 0.0D).endVertex();
        buffer.pos(right, bottom, 0.0D).endVertex();
        buffer.pos(right, top, 0.0D).endVertex();
        buffer.pos(left, top, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    private int abs(int a) {
        return a < 0 ? -a : a;
    }
}
