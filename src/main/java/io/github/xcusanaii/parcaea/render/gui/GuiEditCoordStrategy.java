package io.github.xcusanaii.parcaea.render.gui;

import io.github.xcusanaii.parcaea.Parcaea;
import io.github.xcusanaii.parcaea.model.color.ColorGeneral;
import io.github.xcusanaii.parcaea.model.segment.Segment;
import io.github.xcusanaii.parcaea.render.entity.CoordMarker;
import io.github.xcusanaii.parcaea.util.math.RotationUtil;
import io.github.xcusanaii.parcaea.util.string.StringUtil;
import io.github.xcusanaii.parcaea.render.gui.widget.PGuiButton;
import io.github.xcusanaii.parcaea.render.gui.widget.PGuiTextField;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

import java.io.IOException;

import static io.github.xcusanaii.parcaea.Parcaea.DF_5;

public class GuiEditCoordStrategy extends GuiScreen {

    private PGuiTextField txtX, txtZ, txtF, txtStrategy;

    private final int screenWidth = 255;
    private final int screenHeight = 95;
    private int x;
    private int y;

    @Override
    public void initGui() {
        x = (this.width - screenWidth) / 2;
        y = (this.height - screenHeight) / 2;

        txtX = new PGuiTextField(100, fontRendererObj, x + 55, y, 128, 20);
        txtZ = new PGuiTextField(100, fontRendererObj, x + 55, y + 25, 128, 20);
        txtF = new PGuiTextField(100, fontRendererObj, x + 55, y + 50, 128, 20);
        txtStrategy = new PGuiTextField(100, fontRendererObj, x + 55, y + 75, 128, 20);

        CoordMarker coordMarker = Segment.findNearestCoordMarker();
        if (coordMarker != null) {
            txtX.setText(String.valueOf(coordMarker.coordStrategy.x));
            txtZ.setText(String.valueOf(coordMarker.coordStrategy.z));
            txtF.setText(String.valueOf(coordMarker.coordStrategy.f));
            txtStrategy.setText(String.valueOf(coordMarker.coordStrategy.strategy));
        }

        buttonList.add(new PGuiButton(0, x + 187, y + 75, 68, 20, I18n.format("txt.confirm")));

        for (int i = 0; i < 6; i++) {
            PGuiButton button = new PGuiButton(i + 1, x + 187 + i * 12, y, 8, 8, "");
            button.data = Math.pow(10.0D, -i);
            buttonList.add(button);
        }
        for (int i = 0; i < 6; i++) {
            PGuiButton button = new PGuiButton(i + 7, x + 187 + i * 12, y + 12, 8, 8, "");
            button.data = -Math.pow(10.0D, -i);
            buttonList.add(button);
        }
        for (int i = 0; i < 6; i++) {
            PGuiButton button = new PGuiButton(i + 13, x + 187 + i * 12, y + 25, 8, 8, "");
            button.data = Math.pow(10.0D, -i);
            buttonList.add(button);
        }
        for (int i = 0; i < 6; i++) {
            PGuiButton button = new PGuiButton(i + 19, x + 187 + i * 12, y + 37, 8, 8, "");
            button.data = -Math.pow(10.0D, -i);
            buttonList.add(button);
        }
        for (int i = 0; i < 6; i++) {
            PGuiButton button = new PGuiButton(i + 25, x + 187 + i * 12, y + 50, 8, 8, "");
            button.data = Math.pow(10.0D, -i);
            buttonList.add(button);
        }
        for (int i = 0; i < 6; i++) {
            PGuiButton button = new PGuiButton(i + 31, x + 187 + i * 12, y + 62, 8, 8, "");
            button.data = -Math.pow(10.0D, -i);
            buttonList.add(button);
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 0) {
            String textX = txtX.getText();
            String textZ = txtZ.getText();
            String textF = txtF.getText();
            if (StringUtil.isDouble(textX) && StringUtil.isDouble(textZ) && StringUtil.isDouble(textF)) {
                double x = Double.parseDouble(textX);
                double z = Double.parseDouble(textZ);
                double f = Double.parseDouble(DF_5.format(RotationUtil.mapDisplayYaw(Double.parseDouble(textF))));
                CoordMarker coordMarker = Segment.findNearestCoordMarker();
                if (coordMarker != null) {
                    coordMarker.coordStrategy.x = x;
                    coordMarker.coordStrategy.z = z;
                    coordMarker.coordStrategy.f = f;
                    coordMarker.coordStrategy.strategy = txtStrategy.getText();
                }
                Segment.reGenCoordMarker();
                mc.displayGuiScreen(null);
            }
        } else if (button.id >= 1 && button.id <= 12) {
            String text = txtX.getText();
            if (StringUtil.isDouble(text)) {
                double x = Double.parseDouble(text);
                x += (Double) ((PGuiButton) button).data;
                txtX.setText(DF_5.format(x));
            }
        } else if (button.id >= 13 && button.id <= 24) {
            String text = txtZ.getText();
            if (StringUtil.isDouble(text)) {
                double z = Double.parseDouble(text);
                z += (Double) ((PGuiButton) button).data;
                txtZ.setText(DF_5.format(z));
            }
        } else if (button.id >= 25 && button.id <= 36) {
            String text = txtF.getText();
            if (StringUtil.isDouble(text)) {
                double f = Double.parseDouble(text);
                f += (Double) ((PGuiButton) button).data;
                txtF.setText(DF_5.format(f));
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        txtX.drawTextBox();
        txtZ.drawTextBox();
        txtF.drawTextBox();
        txtStrategy.drawTextBox();
        drawCenteredString(fontRendererObj, I18n.format("txt.x"), x + 25, y + 5, ColorGeneral.LABEL);
        drawCenteredString(fontRendererObj, I18n.format("txt.z"), x + 25, y + 30, ColorGeneral.LABEL);
        drawCenteredString(fontRendererObj, I18n.format("txt.f"), x + 25, y + 55, ColorGeneral.LABEL);
        drawCenteredString(fontRendererObj, I18n.format("txt.strategy"), x + 25, y + 80, ColorGeneral.LABEL);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        txtX.updateCursorCounter();
        txtZ.updateCursorCounter();
        txtF.updateCursorCounter();
        txtStrategy.updateCursorCounter();
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        if (txtX.isFocused()) {
            txtX.textboxKeyTyped(typedChar, keyCode);
        }
        if (txtZ.isFocused()) {
            txtZ.textboxKeyTyped(typedChar, keyCode);
        }
        if (txtF.isFocused()) {
            txtF.textboxKeyTyped(typedChar, keyCode);
        }
        if (txtStrategy.isFocused()) {
            txtStrategy.textboxKeyTyped(typedChar, keyCode);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        int p = Parcaea.CLOSE_SAFE_PADDING;
        if (mouseX < x - p || mouseX > x + screenWidth + p || mouseY < y - p || mouseY > y + screenHeight + p) {
            mc.displayGuiScreen(null);
        }
        txtX.mouseClicked(mouseX, mouseY, mouseButton);
        txtZ.mouseClicked(mouseX, mouseY, mouseButton);
        txtF.mouseClicked(mouseX, mouseY, mouseButton);
        txtStrategy.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
