package com.dommilosz.utilmod.commands.umod;

import com.dommilosz.utilmod.packetIO;
import com.dommilosz.utilmod.internalcommands.*;
import static com.dommilosz.utilmod.internalcommands.*;

public class logging {
	public static String logging_type = "off";
	public static boolean logging_filter = true;

	public static void execute(String msg, String[] args) {
		if (isElementOn(args, "logging", 1)) {
			if (isElementOn(args, "out", 2)) {
				setLoggingType(args[2]);
				properexecuted = true;
				return;
			}
			if (isElementOn(args, "in", 2)) {
				setLoggingType(args[2]);
				properexecuted = true;
				return;
			}
			if (isElementOn(args, "all", 2)) {
				setLoggingType(args[2]);
				properexecuted = true;
				return;
			}
			if (isElementOn(args, "off", 2)) {
				setLoggingType(args[2]);
				properexecuted = true;
				return;
			}
			if (isElementOn(args, "filter", 2)) {
				setLoggingFilter(!logging_filter);
				properexecuted = true;
				return;
			}
			packetIO.SendMessageToClient("Logging type is set to: " + logging_type);
			packetIO.SendMessageToClient("Logging filter is set to: " + logging_filter);
		}
	}

	public static void setLoggingType(String type) {
		logging_type = type;
		packetIO.SendMessageToClient("Logging type is now set to: " + type);
	}

	public static void setLoggingFilter(boolean enablefilter) {
		logging_filter = enablefilter;
		packetIO.SendMessageToClient("Logging filter is now set to: " + enablefilter);
	}
}
