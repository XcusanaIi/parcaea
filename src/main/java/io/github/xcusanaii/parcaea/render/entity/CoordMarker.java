package io.github.xcusanaii.parcaea.render.entity;

import io.github.xcusanaii.parcaea.model.segment.CoordStrategy;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class CoordMarker extends Entity {

    public static List<CoordMarker> coordMarkers = new ArrayList<CoordMarker>();

    public CoordStrategy coordStrategy;
    public int color;

    public CoordMarker(World worldIn, CoordStrategy coordStrategy, int color) {
        super(worldIn);
        this.coordStrategy = coordStrategy;
        this.posX = this.coordStrategy.x;
        this.posY = this.coordStrategy.y;
        this.posZ = this.coordStrategy.z;
        this.color = color;
    }

    @Override
    public int getBrightnessForRender(float partialTicks) {
        return 15728880;
    }

    @Override
    protected void entityInit() {

    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound tagCompund) {

    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound tagCompound) {

    }
}
