package io.github.xcusanaii.parcaea.render.gui;

import io.github.xcusanaii.parcaea.Parcaea;
import io.github.xcusanaii.parcaea.event.NoteHandler;
import io.github.xcusanaii.parcaea.event.RecordHandler;
import io.github.xcusanaii.parcaea.io.JumpLoader;
import io.github.xcusanaii.parcaea.model.Chart;
import io.github.xcusanaii.parcaea.model.config.CfgGeneral;
import io.github.xcusanaii.parcaea.util.widget.PGuiButton;
import io.github.xcusanaii.parcaea.util.widget.PGuiTextField;
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
    private PGuiButton btnEnableParcaea;
    private PGuiButton btnEnableSnake;
    private PGuiButton btnEnable45S;
    private PGuiButton btnEnableAutoPos;
    private PGuiButton btnEnableLastUnstable;
    private PGuiButton btnToggleLockLastInput;
    private PGuiButton btnEnableSoundEffect;
    private PGuiButton[] lstItems;
    private List<String> idData;
    private List<String> displayData;
    private int scrollY = 0;
    private final int screenWidth = 305;
    private final int screenHeight = 270;
    private int x;
    private int y;

    private static final int COLOR_ENABLE = 0xFF00DD00;
    private static final int COLOR_DISABLE = 0xFFDD0000;

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
        if (CfgGeneral.enableParcaea) btnEnableParcaea.packedFGColour = COLOR_ENABLE;
        else btnEnableParcaea.packedFGColour = COLOR_DISABLE;
        buttonList.add(btnEnableParcaea);

        btnEnableSnake = new PGuiButton(16, x + 205, y + 25, 100, 20, I18n.format("txt.enable_snake"));
        if (CfgGeneral.enableSnake) btnEnableSnake.packedFGColour = COLOR_ENABLE;
        else btnEnableSnake.packedFGColour = COLOR_DISABLE;
        buttonList.add(btnEnableSnake);

        btnEnable45S = new PGuiButton(17, x + 205, y + 50, 100, 20, I18n.format("txt.enable_45s"));
        if (CfgGeneral.enable45S) btnEnable45S.packedFGColour = COLOR_ENABLE;
        else btnEnable45S.packedFGColour = COLOR_DISABLE;
        buttonList.add(btnEnable45S);

        btnEnableAutoPos = new PGuiButton(18, x + 205, y + 75, 100, 20, I18n.format("txt.auto_pos"));
        if (CfgGeneral.enableAutoPos) btnEnableAutoPos.packedFGColour = COLOR_ENABLE;
        else btnEnableAutoPos.packedFGColour = COLOR_DISABLE;
        buttonList.add(btnEnableAutoPos);

        btnEnableLastUnstable = new PGuiButton(19, x + 205, y + 100, 75, 20, I18n.format("txt.last_unstable"));
        if (CfgGeneral.enableLastInput) btnEnableLastUnstable.packedFGColour = COLOR_ENABLE;
        else btnEnableLastUnstable.packedFGColour = COLOR_DISABLE;
        buttonList.add(btnEnableLastUnstable);

        PGuiButton btnReloadJump = new PGuiButton(20, x + 180, y, 20, 20, I18n.format("txt.reload_jumps"));
        buttonList.add(btnReloadJump);

        PGuiButton btnHudMenu = new PGuiButton(21, x + 205, y + 225, 100, 20, I18n.format("txt.hud_style"));
        buttonList.add(btnHudMenu);

        btnEnableSoundEffect = new PGuiButton(22, x + 285, y + 250, 20, 20, I18n.format("txt.enable_sound_effect"));
        if (CfgGeneral.enableSoundEffect) btnEnableSoundEffect.packedFGColour = COLOR_ENABLE;
        else btnEnableSoundEffect.packedFGColour = COLOR_DISABLE;
        buttonList.add(btnEnableSoundEffect);

        btnToggleLockLastInput = new PGuiButton(23, x + 285, y + 100, 20, 20, I18n.format("txt.lock_last_unstable"));
        if (NoteHandler.lockLastInput) btnToggleLockLastInput.packedFGColour = COLOR_ENABLE;
        else btnToggleLockLastInput.packedFGColour = COLOR_DISABLE;
        buttonList.add(btnToggleLockLastInput);

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
            for (Chart chart: Chart.charts) {
                if (chart.id.equals(button.displayString)) {
                    Chart.selectedChart = chart;
                    CfgGeneral.enable45S = false;
                    mc.displayGuiScreen(null);
                    RecordHandler.isInRecord = false;
                    return;
                }
            }
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
            btnEnableParcaea.packedFGColour = CfgGeneral.enableParcaea ? COLOR_ENABLE : COLOR_DISABLE;
        }else if (button.id == 16) {
            CfgGeneral.enableSnake = !CfgGeneral.enableSnake;
            btnEnableSnake.packedFGColour = CfgGeneral.enableSnake ? COLOR_ENABLE : COLOR_DISABLE;
        }else if (button.id == 17) {
            CfgGeneral.enable45S = !CfgGeneral.enable45S;
            btnEnable45S.packedFGColour = CfgGeneral.enable45S ? COLOR_ENABLE : COLOR_DISABLE;
        }else if (button.id == 18) {
            CfgGeneral.enableAutoPos = !CfgGeneral.enableAutoPos;
            btnEnableAutoPos.packedFGColour = CfgGeneral.enableAutoPos ? COLOR_ENABLE : COLOR_DISABLE;
        }else if (button.id == 19) {
            CfgGeneral.enableLastInput = !CfgGeneral.enableLastInput;
            btnEnableLastUnstable.packedFGColour = CfgGeneral.enableLastInput ? COLOR_ENABLE : COLOR_DISABLE;
        }else if (button.id == 20) {
            JumpLoader.reloadJump();
            Chart.toChart();
            Chart.printChart();
            reloadChart();
        }else if (button.id == 21) {
            mc.displayGuiScreen(new GuiHudMenu());
        }else if (button.id == 22) {
            CfgGeneral.enableSoundEffect = !CfgGeneral.enableSoundEffect;
            btnEnableSoundEffect.packedFGColour = CfgGeneral.enableSoundEffect ? COLOR_ENABLE : COLOR_DISABLE;
        }else if (button.id == 23) {
            NoteHandler.lockLastInput = !NoteHandler.lockLastInput;
            btnToggleLockLastInput.packedFGColour = NoteHandler.lockLastInput ? COLOR_ENABLE : COLOR_DISABLE;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        txtSearch.drawTextBox();
        txtNoteSpeed.drawTextBox();
        txtToleranceFactor.drawTextBox();
        drawCenteredString(fontRendererObj, I18n.format("txt.note_speed"), x + 255, y + 135, 16777120);
        drawCenteredString(fontRendererObj, I18n.format("txt.tolerance_factor"), x + 255, y + 185, 16777120);
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
            displayData.clear();
            for (String str: idData) {
                if (str.contains(txtSearch.getText())) {
                    displayData.add(str);
                }
            }
            updateItem();
        }
        if (txtNoteSpeed.isFocused()) {
            txtNoteSpeed.textboxKeyTyped(typedChar, keyCode);
            String str = txtNoteSpeed.getText();
            if (str.matches("^-?[0-9]+(\\.[0-9]+)?$")) {
                double noteSpeed = Double.parseDouble(str);
                if (noteSpeed >= 0.1 && noteSpeed <= 10.0) {
                    CfgGeneral.noteSpeed = noteSpeed;
                }
            }
        }
        if (txtToleranceFactor.isFocused()) {
            txtToleranceFactor.textboxKeyTyped(typedChar, keyCode);
            String str = txtToleranceFactor.getText();
            if (str.matches("^-?[0-9]+(\\.[0-9]+)?$")) {
                double toleranceFactor = Double.parseDouble(str);
                if (toleranceFactor >= 0.01 && toleranceFactor <= 1.0) {
                    CfgGeneral.toleranceFactor = toleranceFactor;
                }
            }
        }
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
