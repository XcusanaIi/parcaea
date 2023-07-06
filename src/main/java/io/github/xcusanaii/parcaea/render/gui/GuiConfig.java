package io.github.xcusanaii.parcaea.render.gui;

import io.github.xcusanaii.parcaea.Parcaea;
import io.github.xcusanaii.parcaea.model.color.ColorGeneral;
import io.github.xcusanaii.parcaea.model.config.CfgGeneral;
import io.github.xcusanaii.parcaea.render.gui.widget.PGuiButton;
import io.github.xcusanaii.parcaea.render.gui.widget.PGuiSlider;
import io.github.xcusanaii.parcaea.render.gui.widget.PGuiTextField;
import io.github.xcusanaii.parcaea.util.color.ColorUtil;
import io.github.xcusanaii.parcaea.util.string.StringUtil;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiPageButtonList;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlider;
import net.minecraft.client.resources.I18n;

import java.awt.*;
import java.io.IOException;

public class GuiConfig extends GuiScreen {

    private PGuiTextField txtBarrierDistance;
    private PGuiTextField txtThemeColorRed;
    private PGuiTextField txtThemeColorGreen;
    private PGuiTextField txtThemeColorBlue;
    private PGuiTextField txtBeatInterval;
    private PGuiButton btnEnableBeat;
    private PGuiButton btnEnableDoubleTapHint;

    private String beatInfo;

    private final int screenWidth = 255;
    private final int screenHeight = 195;
    private int x;
    private int y;

