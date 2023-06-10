package io.github.xcusanaii.parcaea.render.gui;

import io.github.xcusanaii.parcaea.Parcaea;
import io.github.xcusanaii.parcaea.model.color.ColorGeneral;
import io.github.xcusanaii.parcaea.model.segment.CoordStrategy;
import io.github.xcusanaii.parcaea.model.segment.Segment;
import io.github.xcusanaii.parcaea.util.MapFacing;
import io.github.xcusanaii.parcaea.util.string.StringUtil;
import io.github.xcusanaii.parcaea.render.gui.widget.PGuiButton;
import io.github.xcusanaii.parcaea.render.gui.widget.PGuiTextField;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

import java.io.IOException;

public class GuiNewCoordStrategy extends GuiScreen {

    private PGuiTextField txtX, txtZ, txtF, txtStrategy;

    private final int screenWidth = 255;
    private final int screenHeight = 120;
    private int x;
    private int y;

    @Override
    public void initGui() {
        x = (this.width - screenWidth) / 2;
        y = (this.height - screenHeight) / 2;

        txtX = new PGuiTextField(100, fontRendererObj, x + 55, y, 200, 20);
        txtZ = new PGuiTextField(100, fontRendererObj, x + 55, y + 25, 200, 20);
        txtF = new PGuiTextField(100, fontRendererObj, x + 55, y + 50, 200, 20);

        if (mc.thePlayer != null) {
            txtX.setText(String.valueOf(mc.thePlayer.posX));
            txtZ.setText(String.valueOf(mc.thePlayer.posZ));
            txtF.setText(String.valueOf(MapFacing.map(mc.thePlayer.rotationYaw)));
        }

        txtStrategy = new PGuiTextField(100, fontRendererObj, x + 55, y + 75, 200, 20);

        buttonList.add(new PGuiButton(0, x + 78, y + 100, 100, 20, I18n.format("txt.confirm")));
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 0 && mc.thePlayer != null && Segment.selectedSegment != null) {
            if (StringUtil.isDouble(txtX.getText()) && StringUtil.isDouble(txtZ.getText()) && StringUtil.isDouble(txtF.getText())) {
                double x = Double.parseDouble(txtX.getText());
                double z = Double.parseDouble(txtZ.getText());
                double f = MapFacing.map(Double.parseDouble(txtF.getText()));
                double y = mc.thePlayer.posY;
                CoordStrategy coordStrategy = new CoordStrategy(x, y, z, f, txtStrategy.getText());
                Segment.addCoordMarker(coordStrategy);
                mc.displayGuiScreen(null);
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
