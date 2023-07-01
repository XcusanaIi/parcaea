package io.github.xcusanaii.parcaea.event.handler.tick;

import io.github.xcusanaii.parcaea.model.Chart;
import io.github.xcusanaii.parcaea.model.KeyBinds;
import io.github.xcusanaii.parcaea.model.config.CfgGeneral;
import io.github.xcusanaii.parcaea.model.input.InputTick;
import io.github.xcusanaii.parcaea.model.input.UnstableMouseNote;
import io.github.xcusanaii.parcaea.model.note.KeyNote;
import io.github.xcusanaii.parcaea.model.note.KeyNoteHead;
import io.github.xcusanaii.parcaea.model.note.MouseNote;
import net.minecraft.client.Minecraft;
import java.util.List;

import static io.github.xcusanaii.parcaea.model.input.InputStat.*;

public abstract class NoteHandler {

    public static int tickI = 0;
    public static boolean isPlaying = false;
    public static boolean isWaitingInput = false;
    
    public static int lastUnstableMouseIndex = -1;
    public static boolean lockLastInput = false;
    public static boolean isMissed = false;
    public static Rating rating = Rating.ALL_PERFECT;

    public static String debugInfo = "";

    private static double oldYaw = 0.0;
    private static double newYaw = 0.0;

    private static final Minecraft mc = Minecraft.getMinecraft();

    public void onClientTickPre() {
        if (!CfgGeneral.enableChart) return;
        if (KeyBinds.keyRestartChart.isPressed()) {
            if (Chart.selectedChart != null){
                onRestartChart();
            }
        }

        if (isPlaying && Chart.selectedChart != null){
            onPlayTick();
        }

        if (isAnyKeyFired() && isWaitingInput && Chart.selectedChart != null) {
            onStartPlay();
        }
    }

    public static void syncInputStatDYaw() {
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
        isKeyDown.dYaw = dYaw;
    }

    private void onStartPlay() {
        isPlaying = true;
        isWaitingInput = false;
        mousePosPercent = Chart.selectedChart.mouseTicks.get(0).posPercent;
        checkInputAtTick(0);
    }

    private void onPlayTick() {
        tickI++;
        if (tickI >= Chart.selectedChart.keyTicks.size()) {
            isPlaying = false;
            calculateNotePos();
            return;
        }
        checkInputAtTick(tickI);
        calculateNotePos();
    }

    public void onRestartChart(){
        tickI = 0;
        isWaitingInput = true;
        isPlaying = false;
        rating = Rating.ALL_PERFECT;
        isMissed = false;
        yawRange = Chart.selectedChart.yawRange;
        mousePosPercent = Chart.selectedChart.mouseTicks.get(0).posPercent;
        initNotePos();
        if (CfgGeneral.enableLastInput && !lockLastInput) {
            lastInput.clear();
        }
        lastUnstableMouses.clear();
    }

    private void checkInputAtTick(int tickI) {
        List<List<KeyNote>> keyTicks = Chart.selectedChart.keyTicks;
        List<MouseNote> mouseTicks = Chart.selectedChart.mouseTicks;
        if (tickI < 0 || tickI >= keyTicks.size()) return;
        if (CfgGeneral.enableLastInput && !lockLastInput) addLastInput();
        for (int i = 0; i < 7; i++) {
            if (keyTicks.get(tickI).get(i) instanceof KeyNoteHead) {
                SoundHandler.playSoundEffect(i);
            }
            if (i != InputTick.JUMP && i != InputTick.SPRINT) {
                if (!isMissed && (isKeyDown.keyList[i] ^ (keyTicks.get(tickI).get(i) != null))) {
                    miss();
                }
            }
        }
        if (!isMissed && (keyTicks.get(tickI).get(InputTick.JUMP) instanceof KeyNoteHead) && !isKeyFired.jump()) {
            miss();
        }
        if (!isMissed && (keyTicks.get(tickI).get(InputTick.SPRINT) instanceof KeyNoteHead) && !isKeyFired.sprint()) {
            miss();
        }
        if (CfgGeneral.enableSnake && !Chart.selectedChart.isNoTurn) {
            if (mouseTicks.get(tickI).toleranceDYaw == 0.0D) {
                if (mousePosPercent != mouseTicks.get(tickI).posPercent) {
                    rating = Rating.A_MISS;
                    lastUnstableMouses.add(new UnstableMouseNote(tickI, UnstableMouseNote.Type.BOTH));
                }
            }else {
                double toleranceDYaw = mouseTicks.get(tickI).toleranceDYaw == -1.0D ? CfgGeneral.toleranceFactor : mouseTicks.get(tickI).toleranceDYaw;
                double dDYaw = (mousePosPercent - mouseTicks.get(tickI).posPercent) * yawRange;
                if (Math.abs(dDYaw) > toleranceDYaw) {
                    if (rating == Rating.ALL_PERFECT) {
                        rating = Rating.FULL_COMBO;
                    }
                    lastUnstableMouses.add(new UnstableMouseNote(tickI, dDYaw > 0.0D ? UnstableMouseNote.Type.RIGHT : UnstableMouseNote.Type.LEFT));
                }
            }
        }
    }

    public void onClientTickPost() {

    }

    private static void miss() {
        rating = Rating.A_MISS;
        isMissed = true;
    }

    public abstract void calculateNotePos();

    public abstract void initNotePos();

    public enum Rating {
        ALL_PERFECT,
        FULL_COMBO,
        A_MISS
    }
}