    @Override
    public void initGui() {
        x = (this.width - screenWidth) / 2;
        y = (this.height - screenHeight) / 2;

        txtBarrierDistance = new PGuiTextField(100, fontRendererObj, x, y + 25, 88, 20);
        txtBarrierDistance.setText(String.valueOf(CfgGeneral.barrierDistance));

        buttonList.add(new PGuiButton(0, x + 92, y + 25, 8, 8, ""));
        buttonList.add(new PGuiButton(1, x + 92, y + 37, 8, 8, ""));

        txtThemeColorRed = new PGuiTextField(100, fontRendererObj, x, y + 75, 30, 20);
        txtThemeColorGreen = new PGuiTextField(100, fontRendererObj, x + 35, y + 75, 30, 20);
        txtThemeColorBlue = new PGuiTextField(100, fontRendererObj, x + 70, y + 75, 30, 20);

        Color color = new Color(CfgGeneral.themeColor);

        txtThemeColorRed.setText(String.valueOf(color.getRed()));
        txtThemeColorGreen.setText(String.valueOf(color.getGreen()));
        txtThemeColorBlue.setText(String.valueOf(color.getBlue()));

        btnEnableBeat = new PGuiButton(2, x, y + 100, 100, 20, I18n.format("parcaea.render.gui.gui_config.enable_beat"));
        buttonList.add(btnEnableBeat);
        btnEnableBeat.packedFGColour = CfgGeneral.enableBeat ? ColorGeneral.BTN_ENABLE : ColorGeneral.BTN_DISABLE;

        btnEnableDoubleTapHint = new PGuiButton(3, x, y + 175, 100, 20, I18n.format("parcaea.render.gui.gui_config.enable_double_tap_hint"));
        buttonList.add(btnEnableDoubleTapHint);
        btnEnableDoubleTapHint.packedFGColour = CfgGeneral.enableDoubleTapHint ? ColorGeneral.BTN_ENABLE : ColorGeneral.BTN_DISABLE;

        txtBeatInterval = new PGuiTextField(100, fontRendererObj, x, y + 150, 100, 20);
        txtBeatInterval.setText(String.valueOf(CfgGeneral.beatInterval));

        PGuiSlider sldSoundEffectVolume = new PGuiSlider(new GuiPageButtonList.GuiResponder() {
            @Override
            public void func_175321_a(int p_175321_1_, boolean p_175321_2_) {
            }

            @Override
            public void onTick(int id, float value) {
                CfgGeneral.soundEffectVolume = value;
            }

            @Override
            public void func_175319_a(int p_175319_1_, String p_175319_2_) {
            }
        }, 100, x + 105, y, I18n.format("parcaea.render.gui.gui_config.sound_effect_volume"), 0.0F, 1.0F, (float) CfgGeneral.soundEffectVolume, new GuiSlider.FormatHelper() {
            @Override
            public String getText(int id, String name, float value) {
                return name + ": " + Parcaea.DF_1.format(value);
            }
        }, 150);
        buttonList.add(sldSoundEffectVolume);

        PGuiSlider sldMusicVolume = new PGuiSlider(new GuiPageButtonList.GuiResponder() {
            @Override
            public void func_175321_a(int p_175321_1_, boolean p_175321_2_) {
            }

            @Override
            public void onTick(int id, float value) {
                CfgGeneral.musicVolume = value;
            }

            @Override
            public void func_175319_a(int p_175319_1_, String p_175319_2_) {
            }
        }, 100, x + 105, y + 25, I18n.format("parcaea.render.gui.gui_config.music_volume"), 0.0F, 1.0F, (float) CfgGeneral.musicVolume, new GuiSlider.FormatHelper() {
            @Override
            public String getText(int id, String name, float value) {
                return name + ": " + Parcaea.DF_1.format(value);
            }
        }, 150);
        buttonList.add(sldMusicVolume);

        PGuiSlider sldJukeBoxVolume = new PGuiSlider(new GuiPageButtonList.GuiResponder() {
            @Override
            public void func_175321_a(int p_175321_1_, boolean p_175321_2_) {}
            @Override
            public void onTick(int id, float value) {
                mc.gameSettings.setSoundLevel(SoundCategory.RECORDS, value);
            }
            @Override
            public void func_175319_a(int p_175319_1_, String p_175319_2_) {}
        }, 100, x + 105, y + 50, I18n.format("parcaea.render.gui.gui_config.juke_box_volume"), 0.0F, 1.0F, mc.gameSettings.getSoundLevel(SoundCategory.RECORDS), new GuiSlider.FormatHelper() {
            @Override
            public String getText(int id, String name, float value) {
                return name + ": " + Parcaea.DF_0.format(value * 100.0F) + "%";
            }
        }, 150);
        buttonList.add(sldJukeBoxVolume);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 0) {
            if (CfgGeneral.barrierDistance + 1 <= 512) {
                CfgGeneral.barrierDistance++;
            }
            txtBarrierDistance.setText(String.valueOf(CfgGeneral.barrierDistance));
        } else if (button.id == 1) {
            if (CfgGeneral.barrierDistance - 1 > 0) {
                CfgGeneral.barrierDistance--;
            }
            txtBarrierDistance.setText(String.valueOf(CfgGeneral.barrierDistance));
        } else if (button.id == 2) {
            CfgGeneral.enableBeat = !CfgGeneral.enableBeat;
            btnEnableBeat.packedFGColour = CfgGeneral.enableBeat ? ColorGeneral.BTN_ENABLE : ColorGeneral.BTN_DISABLE;
        } else if (button.id == 3) {
            CfgGeneral.enableDoubleTapHint = !CfgGeneral.enableDoubleTapHint;
            btnEnableDoubleTapHint.packedFGColour = CfgGeneral.enableDoubleTapHint ? ColorGeneral.BTN_ENABLE : ColorGeneral.BTN_DISABLE;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        txtBarrierDistance.drawTextBox();
        drawCenteredString(fontRendererObj, I18n.format("txt.barrier_distance"), x + 50, y + 10, ColorGeneral.LABEL);
        txtThemeColorRed.drawTextBox();
        txtThemeColorGreen.drawTextBox();
        txtThemeColorBlue.drawTextBox();
        txtBeatInterval.drawTextBox();
        drawCenteredString(fontRendererObj, I18n.format("txt.theme_color"), x + 50, y + 61, ColorGeneral.LABEL);
        beatInfo = I18n.format("parcaea.render.gui.gui_config.beat_interval") + " (" + 1200 / CfgGeneral.beatInterval + " BPM)";
        drawCenteredString(fontRendererObj, beatInfo, x + 50, y + 137, ColorGeneral.LABEL);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        txtBarrierDistance.updateCursorCounter();
        txtThemeColorRed.updateCursorCounter();
        txtThemeColorGreen.updateCursorCounter();
        txtThemeColorBlue.updateCursorCounter();
        txtBeatInterval.updateCursorCounter();
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        if (txtBarrierDistance.isFocused()) {
            txtBarrierDistance.textboxKeyTyped(typedChar, keyCode);
            String str = txtBarrierDistance.getText();
            if (StringUtil.isInteger(str)) {
                int barrierDistance = Integer.parseInt(str);
                if (barrierDistance > 0 && barrierDistance <= 512) {
                    CfgGeneral.barrierDistance = barrierDistance;
                }
            }
        }
        if (txtThemeColorRed.isFocused()) {
            txtThemeColorRed.textboxKeyTyped(typedChar, keyCode);
            submitThemeColor();
        }
        if (txtThemeColorGreen.isFocused()) {
            txtThemeColorGreen.textboxKeyTyped(typedChar, keyCode);
            submitThemeColor();
        }
        if (txtThemeColorBlue.isFocused()) {
            txtThemeColorBlue.textboxKeyTyped(typedChar, keyCode);
            submitThemeColor();
        }
        if (txtBeatInterval.isFocused()) {
            txtBeatInterval.textboxKeyTyped(typedChar, keyCode);
            String str = txtBeatInterval.getText();
            if (StringUtil.isInteger(str)) {
                int beatInterval = Integer.parseInt(str);
                if (beatInterval >= 2) {
                    CfgGeneral.beatInterval = beatInterval;
                }
            }
        }
    }

    private void submitThemeColor() {
        Color color = new Color(
                ColorUtil.mapRGB(txtThemeColorRed.getText()),
                ColorUtil.mapRGB(txtThemeColorGreen.getText()),
                ColorUtil.mapRGB(txtThemeColorBlue.getText())
        );
        CfgGeneral.themeColor = color.getRGB();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        int p = Parcaea.CLOSE_SAFE_PADDING;
        if (mouseX < x - p || mouseX > x + screenWidth + p || mouseY < y - p || mouseY > y + screenHeight + p) {
            mc.displayGuiScreen(null);
        }
        txtBarrierDistance.mouseClicked(mouseX, mouseY, mouseButton);
        txtThemeColorRed.mouseClicked(mouseX, mouseY, mouseButton);
        txtThemeColorGreen.mouseClicked(mouseX, mouseY, mouseButton);
        txtThemeColorBlue.mouseClicked(mouseX, mouseY, mouseButton);
        txtBeatInterval.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
