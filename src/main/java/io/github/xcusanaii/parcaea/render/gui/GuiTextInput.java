package io.github.xcusanaii.parcaea.render.gui;

import io.github.xcusanaii.parcaea.Parcaea;
import io.github.xcusanaii.parcaea.model.color.ColorGeneral;
import io.github.xcusanaii.parcaea.render.gui.widget.PGuiButton;
import io.github.xcusanaii.parcaea.render.gui.widget.PGuiTextField;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

import java.io.IOException;
import java.util.function.BiConsumer;

public class GuiTextInput extends GuiScreen {
    private final int screenWidth = 200;
    private final int screenHeight = 45;
    private int x;
    private int y;

    private String title;
    private PGuiTextField textField;
    private final BiConsumer<String, Boolean> confirm;

    public GuiTextInput(String title, BiConsumer<String, Boolean> confirm) {
        this.title = title;
        this.confirm = confirm;
    }

    @Override
    public void initGui() {
        x = (this.width - screenWidth) / 2;
        y = (this.height - screenHeight) / 2;
        textField = new PGuiTextField(100, fontRendererObj, x, y, 200, 20);
        buttonList.add(new PGuiButton(0, x + 50, y + 25, 100, 20, I18n.format("txt.confirm")));
        textField.setFocused(true);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 0) {
            confirm.accept(textField.getText(), new Boolean(true));
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        int p = Parcaea.CLOSE_SAFE_PADDING;
        if (mouseX < x - p || mouseX > x + screenWidth + p || mouseY < y - p || mouseY > y + screenHeight + p) {
            confirm.accept(textField.getText(), new Boolean(false));
        }
        textField.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        textField.drawTextBox();
        drawCenteredString(fontRendererObj, title, x + 100, y - 16, ColorGeneral.LABEL);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        textField.updateCursorCounter();
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        if (textField.isFocused()) {
            textField.textboxKeyTyped(typedChar, keyCode);
        }
    }

}
