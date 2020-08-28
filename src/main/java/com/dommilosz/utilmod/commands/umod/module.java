package com.dommilosz.utilmod.commands.umod;

import com.dommilosz.utilmod.commands.umod.modules.*;

import static com.dommilosz.utilmod.internalcommands.isElementOn;

public class module {

	public static void execute(String msg, String[] args) {
		if (isElementOn(args, "mod", 1)) {
			try_jump.execute(msg,args);
			auto_try_jump.execute(msg,args);
			free_interact.execute(msg, args);
			macro.execute(msg, args);
			signwriter.execute(msg, args);
			fullbright.execute(msg, args);
		}
	}
}
