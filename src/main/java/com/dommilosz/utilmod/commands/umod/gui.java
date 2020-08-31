package com.dommilosz.utilmod.commands.umod;

import com.dommilosz.utilmod.packetIO;
import com.dommilosz.utilmod.packetevent.PacketEvent;
import com.dommilosz.utilmod.umod_gui;
import net.minecraft.client.Minecraft;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.CPlayerDiggingPacket;
import net.minecraft.network.play.client.CPlayerTryUseItemOnBlockPacket;

import static com.dommilosz.utilmod.internalcommands.isElementOn;
import static com.dommilosz.utilmod.internalcommands.properexecuted;

public class gui {
    public static boolean enabled = false;

    public static void execute(String msg, String[] args) {
        if (isElementOn(args, "gui", 1)) {
            commandActions.CAShow();
            properexecuted = true;
        }
    }

    public static void show() {
        Minecraft.getInstance().displayGuiScreen(null);
        Minecraft.getInstance().displayGuiScreen(new umod_gui());
    }

    public static class commandActions {
        public static void CAShow() {
            show();
        }
    }
}
