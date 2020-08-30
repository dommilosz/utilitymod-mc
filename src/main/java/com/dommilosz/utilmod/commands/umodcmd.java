package com.dommilosz.utilmod.commands;
import com.dommilosz.utilmod.commands.umod.*;

import static com.dommilosz.utilmod.internalcommands.*;
import com.dommilosz.utilmod.packetIO;

public class umodcmd {
	public static void execute(String msg, String[] args) {
		if (isElementOn(args, "/umod", 0)) {
			executed = true;
			logging.execute(msg, args);
			module.execute(msg,args);
			gui.execute(msg,args);
			if(!properexecuted){
				packetIO.SendUMODMessageToClient("Usage:");
				packetIO.SendUMODMessageToClient("/umod logging <in|out|all|off|filter OR /umod mod <module> <action...>");
			}
			return;
		}
	}


}
