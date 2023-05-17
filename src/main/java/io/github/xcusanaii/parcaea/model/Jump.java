package io.github.xcusanaii.parcaea.model;

import java.util.ArrayList;

public class Jump {

    public static ArrayList<Jump> jumps = new ArrayList<Jump>();

    public final String id;
    public final ArrayList<ArrayList<Number>> ticks;

    public Jump(String id, ArrayList<ArrayList<Number>> ticks) {
        this.id = id;
        this.ticks = ticks;
    }
}
