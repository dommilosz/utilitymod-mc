package com.dommilosz.utilmod.commands.umod.modules;

import javax.script.*;

import com.dommilosz.utilmod.packetIO;
import com.dommilosz.utilmod.packetevent.PacketEvent;
import javafx.beans.property.IntegerProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.network.IPacket;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import static com.dommilosz.utilmod.internalcommands.*;
import static com.dommilosz.utilmod.jsapi.cleanListeners;

public class javascript_engine {

    public static void execute(String msg, String[] args) {
        if (isElementOn(args, "js", 2)) {
            properexecuted = true;
            List<String> code = Arrays.asList(args);
            List<String> codef = new ArrayList<>();
            if (isElementOn(args, "$f", 3)) {
                try {
                    packetIO.SendUMODMessageToClient("Loading script from file");
                    File f = new File(String.join(" ",code.subList(4, code.size())));
                    FileReader fr = new FileReader(f);
                    Scanner s = new Scanner(fr);
                    while (s.hasNextLine()){
                        codef.add(s.nextLine()+"\n");
                    }
                    javascript_engine.commandActions.CAExec(codef);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return;
            }
            if (isElementOn(args, "$c", 3)) {
                packetIO.SendUMODMessageToClient("Listeners Cleared");
                cleanListeners();
                return;
            }
            javascript_engine.commandActions.CAExec(code.subList(3, code.size()));
        }
    }

    public static ScriptEngineManager manager;
    public static ScriptEngine engine;

    public static void initEngine() {
        manager = new ScriptEngineManager();
        engine = manager.getEngineByName("javascript");
    }

    public static Object eval(String code) {
        if (engine == null) initEngine();
        code = "load('nashorn:mozilla_compat.js');" + code;
        code = code.replaceAll("#import ([a-zA-Z0-9.]*);", "importPackage('$1');");
        try {
            return (engine.eval(code));
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public static class commandActions {
        public static void CAExec(String code) {
            packetIO.SendMessageToClient(">> " + String.valueOf(eval(code)));
        }

        public static void CAExec(List<String> code) {
            CAExec(String.join(" ", code));
        }
    }
}
