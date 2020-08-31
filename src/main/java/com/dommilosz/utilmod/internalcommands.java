package com.dommilosz.utilmod;

import com.dommilosz.utilmod.commands.umod.modules.auto_try_jump;
import com.dommilosz.utilmod.commands.umod.modules.free_interact;
import com.dommilosz.utilmod.commands.umod.modules.try_jump;
import com.dommilosz.utilmod.commands.umodcmd;

public class internalcommands {
    public static boolean executed = false;
    public static boolean properexecuted = false;

    public static String getElementOn(String[] args, int index) {
        if (args.length > index) {
            return args[index];
        }
        return "";
    }

    public static boolean isElementOn(String[] args, String arg, int index) {
        return getElementOn(args, index).equals(arg);
    }

    public static void executeAll(String msg) {
        properexecuted = false;
        executed = false;
        String[] args = msg.split(" ");
        umodcmd.execute(msg, args);
    }

    public static void resetmods() {
        try_jump.reset();
        auto_try_jump.reset();
        free_interact.reset();
    }


}
