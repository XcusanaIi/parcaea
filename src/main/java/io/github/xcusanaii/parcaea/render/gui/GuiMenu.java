package io.github.xcusanaii.parcaea.render.gui;

import io.github.xcusanaii.parcaea.Parcaea;
import io.github.xcusanaii.parcaea.event.TickHandler;
import io.github.xcusanaii.parcaea.event.handler.tick.NoteHandler;
import io.github.xcusanaii.parcaea.event.handler.tick.RecordHandler;
import io.github.xcusanaii.parcaea.io.JumpLoader;
import io.github.xcusanaii.parcaea.model.Chart;
import io.github.xcusanaii.parcaea.model.color.ColorGeneral;
import io.github.xcusanaii.parcaea.model.config.CfgBongoCapoo;
import io.github.xcusanaii.parcaea.model.config.CfgGeneral;
import io.github.xcusanaii.parcaea.render.entity.BarrierMarker;
import io.github.xcusanaii.parcaea.util.string.StringUtil;
import io.github.xcusanaii.parcaea.render.gui.widget.PGuiButton;
import io.github.xcusanaii.parcaea.render.gui.widget.PGuiTextField;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.I18n;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiMenu extends GuiScreen {

    private PGuiTextField txtSearch;
    private PGuiTextField txtNoteSpeed;
    private PGuiTextField txtToleranceFactor;
    private PGuiTextField txtBarrierDistance;
    private PGuiButton btnEnableParcaea;
    private PGuiButton btnEnableSnake;
    private PGuiButton btnEnable45S;
    private PGuiButton btnEnableAutoPos;
    private PGuiButton btnEnableLastUnstable;
    private PGuiButton btnToggleLockLastInput;
    private PGuiButton btnEnableSoundEffect;
    private PGuiButton btnEnableBongoCapoo;
    private PGuiButton btnEnableAutoClearPB;
    private PGuiButton btnMirrorChart;
    private PGuiButton[] lstItems;
    private List<String> idData;
    private List<String> displayData;
    private int scrollY = 0;
    private final int screenWidth = 410;
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

        txtBarrierDistance = new PGuiTextField(100, fontRendererObj, x + 368, y + 25, 30, 20);
        txtBarrierDistance.setText(String.valueOf(CfgGeneral.barrierDistance));

        buttonList.add(new PGuiButton(10, x + 205, y + 250, 75, 20, I18n.format("txt.keybinds")));

        PGuiButton btnNoteSpeedUp = new PGuiButton(11, x + 297, y + 150, 8, 8, "");
        buttonList.add(btnNoteSpeedUp);
        PGuiButton btnNoteSpeedDown = new PGuiButton(12, x + 297, y + 162, 8, 8, "");
        buttonList.add(btnNoteSpeedDown);

        PGuiButton btnToleranceFactorUp = new PGuiButton(13, x + 297, y + 200, 8, 8, "");
        buttonList.add(btnToleranceFactorUp);
        PGuiButton btnToleranceFactorDown = new PGuiButton(14, x + 297, y + 212, 8, 8, "");
        buttonList.add(btnToleranceFactorDown);

        btnEnableParcaea = new PGuiButton(15, x + 205, y, 100, 20, I18n.format("txt.enable_parcaea"));
        btnEnableParcaea.packedFGColour = CfgGeneral.enableParcaea ? ColorGeneral.BTN_ENABLE : ColorGeneral.BTN_DISABLE;
        buttonList.add(btnEnableParcaea);

        btnEnableSnake = new PGuiButton(16, x + 205, y + 25, 100, 20, I18n.format("txt.enable_snake"));
        btnEnableSnake.packedFGColour = CfgGeneral.enableSnake ? ColorGeneral.BTN_ENABLE : ColorGeneral.BTN_DISABLE;
        buttonList.add(btnEnableSnake);

        btnEnable45S = new PGuiButton(17, x + 205, y + 50, 100, 20, I18n.format("txt.enable_45s"));
        btnEnable45S.packedFGColour = CfgGeneral.enable45S ? ColorGeneral.BTN_ENABLE : ColorGeneral.BTN_DISABLE;
        buttonList.add(btnEnable45S);

        btnEnableAutoPos = new PGuiButton(18, x + 205, y + 75, 100, 20, I18n.format("txt.auto_pos"));
        btnEnableAutoPos.packedFGColour = CfgGeneral.enableAutoPos ? ColorGeneral.BTN_ENABLE : ColorGeneral.BTN_DISABLE;
        buttonList.add(btnEnableAutoPos);

        btnEnableLastUnstable = new PGuiButton(19, x + 205, y + 100, 75, 20, I18n.format("txt.last_unstable"));
        btnEnableLastUnstable.packedFGColour = CfgGeneral.enableLastInput ? ColorGeneral.BTN_ENABLE : ColorGeneral.BTN_DISABLE;
        buttonList.add(btnEnableLastUnstable);

        PGuiButton btnReloadJump = new PGuiButton(20, x + 180, y, 20, 20, I18n.format("txt.reload_jumps"));
        buttonList.add(btnReloadJump);

        PGuiButton btnHudMenu = new PGuiButton(21, x + 205, y + 225, 100, 20, I18n.format("txt.hud_style"));
        buttonList.add(btnHudMenu);

        btnEnableSoundEffect = new PGuiButton(22, x + 285, y + 250, 20, 20, I18n.format("txt.enable_sound_effect"));
        btnEnableSoundEffect.packedFGColour = CfgGeneral.enableSoundEffect ? ColorGeneral.BTN_ENABLE : ColorGeneral.BTN_DISABLE;
        buttonList.add(btnEnableSoundEffect);

        btnToggleLockLastInput = new PGuiButton(23, x + 285, y + 100, 20, 20, I18n.format("txt.lock_last_unstable"));
        btnToggleLockLastInput.packedFGColour = NoteHandler.lockLastInput ? ColorGeneral.BTN_ENABLE : ColorGeneral.BTN_DISABLE;
        buttonList.add(btnToggleLockLastInput);

        buttonList.add(new PGuiButton(24, x + 310, y + 250, 100, 20, I18n.format("txt.segment")));

        buttonList.add(new PGuiButton(25, x + 310, y, 100, 20, I18n.format("txt.reveal_barrier")));

        buttonList.add(new PGuiButton(26, x + 310, y + 50, 100, 20, I18n.format("txt.remove_barrier_marker")));

        buttonList.add(new PGuiButton(27, x + 402, y + 25, 8, 8, ""));
        buttonList.add(new PGuiButton(28, x + 402, y + 37, 8, 8, ""));

        btnEnableBongoCapoo = new PGuiButton(29, x + 310, y + 75, 100, 20, I18n.format("txt.enable_bongo_capoo"));
        btnEnableBongoCapoo.packedFGColour = CfgBongoCapoo.enableBongoCapoo ? ColorGeneral.BTN_ENABLE : ColorGeneral.BTN_DISABLE;
        buttonList.add(btnEnableBongoCapoo);

        buttonList.add(new PGuiButton(30, x + 310, y + 100, 100, 20, I18n.format("txt.bongo_capoo_config")));

        btnEnableAutoClearPB = new PGuiButton(31, x + 310, y + 125, 100, 20, I18n.format("txt.enable_auto_clear_pb"));
        btnEnableAutoClearPB.packedFGColour = TickHandler.enAutoClearPB ? ColorGeneral.BTN_ENABLE : ColorGeneral.BTN_DISABLE;
        buttonList.add(btnEnableAutoClearPB);

        btnMirrorChart = new PGuiButton(32, x + 310, y + 225, 100, 20, I18n.format("txt.enable_mirror_chart"));
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
            CfgGeneral.enable45S = false;
            RecordHandler.isInRecord = false;
            mc.displayGuiScreen(null);
        }else if (button.id == 10) {
            mc.displayGuiScreen(new GuiPKControls());
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
            if (CfgGeneral.toleranceFactor + 0.01 <= 1.0) {
                CfgGeneral.toleranceFactor += 0.01;
            }else {
                CfgGeneral.toleranceFactor = 1.0;
            }
            String str = Parcaea.DF_2.format(CfgGeneral.toleranceFactor);
            CfgGeneral.toleranceFactor = Double.parseDouble(str);
            txtToleranceFactor.setText(String.valueOf(CfgGeneral.toleranceFactor));
        }else if (button.id == 14) {
            if (CfgGeneral.toleranceFactor - 0.01 >= 0.01) {
                CfgGeneral.toleranceFactor -= 0.01;
            }else {
                CfgGeneral.toleranceFactor = 0.01;
            }
            String str = Parcaea.DF_2.format(CfgGeneral.toleranceFactor);
            CfgGeneral.toleranceFactor = Double.parseDouble(str);
            txtToleranceFactor.setText(String.valueOf(CfgGeneral.toleranceFactor));
        }else if (button.id == 15) {
            CfgGeneral.enableParcaea = !CfgGeneral.enableParcaea;
            btnEnableParcaea.packedFGColour = CfgGeneral.enableParcaea ? ColorGeneral.BTN_ENABLE : ColorGeneral.BTN_DISABLE;
        }else if (button.id == 16) {
            CfgGeneral.enableSnake = !CfgGeneral.enableSnake;
            btnEnableSnake.packedFGColour = CfgGeneral.enableSnake ? ColorGeneral.BTN_ENABLE : ColorGeneral.BTN_DISABLE;
        }else if (button.id == 17) {
            CfgGeneral.enable45S = !CfgGeneral.enable45S;
            btnEnable45S.packedFGColour = CfgGeneral.enable45S ? ColorGeneral.BTN_ENABLE : ColorGeneral.BTN_DISABLE;
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
        }else if (button.id == 21) {
            mc.displayGuiScreen(new GuiHudMenu());
        }else if (button.id == 22) {
            CfgGeneral.enableSoundEffect = !CfgGeneral.enableSoundEffect;
            btnEnableSoundEffect.packedFGColour = CfgGeneral.enableSoundEffect ? ColorGeneral.BTN_ENABLE : ColorGeneral.BTN_DISABLE;
        }else if (button.id == 23) {
            NoteHandler.lockLastInput = !NoteHandler.lockLastInput;
            btnToggleLockLastInput.packedFGColour = NoteHandler.lockLastInput ? ColorGeneral.BTN_ENABLE : ColorGeneral.BTN_DISABLE;
        }else if (button.id == 24) {
            mc.displayGuiScreen(new GuiSegmentMenu());
        }else if (button.id == 25) {
            BarrierMarker.revealBarrier();
            mc.displayGuiScreen(null);
        }else if (button.id == 26) {
            BarrierMarker.removeBarrierMarker();
            mc.displayGuiScreen(null);
        }else if (button.id == 27) {
            if (CfgGeneral.barrierDistance + 1 <= 512) {
                CfgGeneral.barrierDistance++;
            }
            txtBarrierDistance.setText(String.valueOf(CfgGeneral.barrierDistance));
        }else if (button.id == 28) {
            if (CfgGeneral.barrierDistance - 1 > 0) {
                CfgGeneral.barrierDistance--;
            }
            txtBarrierDistance.setText(String.valueOf(CfgGeneral.barrierDistance));
        }else if (button.id == 29) {
            CfgBongoCapoo.enableBongoCapoo = !CfgBongoCapoo.enableBongoCapoo;
            btnEnableBongoCapoo.packedFGColour = CfgBongoCapoo.enableBongoCapoo ? ColorGeneral.BTN_ENABLE : ColorGeneral.BTN_DISABLE;
        }else if (button.id == 30) {
            mc.displayGuiScreen(new GuiBongoCapooConfig());
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
        txtBarrierDistance.drawTextBox();
        drawCenteredString(fontRendererObj, I18n.format("txt.note_speed"), x + 255, y + 135, ColorGeneral.LABEL);
        drawCenteredString(fontRendererObj, I18n.format("txt.tolerance_factor"), x + 255, y + 185, ColorGeneral.LABEL);
        drawCenteredString(fontRendererObj, I18n.format("txt.barrier_distance"), x + 340, y + 33, ColorGeneral.LABEL);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        txtSearch.updateCursorCounter();
        txtNoteSpeed.updateCursorCounter();
        txtToleranceFactor.updateCursorCounter();
        txtBarrierDistance.updateCursorCounter();
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
                if (toleranceFactor >= 0.01 && toleranceFactor <= 1.0) {
                    CfgGeneral.toleranceFactor = toleranceFactor;
                }
            }
        }
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
        txtBarrierDistance.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
