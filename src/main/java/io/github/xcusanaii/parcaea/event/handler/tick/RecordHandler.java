package io.github.xcusanaii.parcaea.event.handler.tick;

import io.github.xcusanaii.parcaea.Parcaea;
import io.github.xcusanaii.parcaea.model.KeyBinds;
import io.github.xcusanaii.parcaea.model.color.ColorGeneral;
import io.github.xcusanaii.parcaea.model.input.InputStat;
import io.github.xcusanaii.parcaea.render.InfoHud;
import io.github.xcusanaii.parcaea.util.math.Vec2d;
import net.minecraft.client.Minecraft;

public class RecordHandler {

    public static boolean isInRecord = false;

    private static boolean isRecording = false;
    private static boolean isWaitingInput = false;
    private static int tickI = 0;

    private static final Minecraft mc = Minecraft.getMinecraft();

    public void onClientTickPre() {
        InfoHud.infoDisplayList.add(new InfoHud.InfoDisplay(
                new Vec2d(0.1, 0.1),
                String.valueOf(tickI),
                ColorGeneral.AQUA,
                2,
                2.0f
        ));
        if (KeyBinds.keyRestartChart.isPressed()) {
            onStartRecord();
        }
        if (isWaitingInput && InputStat.isAnyKeyFired()) {
            isWaitingInput = false;
            isRecording = true;
        }
        if (isRecording) {
            if (mc.currentScreen != null) {
                isRecording = false;
            }
            InputStat.addLastInput();
            tickI++;
        }
    }

    public static void onStartRecord() {
        InputStat.lastInput.clear();
        isRecording = false;
        isWaitingInput = true;
        tickI = 0;
    }

    public void onClientTickPost() {

    }
}
