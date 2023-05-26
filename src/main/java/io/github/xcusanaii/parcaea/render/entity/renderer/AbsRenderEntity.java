package io.github.xcusanaii.parcaea.render.entity.renderer;

import io.github.xcusanaii.parcaea.model.color.ColorGeneral;
import io.github.xcusanaii.parcaea.model.segment.CoordStrategy;
import io.github.xcusanaii.parcaea.render.entity.BarrierMarker;
import io.github.xcusanaii.parcaea.util.math.Vec2i;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

import static io.github.xcusanaii.parcaea.model.color.ColorGeneral.COORD_MARKER_BG;

public abstract class AbsRenderEntity extends RenderEntity {

    public AbsRenderEntity(RenderManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public boolean shouldRender(Entity livingEntity, ICamera camera, double camX, double camY, double camZ) {
        return true;
    }

    public static void renderBarrierMarker(BarrierMarker barrierMarker, float partialTicks) {
        GlStateManager.alphaFunc(516, 0.1F);
        GlStateManager.pushMatrix();
        Entity viewer = Minecraft.getMinecraft().getRenderViewEntity();
        double viewerX = viewer.lastTickPosX + (viewer.posX - viewer.lastTickPosX) * partialTicks;
        double viewerY = viewer.lastTickPosY + (viewer.posY - viewer.lastTickPosY) * partialTicks;
        double viewerZ = viewer.lastTickPosZ + (viewer.posZ - viewer.lastTickPosZ) * partialTicks;
        double x = barrierMarker.posX - viewerX;
        double y = barrierMarker.posY - viewerY - viewer.getEyeHeight();
        double z = barrierMarker.posZ - viewerZ;
        GlStateManager.translate(x, y, z);
        GlStateManager.translate(0, viewer.getEyeHeight(), 0);

        renderIcon(
                new Vec2i[]{
                        new Vec2i(-4, 0),
                        new Vec2i(0, 4),
                        new Vec2i(4, 0),
                        new Vec2i(0, -4)
                },
                ColorGeneral.BARRIER,
                0.25F,
                ColorGeneral.BORDER_BLACK
        );

        GlStateManager.popMatrix();
        GlStateManager.disableLighting();
    }

    /**
     * Taken from NotEnoughUpdates
     * <a href="https://github.com/Moulberry/NotEnoughUpdates/blob/master/COPYING">link</a>
     * @author Moulberry
     */
    public static void renderCoordMarker(CoordStrategy coordStrategy, float partialTicks, double distance, int color) {
        GlStateManager.alphaFunc(516, 0.1F);
        GlStateManager.pushMatrix();
        Entity viewer = Minecraft.getMinecraft().getRenderViewEntity();
        double viewerX = viewer.lastTickPosX + (viewer.posX - viewer.lastTickPosX) * partialTicks;
        double viewerY = viewer.lastTickPosY + (viewer.posY - viewer.lastTickPosY) * partialTicks;
        double viewerZ = viewer.lastTickPosZ + (viewer.posZ - viewer.lastTickPosZ) * partialTicks;
        double x = coordStrategy.x - viewerX;
        double y = coordStrategy.y - viewerY - viewer.getEyeHeight();
        double z = coordStrategy.z - viewerZ;
        double distSq = x * x + y * y + z * z;
        double dist = Math.sqrt(distSq);
        if(distSq > 144) {
            x *= 12 / dist;
            y *= 12 / dist;
            z *= 12 / dist;
        }
        GlStateManager.translate(x, y, z);
        GlStateManager.translate(0, viewer.getEyeHeight(), 0);

        renderIcon(
                new Vec2i[]{
                        new Vec2i(-4, 0),
                        new Vec2i(0, 4),
                        new Vec2i(4, 0),
                        new Vec2i(0, -4)
                },
                color,
                0.25F,
                ColorGeneral.BORDER_BLACK
        );

        if (distance <= 3.875D) {
            offsetY(-0.08f);
            renderString("[X]: " + coordStrategy.x, ColorGeneral.LABEL, COORD_MARKER_BG, 0.5F);
            offsetY(-0.12f);
            renderString("[Z]: " + coordStrategy.z, ColorGeneral.LABEL, COORD_MARKER_BG, 0.5F);
            offsetY(-0.12f);
            renderString("[F]: " + coordStrategy.f, ColorGeneral.LABEL, COORD_MARKER_BG, 0.5F);
            offsetY(-0.12f);
            renderString(coordStrategy.strategy, ColorGeneral.LABEL, COORD_MARKER_BG, 0.5F);
        }

        GlStateManager.popMatrix();
        GlStateManager.disableLighting();
    }

    public static void renderIcon(Vec2i[] vs, int color, float borderRatio, int borderColor) {
        renderPolygon(vs, borderColor, borderRatio + 1.0F);
        renderPolygon(vs, color, 1.0F);
    }

    /**
     * Taken from NotEnoughUpdates
     * <a href="https://github.com/Moulberry/NotEnoughUpdates/blob/master/COPYING">link</a>
     * @author Moulberry
     */
    public static void renderPolygon(Vec2i[] vs, int color, float scale) {
        float r = (float)(color >> 16 & 255) / 255.0f;
        float g = (float)(color >> 8 & 255) / 255.0f;
        float b = (float)(color & 255) / 255.0f;
        float a = (float)(color >> 24 & 255) / 255.0f;
        float f = 1.6F;
        float f1 = 0.016666668F * f * scale;
        GlStateManager.pushMatrix();
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-Minecraft.getMinecraft().getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(Minecraft.getMinecraft().getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(f1, f1, f1);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.disableTexture2D();
        worldrenderer.begin(GL11.GL_POLYGON, DefaultVertexFormats.POSITION_COLOR);

        for (Vec2i v : vs) {
            worldrenderer.pos(v.x, v.y, 0.0D).color(r, g, b, a).endVertex();
        }

        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
    }

    /**
     * Taken from NotEnoughUpdates
     * <a href="https://github.com/Moulberry/NotEnoughUpdates/blob/master/COPYING">link</a>
     * @author Moulberry
     */
    public static void renderString(String str, int strColor, int bgColor, float scale) {
        float r = (float)(bgColor >> 16 & 255) / 255.0f;
        float g = (float)(bgColor >> 8 & 255) / 255.0f;
        float b = (float)(bgColor & 255) / 255.0f;
        float a = (float)(bgColor >> 24 & 255) / 255.0f;
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
        float f = 1.6F;
        float f1 = 0.016666668F * f * scale;
        GlStateManager.pushMatrix();
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-Minecraft.getMinecraft().getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(Minecraft.getMinecraft().getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(-f1, -f1, f1);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        int i = 0;
        int j = fontRenderer.getStringWidth(str) / 2;
        GlStateManager.disableTexture2D();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos(-j - 1, -1 + i, 0.0D).color(r, g, b, a).endVertex();
        worldrenderer.pos(-j - 1, 8 + i, 0.0D).color(r, g, b, a).endVertex();
        worldrenderer.pos(j + 1, 8 + i, 0.0D).color(r, g, b, a).endVertex();
        worldrenderer.pos(j + 1, -1 + i, 0.0D).color(r, g, b, a).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        fontRenderer.drawString(str, -j, i, strColor);
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
    }

    public static void offsetY(float offsetY) {
        GlStateManager.rotate(-Minecraft.getMinecraft().getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(Minecraft.getMinecraft().getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
        GlStateManager.translate(0, offsetY, 0);
        GlStateManager.rotate(-Minecraft.getMinecraft().getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(Minecraft.getMinecraft().getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
    }
}
