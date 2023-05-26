package io.github.xcusanaii.parcaea.util.math;

public class RotationUtil {

    public static double deltaDegree(double yaw1, double yaw2) {
        double delta = Math.abs(yaw2 - yaw1) % 360.0D;
        if (delta > 180.0D)
            delta = 360.0D - delta;
        return delta;
    }

    public static int rotateDir(double targetYaw, double curYaw) {
        return ((targetYaw < curYaw && Math.abs(targetYaw - curYaw) <= 180.0D) || (targetYaw > curYaw && Math.abs(targetYaw - curYaw) > 180.0D)) ? 1 : -1;
    }

}
