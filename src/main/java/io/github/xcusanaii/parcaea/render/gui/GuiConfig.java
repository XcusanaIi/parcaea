package io.github.xcusanaii.parcaea.render.gui;

import io.github.xcusanaii.parcaea.Parcaea;
import io.github.xcusanaii.parcaea.model.color.ColorGeneral;
import io.github.xcusanaii.parcaea.model.config.CfgGeneral;
import io.github.xcusanaii.parcaea.render.gui.widget.PGuiButton;
import io.github.xcusanaii.parcaea.render.gui.widget.PGuiTextField;
import io.github.xcusanaii.parcaea.util.string.StringUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

import java.awt.*;
import java.io.IOException;

public class GuiConfig extends GuiScreen {

    private PGuiTextField txtBarrierDistance;
    private PGuiTextField txtThemeColorRed;
    private PGuiTextField txtThemeColorGreen;
    private PGuiTextField txtThemeColorBlue;

    private final int screenWidth = 100;
    private final int screenHeight = 95;
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
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        txtBarrierDistance.drawTextBox();
        drawCenteredString(fontRendererObj, I18n.format("txt.barrier_distance"), x + 50, y + 10, ColorGeneral.LABEL);
        txtThemeColorRed.drawTextBox();
        txtThemeColorGreen.drawTextBox();
        txtThemeColorBlue.drawTextBox();
        drawCenteredString(fontRendererObj, I18n.format("txt.theme_color"), x + 50, y + 61, ColorGeneral.LABEL);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        txtBarrierDistance.updateCursorCounter();
        txtThemeColorRed.updateCursorCounter();
        txtThemeColorGreen.updateCursorCounter();
        txtThemeColorBlue.updateCursorCounter();
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
    }

    private void submitThemeColor() {
        String r = txtThemeColorRed.getText();
        String g = txtThemeColorGreen.getText();
        String b = txtThemeColorBlue.getText();
        if (StringUtil.isInteger(r) && StringUtil.isInteger(g) && StringUtil.isInteger(b)) {
            int r1 = Integer.parseInt(r);
            int g1 = Integer.parseInt(g);
            int b1 = Integer.parseInt(b);
            if (isRGBInteger(r1) && isRGBInteger(g1) && isRGBInteger(b1)) {
                Color color = new Color(r1, g1, b1);
                CfgGeneral.themeColor = color.getRGB();
            }
        }
    }

    private boolean isRGBInteger(int rgb) {
        return rgb >= 0 && rgb <= 255;
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
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
