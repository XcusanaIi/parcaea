package io.github.xcusanaii.parcaea.model.segment;

import io.github.xcusanaii.parcaea.model.color.ColorGeneral;
import io.github.xcusanaii.parcaea.render.entity.CoordMarker;
import io.github.xcusanaii.parcaea.util.math.Vec3d;
import net.minecraft.client.Minecraft;
import java.util.ArrayList;
import java.util.List;

public class Segment {

    public static List<Segment> segments = new ArrayList<Segment>();
    public static Segment selectedSegment = null;

    private static final Minecraft mc = Minecraft.getMinecraft();

    public String id;
    public List<CoordStrategy> coords;

    public Segment(String id, List<CoordStrategy> coords) {
        this.id = id;
        this.coords = coords;
    }

    public static void setSelectedSegment(Segment segment) {
        selectedSegment = segment;
        reGenCoordMarker();
    }

    public static void reGenCoordMarker() {
        if (selectedSegment == null || mc.thePlayer == null || mc.theWorld == null) return;
        for (CoordMarker coordMarker : CoordMarker.coordMarkers) {
            mc.theWorld.removeEntity(coordMarker);
        }
        CoordMarker.coordMarkers.clear();
        for (CoordStrategy coordStrategy : selectedSegment.coords) {
            CoordMarker coordMarker = new CoordMarker(mc.theWorld, coordStrategy, ColorGeneral.BLUE);
            mc.theWorld.spawnEntityInWorld(coordMarker);
            CoordMarker.coordMarkers.add(coordMarker);
        }
    }

    public static void addCoordMarker(CoordStrategy coordStrategy) {
        if (selectedSegment == null || mc.thePlayer == null || mc.theWorld == null) return;
        selectedSegment.coords.add(coordStrategy);
        CoordMarker coordMarker = new CoordMarker(mc.theWorld, coordStrategy, ColorGeneral.BLUE);
        mc.theWorld.spawnEntityInWorld(coordMarker);
        CoordMarker.coordMarkers.add(coordMarker);
    }

    public static CoordMarker findNearestCoordMarker() {
        if (selectedSegment == null || mc.thePlayer == null || mc.theWorld == null) return null;
        double distance = Double.MAX_VALUE;
        for (CoordMarker coordMarker : CoordMarker.coordMarkers) {
            double distanceI = Vec3d.distance(new Vec3d(coordMarker.posX, coordMarker.posY, coordMarker.posZ), new Vec3d(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ));
            coordMarker.distance = distanceI;
            if (distance > distanceI) {
                distance = distanceI;
            }
        }
        CoordMarker nearest = null;
        for (CoordMarker coordMarker : CoordMarker.coordMarkers) {
            if (coordMarker.distance == distance) {
                nearest = coordMarker;
                break;
            }
        }
        return nearest;
    }

    public static void removeNearestCoordMarker() {
        if (selectedSegment == null || mc.thePlayer == null || mc.theWorld == null) return;
        CoordMarker nearest = findNearestCoordMarker();
        if (nearest != null) {
            mc.theWorld.removeEntity(nearest);
            CoordMarker.coordMarkers.remove(nearest);
            selectedSegment.coords.remove(nearest.coordStrategy);
        }
    }

    public static Segment searchSegment(String id) {
        for (Segment segment : segments) {
            if (segment.id.equals(id)) {
                return segment;
            }
        }
        return null;
    }
}
