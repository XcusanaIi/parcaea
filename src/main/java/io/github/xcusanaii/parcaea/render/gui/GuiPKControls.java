package io.github.xcusanaii.parcaea.render.gui;

import io.github.xcusanaii.parcaea.Parcaea;
import io.github.xcusanaii.parcaea.model.KeyBinds;
import io.github.xcusanaii.parcaea.util.widget.PGuiButton;
import io.github.xcusanaii.parcaea.util.widget.PGuiSlider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiPageButtonList;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlider;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.github.xcusanaii.parcaea.model.KeyBinds.*;
import static io.github.xcusanaii.parcaea.util.KeyMouse.getKeyName;

public class GuiPKControls extends GuiScreen {

    private static final Minecraft mc = Minecraft.getMinecraft();
    private PGuiButton btnW, btnA, btnS, btnD, btnSpace, btnShift, btnCtrl, btnIntenseSpaceLeft, btnIntenseSpaceRight, btnAttack, btnUseItem, btnWA, btnWD, btnSA, btnSD;
    private PGuiButton btnDefault;
    private PGuiButton selectedBtn;
    private PGuiSlider sldMouseSensitivity;
    private List<KeyBinding> keyBinds;
    private static int keyCodeDoubleTapPreWA = 0;
    private static int keyCodeDoubleTapPreWD = 0;
    private static int keyCodeDoubleTapPreSA = 0;
    private static int keyCodeDoubleTapPreSD = 0;
    private int screenWidth = 190;
    private int screenHeight = 195;
    private int x;
    private int y;

