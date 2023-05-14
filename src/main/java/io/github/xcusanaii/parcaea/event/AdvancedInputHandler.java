package io.github.xcusanaii.parcaea.event;

import io.github.xcusanaii.parcaea.Parcaea;
import io.github.xcusanaii.parcaea.model.color.ColorGeneral;
import io.github.xcusanaii.parcaea.render.InfoHud;
import io.github.xcusanaii.parcaea.util.KeyMouse;
import io.github.xcusanaii.parcaea.util.math.Vec2d;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;

public class AdvancedInputHandler {

    private static final Minecraft mc = Minecraft.getMinecraft();

    public static boolean keyIntenseSpacePressed;

    private static KeyBinding keyBindSprint;
    private static KeyBinding keyBindJump;

    private static boolean intenseSpaceLeftPressedPre = false;
    private static boolean intenseSpaceRightPressedPre = false;

    private static int sprintKeyCodePre = 0;

    private static boolean invertSprint;

    public void onClientTickPre() {
        keyBindSprint = mc.gameSettings.keyBindSprint;
        if (Parcaea.keyToggleInvertSprint.isPressed()) {
            invertSprint = !invertSprint;
            if (invertSprint) {
                sprintKeyCodePre = keyBindSprint.getKeyCode();
                keyBindSprint.setKeyCode(-1);
                Parcaea.keyInvertSprint.setKeyCode(sprintKeyCodePre);
                KeyBinding.resetKeyBindingArrayAndHash();
                KeyBinding.setKeyBindState(keyBindSprint.getKeyCode(), true);
            }else {
                keyBindSprint.setKeyCode(sprintKeyCodePre);
                Parcaea.keyInvertSprint.setKeyCode(Parcaea.keyInvertSprint.getKeyCodeDefault());
                KeyBinding.resetKeyBindingArrayAndHash();
                KeyBinding.setKeyBindState(keyBindSprint.getKeyCode(), false);
            }
            InfoHud.infoDisplayList.add(new InfoHud.InfoDisplay(
                    new Vec2d(0.5, 0.75),
                    I18n.format("txt.invert_sprint"),
                    invertSprint ? ColorGeneral.AQUA : ColorGeneral.WHITE,
                    20,
                    2.0f
                    )
            );
        }
    }

