package io.github.xcusanaii.parcaea.event.handler.tick.note;

import io.github.xcusanaii.parcaea.Parcaea;
import io.github.xcusanaii.parcaea.event.handler.tick.NoteHandler;
import io.github.xcusanaii.parcaea.model.Chart;
import io.github.xcusanaii.parcaea.model.color.ColorGeneral;
import io.github.xcusanaii.parcaea.model.config.CfgGeneral;
import io.github.xcusanaii.parcaea.model.input.InputStat;
import io.github.xcusanaii.parcaea.model.input.InputTick;
import io.github.xcusanaii.parcaea.model.input.UnstableMouseNote;
import io.github.xcusanaii.parcaea.model.note.KeyNote;
import io.github.xcusanaii.parcaea.model.note.KeyNoteHead;
import io.github.xcusanaii.parcaea.model.note.MouseNote;
import io.github.xcusanaii.parcaea.render.hud.BasicHud;

import java.util.List;

public class BasicNoteHandler extends NoteHandler {

    @Override
    public void calculateNotePos() {
        if (isPlaying) {
            for (BasicHud.KeyNoteDisplay keyNote : BasicHud.keyNoteDisplays) {
                keyNote.y1 += Parcaea.PX_PER_TICK * CfgGeneral.noteSpeed;
                keyNote.y2 += Parcaea.PX_PER_TICK * CfgGeneral.noteSpeed;
            }
            for (BasicHud.MouseNoteDisplay mouseNote : BasicHud.mouseNoteDisplays) {
                mouseNote.y += Parcaea.PX_PER_TICK * CfgGeneral.noteSpeed;
            }
            for (BasicHud.LastInputDisplay lastInput : BasicHud.lastInputDisplays) {
                lastInput.y += Parcaea.PX_PER_TICK * CfgGeneral.noteSpeed;
            }
            for (BasicHud.UnstableMouseNoteDisplay mouseNote : BasicHud.unstableMouseNoteDisplays) {
                mouseNote.y += Parcaea.PX_PER_TICK * CfgGeneral.noteSpeed;
            }
        }else {
            BasicHud.lastInputDisplays.clear();
            BasicHud.keyNoteDisplays.clear();
            BasicHud.mouseNoteDisplays.clear();
            BasicHud.unstableMouseNoteDisplays.clear();
        }
    }

    @Override
    public void initNotePos() {
        BasicHud.keyNoteDisplays.clear();
        List<List<KeyNote>> keyTicks = Chart.selectedChart.keyTicks;
        for (int i = 0; i < keyTicks.size(); i++) {
            for (int j = 0; j < 7; j++) {
                if (keyTicks.get(i).get(j) instanceof KeyNoteHead) {
                    KeyNoteHead keyNote = (KeyNoteHead) keyTicks.get(i).get(j);
                    BasicHud.keyNoteDisplays.add(new BasicHud.KeyNoteDisplay(
                            mapTrackSlot(keyNote.keySlot),
                            mapKeyNoteColor(keyNote.keySlot, keyNote.isMultiTap),
                            BasicHud.jLineCenter.y - Parcaea.PX_PER_TICK * CfgGeneral.noteSpeed * (i + keyNote.length),
                            BasicHud.jLineCenter.y - Parcaea.PX_PER_TICK * CfgGeneral.noteSpeed * i
                    ));
                }
            }
        }

        BasicHud.lastInputDisplays.clear();
        for (int i = 0; i < InputStat.lastInput.size(); i++) {
            for (int j = 0; j < 7; j++) {
                if (InputStat.lastInput.get(i).keyList[j]) {
                    BasicHud.lastInputDisplays.add(new BasicHud.LastInputDisplay(
                            mapTrackSlot(j),
                            mapLastInputColor(j),
                            BasicHud.jLineCenter.y - Parcaea.PX_PER_TICK * CfgGeneral.noteSpeed * i
                    ));
                }
            }
        }

        BasicHud.mouseNoteDisplays.clear();
        BasicHud.unstableMouseNoteDisplays.clear();

        if (!CfgGeneral.enableSnake || Chart.selectedChart.isNoTurn) return;

        List<MouseNote> mouseTicks = Chart.selectedChart.mouseTicks;
        for (int i = 0; i < mouseTicks.size(); i++) {
            MouseNote mouseNote = mouseTicks.get(i);
            if (!mouseNote.canIgnoreDisplay) {
                BasicHud.mouseNoteDisplays.add(new BasicHud.MouseNoteDisplay(
                        mouseNote.posPercent,
                        mouseNote.is45 ? ColorGeneral.BLUE : ColorGeneral.YELLOW,
                        BasicHud.jLineCenter.y - Parcaea.PX_PER_TICK * CfgGeneral.noteSpeed * i
                ));
            }
        }

        for (UnstableMouseNote lastUnstableMouse : InputStat.lastUnstableMouses) {
            int i = lastUnstableMouse.index;
            if (i < 0 || i >= mouseTicks.size()) continue;
            BasicHud.unstableMouseNoteDisplays.add(new BasicHud.UnstableMouseNoteDisplay(
                    mouseTicks.get(i).posPercent,
                    BasicHud.jLineCenter.y - Parcaea.PX_PER_TICK * CfgGeneral.noteSpeed * lastUnstableMouse.index,
                    lastUnstableMouse.type
            ));
        }
    }

    public static int mapTrackSlot(int keySlot) {
        switch (keySlot) {
            case InputTick.W:
            case InputTick.S:
                return 2;
            case InputTick.A:
                return 1;
            case InputTick.D:
                return 3;
            case InputTick.JUMP:
                return 4;
            default:
                return 0;
        }
    }

    public static int mapKeyNoteColor(int keySlot, boolean isMultiTap) {
        if (isMultiTap) return ColorGeneral.ORANGE;
        else switch (keySlot) {
            case InputTick.S:
            case InputTick.SNEAK:
                return ColorGeneral.PINK;
            default:
                return ColorGeneral.BLUE;
        }
    }

    public static int mapLastInputColor(int keySlot) {
        switch (keySlot) {
            case InputTick.S:
            case InputTick.SNEAK:
                return ColorGeneral.PINK_ALPHA;
            default:
                return ColorGeneral.BLUE_ALPHA;
        }
    }
}
