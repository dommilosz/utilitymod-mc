package com.dommilosz.utilmod;

import com.dommilosz.utilmod.commands.umod.modules.autotryjump;
import com.dommilosz.utilmod.commands.umod.modules.tryjump;
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
	public static void resetmods(){
		tryjump.reset();
		autotryjump.reset();
	}



}
