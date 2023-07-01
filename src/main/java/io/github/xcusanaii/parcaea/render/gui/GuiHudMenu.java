package io.github.xcusanaii.parcaea.render.gui;

import io.github.xcusanaii.parcaea.Parcaea;
import io.github.xcusanaii.parcaea.model.color.ColorGeneral;
import io.github.xcusanaii.parcaea.render.gui.hudmenu.GuiBasicHudMenu;
import io.github.xcusanaii.parcaea.render.gui.widget.PGuiButton;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import java.io.IOException;

public class GuiHudMenu extends GuiScreen {

    private PGuiButton btnSelectBasic;
    private PGuiButton btnEditBasic;
    private final int screenWidth = 210;
    private final int screenHeight = 200;
    private int x;
    private int y;

    @Override
    public void initGui() {
        x = (this.width - screenWidth) / 2;
        y = (this.height - screenHeight) / 2;
        btnSelectBasic = new PGuiButton(0, x + 105, y, 50, 20, I18n.format("txt.select"));
        buttonList.add(btnSelectBasic);
        btnEditBasic = new PGuiButton(1, x + 160, y, 50, 20, I18n.format("txt.edit"));
        buttonList.add(btnEditBasic);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 0) {

        }else if (button.id == 1) {
            GuiBasicHudMenu.y = 5;
            mc.displayGuiScreen(new GuiBasicHudMenu());
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        int p = Parcaea.CLOSE_SAFE_PADDING;
        if (mouseX < x - p || mouseX > x + screenWidth + p || mouseY < y - p || mouseY > y + screenHeight + p) {
            mc.displayGuiScreen(null);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawCenteredString(fontRendererObj, I18n.format("txt.default_style"), x + 50, y + 5, ColorGeneral.LABEL);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
