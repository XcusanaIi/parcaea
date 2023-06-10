package io.github.xcusanaii.parcaea.render.gui.hudmenu;

import io.github.xcusanaii.parcaea.Parcaea;
import io.github.xcusanaii.parcaea.event.TickHandler;
import io.github.xcusanaii.parcaea.model.Chart;
import io.github.xcusanaii.parcaea.model.config.CfgBasic;
import io.github.xcusanaii.parcaea.model.config.CfgGeneral;
import io.github.xcusanaii.parcaea.render.gui.widget.PGuiButton;
import io.github.xcusanaii.parcaea.render.gui.widget.PGuiSlider;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiPageButtonList;
import net.minecraft.client.gui.GuiSlider;
import net.minecraft.client.resources.I18n;
import org.lwjgl.input.Mouse;
import java.io.IOException;

public class GuiBasicHudMenu extends AGuiHudMenu {

    private PGuiSlider sldOffsetX, sldOffsetY;
    private PGuiSlider sldWidth;
    private PGuiSlider sldHeight;
    private PGuiSlider sldJLineOffsetY;
    private PGuiSlider sldMouseTrackPaddingRatio;
    private PGuiSlider sldBGOpacity;
    private PGuiSlider sldBGPadding;
    private PGuiSlider sldKeyNoteSize;
    private PGuiSlider sldMouseIndicatorSize;
    private PGuiSlider sldMouseNoteSizeRatio;
    private PGuiSlider sldKeyNoteAspectRatio;
    private PGuiSlider sldStripWidthRatio;
    private PGuiSlider sldNoteBorderSize;
    private final int screenWidth = 150;
    private final int screenHeight = 370;
    private static boolean toggleRightPos = false;
    public static int x = 5;
    public static int y = 5;

