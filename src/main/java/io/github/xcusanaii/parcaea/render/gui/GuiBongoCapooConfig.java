package io.github.xcusanaii.parcaea.render.gui;

import io.github.xcusanaii.parcaea.Parcaea;
import io.github.xcusanaii.parcaea.model.config.CfgBongoCapoo;
import io.github.xcusanaii.parcaea.model.config.CfgGeneral;
import io.github.xcusanaii.parcaea.render.gui.widget.PGuiSlider;
import net.minecraft.client.gui.GuiPageButtonList;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlider;

import java.io.IOException;

public class GuiBongoCapooConfig extends GuiScreen {

    private PGuiSlider sldX;
    private PGuiSlider sldY;
    private PGuiSlider sldScale;

    private final int screenWidth = 300;
    private final int screenHeight = 70;
    public int x;
    public int y;

    @Override
    public void initGui() {
        x = (this.width - screenWidth) / 2;
        y = (this.height - screenHeight) / 2;

        sldX = new PGuiSlider(new GuiPageButtonList.GuiResponder() {
            @Override
            public void func_175321_a(int p_175321_1_, boolean p_175321_2_) {}
            @Override
            public void onTick(int id, float value) {
                CfgBongoCapoo.x = (int) value;
            }
            @Override
            public void func_175319_a(int p_175319_1_, String p_175319_2_) {}
        }, 100, x, y, "txt.bongo_capoo_x", 0.0F, 1920.0F, CfgBongoCapoo.x, new GuiSlider.FormatHelper() {
            @Override
            public String getText(int id, String name, float value) {return name + ": " + Parcaea.DF_0.format(value);}
        }, 300);
        buttonList.add(sldX);

        sldY = new PGuiSlider(new GuiPageButtonList.GuiResponder() {
            @Override
            public void func_175321_a(int p_175321_1_, boolean p_175321_2_) {}
            @Override
            public void onTick(int id, float value) {
                CfgBongoCapoo.y = (int) value;
            }
            @Override
            public void func_175319_a(int p_175319_1_, String p_175319_2_) {}
        }, 100, x, y + 25, "txt.bongo_capoo_y", 0.0F, 1080.0F, CfgBongoCapoo.y, new GuiSlider.FormatHelper() {
            @Override
            public String getText(int id, String name, float value) {return name + ": " + Parcaea.DF_0.format(value);}
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
        }, 100, x, y + 50, "txt.bongo_capoo_scale", 0.1F, 10.0F, (float) CfgBongoCapoo.scale, new GuiSlider.FormatHelper() {
            @Override
            public String getText(int id, String name, float value) {return name + ": " + Parcaea.DF_2.format(value);}
        }, 300);
        buttonList.add(sldScale);
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
