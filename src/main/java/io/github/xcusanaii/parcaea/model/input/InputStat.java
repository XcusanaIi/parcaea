package io.github.xcusanaii.parcaea.model.input;

import org.apache.commons.lang3.SerializationUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InputStat {

    public static List<InputTick> lastInput = new ArrayList<InputTick>();
    public static InputTick isKeyDown = new InputTick();
    public static InputTick isKeyDownExact = new InputTick();
    public static List<UnstableMouseNote> lastUnstableMouses = new ArrayList<UnstableMouseNote>();
    public static long[] keyExactPressTime = new long[7];
    public static int lastKeyDownExactLeft = -1;
    public static int lastKeyDownExactRight = -1;
    public static InputTick isKeyDownPre = new InputTick();
    public static InputTick isKeyFired = new InputTick();
    public static double mousePosPercent = 0.5;
    public static double mousePosPercentPre = 0.5;
    public static double yawRange = 180.0;

    public static void addLastInput() {
        InputStat.lastInput.add(SerializationUtils.clone(InputStat.isKeyDown));
    }

    public static boolean isAnyKeyDown() {
        for (int i = 0; i < 7; i++) {
            if (isKeyDown.keyList[i]) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAnyKeyFired() {
        for (int i = 0; i < 7; i++) {
            if (isKeyFired.keyList[i]) {
                return true;
            }
        }
        return false;
    }
}
