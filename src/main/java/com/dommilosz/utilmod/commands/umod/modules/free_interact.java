package com.dommilosz.utilmod.commands.umod.modules;

import com.dommilosz.utilmod.packetIO;
import com.dommilosz.utilmod.packetevent.PacketEvent;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.CPlayerDiggingPacket;
import net.minecraft.network.play.client.CPlayerTryUseItemOnBlockPacket;

import static com.dommilosz.utilmod.internalcommands.isElementOn;

public class free_interact {
    public static boolean enabled = false;
    public static void execute(String msg, String[] args) {
        if (isElementOn(args, "freeinteract", 2)) {
            commandActions.CAtoggle();
        }
    }
    public static void ModifyPacketsToFreeInteract(IPacket packet,PacketEvent event){
        if(enabled){
            if(packet instanceof CPlayerTryUseItemOnBlockPacket){
                event.setCanceled(true);
            }
            if(packet instanceof CPlayerDiggingPacket){
                event.setCanceled(true);
            }
        }
    }
    public static void reset(){
        enabled = false;
    }
    public static void start(){
        enabled = true;
    }
    public static class commandActions{
        public static void CAtoggle(){
            enabled = !enabled;
            packetIO.SendMessageToClient("[UMOD] FreeInteract is now: "+enabled);
        }
    }
}
