package io.github.xcusanaii.parcaea.model.note;

public class MouseNote {

    public double posPercent;
    public int life;
    public boolean is45;
    public double dYaw;
    public boolean canIgnoreDisplay;
    public double toleranceDYaw;

    public MouseNote(double posPercent, int life, boolean is45, double dYaw, double toleranceDYaw) {
        this.posPercent = posPercent;
        this.life = life;
        this.is45 = is45;
        this.dYaw = dYaw;
        this.toleranceDYaw = toleranceDYaw;
        this.canIgnoreDisplay = false;
    }

    @Override
    public String toString() {
        return String.valueOf(posPercent);
    }
}
