package io.github.xcusanaii.parcaea.event;

import io.github.xcusanaii.parcaea.Parcaea;
import io.github.xcusanaii.parcaea.model.config.CfgGeneral;
import io.github.xcusanaii.parcaea.model.input.InputStat;
import io.github.xcusanaii.parcaea.render.gui.GuiMenu;
import io.github.xcusanaii.parcaea.util.KeyMouse;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class TickHandler {

    private static final Minecraft mc = Minecraft.getMinecraft();
    public static NoteHandler noteHandler = null;
    public static InfoHandler infoHandler = new InfoHandler();
    public static AdvancedInputHandler advancedInputHandler = new AdvancedInputHandler();
    public static CommandMacroHandler commandMacroHandler = new CommandMacroHandler();

    public static boolean advInputKeyBindJumpPressed = false;
    public static boolean advInputKeyBindForwardPressed = false;
    public static boolean advInputKeyBindLeftPressed = false;
    public static boolean advInputKeyBindRightPressed = false;
    public static boolean advInputKeyBindBackPressed = false;
    public static boolean advInputKeyBindSprintPressed = false;

    @SubscribeEvent
    public void onClientTick(ClientTickEvent event) {
        if (mc.thePlayer == null || !CfgGeneral.enableMod) return;
        if (event.phase == TickEvent.Phase.END) {
            advancedInputHandler.onClientTickPost();
            noteHandler.onClientTickPost();
            infoHandler.onClientTickPost();
            syncKeyInputStatOnClientTickPost();
        }else {
            syncKeyInputStatOnClientTickPre();
            advancedInputHandler.onClientTickPre();
            noteHandler.onClientTickPre();
            commandMacroHandler.onClientTickPre();
            if (Parcaea.keyMenu.isPressed()) {
                mc.displayGuiScreen(new GuiMenu());
            }
        }
    }

    public static void setNoteHandler(NoteHandler noteHandler) {
        TickHandler.noteHandler = noteHandler;
    }

    private static void syncKeyInputStatOnClientTickPre() {
        if (mc.currentScreen != null) return;
        for (int i = 0; i < 7; i++) {
            KeyBinding.setKeyBindState(Parcaea.PK_KEY_BINDS[i].getKeyCode(), KeyMouse.isKeyOrMouseDown(Parcaea.PK_KEY_BINDS[i].getKeyCode()));
        }
        KeyBinding keyBindForward = mc.gameSettings.keyBindForward;
        KeyBinding keyBindLeft = mc.gameSettings.keyBindLeft;
        KeyBinding keyBindBack = mc.gameSettings.keyBindBack;
        KeyBinding keyBindRight = mc.gameSettings.keyBindRight;
        KeyBinding keyBindJump = mc.gameSettings.keyBindJump;
        KeyBinding keyBindSprint = mc.gameSettings.keyBindSprint;
        if (advInputKeyBindJumpPressed) {
            KeyBinding.setKeyBindState(keyBindJump.getKeyCode(), true);
        }
        if (advInputKeyBindSprintPressed) {
            KeyBinding.setKeyBindState(keyBindSprint.getKeyCode(), true);
        }
        if (advInputKeyBindForwardPressed) {
            KeyBinding.setKeyBindState(keyBindForward.getKeyCode(), true);
        }
        if (advInputKeyBindLeftPressed) {
            KeyBinding.setKeyBindState(keyBindLeft.getKeyCode(), true);
        }
        if (advInputKeyBindBackPressed) {
            KeyBinding.setKeyBindState(keyBindBack.getKeyCode(), true);
        }
        if (advInputKeyBindRightPressed) {
            KeyBinding.setKeyBindState(keyBindRight.getKeyCode(), true);
        }
        for (int i = 0; i < 7; i++) {
            InputStat.isKeyFired.keyList[i] = Parcaea.PK_KEY_BINDS[i].isKeyDown() && !InputStat.isKeyDownPre.keyList[i];
            InputStat.isKeyDown.keyList[i] = Parcaea.PK_KEY_BINDS[i].isKeyDown();
            InputStat.isKeyDownPre.keyList[i] = InputStat.isKeyDown.keyList[i];
        }
    }

    private static void syncKeyInputStatOnClientTickPost() {
        advInputKeyBindJumpPressed = false;
        advInputKeyBindForwardPressed = false;
        advInputKeyBindLeftPressed = false;
        advInputKeyBindRightPressed = false;
        advInputKeyBindBackPressed = false;
        advInputKeyBindSprintPressed = false;
    }
}
