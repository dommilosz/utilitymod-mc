package com.dommilosz.utilmod.commands;
import com.dommilosz.utilmod.commands.umod.logging;
import com.dommilosz.utilmod.commands.umod.modules.autotryjump;
import com.dommilosz.utilmod.commands.umod.modules.tryjump;

import static com.dommilosz.utilmod.internalcommands.*;
import com.dommilosz.utilmod.packetIO;

public class umodcmd {
	public static void execute(String msg, String[] args) {
		if (isElementOn(args, "/umod", 0)) {
			executed = true;
			logging.execute(msg, args);
			tryjump.execute(msg,args);
			autotryjump.execute(msg,args);
			if(!properexecuted){
				packetIO.SendMessageToClient("Usage:");
				packetIO.SendMessageToClient("/umod logging <in|out|all|off|filter OR /umod mod <module> <action...>");
			}
			return;
		}
	}


}