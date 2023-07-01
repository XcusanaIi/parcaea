package io.github.xcusanaii.parcaea.render.gui;

import io.github.xcusanaii.parcaea.Parcaea;
import io.github.xcusanaii.parcaea.event.TickHandler;
import io.github.xcusanaii.parcaea.event.handler.tick.NoteHandler;
import io.github.xcusanaii.parcaea.event.handler.tick.RecordHandler;
import io.github.xcusanaii.parcaea.io.JumpLoader;
import io.github.xcusanaii.parcaea.model.Chart;
import io.github.xcusanaii.parcaea.model.color.ColorGeneral;
import io.github.xcusanaii.parcaea.model.config.CfgGeneral;
import io.github.xcusanaii.parcaea.util.string.StringUtil;
import io.github.xcusanaii.parcaea.render.gui.widget.PGuiButton;
import io.github.xcusanaii.parcaea.render.gui.widget.PGuiTextField;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.I18n;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiChart extends GuiScreen {

    private PGuiTextField txtSearch;
    private PGuiTextField txtNoteSpeed;
    private PGuiTextField txtToleranceFactor;
    private PGuiButton btnEnableChart;
    private PGuiButton btnEnableSnake;
    private PGuiButton btnEnableAutoPos;
    private PGuiButton btnEnableLastUnstable;
    private PGuiButton btnToggleLockLastInput;
    private PGuiButton btnEnableSoundEffect;
    private PGuiButton btnEnableAutoClearPB;
    private PGuiButton btnMirrorChart;
    private PGuiButton[] lstItems;
    private List<String> idData;
    private List<String> displayData;
    private int scrollY = 0;
    private final int screenWidth = 305;
    private final int screenHeight = 270;
    private int x;
    private int y;

    @Override
    public void initGui() {
        x = (this.width - screenWidth) / 2;
        y = (this.height - screenHeight) / 2;

        idData = new ArrayList<String>();
        displayData = new ArrayList<String>();
        lstItems = new PGuiButton[10];

        txtSearch = new PGuiTextField(100, fontRendererObj, x, y, 175, 20);

        txtNoteSpeed = new PGuiTextField(100, fontRendererObj, x + 205, y + 150, 88, 20);
        txtNoteSpeed.setText(String.valueOf(CfgGeneral.noteSpeed));

        txtToleranceFactor = new PGuiTextField(100, fontRendererObj, x + 205, y + 200, 88, 20);
        txtToleranceFactor.setText(String.valueOf(CfgGeneral.toleranceFactor));

        PGuiButton btnNoteSpeedUp = new PGuiButton(11, x + 297, y + 150, 8, 8, "");
        buttonList.add(btnNoteSpeedUp);
        PGuiButton btnNoteSpeedDown = new PGuiButton(12, x + 297, y + 162, 8, 8, "");
        buttonList.add(btnNoteSpeedDown);

        PGuiButton btnToleranceFactorUp = new PGuiButton(13, x + 297, y + 200, 8, 8, "");
        buttonList.add(btnToleranceFactorUp);
        PGuiButton btnToleranceFactorDown = new PGuiButton(14, x + 297, y + 212, 8, 8, "");
        buttonList.add(btnToleranceFactorDown);

        btnEnableChart = new PGuiButton(15, x + 205, y, 100, 20, I18n.format("txt.enable_parcaea"));
        btnEnableChart.packedFGColour = CfgGeneral.enableChart ? ColorGeneral.BTN_ENABLE : ColorGeneral.BTN_DISABLE;
        buttonList.add(btnEnableChart);

        btnEnableSnake = new PGuiButton(16, x + 205, y + 25, 100, 20, I18n.format("txt.enable_snake"));
        btnEnableSnake.packedFGColour = CfgGeneral.enableSnake ? ColorGeneral.BTN_ENABLE : ColorGeneral.BTN_DISABLE;
        buttonList.add(btnEnableSnake);

        btnEnableAutoPos = new PGuiButton(18, x + 205, y + 75, 100, 20, I18n.format("txt.auto_pos"));
        btnEnableAutoPos.packedFGColour = CfgGeneral.enableAutoPos ? ColorGeneral.BTN_ENABLE : ColorGeneral.BTN_DISABLE;
        buttonList.add(btnEnableAutoPos);

        btnEnableLastUnstable = new PGuiButton(19, x + 205, y + 100, 75, 20, I18n.format("txt.last_unstable"));
        btnEnableLastUnstable.packedFGColour = CfgGeneral.enableLastInput ? ColorGeneral.BTN_ENABLE : ColorGeneral.BTN_DISABLE;
        buttonList.add(btnEnableLastUnstable);

        PGuiButton btnReloadJump = new PGuiButton(20, x + 180, y, 20, 20, I18n.format("txt.reload_jumps"));
        buttonList.add(btnReloadJump);

        btnEnableSoundEffect = new PGuiButton(22, x + 205, y + 250, 100, 20, I18n.format("txt.enable_sound_effect"));
        btnEnableSoundEffect.packedFGColour = CfgGeneral.enableSoundEffect ? ColorGeneral.BTN_ENABLE : ColorGeneral.BTN_DISABLE;
        buttonList.add(btnEnableSoundEffect);

        btnToggleLockLastInput = new PGuiButton(23, x + 285, y + 100, 20, 20, I18n.format("txt.lock_last_unstable"));
        btnToggleLockLastInput.packedFGColour = NoteHandler.lockLastInput ? ColorGeneral.BTN_ENABLE : ColorGeneral.BTN_DISABLE;
        buttonList.add(btnToggleLockLastInput);

        btnEnableAutoClearPB = new PGuiButton(31, x + 205, y + 50, 100, 20, I18n.format("txt.enable_auto_clear_pb"));
        btnEnableAutoClearPB.packedFGColour = TickHandler.enAutoClearPB ? ColorGeneral.BTN_ENABLE : ColorGeneral.BTN_DISABLE;
        buttonList.add(btnEnableAutoClearPB);

        btnMirrorChart = new PGuiButton(32, x + 205, y + 225, 100, 20, I18n.format("txt.enable_mirror_chart"));
        btnMirrorChart.packedFGColour = (Chart.selectedChart != null && Chart.selectedChart.isMirrored) ? ColorGeneral.BTN_ENABLE : ColorGeneral.BTN_DISABLE;
        buttonList.add(btnMirrorChart);

        for (int i = 0; i < 10; i++) {
            lstItems[i] = new PGuiButton(i, x, y + 25 * (i + 1), 200, 20, I18n.format("txt.key_none"));
            buttonList.add(lstItems[i]);
        }

        reloadChart();
    }

    private void reloadChart() {
        idData.clear();
        displayData.clear();
        for (Chart chart: Chart.charts) {
            idData.add(chart.id);
        }
        displayData.addAll(idData);
        updateItem();
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        if (displayData.size() > 10) {
            int wheel = Mouse.getDWheel();
            if (wheel != 0) {
                if (wheel > 0) {
                    if (scrollY > 0) scrollY--;
                }else {
                    if (scrollY < displayData.size() - 10) scrollY++;
                }
                updateItem();
            }
        }

    }

    private void updateItem() {
        for (int i = 0; i < 10; i++) {
            lstItems[i].displayString = I18n.format("txt.key_none");
        }
        if (displayData.size() <= 10) {
            for (int i = 0; i < displayData.size(); i++) {
                lstItems[i].displayString = displayData.get(i);
            }
        }else {
            for (int i = 0; i < 10; i++) {
                lstItems[i].displayString = displayData.get(scrollY + i);
            }
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id < 10) {
            Chart chart = Chart.searchChart(button.displayString);
            if (chart != null) Chart.selectedChart = chart;
            RecordHandler.isInRecord = false;
            mc.displayGuiScreen(null);
        }else if (button.id == 11) {
            if (CfgGeneral.noteSpeed + 0.1 <= 10.0) {
                CfgGeneral.noteSpeed += 0.1;
            }else {
                CfgGeneral.noteSpeed = 10.0;
            }
            String str = Parcaea.DF_1.format(CfgGeneral.noteSpeed);
            CfgGeneral.noteSpeed = Double.parseDouble(str);
            txtNoteSpeed.setText(String.valueOf(CfgGeneral.noteSpeed));
        }else if (button.id == 12) {
            if (CfgGeneral.noteSpeed - 0.1 >= 0.1) {
                CfgGeneral.noteSpeed -= 0.1;
            }else {
                CfgGeneral.noteSpeed = 0.1;
            }
            String str = Parcaea.DF_1.format(CfgGeneral.noteSpeed);
            CfgGeneral.noteSpeed = Double.parseDouble(str);
            txtNoteSpeed.setText(String.valueOf(CfgGeneral.noteSpeed));
        }else if (button.id == 13) {
            if (CfgGeneral.toleranceFactor + 0.01D <= 45.0D) {
                CfgGeneral.toleranceFactor += 0.01D;
            }else {
                CfgGeneral.toleranceFactor = 45.0D;
            }
            String str = Parcaea.DF_2.format(CfgGeneral.toleranceFactor);
            CfgGeneral.toleranceFactor = Double.parseDouble(str);
            txtToleranceFactor.setText(String.valueOf(CfgGeneral.toleranceFactor));
        }else if (button.id == 14) {
            if (CfgGeneral.toleranceFactor - 0.01D >= 0.01D) {
                CfgGeneral.toleranceFactor -= 0.01D;
            }else {
                CfgGeneral.toleranceFactor = 0.01D;
            }
            String str = Parcaea.DF_2.format(CfgGeneral.toleranceFactor);
            CfgGeneral.toleranceFactor = Double.parseDouble(str);
            txtToleranceFactor.setText(String.valueOf(CfgGeneral.toleranceFactor));
        }else if (button.id == 15) {
            CfgGeneral.enableChart = !CfgGeneral.enableChart;
            btnEnableChart.packedFGColour = CfgGeneral.enableChart ? ColorGeneral.BTN_ENABLE : ColorGeneral.BTN_DISABLE;
        }else if (button.id == 16) {
            CfgGeneral.enableSnake = !CfgGeneral.enableSnake;
            btnEnableSnake.packedFGColour = CfgGeneral.enableSnake ? ColorGeneral.BTN_ENABLE : ColorGeneral.BTN_DISABLE;
        }else if (button.id == 18) {
            CfgGeneral.enableAutoPos = !CfgGeneral.enableAutoPos;
            btnEnableAutoPos.packedFGColour = CfgGeneral.enableAutoPos ? ColorGeneral.BTN_ENABLE : ColorGeneral.BTN_DISABLE;
        }else if (button.id == 19) {
            CfgGeneral.enableLastInput = !CfgGeneral.enableLastInput;
            btnEnableLastUnstable.packedFGColour = CfgGeneral.enableLastInput ? ColorGeneral.BTN_ENABLE : ColorGeneral.BTN_DISABLE;
        }else if (button.id == 20) {
            JumpLoader.reloadJump();
            Chart.toChart();
            Chart.printChart();
            reloadChart();
            txtSearch.setText("");
            matchJumps();
        }else if (button.id == 22) {
            CfgGeneral.enableSoundEffect = !CfgGeneral.enableSoundEffect;
            btnEnableSoundEffect.packedFGColour = CfgGeneral.enableSoundEffect ? ColorGeneral.BTN_ENABLE : ColorGeneral.BTN_DISABLE;
        }else if (button.id == 23) {
            NoteHandler.lockLastInput = !NoteHandler.lockLastInput;
            btnToggleLockLastInput.packedFGColour = NoteHandler.lockLastInput ? ColorGeneral.BTN_ENABLE : ColorGeneral.BTN_DISABLE;
        }else if (button.id == 31) {
            TickHandler.enAutoClearPB = !TickHandler.enAutoClearPB;
            btnEnableAutoClearPB.packedFGColour = TickHandler.enAutoClearPB ? ColorGeneral.BTN_ENABLE : ColorGeneral.BTN_DISABLE;
        }else if (button.id == 32 && Chart.selectedChart != null) {
            Chart.selectedChart.mirror();
            btnMirrorChart.packedFGColour = Chart.selectedChart.isMirrored ? ColorGeneral.BTN_ENABLE : ColorGeneral.BTN_DISABLE;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        txtSearch.drawTextBox();
        txtNoteSpeed.drawTextBox();
        txtToleranceFactor.drawTextBox();
        drawCenteredString(fontRendererObj, I18n.format("txt.note_speed"), x + 255, y + 135, ColorGeneral.LABEL);
        drawCenteredString(fontRendererObj, I18n.format("txt.tolerance_factor"), x + 255, y + 185, ColorGeneral.LABEL);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        txtSearch.updateCursorCounter();
        txtNoteSpeed.updateCursorCounter();
        txtToleranceFactor.updateCursorCounter();
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        if (txtSearch.isFocused()) {
            txtSearch.textboxKeyTyped(typedChar, keyCode);
            matchJumps();
        }
        if (txtNoteSpeed.isFocused()) {
            txtNoteSpeed.textboxKeyTyped(typedChar, keyCode);
            String str = txtNoteSpeed.getText();
            if (StringUtil.isDouble(str)) {
                double noteSpeed = Double.parseDouble(str);
                if (noteSpeed >= 0.1 && noteSpeed <= 10.0) {
                    CfgGeneral.noteSpeed = noteSpeed;
                }
            }
        }
        if (txtToleranceFactor.isFocused()) {
            txtToleranceFactor.textboxKeyTyped(typedChar, keyCode);
            String str = txtToleranceFactor.getText();
            if (StringUtil.isDouble(str)) {
                double toleranceFactor = Double.parseDouble(str);
                if (toleranceFactor >= 0.01D && toleranceFactor <= 45.0D) {
                    CfgGeneral.toleranceFactor = toleranceFactor;
                }
            }
        }
    }

    private void matchJumps() {
        displayData.clear();
        for (String str: idData) {
            if (StringUtil.matchString(txtSearch.getText(), str)) {
                displayData.add(str);
            }
        }
        updateItem();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        int p = Parcaea.CLOSE_SAFE_PADDING;
        if (mouseX < x - p || mouseX > x + screenWidth + p || mouseY < y - p || mouseY > y + screenHeight + p) {
            mc.displayGuiScreen(null);
        }
        txtSearch.mouseClicked(mouseX, mouseY, mouseButton);
        txtNoteSpeed.mouseClicked(mouseX, mouseY, mouseButton);
        txtToleranceFactor.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
