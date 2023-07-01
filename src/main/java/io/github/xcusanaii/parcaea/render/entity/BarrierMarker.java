package io.github.xcusanaii.parcaea.render.entity;

import io.github.xcusanaii.parcaea.model.config.CfgGeneral;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class BarrierMarker extends Entity {

    public static List<BarrierMarker> barrierMarkers = new ArrayList<BarrierMarker>();
    private static boolean toggleBarrier = false;

    private static final Minecraft mc = Minecraft.getMinecraft();

    public BarrierMarker(World worldIn, BlockPos blockPos) {
        super(worldIn);
        this.posX = blockPos.getX() + 0.5D;
        this.posY = blockPos.getY() + 0.5D;
        this.posZ = blockPos.getZ() + 0.5D;
    }

    public static void toggleBarrier() {
        toggleBarrier = !toggleBarrier;
        if (toggleBarrier) {
            revealBarrier();
        }else {
            removeBarrierMarker();
        }
    }

    public static void revealBarrier() {
        if (mc.thePlayer == null || mc.theWorld == null) return;
        removeBarrierMarker();
        int d = CfgGeneral.barrierDistance;
        BlockPos blockPos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
        for (int i = blockPos.getX() - d; i < blockPos.getX() + d; i++) {
            for (int j = blockPos.getY() - d; j < blockPos.getY() + d; j++) {
                for (int k = blockPos.getZ() - d; k < blockPos.getZ() + d; k++) {
                    BlockPos blockPosI = new BlockPos(i, j, k);
                    IBlockState blockState = mc.theWorld.getBlockState(blockPosI);
                    if (blockState.getBlock() == Blocks.barrier) {
                        BarrierMarker barrierMarker = new BarrierMarker(mc.theWorld, blockPosI);
                        mc.theWorld.spawnEntityInWorld(barrierMarker);
                        barrierMarkers.add(barrierMarker);
                    }
                }
            }
        }
    }

    public static void removeBarrierMarker() {
        if (mc.thePlayer == null || mc.theWorld == null) return;
        for (BarrierMarker barrierMarker : barrierMarkers) {
            mc.theWorld.removeEntity(barrierMarker);
        }
        barrierMarkers.clear();
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
