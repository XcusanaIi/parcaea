package io.github.xcusanaii.parcaea.util.math;

public class RotationUtil {

    public static double deltaDegree(double yaw1, double yaw2) {
        double delta = Math.abs(yaw2 - yaw1) % 360.0D;
        if (delta > 180.0D)
            delta = 360.0D - delta;
        return delta;
    }

    public static int getRotationDir(double targetYaw, double curYaw) {
        double delta = ((targetYaw - curYaw) % 360 + 540) % 360 - 180;
        if (delta > 0) {
            return -1;
        } else if (delta < 0) {
            return 1;
        } else {
            return 0;
        }
    }

    public static double mapTrueYaw(double yaw) {
        yaw %= 360.0;
        if (yaw < 0) yaw += 360.0;
        return yaw;
    }

    public static double mapDisplayYaw(double yaw) {
        yaw = mapTrueYaw(yaw);
        if (yaw > 180.0) yaw -= 360.0;
        return yaw;
    }

}
