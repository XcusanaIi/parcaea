package io.github.xcusanaii.parcaea.model;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class KeyBinds {

    public static final String CATEGORY = "category.parcaea";

    public static final int keyBindSprintMap = -1;
    public static final int keyBindForwardMap = -2;
    public static final int keyBindLeftMap = -3;
    public static final int keyBindBackMap = -4;
    public static final int keyBindRightMap = -5;

    public static final KeyBinding keyTest = new KeyBinding("key.test", Keyboard.KEY_NONE, CATEGORY);
    public static final KeyBinding keyRestartChart = new KeyBinding("key.play_chart", Keyboard.KEY_R, CATEGORY);
    public static final KeyBinding keyIndex = new KeyBinding("key.index", Keyboard.KEY_Z, CATEGORY);
    public static final KeyBinding keyToggleInvertSprint = new KeyBinding("key.toggle_invert_sprint", Keyboard.KEY_X, CATEGORY);
    public static final KeyBinding keyInvertSprint = new KeyBinding("key.invert_sprint", Keyboard.KEY_NONE, CATEGORY);
    public static final KeyBinding keyIntenseSpaceLeft = new KeyBinding("key.intense_space_left", Keyboard.KEY_NONE, CATEGORY);
    public static final KeyBinding keyIntenseSpaceRight = new KeyBinding("key.intense_space_right", Keyboard.KEY_NONE, CATEGORY);
    public static final KeyBinding keyDoubleTapWA = new KeyBinding("key.double_tap_wa", Keyboard.KEY_NONE, CATEGORY);
    public static final KeyBinding keyDoubleTapWD = new KeyBinding("key.double_tap_wd", Keyboard.KEY_NONE, CATEGORY);
    public static final KeyBinding keyDoubleTapSA = new KeyBinding("key.double_tap_sa", Keyboard.KEY_NONE, CATEGORY);
    public static final KeyBinding keyDoubleTapSD = new KeyBinding("key.double_tap_sd", Keyboard.KEY_NONE, CATEGORY);
    public static final KeyBinding keyAutoCoord = new KeyBinding("key.auto_coord", Keyboard.KEY_B, CATEGORY);

    public static void registerKeyBinds() {
        ClientRegistry.registerKeyBinding(keyTest);
        ClientRegistry.registerKeyBinding(keyRestartChart);
        ClientRegistry.registerKeyBinding(keyIndex);
        ClientRegistry.registerKeyBinding(keyToggleInvertSprint);
        ClientRegistry.registerKeyBinding(keyInvertSprint);
        ClientRegistry.registerKeyBinding(keyIntenseSpaceLeft);
        ClientRegistry.registerKeyBinding(keyIntenseSpaceRight);
        ClientRegistry.registerKeyBinding(keyDoubleTapWA);
        ClientRegistry.registerKeyBinding(keyDoubleTapWD);
        ClientRegistry.registerKeyBinding(keyDoubleTapSA);
        ClientRegistry.registerKeyBinding(keyDoubleTapSD);
        ClientRegistry.registerKeyBinding(keyAutoCoord);
    }
}
