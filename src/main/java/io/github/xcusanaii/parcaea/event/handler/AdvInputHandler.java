package io.github.xcusanaii.parcaea.event.handler;

import io.github.xcusanaii.parcaea.event.TickHandler;
import io.github.xcusanaii.parcaea.model.KeyBinds;
import io.github.xcusanaii.parcaea.model.color.ColorGeneral;
import io.github.xcusanaii.parcaea.render.InfoHud;
import io.github.xcusanaii.parcaea.util.KeyMouse;
import io.github.xcusanaii.parcaea.util.math.Vec2d;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;

import static io.github.xcusanaii.parcaea.model.KeyBinds.keyBindSprintMap;

public class AdvInputHandler {

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
        if (KeyBinds.keyToggleInvertSprint.isPressed()) {
            invertSprint = !invertSprint;
            if (invertSprint) {
                sprintKeyCodePre = keyBindSprint.getKeyCode();
                keyBindSprint.setKeyCode(keyBindSprintMap);
                KeyBinds.keyInvertSprint.setKeyCode(sprintKeyCodePre);
                KeyBinding.resetKeyBindingArrayAndHash();
                KeyBinding.setKeyBindState(keyBindSprint.getKeyCode(), true);
            }else {
                keyBindSprint.setKeyCode(sprintKeyCodePre);
                KeyBinds.keyInvertSprint.setKeyCode(KeyBinds.keyInvertSprint.getKeyCodeDefault());
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

    public static void onRenderGameOverlayEventPost() {
        if (mc.currentScreen != null) return;
        keyBindJump = mc.gameSettings.keyBindJump;
        if (KeyMouse.isKeyOrMouseDown(KeyBinds.keyIntenseSpaceLeft.getKeyCode()) && !keyIntenseSpacePressed && !intenseSpaceLeftPressedPre) {
            keyIntenseSpacePressed = true;
            TickHandler.advInputKeyBindJumpPressed = true;
        }
        intenseSpaceLeftPressedPre = KeyMouse.isKeyOrMouseDown(KeyBinds.keyIntenseSpaceLeft.getKeyCode());
        if (KeyMouse.isKeyOrMouseDown(KeyBinds.keyIntenseSpaceRight.getKeyCode()) && !keyIntenseSpacePressed && !intenseSpaceRightPressedPre) {
            keyIntenseSpacePressed = true;
            TickHandler.advInputKeyBindJumpPressed = true;
        }
        intenseSpaceRightPressedPre = KeyMouse.isKeyOrMouseDown(KeyBinds.keyIntenseSpaceRight.getKeyCode());

        if (KeyBinds.keyDoubleTapWA.getKeyCode() != 0) {
            TickHandler.advInputKeyBindForwardPressed = false;
            TickHandler.advInputKeyBindLeftPressed = false;
        }
        if (KeyBinds.keyDoubleTapWD.getKeyCode() != 0) {
            TickHandler.advInputKeyBindForwardPressed = false;
            TickHandler.advInputKeyBindRightPressed = false;
        }
        if (KeyBinds.keyDoubleTapSA.getKeyCode() != 0) {
            TickHandler.advInputKeyBindBackPressed = false;
            TickHandler.advInputKeyBindLeftPressed = false;
        }
        if (KeyBinds.keyDoubleTapSD.getKeyCode() != 0) {
            TickHandler.advInputKeyBindBackPressed = false;
            TickHandler.advInputKeyBindRightPressed = false;
        }

        if (KeyBinds.keyDoubleTapWA.getKeyCode() != 0 && KeyMouse.isKeyOrMouseDown(KeyBinds.keyDoubleTapWA.getKeyCode())) {
            TickHandler.advInputKeyBindForwardPressed = true;
            TickHandler.advInputKeyBindLeftPressed = true;
        }
        if (KeyBinds.keyDoubleTapWD.getKeyCode() != 0 && KeyMouse.isKeyOrMouseDown(KeyBinds.keyDoubleTapWD.getKeyCode())) {
            TickHandler.advInputKeyBindForwardPressed = true;
            TickHandler.advInputKeyBindRightPressed = true;
        }
        if (KeyBinds.keyDoubleTapSA.getKeyCode() != 0 && KeyMouse.isKeyOrMouseDown(KeyBinds.keyDoubleTapSA.getKeyCode())) {
            TickHandler.advInputKeyBindBackPressed = true;
            TickHandler.advInputKeyBindLeftPressed = true;
        }
        if (KeyBinds.keyDoubleTapSD.getKeyCode() != 0 && KeyMouse.isKeyOrMouseDown(KeyBinds.keyDoubleTapSD.getKeyCode())) {
            TickHandler.advInputKeyBindBackPressed = true;
            TickHandler.advInputKeyBindRightPressed = true;
        }

        if (invertSprint) {
            TickHandler.advInputKeyBindSprintPressed = !KeyMouse.isKeyOrMouseDown(KeyBinds.keyInvertSprint.getKeyCode());
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