    @Override
    public void initGui() {

        selectedBtn = null;
        keyBinds = Arrays.asList(Parcaea.PK_KEY_BINDS);
        keyBinds = new ArrayList<KeyBinding>(keyBinds);
        keyBinds.add(KeyBinds.keyIntenseSpaceLeft);
        keyBinds.add(KeyBinds.keyIntenseSpaceRight);
        keyBinds.add(mc.gameSettings.keyBindAttack);
        keyBinds.add(mc.gameSettings.keyBindUseItem);
        keyBinds.add(KeyBinds.keyDoubleTapWA);
        keyBinds.add(KeyBinds.keyDoubleTapWD);
        keyBinds.add(KeyBinds.keyDoubleTapSA);
        keyBinds.add(KeyBinds.keyDoubleTapSD);

        x = (this.width - screenWidth) / 2;
        y = (this.height -screenHeight) / 2;

        btnDefault = new PGuiButton(15, x, y, 60, 20, I18n.format("txt.default"));
        buttonList.add(btnDefault);

        btnW = new PGuiButton(0, x + 65, y, 60, 20, getKeyName(keyBinds.get(0)));
        buttonList.add(btnW);
        btnA = new PGuiButton(1, x, y + 25, 60, 20, getKeyName(keyBinds.get(1)));
        buttonList.add(btnA);
        btnS = new PGuiButton(2, x + 65, y + 25, 60, 20, getKeyName(keyBinds.get(2)));
        buttonList.add(btnS);
        btnD = new PGuiButton(3, x + 130, y + 25, 60, 20, getKeyName(keyBinds.get(3)));
        buttonList.add(btnD);
        btnSpace = new PGuiButton(4, x, y + 50, 92, 20, getKeyName(keyBinds.get(4)));
        buttonList.add(btnSpace);
        btnShift = new PGuiButton(5, x + 98, y +  50, 92, 20, getKeyName(keyBinds.get(5)));
        buttonList.add(btnShift);
        btnCtrl = new PGuiButton(6, x + 130, y, 60, 20, getKeyName(keyBinds.get(6)));
        buttonList.add(btnCtrl);
        btnIntenseSpaceLeft = new PGuiButton(7, x, y + 75, 92, 20, getKeyName(keyBinds.get(7)));
        buttonList.add(btnIntenseSpaceLeft);
        btnIntenseSpaceRight = new PGuiButton(8, x + 98, y + 75, 92, 20, getKeyName(keyBinds.get(8)));
        buttonList.add(btnIntenseSpaceRight);
        btnAttack = new PGuiButton(9, x, y + 100, 92, 20, getKeyName(keyBinds.get(9)));
        buttonList.add(btnAttack);
        btnUseItem = new PGuiButton(10, x + 98, y + 100, 92, 20, getKeyName(keyBinds.get(10)));
        buttonList.add(btnUseItem);
        btnWA = new PGuiButton(11, x, y + 150, 92, 20, getKeyName(keyBinds.get(11)));
        buttonList.add(btnWA);
        btnWD = new PGuiButton(12, x + 98, y + 150, 92, 20, getKeyName(keyBinds.get(12)));
        buttonList.add(btnWD);
        btnSA = new PGuiButton(13, x, y + 175, 92, 20, getKeyName(keyBinds.get(13)));
        buttonList.add(btnSA);
        btnSD = new PGuiButton(14, x + 98, y + 175, 92, 20, getKeyName(keyBinds.get(14)));
        buttonList.add(btnSD);

        sldMouseSensitivity = new PGuiSlider(new GuiPageButtonList.GuiResponder() {
            @Override
            public void func_175321_a(int p_175321_1_, boolean p_175321_2_) {
            }

            @Override
            public void onTick(int id, float value) {
                mc.gameSettings.mouseSensitivity = value;
            }

            @Override
            public void func_175319_a(int p_175319_1_, String p_175319_2_) {
            }
        }, 100, x, y + 125, I18n.format("txt.sensitivity"), 0.005f, 1.0f, mc.gameSettings.mouseSensitivity, new GuiSlider.FormatHelper() {
            @Override
            public String getText(int id, String name, float value) {
                return name + ": " + Parcaea.DF_0.format(value * 200.0) + "%";
            }
        }, 190);
        buttonList.add(sldMouseSensitivity);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 15) {
            for (KeyBinding keyBind : keyBinds) {
                mc.gameSettings.setOptionKeyBinding(keyBind, keyBind.getKeyCodeDefault());
            }
            syncKeyBinds();
            sldMouseSensitivity.func_175218_a(0.5f, true);
        } else if (selectedBtn == null && button.id != 100) {
            selectedBtn = (PGuiButton) button;
            button.displayString = "<->";
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (selectedBtn != null) {
            if (keyCode == Keyboard.KEY_ESCAPE) {
                mc.gameSettings.setOptionKeyBinding(keyBinds.get(selectedBtn.id), 0);
            }else {
                mc.gameSettings.setOptionKeyBinding(keyBinds.get(selectedBtn.id), keyCode);
            }
            syncKeyBinds();
            selectedBtn = null;
        }else {
            super.keyTyped(typedChar, keyCode);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        int p = Parcaea.CLOSE_SAFE_PADDING;
        if (mouseX < x - p || mouseX > x + screenWidth + p || mouseY < y - p || mouseY > y + screenHeight + p) {
            mc.displayGuiScreen(new GuiMenu());
        }
        if (selectedBtn != null && selectedBtn.id != 100) {
            mc.gameSettings.setOptionKeyBinding(keyBinds.get(selectedBtn.id), -100 + mouseButton);
            syncKeyBinds();
            selectedBtn = null;
        }else {
            super.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    private void syncKeyBinds() {
        if (KeyBinds.keyDoubleTapWA.getKeyCode() != 0 && keyCodeDoubleTapPreWA == 0) {
            keyCodeDoubleTapPreWA = KeyBinds.keyDoubleTapWA.getKeyCode();
            mc.gameSettings.keyBindForward.setKeyCode(keyBindForwardMap);
            mc.gameSettings.keyBindLeft.setKeyCode(keyBindLeftMap);
        }
        if (KeyBinds.keyDoubleTapWA.getKeyCode() == 0 && keyCodeDoubleTapPreWA != 0){
            keyCodeDoubleTapPreWA = 0;
            mc.gameSettings.keyBindForward.setKeyCode(mc.gameSettings.keyBindForward.getKeyCodeDefault());
            mc.gameSettings.keyBindLeft.setKeyCode(mc.gameSettings.keyBindLeft.getKeyCodeDefault());
        }
        if (KeyBinds.keyDoubleTapWD.getKeyCode() != 0 && keyCodeDoubleTapPreWD == 0) {
            keyCodeDoubleTapPreWD = KeyBinds.keyDoubleTapWD.getKeyCode();
            mc.gameSettings.keyBindForward.setKeyCode(keyBindForwardMap);
            mc.gameSettings.keyBindRight.setKeyCode(keyBindRightMap);
        }
        if (KeyBinds.keyDoubleTapWD.getKeyCode() == 0 && keyCodeDoubleTapPreWD != 0){
            keyCodeDoubleTapPreWD = 0;
            mc.gameSettings.keyBindForward.setKeyCode(mc.gameSettings.keyBindForward.getKeyCodeDefault());
            mc.gameSettings.keyBindRight.setKeyCode(mc.gameSettings.keyBindRight.getKeyCodeDefault());
        }
        if (KeyBinds.keyDoubleTapSA.getKeyCode() != 0 && keyCodeDoubleTapPreSA == 0) {
            keyCodeDoubleTapPreSA = KeyBinds.keyDoubleTapSA.getKeyCode();
            mc.gameSettings.keyBindBack.setKeyCode(keyBindBackMap);
            mc.gameSettings.keyBindLeft.setKeyCode(keyBindLeftMap);
        }
        if (KeyBinds.keyDoubleTapSA.getKeyCode() == 0 && keyCodeDoubleTapPreSA != 0){
            keyCodeDoubleTapPreSA = 0;
            mc.gameSettings.keyBindBack.setKeyCode(mc.gameSettings.keyBindBack.getKeyCodeDefault());
            mc.gameSettings.keyBindLeft.setKeyCode(mc.gameSettings.keyBindLeft.getKeyCodeDefault());
        }
        if (KeyBinds.keyDoubleTapSD.getKeyCode() != 0 && keyCodeDoubleTapPreSD == 0) {
            keyCodeDoubleTapPreSD = KeyBinds.keyDoubleTapSD.getKeyCode();
            mc.gameSettings.keyBindBack.setKeyCode(keyBindBackMap);
            mc.gameSettings.keyBindRight.setKeyCode(keyBindRightMap);
        }
        if (KeyBinds.keyDoubleTapSD.getKeyCode() == 0 && keyCodeDoubleTapPreSD != 0){
            keyCodeDoubleTapPreSD = 0;
            mc.gameSettings.keyBindBack.setKeyCode(mc.gameSettings.keyBindBack.getKeyCodeDefault());
            mc.gameSettings.keyBindRight.setKeyCode(mc.gameSettings.keyBindRight.getKeyCodeDefault());
        }


        KeyBinding.resetKeyBindingArrayAndHash();

        btnW.displayString = getKeyName(keyBinds.get(0));
        btnA.displayString = getKeyName(keyBinds.get(1));
        btnS.displayString = getKeyName(keyBinds.get(2));
        btnD.displayString = getKeyName(keyBinds.get(3));
        btnSpace.displayString = getKeyName(keyBinds.get(4));
        btnShift.displayString = getKeyName(keyBinds.get(5));
        btnCtrl.displayString = getKeyName(keyBinds.get(6));
        btnIntenseSpaceLeft.displayString = getKeyName(keyBinds.get(7));
        btnIntenseSpaceRight.displayString = getKeyName(keyBinds.get(8));
        btnAttack.displayString = getKeyName(keyBinds.get(9));
        btnUseItem.displayString = getKeyName(keyBinds.get(10));
        btnWA.displayString = getKeyName(keyBinds.get(11));
        btnWD.displayString = getKeyName(keyBinds.get(12));
        btnSA.displayString = getKeyName(keyBinds.get(13));
        btnSD.displayString = getKeyName(keyBinds.get(14));
    }
}
