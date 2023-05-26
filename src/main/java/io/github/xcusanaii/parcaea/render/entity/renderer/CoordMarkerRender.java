package io.github.xcusanaii.parcaea.render.entity.renderer;

import io.github.xcusanaii.parcaea.model.config.CfgGeneral;
import io.github.xcusanaii.parcaea.model.segment.CoordStrategy;
import io.github.xcusanaii.parcaea.render.entity.CoordMarker;
import io.github.xcusanaii.parcaea.util.math.Vec3d;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;

public class CoordMarkerRender extends AbsRenderEntity {

    private static final Minecraft mc = Minecraft.getMinecraft();

    public CoordMarkerRender(RenderManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float entityYaw, float partialTicks) {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);

        if (!CfgGeneral.enableSegment || mc.thePlayer == null) return;

        CoordMarker coordMarker;
        if (entity instanceof CoordMarker) {
            coordMarker = (CoordMarker) entity;
        }else {
            return;
        }

        CoordStrategy coordStrategy = coordMarker.coordStrategy;

        Vec3d playerPos = new Vec3d(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
        Vec3d markerPos = new Vec3d(coordStrategy.x, coordStrategy.y, coordStrategy.z);

        double distance = Vec3d.distance(playerPos, markerPos);

        if (distance <= CfgGeneral.segmentViewDistance) {
            renderCoordMarker(coordStrategy, partialTicks, distance, coordMarker.color);
        }
    }
}
