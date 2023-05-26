package io.github.xcusanaii.parcaea.event;

import io.github.xcusanaii.parcaea.event.handler.AdvInputHandler;
import io.github.xcusanaii.parcaea.model.input.InputStat;
import io.github.xcusanaii.parcaea.model.config.CfgGeneral;
import io.github.xcusanaii.parcaea.render.AbsHud;
import io.github.xcusanaii.parcaea.render.gui.hudmenu.AGuiHudMenu;
import io.github.xcusanaii.parcaea.render.hud.BasicHud;
import io.github.xcusanaii.parcaea.render.InfoHud;
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
        if (!CfgGeneral.enableMod || event.type != RenderGameOverlayEvent.ElementType.EXPERIENCE) return;
        syncMouseInputStat();
        AdvInputHandler.onRenderGameOverlayEventPost();
        INFO_HUD.draw();
        if (CfgGeneral.enableParcaea && (mc.currentScreen == null || mc.currentScreen instanceof AGuiHudMenu)) hud.draw(event.partialTicks);
    }

    public static void setHud(AbsHud hud) {
        RenderHandler.hud = hud;
        RenderHandler.hud.setNoteHandler();
    }

    private static void syncMouseInputStat() {
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
