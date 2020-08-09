package com.dommilosz.utilmod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.math.MathHelper;

public class playerFunctions {
    public static void PF_setPosition(double x, double y, double z){
        Minecraft.getInstance().player.setPosition(x,y,z);

    }
    public static void PF_setPosRot(double x, double y, double z, float yaw, float pitch){
        Minecraft.getInstance().player.setPositionAndRotation(x,y,z,yaw,pitch);
    }
    public static void PF_setRot(float yaw, float pitch){
        ClientPlayerEntity playerEntity = Minecraft.getInstance().player;
        double x = playerEntity.getPosX();
        double y= playerEntity.getPosY();
        double z= playerEntity.getPosZ();
        Minecraft.getInstance().player.setPositionAndRotation(x,y,z,yaw,pitch);
    }
    public static String PF_getRotationString() {
        String rotationStr = "n";
        int rotationInt = PF_getRotation();
        switch (rotationInt){
            case 0:rotationStr = "s";break;
            case 1:rotationStr = "w";break;
            case 2:rotationStr = "n";break;
            case 3:rotationStr = "e";break;
        }
        return rotationStr;
    }
    public static int PF_getRotation() {
        int rotationInt = MathHelper.floor((double)(Minecraft.getInstance().player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        return rotationInt;
    }
    public static class Pos{
        public double X = 0;
        public double Y = 0;
        public double Z = 0;
        public float Pitch = 0;
        public float Yaw = 0;
        public boolean onGround = true;
    }
}
