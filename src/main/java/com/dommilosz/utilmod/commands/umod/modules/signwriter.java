package com.dommilosz.utilmod.commands.umod.modules;

import com.dommilosz.utilmod.packetIO;
import com.dommilosz.utilmod.packetevent.PacketEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.CUpdateSignPacket;
import net.minecraft.network.play.server.SOpenSignMenuPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.dommilosz.utilmod.internalcommands.*;

public class signwriter {
    public static boolean enabled = false;
    public static String[] lines = new String[]{"", "", "", ""};
    public static int delay = 150;
    public static int maxVal = 5000;
    public static int minVal = 10;

    public static void execute(String msg, String[] args) {
        if (isElementOn(args, "signwriter", 2)) {
            if (isElementOn(args, "on", 3)) {
                commandActions.CAon();
            }
            if (isElementOn(args, "off", 3)) {
                commandActions.CAoff();
            }
            if (isElementOn(args, "setline", 3)) {
                try {
                    List<String> argsl = new ArrayList<>();
                    for (String arg : args) {
                        argsl.add(arg);
                    }

                    argsl.remove(0);
                    argsl.remove(0);
                    argsl.remove(0);
                    argsl.remove(0);
                    argsl.remove(0);

                    commandActions.CAsetLine(getElementOn(args, 4), String.join(" ", argsl));
                } catch (Exception ex) {
                }
            }
            if (isElementOn(args, "clearlines", 3)) {
                commandActions.CAclearLines();
            }
            if (isElementOn(args, "delay", 3)) {
                commandActions.CAsetDelay(getElementOn(args, 4));
            }
            if (isElementOn(args, "load", 3)) {
                commandActions.CAload(getElementOn(args, 4));
            }
            if (isElementOn(args, "save", 3)) {
                commandActions.CAsave(getElementOn(args, 4));
            }
            if (isElementOn(args, "lines", 3)) {
                packetIO.SendUMODMessageToClient("SignWriter Lines:");
                packetIO.SendUMODMessageToClient("Line1: \"$&b" + lines[0] + "$&6\"");
                packetIO.SendUMODMessageToClient("Line2: \"$&b" + lines[1] + "$&6\"");
                packetIO.SendUMODMessageToClient("Line3: \"$&b" + lines[2] + "$&6\"");
                packetIO.SendUMODMessageToClient("Line4: \"$&b" + lines[3] + "$&6\"");
                properexecuted = true;
            }
        }

    }

    public static void ModifyPacketsToSignPlacer(IPacket packet, PacketEvent event) {
        if (enabled) {
            if (packet instanceof SOpenSignMenuPacket) {
                event.setCanceled(true);
                BlockPos signPos = ((SOpenSignMenuPacket) packet).getSignPosition();
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                packetIO.SendPacketToServer(new CUpdateSignPacket(signPos, new StringTextComponent(lines[0]), new StringTextComponent(lines[1]), new StringTextComponent(lines[2]), new StringTextComponent(lines[3])));
                packetIO.SendUMODMessageToClient("SignWriter Sent Packet");

            }
        }
    }

    public static void reset() {
        enabled = false;
    }

    public static void start() {
        enabled = true;
    }

    public static void save(String fileName) {
        try {
            File file = new File(Minecraft.getInstance().gameDir.getAbsolutePath() + "/UMOD");
            if (!file.exists()) file.mkdir();
            file = new File(Minecraft.getInstance().gameDir.getAbsolutePath() + "/UMOD/signwriter");
            if (!file.exists()) file.mkdir();
            file = new File(Minecraft.getInstance().gameDir.getAbsolutePath() + "/UMOD/signwriter/" + fileName + ".json");
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            FileWriter fw = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(fw);
            JSONObject alignobj = new JSONObject();
            alignobj.put("line0", lines[0]);
            alignobj.put("line1", lines[1]);
            alignobj.put("line2", lines[2]);
            alignobj.put("line3", lines[3]);
            writer.append(alignobj.toString());
            writer.flush();
            writer.close();

        } catch (Exception ex) {
            packetIO.SendUMODMessageToClient("$errError when saving lines!");
            return;
        }

        packetIO.SendUMODMessageToClient("$sucLines successfully saved!");
    }

    public static boolean load(String fileName) {
        try {
            File file = new File(Minecraft.getInstance().gameDir.getAbsolutePath() + "/UMOD/signwriter/" + fileName + ".json");
            if (!file.exists()) {
                throw new Exception();
            }
            FileReader fr = new FileReader(file);
            Scanner s = new Scanner(fr);
            String json = s.nextLine();

            JSONObject alignobj = new JSONObject(json);
            lines[0] = alignobj.getString("line0");
            lines[1] = alignobj.getString("line1");
            lines[2] = alignobj.getString("line2");
            lines[3] = alignobj.getString("line3");

        } catch (Exception ex) {
            packetIO.SendUMODMessageToClient("$errError when loading lines!");
            return false;
        }
        packetIO.SendUMODMessageToClient("$sucLines successfully loaded!");
        return true;
    }

    public static class commandActions {
        public static void CAon() {
            enabled = true;
            packetIO.SendUMODMessageToClient("SignWriter is now: " + enabled);
            properexecuted = true;
        }

        public static void CAoff() {
            enabled = false;
            packetIO.SendUMODMessageToClient("SignWriter is now: " + enabled);
            properexecuted = true;
        }

        public static void CAsetLine(String index, String txt) {
            int line = -1;
            try {
                line = Integer.parseInt(index) - 1;

                lines[line] = txt;
                packetIO.SendUMODMessageToClient("SignWriter line[$&b" + (line + 1) + "$&6] is now: \"$&b" + lines[line]+"$&6\"");
                properexecuted = true;
            } catch (Exception ex) {
            }
        }

        public static void CAclearLines() {
            lines[0] = "";
            lines[1] = "";
            lines[2] = "";
            lines[3] = "";
            packetIO.SendUMODMessageToClient("SignWriter lines cleared");
            properexecuted = true;
        }

        public static void CAsetDelay(String delaystr) {
            properexecuted = true;
            if (delaystr.equals("")) {
                packetIO.SendUMODMessageToClient("SignWriter delay is: $&b" + delay + "ms");
                properexecuted = true;
                return;
            }
            try {
                String ds = delaystr;
                int di = Integer.parseInt(ds);
                if (di >= minVal && di <= maxVal) {
                    delay = di;
                    packetIO.SendUMODMessageToClient("SignWriter delay is now: $&b" + delay + "ms");
                    return;
                }
            } catch (Exception ex) {
                packetIO.SendUMODMessageToClient("$errSignWriter delay was not int");
                return;
            }
            packetIO.SendUMODMessageToClient("$errSignWriter delay was not in range ["+minVal+"-"+maxVal+"] ms");


        }

        public static void CAload(String filename) {
            if (filename.equals("")) {
                packetIO.SendUMODMessageToClient("$errInvalid lines file name");
                return;
            }
            load(filename);
            properexecuted = true;
            packetIO.SendUMODMessageToClient("$sucLines Loaded");

        }

        public static void CAsave(String filename) {
            if (filename.equals("")) {
                packetIO.SendUMODMessageToClient("$errInvalid lines file name");
                return;
            }
            save(filename);
            properexecuted = true;
            packetIO.SendUMODMessageToClient("$sucLines Saved to " + filename);


        }
    }
}
