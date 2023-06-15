package io.github.xcusanaii.parcaea.model.input;

public class UnstableMouseNote {

    public int index;
    public Type type;

    public UnstableMouseNote(int index, Type type) {
        this.index = index;
        this.type = type;
    }

    public enum Type {
        LEFT,
        RIGHT,
        BOTH
    }

}
