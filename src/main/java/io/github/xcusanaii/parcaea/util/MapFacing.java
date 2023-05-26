package io.github.xcusanaii.parcaea.util;

public class MapFacing {
    public static double map(double yaw) {
        yaw %= 360.0;
        if (yaw < 0) yaw += 360.0;
        if (yaw > 180.0) yaw -= 360.0;
        return yaw;
    }
}
