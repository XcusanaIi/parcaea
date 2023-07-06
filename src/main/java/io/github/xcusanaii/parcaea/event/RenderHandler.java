package io.github.xcusanaii.parcaea.event;

import io.github.xcusanaii.parcaea.Parcaea;
import io.github.xcusanaii.parcaea.event.handler.AdvInputHandler;
import io.github.xcusanaii.parcaea.event.handler.render.BongoCapooHandler;
import io.github.xcusanaii.parcaea.model.input.InputStat;
import io.github.xcusanaii.parcaea.model.config.CfgGeneral;
import io.github.xcusanaii.parcaea.render.AbsHud;
import io.github.xcusanaii.parcaea.render.gui.hudmenu.AGuiHudMenu;
import io.github.xcusanaii.parcaea.render.hud.BasicHud;
import io.github.xcusanaii.parcaea.render.InfoHud;
import io.github.xcusanaii.parcaea.util.io.KeyMouse;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RenderHandler {

    private static final Minecraft mc = Minecraft.getMinecraft();

    public static AbsHud hud;
    private static final InfoHud INFO_HUD = new InfoHud(mc);

    private static double oldYaw = 0.0;
    private static double newYaw = 0.0;

    @SubscribeEvent
    public void onRenderGameOverlayEventPost(RenderGameOverlayEvent.Post event) {
        if (!CfgGeneral.enableParcaea || event.type != RenderGameOverlayEvent.ElementType.EXPERIENCE) return;
        syncMouseInputStat();
        syncIsKeyDownExact();
        AdvInputHandler.onRenderGameOverlayEventPost();
        if (CfgGeneral.enableChart && (mc.currentScreen == null || mc.currentScreen instanceof AGuiHudMenu)) hud.draw(event.partialTicks);
        INFO_HUD.draw();
        BongoCapooHandler.onRenderGameOverlayEventPost(event.partialTicks);
    }

    public static void setHud(AbsHud hud) {
        RenderHandler.hud = hud;
        RenderHandler.hud.setNoteHandler();
    }

    private static void syncIsKeyDownExact() {
        for (int i = 0; i < 7; i++) {
            InputStat.isKeyDownExact.keyList[i] = KeyMouse.isKeyOrMouseDown(Parcaea.PK_KEY_BINDS[i].getKeyCode());
            if (!InputStat.isKeyDownExact.keyList[i]) {
                InputStat.keyExactPressTime[i] = 0;
            }else {
                InputStat.keyExactPressTime[i]++;
            }
        }
        long leastPressTimeLeft = Long.MAX_VALUE;
        for (int i = 0; i < 4; i++) {
            if (InputStat.keyExactPressTime[i] != 0 && leastPressTimeLeft > InputStat.keyExactPressTime[i]) {
                leastPressTimeLeft = InputStat.keyExactPressTime[i];
            }
        }
        int indexLeft = -1;
        if (leastPressTimeLeft != 0) {
            for (int i = 0; i < 4; i++) {
                if (leastPressTimeLeft == InputStat.keyExactPressTime[i]) {
                    indexLeft = i;
                    break;
                }
            }
        }
        InputStat.lastKeyDownExactLeft = indexLeft;

        long leastPressTimeRight = Long.MAX_VALUE;
        for (int i = 4; i < 7; i++) {
            if (InputStat.keyExactPressTime[i] != 0 && leastPressTimeRight > InputStat.keyExactPressTime[i]) {
                leastPressTimeRight = InputStat.keyExactPressTime[i];
            }
        }
        int indexRight = -1;
        if (leastPressTimeRight != 0) {
            for (int i = 4; i < 7; i++) {
                if (leastPressTimeRight == InputStat.keyExactPressTime[i]) {
                    indexRight = i;
                    break;
                }
            }
        }
        InputStat.lastKeyDownExactRight = indexRight;
    }

    private static void syncMouseInputStat() {
        InputStat.mousePosPercentPre = InputStat.mousePosPercent;
        double yaw = mc.thePlayer.rotationYaw;
        yaw %= 360.0;
        if (yaw < 0) yaw += 360.0;
        oldYaw = newYaw;
        newYaw = yaw;
        double dYaw;
        if (Math.abs(oldYaw - newYaw) > 180.0) {
            if (oldYaw < newYaw) {
                dYaw = newYaw - (oldYaw + 360.0);
            }
            else {
                dYaw = newYaw + 360.0 - oldYaw;
            }
        }else {
            dYaw = newYaw - oldYaw;
        }
        InputStat.mousePosPercent += dYaw / InputStat.yawRange;
    }

    static {
        hud = new BasicHud();
        hud.setNoteHandler();
    }
}
