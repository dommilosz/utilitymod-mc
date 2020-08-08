package com.dommilosz.utilmod.commands.umod;

import com.dommilosz.utilmod.commands.umod.modules.tryjump;
import com.dommilosz.utilmod.packetIO;

import static com.dommilosz.utilmod.internalcommands.isElementOn;
import static com.dommilosz.utilmod.internalcommands.properexecuted;

public class module {

	public static void execute(String msg, String[] args) {
		if (isElementOn(args, "mod", 1)) {
			tryjump.execute(msg,args);
		}
	}
}
