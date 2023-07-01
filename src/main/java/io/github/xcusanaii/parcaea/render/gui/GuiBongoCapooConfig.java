package io.github.xcusanaii.parcaea.render.gui;

import io.github.xcusanaii.parcaea.Parcaea;
import io.github.xcusanaii.parcaea.model.color.ColorGeneral;
import io.github.xcusanaii.parcaea.model.config.CfgBongoCapoo;
import io.github.xcusanaii.parcaea.render.gui.widget.PGuiButton;
import io.github.xcusanaii.parcaea.render.gui.widget.PGuiSlider;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiPageButtonList;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlider;
import net.minecraft.client.resources.I18n;

import java.io.IOException;

public class GuiBongoCapooConfig extends GuiScreen {

    private PGuiButton btnEnableBongoCapoo;

    private PGuiSlider sldX;
    private PGuiSlider sldY;
    private PGuiSlider sldScale;

    private final int screenWidth = 300;
    private final int screenHeight = 95;
    public int x;
    public int y;

    @Override
    public void initGui() {
        x = (this.width - screenWidth) / 2;
        y = (this.height - screenHeight) / 2;

        btnEnableBongoCapoo = new PGuiButton(0, x, y, 300, 20, I18n.format("txt.enable_bongo_capoo"));
        btnEnableBongoCapoo.packedFGColour = CfgBongoCapoo.enableBongoCapoo ? ColorGeneral.BTN_ENABLE : ColorGeneral.BTN_DISABLE;
        buttonList.add(btnEnableBongoCapoo);

        sldX = new PGuiSlider(new GuiPageButtonList.GuiResponder() {
            @Override
            public void func_175321_a(int p_175321_1_, boolean p_175321_2_) {}
            @Override
            public void onTick(int id, float value) {
                CfgBongoCapoo.xPercent = value;
            }
            @Override
            public void func_175319_a(int p_175319_1_, String p_175319_2_) {}
        }, 100, x, y + 25, "txt.bongo_capoo_x", 0.0F, 1.0F, (float) CfgBongoCapoo.xPercent, new GuiSlider.FormatHelper() {
            @Override
            public String getText(int id, String name, float value) {return name + ": " + Parcaea.DF_2.format(value);}
        }, 300);
        buttonList.add(sldX);

        sldY = new PGuiSlider(new GuiPageButtonList.GuiResponder() {
            @Override
            public void func_175321_a(int p_175321_1_, boolean p_175321_2_) {}
            @Override
            public void onTick(int id, float value) {
                CfgBongoCapoo.yPercent = value;
            }
            @Override
            public void func_175319_a(int p_175319_1_, String p_175319_2_) {}
        }, 100, x, y + 50, "txt.bongo_capoo_y", 0.0F, 1.0F, (float) CfgBongoCapoo.yPercent, new GuiSlider.FormatHelper() {
            @Override
            public String getText(int id, String name, float value) {return name + ": " + Parcaea.DF_2.format(value);}
        }, 300);
        buttonList.add(sldY);

        sldScale = new PGuiSlider(new GuiPageButtonList.GuiResponder() {
            @Override
            public void func_175321_a(int p_175321_1_, boolean p_175321_2_) {}
            @Override
            public void onTick(int id, float value) {
                CfgBongoCapoo.scale = value;
            }
            @Override
            public void func_175319_a(int p_175319_1_, String p_175319_2_) {}
        }, 100, x, y + 75, "txt.bongo_capoo_scale", 0.1F, 10.0F, (float) CfgBongoCapoo.scale, new GuiSlider.FormatHelper() {
            @Override
            public String getText(int id, String name, float value) {return name + ": " + Parcaea.DF_2.format(value);}
        }, 300);
        buttonList.add(sldScale);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 0) {
            CfgBongoCapoo.enableBongoCapoo = !CfgBongoCapoo.enableBongoCapoo;
            btnEnableBongoCapoo.packedFGColour = CfgBongoCapoo.enableBongoCapoo ? ColorGeneral.BTN_ENABLE : ColorGeneral.BTN_DISABLE;
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        int p = Parcaea.CLOSE_SAFE_PADDING;
        if (mouseX < x - p || mouseX > x + screenWidth + p || mouseY < y - p || mouseY > y + screenHeight + p) {
            mc.displayGuiScreen(null);
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
