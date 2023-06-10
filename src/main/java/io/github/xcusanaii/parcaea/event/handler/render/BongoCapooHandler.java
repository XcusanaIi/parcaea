package io.github.xcusanaii.parcaea.event.handler.render;

import io.github.xcusanaii.parcaea.Parcaea;
import io.github.xcusanaii.parcaea.model.config.CfgBongoCapoo;
import io.github.xcusanaii.parcaea.model.input.InputStat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import java.util.ArrayList;
import java.util.List;

public class BongoCapooHandler {

    private static final ResourceLocation BACKGROUND = new ResourceLocation(Parcaea.MODID, "textures/bongo_capoo/background.png");
    private static final ResourceLocation IDLE = new ResourceLocation(Parcaea.MODID, "textures/bongo_capoo/idle.png");
    private static final ResourceLocation KEYBOARD = new ResourceLocation(Parcaea.MODID, "textures/bongo_capoo/keyboard.png");
    private static final ResourceLocation HAND_1 = new ResourceLocation(Parcaea.MODID, "textures/bongo_capoo/hand_1.png");
    private static final ResourceLocation HAND_2 = new ResourceLocation(Parcaea.MODID, "textures/bongo_capoo/hand_2.png");
    private static final ResourceLocation HAND_3 = new ResourceLocation(Parcaea.MODID, "textures/bongo_capoo/hand_3.png");
    private static final ResourceLocation HAND_4 = new ResourceLocation(Parcaea.MODID, "textures/bongo_capoo/hand_4.png");
    private static final ResourceLocation HAND_5 = new ResourceLocation(Parcaea.MODID, "textures/bongo_capoo/hand_5.png");
    private static final ResourceLocation HAND_6 = new ResourceLocation(Parcaea.MODID, "textures/bongo_capoo/hand_6.png");
    private static final ResourceLocation HAND_7 = new ResourceLocation(Parcaea.MODID, "textures/bongo_capoo/hand_7.png");
    private static final ResourceLocation KEYBOARD_1 = new ResourceLocation(Parcaea.MODID, "textures/bongo_capoo/keyboard_1.png");
    private static final ResourceLocation KEYBOARD_2 = new ResourceLocation(Parcaea.MODID, "textures/bongo_capoo/keyboard_2.png");
    private static final ResourceLocation KEYBOARD_3 = new ResourceLocation(Parcaea.MODID, "textures/bongo_capoo/keyboard_3.png");
    private static final ResourceLocation KEYBOARD_4 = new ResourceLocation(Parcaea.MODID, "textures/bongo_capoo/keyboard_4.png");
    private static final ResourceLocation KEYBOARD_5 = new ResourceLocation(Parcaea.MODID, "textures/bongo_capoo/keyboard_5.png");
    private static final ResourceLocation KEYBOARD_6 = new ResourceLocation(Parcaea.MODID, "textures/bongo_capoo/keyboard_6.png");
    private static final ResourceLocation KEYBOARD_7 = new ResourceLocation(Parcaea.MODID, "textures/bongo_capoo/keyboard_7.png");

    private static final List<ResourceLocation> HANDS;
    private static final List<ResourceLocation> KEYBOARDS;

    static {
        HANDS = new ArrayList<ResourceLocation>();
        KEYBOARDS = new ArrayList<ResourceLocation>();
        HANDS.add(HAND_1);
        HANDS.add(HAND_2);
        HANDS.add(HAND_3);
        HANDS.add(HAND_4);
        HANDS.add(HAND_5);
        HANDS.add(HAND_6);
        HANDS.add(HAND_7);
        KEYBOARDS.add(KEYBOARD_1);
        KEYBOARDS.add(KEYBOARD_2);
        KEYBOARDS.add(KEYBOARD_3);
        KEYBOARDS.add(KEYBOARD_4);
        KEYBOARDS.add(KEYBOARD_5);
        KEYBOARDS.add(KEYBOARD_6);
        KEYBOARDS.add(KEYBOARD_7);
    }

    private static final Minecraft mc = Minecraft.getMinecraft();

    private static final int W = 128;

    public static void onRenderGameOverlayEventPost(float partialTicks) {

        if (!CfgBongoCapoo.enableBongoCapoo) return;

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.blendFunc(770, 771);
        GlStateManager.pushMatrix();

        float scale = (float) CfgBongoCapoo.scale;
        GlStateManager.scale(scale, scale, 1.0F);

        int x = CfgBongoCapoo.x;
        int y = CfgBongoCapoo.y;

        mc.getTextureManager().bindTexture(BACKGROUND);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0, W, W, W, W);

        mc.getTextureManager().bindTexture(KEYBOARD);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0, W, W, W, W);

        for (int i = 0; i < 7; i++) {
            if (InputStat.isKeyDown.keyList[i]) {
                mc.getTextureManager().bindTexture(KEYBOARDS.get(i));
                Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0, W, W, W, W);
            }
        }

        if (InputStat.lastKeyDownExactLeft != -1) {
            mc.getTextureManager().bindTexture(HANDS.get(InputStat.lastKeyDownExactLeft));
            Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0, W, W, W, W);
        }else {
            mc.getTextureManager().bindTexture(IDLE);
            Gui.drawModalRectWithCustomSizedTexture(x + W / 2, y, W / 2.0F, 0, W / 2, W, W, W);
        }

        if (InputStat.lastKeyDownExactRight != -1) {
            mc.getTextureManager().bindTexture(HANDS.get(InputStat.lastKeyDownExactRight));
            Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0, W, W, W, W);
        }else {
            mc.getTextureManager().bindTexture(IDLE);
            Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0, W / 2, W, W, W);
        }

        GlStateManager.popMatrix();
    }
}
