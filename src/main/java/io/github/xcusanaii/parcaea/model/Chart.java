package io.github.xcusanaii.parcaea.model;

import io.github.xcusanaii.parcaea.model.note.KeyNote;
import io.github.xcusanaii.parcaea.model.note.KeyNoteBody;
import io.github.xcusanaii.parcaea.model.note.KeyNoteHead;
import io.github.xcusanaii.parcaea.model.note.MouseNote;

import java.util.*;

public class Chart {

    public static ArrayList<Chart> charts = new ArrayList<Chart>();
    public static Chart selectedChart = null;

    public final String id;
    public final List<List<KeyNote>> keyTicks;
    public final List<MouseNote> mouseTicks;
    public double yawRange;
    public boolean isNoTurn;

    public Chart(Jump jump) {
        this.id = jump.id;
        this.keyTicks = toKeyNote(jump);
        this.mouseTicks = toMouseNote(jump);
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
            for (List<KeyNote> keyTick : chart.keyTicks) {
                System.out.println(keyTick);
            }
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
        double yaw = 0.0;
        double minYaw = 0.0;
        double maxYaw = 0.0;
        for (int i = 0; i < jump.ticks.size(); i++) {
            yaw += (Double) jump.ticks.get(i).get(7);
            if (yaw < minYaw) minYaw = yaw;
            if (yaw > minYaw) maxYaw = yaw;
        }
        yawRange = maxYaw - minYaw;
        isNoTurn = yawRange == 0.0;
        yaw = -minYaw;
        for (int i = 0; i < jump.ticks.size(); i++) {
            yaw += (Double) jump.ticks.get(i).get(7);
            boolean is45 = Math.abs((Double)jump.ticks.get(i).get(7)) == 45.0;
            MouseNote mouseNote = new MouseNote(yaw / yawRange, i, is45);
            ticks.add(mouseNote);
        }
        return ticks;
    }
}
