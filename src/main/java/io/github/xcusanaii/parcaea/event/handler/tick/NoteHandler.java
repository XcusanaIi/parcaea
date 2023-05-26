package io.github.xcusanaii.parcaea.event.handler.tick;

import io.github.xcusanaii.parcaea.Parcaea;
import io.github.xcusanaii.parcaea.model.Chart;
import io.github.xcusanaii.parcaea.model.KeyBinds;
import io.github.xcusanaii.parcaea.model.config.CfgGeneral;
import io.github.xcusanaii.parcaea.model.input.InputStat;
import io.github.xcusanaii.parcaea.model.input.InputTick;
import io.github.xcusanaii.parcaea.model.note.KeyNote;
import io.github.xcusanaii.parcaea.model.note.KeyNoteHead;
import io.github.xcusanaii.parcaea.model.note.MouseNote;
import net.minecraft.client.Minecraft;
import java.util.List;

public abstract class NoteHandler {

    public static int tickI = 0;
    public static boolean isPlaying = false;
    public static boolean isWaitingInput = false;
    public static int lastUnstableMouseIndex = -1;
    public static boolean lockLastInput = false;
    public static boolean isMissed = false;
    public static Rating rating = Rating.ALL_PERFECT;

    private static final Minecraft mc = Minecraft.getMinecraft();

    public void onClientTickPre() {
        if (!CfgGeneral.enableParcaea) return;
        if (KeyBinds.keyRestartChart.isPressed()) {
            if (CfgGeneral.enable45S) {
                init45S();
            }else if (Chart.selectedChart != null){
                onRestartChart();
            }
        }

        if (isPlaying && Chart.selectedChart != null){
            onPlayTick();
        }

        if (InputStat.isAnyKeyFired() && isWaitingInput && Chart.selectedChart != null) {
            onStartPlay();
        }
    }

    private void init45S() {
        InputStat.yawRange = 180.0D;
        InputStat.mousePosPercent = 0.5D;
    }

    private void onStartPlay() {
        isPlaying = true;
        isWaitingInput = false;
        InputStat.mousePosPercent = Chart.selectedChart.mouseTicks.get(0).posPercent;
        checkInputAtTick(0);
    }

    private void onPlayTick() {
        tickI++;
        if (tickI >= Chart.selectedChart.keyTicks.size()) {
            isPlaying = false;
            calculateNotePos();
            if (rating == Rating.ALL_PERFECT) {
                lastUnstableMouseIndex = -1;
            }
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
        InputStat.yawRange = Chart.selectedChart.yawRange;
        InputStat.mousePosPercent = Chart.selectedChart.mouseTicks.get(0).posPercent;
        initNotePos();
        if (CfgGeneral.enableLastInput && !lockLastInput) InputStat.lastInput.clear();
    }

    private void checkInputAtTick(int tickI) {
        List<List<KeyNote>> keyTicks = Chart.selectedChart.keyTicks;
        List<MouseNote> mouseTicks = Chart.selectedChart.mouseTicks;
        if (tickI < 0 || tickI >= keyTicks.size()) return;
        if (CfgGeneral.enableLastInput && !lockLastInput) InputStat.addLastInput();
        for (int i = 0; i < 7; i++) {
            if (keyTicks.get(tickI).get(i) instanceof KeyNoteHead) {
                SoundHandler.playSoundEffect(i);
            }
            if (i != InputTick.JUMP && i != InputTick.SPRINT) {
                if (!isMissed && (InputStat.isKeyDown.keyList[i] ^ (keyTicks.get(tickI).get(i) != null))) {
                    miss();
                }
            }
        }
        if (!isMissed && (keyTicks.get(tickI).get(InputTick.JUMP) instanceof KeyNoteHead) && !InputStat.isKeyFired.jump()) {
            miss();
        }
        if (!isMissed && (keyTicks.get(tickI).get(InputTick.SPRINT) instanceof KeyNoteHead) && !InputStat.isKeyFired.sprint()) {
            miss();
        }
        if (CfgGeneral.enableSnake && !Chart.selectedChart.isNoTurn && rating == Rating.ALL_PERFECT && Math.abs(mouseTicks.get(tickI).posPercent - InputStat.mousePosPercent) > CfgGeneral.toleranceFactor) {
            rating = Rating.FULL_COMBO;
            lastUnstableMouseIndex = tickI;
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
