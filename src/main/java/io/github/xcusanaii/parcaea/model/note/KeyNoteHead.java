package io.github.xcusanaii.parcaea.model.note;

public class KeyNoteHead extends KeyNote{

    public int keySlot;
    public int length;
    public boolean isMultiTap;
    public int life;

    public KeyNoteHead(int keySlot, int length, boolean isMultiTap, int life) {
        this.keySlot = keySlot;
        this.length = length;
        this.isMultiTap = isMultiTap;
        this.life = life;
    }

    @Override
    public String toString() {
        return "1";
    }
}
