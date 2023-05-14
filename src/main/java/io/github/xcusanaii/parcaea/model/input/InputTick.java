package io.github.xcusanaii.parcaea.model.input;

import java.io.Serializable;
import java.util.Arrays;

public class InputTick implements Serializable {

    public static final int W = 0;
    public static final int A = 1;
    public static final int S = 2;
    public static final int D = 3;
    public static final int JUMP = 4;
    public static final int SNEAK = 5;
    public static final int SPRINT = 6;
    public boolean[] keyList;
    public double dYaw;

    public InputTick() {
        this.dYaw = 0.0D;
        this.keyList = new boolean[7];
        Arrays.fill(keyList, false);
    }

    public InputTick(boolean w, boolean a, boolean s, boolean d, boolean jump, boolean sneak, boolean sprint, double dYaw) {
        this.dYaw = dYaw;
        this.keyList = new boolean[]{w, a, s, d, jump, sneak, sprint};
    }

    public boolean w() {
        return keyList[W];
    }

    public boolean a() {
        return keyList[A];
    }

    public boolean s() {
        return keyList[S];
    }

    public boolean d() {
        return keyList[D];
    }

    public boolean jump() {
        return keyList[JUMP];
    }

    public boolean sneak() {
        return keyList[SNEAK];
    }

    public boolean sprint() {
        return keyList[SPRINT];
    }

}
