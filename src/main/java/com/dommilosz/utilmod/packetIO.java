package com.dommilosz.utilmod;

import com.dommilosz.utilmod.commands.umod.logging;
import com.dommilosz.utilmod.colorhandler;

import static com.dommilosz.utilmod.colorhandler.Color.*;
import static com.dommilosz.utilmod.umod.commandLine;

import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.SEntityPacket;
import net.minecraft.util.text.StringTextComponent;

import java.io.IOException;
import java.util.regex.Pattern;

public class packetIO {
    public static String prefix = GOLD + "[" + DARK_PURPLE + "UMOD" + GOLD + "]";
    public static String prefix_RAW = "[UMOD]";

    public static void SendPacketToServer(IPacket<?> packet) {
        if(commandLine){return;}
        Minecraft.getInstance().player.connection.sendPacket(packet);
    }

    public static void SendUMODMessageToClient(String msg) {
        if(commandLine){System.out.println(prefix_RAW+msg);return;}
        String chars = "0123456789abcdef";
        for (char c : chars.toCharArray()) {
            msg = msg.replaceAll(Pattern.quote("$&" + c), fcode(c));
        }
        boolean showInGui = false;
        if (msg.startsWith("$err")) {
            msg = msg.replace("$err", RED);
            showInGui = true;
        }
        if (msg.startsWith("$suc")) {
            msg = msg.replace("$suc", GREEN);
            showInGui = true;
        }
        Minecraft.getInstance().player.sendMessage(new StringTextComponent(prefix + " " + msg));
        if (showInGui) {
            try {
                umod_gui.umod_gui_obj.renderInfoPrompt(msg);
            } catch (Exception ex) {
            }
        }
    }

    public static void SendMessageToClient(String msg) {
        if(commandLine){System.out.println(msg);return;}
        Minecraft.getInstance().player.sendMessage(new StringTextComponent(msg));
    }

    public static void SendStatusMessageToClient(String msg, boolean actionbar) {
        if(commandLine){System.out.println(msg);return;}
        Minecraft.getInstance().player.sendStatusMessage(new StringTextComponent(msg), actionbar);
    }

    public static void SendChat(String msg) {
        if(commandLine){System.out.println(msg);return;}
        SendPacketToServer(new CChatMessagePacket(msg));
    }

    public static boolean FilterPacket(IPacket<?> packet, String direction) {
        if (logging.logging_type.equals("off")) return false;
        if (logging.logging_type.equals("in") && direction.equals("out")) return false;
        if (logging.logging_type.equals("out") && direction.equals("in")) return false;
        if (!logging.logging_filter) return true;
        if (packet instanceof net.minecraft.network.play.client.CKeepAlivePacket) return false;
        if (packet instanceof CPlayerPacket.PositionPacket) return false;
        if (packet instanceof CPlayerPacket.PositionRotationPacket) return false;
        if (packet instanceof CPlayerPacket.RotationPacket) return false;

        if (packet instanceof net.minecraft.network.play.server.SUpdateTimePacket) return false;
        if (packet instanceof SEntityPacket.LookPacket) return false;
        if (packet instanceof SEntityPacket.MovePacket) return false;
        if (packet instanceof SEntityPacket.RelativeMovePacket) return false;
        if (packet instanceof net.minecraft.network.play.server.SChunkDataPacket) return false;
        if (packet instanceof net.minecraft.network.play.server.SEntityHeadLookPacket) return false;
        if (packet instanceof net.minecraft.network.play.server.SKeepAlivePacket) return false;
        if (packet instanceof net.minecraft.network.play.server.SEntityVelocityPacket) return false;
        if (packet instanceof net.minecraft.network.play.server.SUnloadChunkPacket) return false;
        if (packet instanceof net.minecraft.network.play.server.SUpdateLightPacket) return false;

        return true;
    }

    public static void FilterPacketAndWriteToChat(IPacket<?> packet, String direction) {
        if (FilterPacket(packet, direction)) {
            try {
                String classtxt = packet.getClass().toString();
                classtxt = classtxt.split("\\.")[classtxt.split("\\.").length - 1];
                SendUMODMessageToClient("$&7" + classtxt);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static PacketBuffer readPacket(IPacket packet) {
        byte[] bytes = new byte[8 * 1024];
        PacketBuffer packetbuffer = new PacketBuffer(Unpooled.copiedBuffer(bytes));

        try {
            packet.readPacketData(packetbuffer);
            return packetbuffer;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return packetbuffer;
    }
}
