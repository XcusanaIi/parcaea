package io.github.xcusanaii.parcaea.event.handler.tick;

import io.github.xcusanaii.parcaea.model.segment.CoordStrategy;
import io.github.xcusanaii.parcaea.util.math.RotationUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import java.util.ArrayList;
import java.util.List;

public class AutoCoordHandler {

    private static final Minecraft mc = Minecraft.getMinecraft();

    public static CoordStrategy coordStrategy = null;

    private static final List<Entity> blockers = new ArrayList<Entity>();
    private static final Direction[] direction = new Direction[2];
    private static double playerPosY = 0.0;
    private static final float anglePerTick = 5.0F;
    private static boolean isTurning = false;

    public void onClientTickPre() {
        if (mc.thePlayer.posY != playerPosY)
            clearBlocker(mc.thePlayer.worldObj);
        if (mc.currentScreen != null) {
            isTurning = false;
        }
        if (isTurning && coordStrategy != null) {
            double deltaDegree = RotationUtil.deltaDegree(mc.thePlayer.rotationYaw, coordStrategy.f);
            int rotateDir = RotationUtil.getRotationDir(mc.thePlayer.rotationYaw, coordStrategy.f);
            if (deltaDegree < anglePerTick) {
                mc.thePlayer.rotationYaw += rotateDir * deltaDegree;
                isTurning = false;
            }else {
                mc.thePlayer.rotationYaw += rotateDir * anglePerTick;
            }
        }
    }

    public static void onStartAC() {
        if (mc.thePlayer == null || coordStrategy == null || mc.thePlayer.worldObj == null || isPlayerMoving(mc.thePlayer)) return;
        World world = mc.thePlayer.worldObj;
        if (mc.thePlayer.posX > coordStrategy.x) {
            direction[0] = Direction.W;
        }else direction[0] = Direction.E;
        if (mc.thePlayer.posZ > coordStrategy.z) {
            direction[1] = Direction.N;
        }else direction[1] = Direction.S;
        clearBlocker(world);
        playerPosY = mc.thePlayer.posY;
        spawnBlocker(world, coordStrategy.x, coordStrategy.z, direction[0], mc.thePlayer);
        spawnBlocker(world, coordStrategy.x, coordStrategy.z, direction[1], mc.thePlayer);
        isTurning = true;
    }

    private static boolean isPlayerMoving(EntityPlayerSP player) {
        return !player.onGround || player.motionX != 0.0  || player.motionZ != 0.0;
    }

    private static void spawnBlocker(World world, double x, double z, Direction direction, EntityPlayer player) {
        EntityBoat barrier = new EntityBoat(world);
        switch (direction) {
            case S:
                barrier.setPosition(x, player.posY + 1.5D, z + 1.05D);
                break;
            case N:
                barrier.setPosition(x, player.posY + 1.5D, z - 1.05D);
                break;
            case W:
                barrier.setPosition(x - 1.05D, player.posY + 1.5D, z);
                break;
            case E:
                barrier.setPosition(x + 1.05D, player.posY + 1.5D, z);
                break;
        }
        world.spawnEntityInWorld(barrier);
        blockers.add(barrier);
    }
    private static void clearBlocker(World world) {
        for (Entity entity: blockers) {
            world.removeEntity(entity);
        }
        blockers.clear();
    }
    private enum Direction {
        N, S, W, E
    }
}
