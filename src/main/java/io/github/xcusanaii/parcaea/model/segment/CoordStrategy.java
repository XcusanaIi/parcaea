package io.github.xcusanaii.parcaea.model.segment;

public class CoordStrategy {
    public double x;
    public double y;
    public double z;
    public double f;
    public String strategy;

    public CoordStrategy(double x, double y, double z, double f, String strategy) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.f = f;
        this.strategy = strategy;
    }
}
