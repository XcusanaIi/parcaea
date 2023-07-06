package io.github.xcusanaii.parcaea.event;

import io.github.xcusanaii.parcaea.Parcaea;
import io.github.xcusanaii.parcaea.event.handler.*;
import io.github.xcusanaii.parcaea.event.handler.tick.*;
import io.github.xcusanaii.parcaea.model.KeyBinds;
import io.github.xcusanaii.parcaea.model.config.CfgGeneral;
import io.github.xcusanaii.parcaea.model.input.InputStat;
import io.github.xcusanaii.parcaea.model.segment.Segment;
import io.github.xcusanaii.parcaea.render.entity.CoordMarker;
import io.github.xcusanaii.parcaea.render.gui.GuiIndex;
import io.github.xcusanaii.parcaea.util.io.KeyMouse;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class TickHandler {

    private static final Minecraft mc = Minecraft.getMinecraft();
    public static NoteHandler noteHandler = null;
    public static InfoHandler infoHandler = new InfoHandler();
    public static AdvInputHandler advInputHandler = new AdvInputHandler();
    public static CommandMacroHandler commandMacroHandler = new CommandMacroHandler();
    public static RecordHandler recordHandler = new RecordHandler();
    public static AutoCoordHandler autoCoordHandler = new AutoCoordHandler();
    public static LandingBlockHandler landingBlockHandler = new LandingBlockHandler();
    public static BeatHandler beatHandler = new BeatHandler();

    public static boolean advInputKeyBindJumpPressed = false;
    public static boolean advInputKeyBindForwardPressed = false;
    public static boolean advInputKeyBindLeftPressed = false;
    public static boolean advInputKeyBindRightPressed = false;
    public static boolean advInputKeyBindBackPressed = false;
    public static boolean advInputKeyBindSprintPressed = false;

    private static boolean autoRightClicked = false;

    public static boolean enAutoClearPB = true;

    @SubscribeEvent
    public void onClientTick(ClientTickEvent event) {
        if (mc.thePlayer == null || mc.theWorld == null || !CfgGeneral.enableParcaea) return;
        if (event.phase == TickEvent.Phase.END) {
            advInputHandler.onClientTickPost();
            if (RecordHandler.isInRecord) {
                recordHandler.onClientTickPost();
            }else {
                noteHandler.onClientTickPost();
            }
            if (autoRightClicked) {
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
                autoRightClicked = false;
            }
            infoHandler.onClientTickPost();
            landingBlockHandler.onClientTickPost();
            syncKeyInputStatOnClientTickPost();
        }else {
            syncKeyInputStatOnClientTickPre();
            advInputHandler.onClientTickPre();
            NoteHandler.syncInputStatDYaw();
            if (RecordHandler.isInRecord) {
                recordHandler.onClientTickPre();
            }else {
                noteHandler.onClientTickPre();
            }
            if (KeyBinds.keyIndex.isPressed()) {
                mc.displayGuiScreen(new GuiIndex());
            }
            if (KeyBinds.keyRestartChart.isKeyDown() && CfgGeneral.enableAutoPos && mc.currentScreen == null && !autoRightClicked) {
                autoRightClicked = true;
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
                if (enAutoClearPB && mc.thePlayer != null) {
                    ClientCommandHandler.instance.executeCommand(mc.thePlayer, "/mpk clearpb");
                }
            }
            if (KeyBinds.keyAutoCoord.isPressed()) {
                CoordMarker nearestCoordMarker = Segment.findNearestCoordMarker();
                if (nearestCoordMarker != null) {
                    AutoCoordHandler.coordStrategy = nearestCoordMarker.coordStrategy;
                    AutoCoordHandler.onStartAC();
                }
            }
            autoCoordHandler.onClientTickPre();

            beatHandler.onClientTickPre();

//            InfoHud.infoDisplayList.add(new InfoHud.InfoDisplay(
//                    new Vec2d(0.1D, 0.1D),
//                    String.valueOf(mc.thePlayer.rotationYaw),
//                    ColorGeneral.LABEL,
//                    2,
//                    1.0F
//            ));
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
