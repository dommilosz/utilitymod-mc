package com.dommilosz.utilmod.commands.umod;

import com.dommilosz.utilmod.commands.umod.modules.try_jump;

import static com.dommilosz.utilmod.internalcommands.isElementOn;

public class module {

	public static void execute(String msg, String[] args) {
		if (isElementOn(args, "mod", 1)) {
			try_jump.execute(msg,args);
		}
	}
}
