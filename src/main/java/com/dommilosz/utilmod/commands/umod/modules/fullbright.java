package com.dommilosz.utilmod.commands.umod.modules;

import com.dommilosz.utilmod.packetIO;
import net.minecraft.client.Minecraft;

import static com.dommilosz.utilmod.internalcommands.isElementOn;
import static com.dommilosz.utilmod.internalcommands.properexecuted;

public class fullbright {
    public static boolean enabled = false;

    public static void execute(String msg, String[] args) {
        if (isElementOn(args, "fullbright", 2)) {
            fullbright.commandActions.CAtoggle();
            properexecuted = true;
        }
    }

    public static double gamma = 3000000;
    public static double oldgamma = 1;

    public static class commandActions {
        public static void CAStart() {
            oldgamma = Minecraft.getInstance().gameSettings.gamma;
            Minecraft.getInstance().gameSettings.gamma = gamma;
        }

        public static void CAStop() {
            if (oldgamma == gamma) oldgamma = 1;
            Minecraft.getInstance().gameSettings.gamma = oldgamma;
        }

        public static void CAtoggle() {
            enabled = !enabled;
            packetIO.SendUMODMessageToClient("Fullbright is now: $&b" + enabled);
            if (enabled) CAStart();
            else CAStop();
        }
    }
}
