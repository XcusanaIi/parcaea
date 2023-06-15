package io.github.xcusanaii.parcaea.model;

import io.github.xcusanaii.parcaea.model.note.KeyNote;
import io.github.xcusanaii.parcaea.model.note.KeyNoteBody;
import io.github.xcusanaii.parcaea.model.note.KeyNoteHead;
import io.github.xcusanaii.parcaea.model.note.MouseNote;
import io.github.xcusanaii.parcaea.util.math.Vec2i;

import java.util.*;

public class Chart {

    public static List<Chart> charts = new ArrayList<Chart>();
    public static Chart selectedChart = null;

    public static Chart searchChart(String id) {
        for (Chart chart : charts) {
            if (chart.id.equals(id)) {
                return chart;
            }
        }
        return null;
    }

    public static void toChart() {
        charts.clear();
        for (Jump jump: Jump.jumps) {
            charts.add(new Chart(jump));
        }
    }

    public static void printChart() {
        for (Chart chart: charts) {
            System.out.println(chart.id);
            System.out.println(chart.keyTicks.size() + " " + chart.mouseTicks.size());
            for (int i = 0; i < chart.keyTicks.size(); i++) {
                System.out.print(chart.keyTicks.get(i));
                System.out.println(" " + chart.mouseTicks.get(i));
            }
        }
    }

    private static int mapKeySlotInMirror(int keySlot) {
        if (keySlot == 1) return 3;
        else if (keySlot == 3) return 1;
        else return keySlot;
    }

    private static void mirrorKeyNote(KeyNote keyNote) {
        if (keyNote instanceof KeyNoteHead) {
            KeyNoteHead keyNoteHead = (KeyNoteHead) keyNote;
            keyNoteHead.keySlot = mapKeySlotInMirror(keyNoteHead.keySlot);
        } else if (keyNote instanceof KeyNoteBody) {
            KeyNoteBody keyNoteBody = (KeyNoteBody) keyNote;
            keyNoteBody.keySlot = mapKeySlotInMirror(keyNoteBody.keySlot);
        }
    }

    public final String id;
    public final List<List<KeyNote>> keyTicks;
    public final List<MouseNote> mouseTicks;
    public double yawRange;
    public boolean isNoTurn;
    public boolean isMirrored = false;

    public Chart(Jump jump) {
        id = jump.id;
        keyTicks = toKeyNote(jump);
        mouseTicks = toMouseNote(jump);
        checkCanIgnoreDisplay();
    }

    public void mirror() {
        isMirrored = !isMirrored;
        for (List<KeyNote> keyTick : keyTicks) {
            KeyNote temp = keyTick.get(1);
            keyTick.set(1, keyTick.get(3));
            keyTick.set(3, temp);
            for (KeyNote keyNote : keyTick) {
                mirrorKeyNote(keyNote);
            }
        }
        for (MouseNote mouseTick : mouseTicks) {
            mouseTick.dYaw *= -1;
            mouseTick.posPercent = 1.0D - mouseTick.posPercent;
        }
    }

    private List<List<KeyNote>> toKeyNote(Jump jump) {
        List<List<KeyNote>> ticks = new ArrayList<List<KeyNote>>();
        for (int i = 0; i < jump.ticks.size(); i++) {
            List<KeyNote> noteTick = new ArrayList<KeyNote>();
            for (int j = 0; j < 7; j++) {
                if ((Integer) jump.ticks.get(i).get(j) == 1) {
                    noteTick.add(new KeyNoteBody(j, i));
                }else {
                    noteTick.add(null);
                }
            }
            ticks.add(noteTick);
        }
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < ticks.size(); j++) {
                if (ticks.get(j).get(i) != null) {
                    int length = 1;
                    while (j + 1 < ticks.size() && ticks.get(j + 1).get(i) != null) {
                        length++;
                        j++;
                    }
                    int startIndex = j + 1 - length;
                    ticks.get(startIndex).set(i, new KeyNoteHead(i, length, false, startIndex));
                }
            }
        }
        for (List<KeyNote> noteTick : ticks) {
            int multiTapCount = 0;
            for (int j = 0; j < 7; j++) {
                if (noteTick.get(j) != null && noteTick.get(j) instanceof KeyNoteHead) {
                    multiTapCount++;
                }
            }
            if (multiTapCount > 1) {
                for (int j = 0; j < 7; j++) {
                    if (noteTick.get(j) != null && noteTick.get(j) instanceof KeyNoteHead) {
                        ((KeyNoteHead) noteTick.get(j)).isMultiTap = true;
                    }
                }
            }
        }
        return ticks;
    }

    private List<MouseNote> toMouseNote(Jump jump) {
        List<MouseNote> ticks = new ArrayList<MouseNote>();
        List<Double> posRecord = new ArrayList<Double>();
        double yaw = 0.0;
        double minYaw = 0.0;
        double maxYaw = 0.0;
        for (int i = 0; i < jump.ticks.size(); i++) {
            yaw += (Double) jump.ticks.get(i).get(7);
            posRecord.add(yaw);
            if (yaw < minYaw) minYaw = yaw;
            if (yaw > maxYaw) maxYaw = yaw;
        }
        yawRange = maxYaw - minYaw;
        isNoTurn = yawRange == 0.0;
        for (int i = 0; i < jump.ticks.size(); i++) {
            boolean is45 = Math.abs((Double)jump.ticks.get(i).get(7)) == 45.0;
            MouseNote mouseNote = new MouseNote((posRecord.get(i) - minYaw) / yawRange, i, is45, (Double) jump.ticks.get(i).get(7), (Double) jump.ticks.get(i).get(8));
            ticks.add(mouseNote);
        }
        return ticks;
    }

    private void checkCanIgnoreDisplay() {
        List<Vec2i> indexLengths = new ArrayList<Vec2i>();
        for (int i = 0; i < mouseTicks.size(); i++) {
            if (mouseTicks.get(i).dYaw == 0.0) {
                int length = 1;
                while (i + 1 < mouseTicks.size() && mouseTicks.get(i + 1).dYaw == 0.0) {
                    length++;
                    i++;
                }
                int startIndex = i + 1 - length;
                indexLengths.add(new Vec2i(startIndex, length));
            }
        }
        for (Vec2i indexLength : indexLengths) {
            if (indexLength.y >= 3) {
                for (int i = indexLength.x + 1; i < indexLength.x + indexLength.y - 1; i++) {
                    mouseTicks.get(i).canIgnoreDisplay = true;
                }
            }
        }

    }
}
