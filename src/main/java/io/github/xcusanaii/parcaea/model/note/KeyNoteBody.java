package io.github.xcusanaii.parcaea.model.note;

public class KeyNoteBody extends KeyNote{

    public final int keySlot;
    public int life;

    public KeyNoteBody(int keySlot, int life) {
        this.keySlot = keySlot;
        this.life = life;
    }

    @Override
    public String toString() {
        return "1";
    }
}
