package io.github.xcusanaii.parcaea.render.gui.widget;

import io.github.xcusanaii.parcaea.Parcaea;
import io.github.xcusanaii.parcaea.model.color.ColorGeneral;
import io.github.xcusanaii.parcaea.model.config.CfgGeneral;
import io.github.xcusanaii.parcaea.util.sound.SoundUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class PGuiButton extends GuiButton {

    public Object data;

    private ResourceLocation texture;
    private ResourceLocation textureHovered;
    private int textureWidth;
    private int textureHeight;
    private boolean canHideText;
    private boolean enableThemeColor;

    public PGuiButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        texture = null;
        enableThemeColor = false;
    }

    public void setEnableThemeColor(boolean enableThemeColor) {
        this.enableThemeColor = enableThemeColor;
    }

    public void setTexture(String texture, String textureHovered, int width, int height, boolean canHideText) {
        this.texture = new ResourceLocation(Parcaea.MODID, texture);
        this.textureHovered = new ResourceLocation(Parcaea.MODID, textureHovered);
        this.textureWidth = width;
        this.textureHeight = height;
        this.canHideText = canHideText;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (!this.visible) return;
        FontRenderer fontrenderer = mc.fontRendererObj;
        this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
        int color = this.hovered ? ColorGeneral.BUTTON_COLOR_HOVER : ColorGeneral.BUTTON_COLOR;
        drawRect(xPosition, yPosition, xPosition + width, yPosition + height, color);

        if (texture != null) {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.blendFunc(770, 771);
            if (this.hovered) {
                mc.getTextureManager().bindTexture(textureHovered);
            }else {
                mc.getTextureManager().bindTexture(texture);
            }
            Gui.drawModalRectWithCustomSizedTexture(this.xPosition + this.width / 2 - textureWidth / 2, this.yPosition + this.height / 2 - textureHeight / 2, 0, 0, textureWidth, textureHeight, textureWidth, textureHeight);
        }

        this.mouseDragged(mc, mouseX, mouseY);
        int j = enableThemeColor ? CfgGeneral.themeColor : 14737632;
        if (packedFGColour != 0) {
            j = packedFGColour;
        } else if (!this.enabled) {
            j = 10526880;
        } else if (this.hovered) {
            j = 16777120;
        }
        if (!canHideText || texture == null || this.hovered) {
            this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, j);
        }
    }

    @Override
    public void playPressSound(SoundHandler soundHandlerIn) {
        SoundUtil.playSound(SoundUtil.getSoundName("btn"), (float) CfgGeneral.soundEffectVolume);
    }
}
