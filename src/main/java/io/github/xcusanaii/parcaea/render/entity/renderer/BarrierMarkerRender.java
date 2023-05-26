package io.github.xcusanaii.parcaea.render.entity.renderer;

import io.github.xcusanaii.parcaea.model.config.CfgGeneral;
import io.github.xcusanaii.parcaea.model.segment.CoordStrategy;
import io.github.xcusanaii.parcaea.render.entity.BarrierMarker;
import io.github.xcusanaii.parcaea.render.entity.CoordMarker;
import io.github.xcusanaii.parcaea.util.math.Vec3d;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;

public class BarrierMarkerRender extends AbsRenderEntity{

    private static final Minecraft mc = Minecraft.getMinecraft();

    public BarrierMarkerRender(RenderManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float entityYaw, float partialTicks) {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);

        if (mc.thePlayer == null || mc.theWorld == null) return;

        BarrierMarker barrierMarker;
        if (entity instanceof BarrierMarker) {
            barrierMarker = (BarrierMarker) entity;
        }else {
            return;
        }

        renderBarrierMarker(barrierMarker, partialTicks);

    }

}
