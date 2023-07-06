package io.github.xcusanaii.parcaea.util.io;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class KeyMouse {

    public static boolean isKeyOrMouseDown(int keyBindCode) {
        if (keyBindCode >= -100 && keyBindCode <= -91) return Mouse.isButtonDown(keyBindCode + 100);
        else if (keyBindCode > 0 && keyBindCode <= 255) return Keyboard.isKeyDown(keyBindCode);
        else return false;
    }

    public static String getKeyName(KeyBinding keyBind) {
        if (keyBind.getKeyCode() < 0) {
            if (keyBind.getKeyCode() == -100) {
                return I18n.format("txt.left_click");
            } else if (keyBind.getKeyCode() == -99) {
                return I18n.format("txt.right_click");
            } else if (keyBind.getKeyCode() == -98) {
                return I18n.format("txt.middle_click");
            } else if (keyBind.getKeyCode() == -97) {
                return I18n.format("txt.button_4");
            } else if (keyBind.getKeyCode() == -96) {
                return I18n.format("txt.button_5");
            } else if (keyBind.getKeyCode() == -95) {
                return I18n.format("txt.button_6");
            } else if (keyBind.getKeyCode() == -94) {
                return I18n.format("txt.button_7");
            } else if (keyBind.getKeyCode() == -93) {
                return I18n.format("txt.button_8");
            } else if (keyBind.getKeyCode() == -92) {
                return I18n.format("txt.button_9");
            } else if (keyBind.getKeyCode() == -91) {
                return I18n.format("txt.button_10");
            } else {
                return I18n.format("txt.unknown");
            }
        } else if (keyBind.getKeyCode() == 0){
            return I18n.format("txt.key_none");
        } else if (keyBind.getKeyCode() <= 255){
            return Keyboard.getKeyName(keyBind.getKeyCode());
        } else {
            return I18n.format("txt.unknown");
        }
    }
}