    public static void onRenderTickPost() {
        if (mc.currentScreen != null) return;
        keyBindJump = mc.gameSettings.keyBindJump;
        if (KeyMouse.isKeyOrMouseDown(Parcaea.keyIntenseSpaceLeft.getKeyCode()) && !keyIntenseSpacePressed && !intenseSpaceLeftPressedPre) {
            keyIntenseSpacePressed = true;
//            KeyBinding.setKeyBindState(keyBindJump.getKeyCode(), true);
            TickHandler.advInputKeyBindJumpPressed = true;
        }
        intenseSpaceLeftPressedPre = KeyMouse.isKeyOrMouseDown(Parcaea.keyIntenseSpaceLeft.getKeyCode());
        if (KeyMouse.isKeyOrMouseDown(Parcaea.keyIntenseSpaceRight.getKeyCode()) && !keyIntenseSpacePressed && !intenseSpaceRightPressedPre) {
            keyIntenseSpacePressed = true;
//            KeyBinding.setKeyBindState(keyBindJump.getKeyCode(), true);
            TickHandler.advInputKeyBindJumpPressed = true;
        }
        intenseSpaceRightPressedPre = KeyMouse.isKeyOrMouseDown(Parcaea.keyIntenseSpaceRight.getKeyCode());

        KeyBinding keyBindForward = mc.gameSettings.keyBindForward;
        KeyBinding keyBindLeft = mc.gameSettings.keyBindLeft;
        KeyBinding keyBindBack = mc.gameSettings.keyBindBack;
        KeyBinding keyBindRight = mc.gameSettings.keyBindRight;

        if (Parcaea.keyDoubleTapWA.getKeyCode() != 0) {
//            KeyBinding.setKeyBindState(keyBindForward.getKeyCode(), false);
//            KeyBinding.setKeyBindState(keyBindLeft.getKeyCode(), false);
            TickHandler.advInputKeyBindForwardPressed = false;
            TickHandler.advInputKeyBindLeftPressed = false;
        }
        if (Parcaea.keyDoubleTapWD.getKeyCode() != 0) {
//            KeyBinding.setKeyBindState(keyBindForward.getKeyCode(), false);
//            KeyBinding.setKeyBindState(keyBindRight.getKeyCode(), false);
            TickHandler.advInputKeyBindForwardPressed = false;
            TickHandler.advInputKeyBindRightPressed = false;
        }
        if (Parcaea.keyDoubleTapSA.getKeyCode() != 0) {
//            KeyBinding.setKeyBindState(keyBindBack.getKeyCode(), false);
//            KeyBinding.setKeyBindState(keyBindLeft.getKeyCode(), false);
            TickHandler.advInputKeyBindBackPressed = false;
            TickHandler.advInputKeyBindLeftPressed = false;
        }
        if (Parcaea.keyDoubleTapSD.getKeyCode() != 0) {
//            KeyBinding.setKeyBindState(keyBindBack.getKeyCode(), false);
//            KeyBinding.setKeyBindState(keyBindRight.getKeyCode(), false);
            TickHandler.advInputKeyBindBackPressed = false;
            TickHandler.advInputKeyBindRightPressed = false;
        }

        if (Parcaea.keyDoubleTapWA.getKeyCode() != 0 && KeyMouse.isKeyOrMouseDown(Parcaea.keyDoubleTapWA.getKeyCode())) {
//            KeyBinding.setKeyBindState(keyBindForward.getKeyCode(), true);
//            KeyBinding.setKeyBindState(keyBindLeft.getKeyCode(), true);
            TickHandler.advInputKeyBindForwardPressed = true;
            TickHandler.advInputKeyBindLeftPressed = true;
        }
        if (Parcaea.keyDoubleTapWD.getKeyCode() != 0 && KeyMouse.isKeyOrMouseDown(Parcaea.keyDoubleTapWD.getKeyCode())) {
//            KeyBinding.setKeyBindState(keyBindForward.getKeyCode(), true);
//            KeyBinding.setKeyBindState(keyBindRight.getKeyCode(), true);
            TickHandler.advInputKeyBindForwardPressed = true;
            TickHandler.advInputKeyBindRightPressed = true;
        }
        if (Parcaea.keyDoubleTapSA.getKeyCode() != 0 && KeyMouse.isKeyOrMouseDown(Parcaea.keyDoubleTapSA.getKeyCode())) {
//            KeyBinding.setKeyBindState(keyBindBack.getKeyCode(), true);
//            KeyBinding.setKeyBindState(keyBindLeft.getKeyCode(), true);
            TickHandler.advInputKeyBindBackPressed = true;
            TickHandler.advInputKeyBindLeftPressed = true;
        }
        if (Parcaea.keyDoubleTapSD.getKeyCode() != 0 && KeyMouse.isKeyOrMouseDown(Parcaea.keyDoubleTapSD.getKeyCode())) {
//            KeyBinding.setKeyBindState(keyBindBack.getKeyCode(), true);
//            KeyBinding.setKeyBindState(keyBindRight.getKeyCode(), true);
            TickHandler.advInputKeyBindBackPressed = true;
            TickHandler.advInputKeyBindRightPressed = true;
        }

        if (invertSprint) {
//            KeyBinding.setKeyBindState(keyBindSprint.getKeyCode(), !KeyMouse.isKeyOrMouseDown(Parcaea.keyInvertSprint.getKeyCode()));
            TickHandler.advInputKeyBindSprintPressed = !KeyMouse.isKeyOrMouseDown(Parcaea.keyInvertSprint.getKeyCode());
        }
    }

    public void onClientTickPost() {
        if (keyIntenseSpacePressed) {
            KeyBinding.setKeyBindState(keyBindJump.getKeyCode(), false);
            keyIntenseSpacePressed = false;
        }
    }

    public void tweakSprint() {
        if (invertSprint) {
            mc.gameSettings.keyBindSprint.setKeyCode(sprintKeyCodePre);
            KeyBinding.resetKeyBindingArrayAndHash();
            invertSprint = false;
        }
    }
}
