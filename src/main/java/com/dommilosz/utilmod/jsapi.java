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

    public static List<Runnable> onChat = new ArrayList<>();
    public static List<Runnable> onPacketIN = new ArrayList<>();
    public static List<Runnable> onPacketOUT = new ArrayList<>();
    public static List<Runnable> onCMD = new ArrayList<>();

    public static void onChatMake(String msg_a, PacketEvent event_a, IPacket packet_a) {
        msg = msg_a;
        event = event_a;
        packet = packet_a;
        for (Runnable r : onChat) {
            r.run();
        }
    }

    public static void onCMDMake(String msg_a) {
        msg = msg_a;
        packet = null;
        event = null;
        for (Runnable r : onCMD) {
            r.run();
        }
    }

    public static void onInMake(PacketEvent.Incoming event_a, IPacket packet_a) {
        event = event_a;
        packet = packet_a;
        msg = null;
        for (Runnable r : onPacketIN) {
            r.run();
        }
    }

    public static void onOutMake(PacketEvent.Outgoing event_a, IPacket packet_a) {
        event = event_a;
        packet = packet_a;
        msg = null;
        for (Runnable r : onPacketOUT) {
            r.run();
        }
    }

    public static void cleanListeners(){
        onCMD.clear();
        onChat.clear();
        onPacketOUT.clear();
        onPacketIN.clear();
    }

    public static void addListener(List<Runnable> array, Runnable listener){
        array.add(listener);
    }

    public static void sendPacket(IPacket packet){
        packetIO.SendPacketToServer(packet);
    }
    public static void printMessage(String msg){
        packetIO.SendMessageToClient(msg);
    }
    public static void printMessageUMOD(String msg){
        packetIO.SendUMODMessageToClient(msg);
    }
    public static void sendChat(String msg){
        packetIO.SendChat(msg);
    }
    public static void printStatusMsg(String msg,boolean actionbar){
        packetIO.SendStatusMessageToClient(msg,actionbar);
    }
    public static void executeCommand(String msg){
        internalcommands.executeAll(msg);
    }
}
