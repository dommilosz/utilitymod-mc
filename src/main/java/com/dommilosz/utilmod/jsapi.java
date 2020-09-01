package com.dommilosz.utilmod;

import com.dommilosz.utilmod.commands.umod.modules.javascript_engine;
import com.dommilosz.utilmod.packetevent.PacketEvent;
import net.minecraft.network.IPacket;

import java.util.ArrayList;
import java.util.List;

public class jsapi {
    public static PacketEvent event;
    public static String msg;
    public static IPacket packet;
    public static umod_gui gui;

    public static List<JSListener> onChat = new ArrayList<>();
    public static List<JSListener> onPacketIN = new ArrayList<>();
    public static List<JSListener> onPacketOUT = new ArrayList<>();
    public static List<JSListener> onCMD = new ArrayList<>();
    public static List<JSListener> onGUI = new ArrayList<>();
    public static List<JSListener> onGUIRender = new ArrayList<>();

    public static void resetArgs(){
        event = null;
        msg = null;
        packet = null;
    }

    public static void onChatMake(String msg_a, PacketEvent event_a, IPacket packet_a) {
        resetArgs();
        msg = msg_a;
        event = event_a;
        packet = packet_a;
        runAllListeners(onChat);
    }

    public static void onCMDMake(String msg_a) {
        resetArgs();
        msg = msg_a;
        runAllListeners(onCMD);
    }

    public static void onInMake(PacketEvent.Incoming event_a, IPacket packet_a) {
        resetArgs();
        event = event_a;
        packet = packet_a;
        runAllListeners(onPacketIN);
    }

    public static void onOutMake(PacketEvent.Outgoing event_a, IPacket packet_a) {
        resetArgs();
        event = event_a;
        packet = packet_a;
        runAllListeners(onPacketOUT);
    }

    public static void onGUIMake(umod_gui gui_a) {
        resetArgs();
        gui = gui_a;
        runAllListeners(onGUI);
    }

    public static void onGUIRenderMake(umod_gui gui_a) {
        resetArgs();
        gui = gui_a;
        runAllListeners(onGUIRender);
    }

    public static void runAllListeners(List<JSListener> listeners) {
        for (JSListener listener : listeners) {
            try {
                listener.runnable.run();
            } catch (Exception ex) {
                packetIO.SendMessageToClient(ex.toString());
            }
        }
    }
    public static void cleanListeners() {
        onCMD.clear();
        onChat.clear();
        onPacketOUT.clear();
        onPacketIN.clear();
        onGUI.clear();
        onGUIRender.clear();
    }

    public static void addListener(List<JSListener> array, Runnable listener, String name) {
        for (int i = 0; i < array.size(); i++) {
            JSListener jsListener = array.get(i);
            if (jsListener.name.equals(name)) {
                array.set(i, (new JSListener(listener, name)));
                packetIO.SendUMODMessageToClient("listener overwritten: $&b"+name);
                return;
            }
        }
        array.add(new JSListener(listener, name));
        packetIO.SendUMODMessageToClient("New listener registered: $&b"+name);
    }

    public static void sendPacket(IPacket packet) {
        packetIO.SendPacketToServer(packet);
    }

    public static void printMessage(String msg) {
        packetIO.SendMessageToClient(msg);
    }

    public static void printMessageUMOD(String msg) {
        packetIO.SendUMODMessageToClient(msg);
    }

    public static void sendChat(String msg) {
        packetIO.SendChat(msg);
    }

    public static void printStatusMsg(String msg, boolean actionbar) {
        packetIO.SendStatusMessageToClient(msg, actionbar);
    }

    public static void executeCommand(String msg) {
        internalcommands.executeAll(msg);
    }

    public static void setExecuted(boolean state) {
        internalcommands.executed = state;
    }

    public static void setProperExecuted(boolean state) {
        internalcommands.properexecuted = state;
    }

    public static class JSListener {
        public Runnable runnable;
        public String name;

        public JSListener(Runnable r, String n) {
            runnable = r;
            name = n;
        }
    }
    public static void print(String txt){
        printMessage(txt);
    }
}