    @Override
    public void initGui() {

        sldOffsetX = new PGuiSlider(new GuiPageButtonList.GuiResponder() {
            @Override
            public void func_175321_a(int p_175321_1_, boolean p_175321_2_) {}
            @Override
            public void onTick(int id, float value) {CfgGeneral.hudOffsetX = (int) value;
                syncHudWhenInGuiHudMenu();}
            @Override
            public void func_175319_a(int p_175319_1_, String p_175319_2_) {}
        }, 100, x, y, "cfg.basic.offset_x", -800.0f, 800.0f, CfgGeneral.hudOffsetX, new GuiSlider.FormatHelper() {
            @Override
            public String getText(int id, String name, float value) {return name + ": " + Parcaea.DF_0.format(value);}
        }, 150);
        buttonList.add(sldOffsetX);

        sldOffsetY = new PGuiSlider(new GuiPageButtonList.GuiResponder() {
            @Override
            public void func_175321_a(int p_175321_1_, boolean p_175321_2_) {}
            @Override
            public void onTick(int id, float value) {CfgGeneral.hudOffsetY = (int) value;
                syncHudWhenInGuiHudMenu();}
            @Override
            public void func_175319_a(int p_175319_1_, String p_175319_2_) {}
        }, 100, x, y + 25, "cfg.basic.offset_y", -600.0f, 600.0f, CfgGeneral.hudOffsetY, new GuiSlider.FormatHelper() {
            @Override
            public String getText(int id, String name, float value) {return name + ": " + Parcaea.DF_0.format(value);}
        }, 150);
        buttonList.add(sldOffsetY);

        sldWidth = new PGuiSlider(new GuiPageButtonList.GuiResponder() {
            @Override
            public void func_175321_a(int p_175321_1_, boolean p_175321_2_) {}
            @Override
            public void onTick(int id, float value) {CfgBasic.basicHudWidth = (int) value;
                syncHudWhenInGuiHudMenu();}
            @Override
            public void func_175319_a(int p_175319_1_, String p_175319_2_) {}
        }, 100, x, y + 50, "cfg.basic.width", 200.0f, 800.0f, CfgBasic.basicHudWidth, new GuiSlider.FormatHelper() {
            @Override
            public String getText(int id, String name, float value) {return name + ": " + Parcaea.DF_0.format(value);}
        }, 150);
        buttonList.add(sldWidth);

        sldHeight = new PGuiSlider(new GuiPageButtonList.GuiResponder() {
            @Override
            public void func_175321_a(int p_175321_1_, boolean p_175321_2_) {}
            @Override
            public void onTick(int id, float value) {CfgBasic.basicHudHeight = (int) value;
                syncHudWhenInGuiHudMenu();}
            @Override
            public void func_175319_a(int p_175319_1_, String p_175319_2_) {}
        }, 100, x, y + 75, "cfg.basic.height", 150.0f, 600.0f, CfgBasic.basicHudHeight, new GuiSlider.FormatHelper() {
            @Override
            public String getText(int id, String name, float value) {return name + ": " + Parcaea.DF_0.format(value);}
        }, 150);
        buttonList.add(sldHeight);

        sldJLineOffsetY = new PGuiSlider(new GuiPageButtonList.GuiResponder() {
            @Override
            public void func_175321_a(int p_175321_1_, boolean p_175321_2_) {}
            @Override
            public void onTick(int id, float value) {CfgBasic.basicJLineOffsetY = (int) value;
                syncHudWhenInGuiHudMenu();}
            @Override
            public void func_175319_a(int p_175319_1_, String p_175319_2_) {}
        }, 100, x, y + 100, "cfg.basic.j_line_offset_y", 20.0f, 80.0f, CfgBasic.basicJLineOffsetY, new GuiSlider.FormatHelper() {
            @Override
            public String getText(int id, String name, float value) {return name + ": " + Parcaea.DF_0.format(value);}
        }, 150);
        buttonList.add(sldJLineOffsetY);

        sldMouseTrackPaddingRatio = new PGuiSlider(new GuiPageButtonList.GuiResponder() {
            @Override
            public void func_175321_a(int p_175321_1_, boolean p_175321_2_) {}
            @Override
            public void onTick(int id, float value) {CfgBasic.basicMouseTrackPaddingRatio = value;
                syncHudWhenInGuiHudMenu();}
            @Override
            public void func_175319_a(int p_175319_1_, String p_175319_2_) {}
        }, 100, x, y + 125, "cfg.basic.mouse_track_padding_ratio", 0.0f, 0.5f, (float) CfgBasic.basicMouseTrackPaddingRatio, new GuiSlider.FormatHelper() {
            @Override
            public String getText(int id, String name, float value) {return name + ": " + Parcaea.DF_2.format(value);}
        }, 150);
        buttonList.add(sldMouseTrackPaddingRatio);

        sldBGOpacity = new PGuiSlider(new GuiPageButtonList.GuiResponder() {
            @Override
            public void func_175321_a(int p_175321_1_, boolean p_175321_2_) {}
            @Override
            public void onTick(int id, float value) {CfgBasic.basicHudBGOpacity = (int) value;
                syncHudWhenInGuiHudMenu();}
            @Override
            public void func_175319_a(int p_175319_1_, String p_175319_2_) {}
        }, 100, x, y + 150, "cfg.basic.bg_opacity", 0.0f, 100.0f, CfgBasic.basicHudBGOpacity, new GuiSlider.FormatHelper() {
            @Override
            public String getText(int id, String name, float value) {return name + ": " + Parcaea.DF_0.format(value);}
        }, 150);
        buttonList.add(sldBGOpacity);

        sldBGPadding = new PGuiSlider(new GuiPageButtonList.GuiResponder() {
            @Override
            public void func_175321_a(int p_175321_1_, boolean p_175321_2_) {}
            @Override
            public void onTick(int id, float value) {CfgBasic.basicHudBGPadding = (int) value;
                syncHudWhenInGuiHudMenu();}
            @Override
            public void func_175319_a(int p_175319_1_, String p_175319_2_) {}
        }, 100, x, y + 175, "cfg.basic.bg_padding", 0.0f, 50.0f, CfgBasic.basicHudBGPadding, new GuiSlider.FormatHelper() {
            @Override
            public String getText(int id, String name, float value) {return name + ": " + Parcaea.DF_0.format(value);}
        }, 150);
        buttonList.add(sldBGPadding);

        sldKeyNoteSize = new PGuiSlider(new GuiPageButtonList.GuiResponder() {
            @Override
            public void func_175321_a(int p_175321_1_, boolean p_175321_2_) {}
            @Override
            public void onTick(int id, float value) {CfgBasic.basicKeyNoteSize = value;
                syncHudWhenInGuiHudMenu();}
            @Override
            public void func_175319_a(int p_175319_1_, String p_175319_2_) {}
        }, 100, x, y + 200, "cfg.basic.key_note_size", 0.1f, 1.0f, (float) CfgBasic.basicKeyNoteSize, new GuiSlider.FormatHelper() {
            @Override
            public String getText(int id, String name, float value) {return name + ": " + Parcaea.DF_2.format(value);}
        }, 150);
        buttonList.add(sldKeyNoteSize);

        sldMouseIndicatorSize = new PGuiSlider(new GuiPageButtonList.GuiResponder() {
            @Override
            public void func_175321_a(int p_175321_1_, boolean p_175321_2_) {}
            @Override
            public void onTick(int id, float value) {CfgBasic.basicMouseIndicatorSize = value;
                syncHudWhenInGuiHudMenu();}
            @Override
            public void func_175319_a(int p_175319_1_, String p_175319_2_) {}
        }, 100, x, y + 225, "cfg.basic.mouse_indicator_size", 0.1f, 1.0f, (float) CfgBasic.basicMouseIndicatorSize, new GuiSlider.FormatHelper() {
            @Override
            public String getText(int id, String name, float value) {return name + ": " + Parcaea.DF_2.format(value);}
        }, 150);
        buttonList.add(sldMouseIndicatorSize);

        sldMouseNoteSizeRatio = new PGuiSlider(new GuiPageButtonList.GuiResponder() {
            @Override
            public void func_175321_a(int p_175321_1_, boolean p_175321_2_) {}
            @Override
            public void onTick(int id, float value) {CfgBasic.basicMouseNoteSizeRatio = value;
                syncHudWhenInGuiHudMenu();}
            @Override
            public void func_175319_a(int p_175319_1_, String p_175319_2_) {}
        }, 100, x, y + 250, "cfg.basic.mouse_note_size_ratio", 0.1f, 1.0f, (float) CfgBasic.basicMouseNoteSizeRatio, new GuiSlider.FormatHelper() {
            @Override
            public String getText(int id, String name, float value) {return name + ": " + Parcaea.DF_2.format(value);}
        }, 150);
        buttonList.add(sldMouseNoteSizeRatio);

        sldKeyNoteAspectRatio = new PGuiSlider(new GuiPageButtonList.GuiResponder() {
            @Override
            public void func_175321_a(int p_175321_1_, boolean p_175321_2_) {}
            @Override
            public void onTick(int id, float value) {CfgBasic.basicKeyNoteAspectRatio = value;
                syncHudWhenInGuiHudMenu();}
            @Override
            public void func_175319_a(int p_175319_1_, String p_175319_2_) {}
        }, 100, x, y + 275, "cfg.basic.key_note_aspect_ratio", 1.0f, 10.0f, (float) CfgBasic.basicKeyNoteAspectRatio, new GuiSlider.FormatHelper() {
            @Override
            public String getText(int id, String name, float value) {return name + ": " + Parcaea.DF_2.format(value);}
        }, 150);
        buttonList.add(sldKeyNoteAspectRatio);

        sldStripWidthRatio = new PGuiSlider(new GuiPageButtonList.GuiResponder() {
            @Override
            public void func_175321_a(int p_175321_1_, boolean p_175321_2_) {}
            @Override
            public void onTick(int id, float value) {CfgBasic.basicStripWidthRatio = value;
                syncHudWhenInGuiHudMenu();}
            @Override
            public void func_175319_a(int p_175319_1_, String p_175319_2_) {}
        }, 100, x, y + 300, "cfg.basic.strip_width_ratio", 0.1f, 1.0f, (float) CfgBasic.basicStripWidthRatio, new GuiSlider.FormatHelper() {
            @Override
            public String getText(int id, String name, float value) {return name + ": " + Parcaea.DF_2.format(value);}
        }, 150);
        buttonList.add(sldStripWidthRatio);

        sldNoteBorderSize = new PGuiSlider(new GuiPageButtonList.GuiResponder() {
            @Override
            public void func_175321_a(int p_175321_1_, boolean p_175321_2_) {}
            @Override
            public void onTick(int id, float value) {CfgBasic.basicNoteBorderSize = (int) value;
                syncHudWhenInGuiHudMenu();}
            @Override
            public void func_175319_a(int p_175319_1_, String p_175319_2_) {}
        }, 100, x, y + 325, "cfg.basic.note_border_size", 0.0f, 10.0f, CfgBasic.basicNoteBorderSize, new GuiSlider.FormatHelper() {
            @Override
            public String getText(int id, String name, float value) {return name + ": " + Parcaea.DF_0.format(value);}
        }, 150);
        buttonList.add(sldNoteBorderSize);

        buttonList.add(new PGuiButton(0, x + 100, y + 350, 50, 20, I18n.format("txt.default")));

        buttonList.add(new PGuiButton(1, x, y + 350, 50, 20, I18n.format("txt.mirror_position")));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {
            sldOffsetX.func_175218_a(0, true);
            sldOffsetY.func_175218_a(0, true);
            sldWidth.func_175218_a(400, true);
            sldHeight.func_175218_a(300, true);
            sldJLineOffsetY.func_175218_a(40, true);
            sldMouseTrackPaddingRatio.func_175218_a(0.1f, true);
            sldBGOpacity.func_175218_a(40, true);
            sldBGPadding.func_175218_a(5, true);
            sldKeyNoteSize.func_175218_a(0.8f, true);
            sldMouseIndicatorSize.func_175218_a(0.25f, true);
            sldMouseNoteSizeRatio.func_175218_a(0.8f, true);
            sldKeyNoteAspectRatio.func_175218_a(5.0f, true);
            sldStripWidthRatio.func_175218_a(0.5f, true);
            sldNoteBorderSize.func_175218_a(2, true);
            syncHudWhenInGuiHudMenu();
        }else if (button.id == 1) {
            toggleRightPos = !toggleRightPos;
            x = toggleRightPos ? width - screenWidth - 5 : 5;
            mc.displayGuiScreen(null);
            mc.displayGuiScreen(new GuiBasicHudMenu());
        }
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int wheel = Mouse.getDWheel();
        if (wheel != 0) {
            if (wheel > 0) {
                y += 25;
            }else {
                y -= 25;
            }
            mc.displayGuiScreen(new GuiBasicHudMenu());
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

    private void syncHudWhenInGuiHudMenu() {
        if (Chart.selectedChart != null) {
            TickHandler.noteHandler.onRestartChart();
        }
    }
}
