package com.dommilosz.utilmod;

import com.dommilosz.utilmod.commands.umod.logging;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.SEntityPacket;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import com.dommilosz.utilmod.packetevent.PacketListener;

import java.io.IOException;

public class packetIO {

	public static void SendPacketToServer(IPacket<?> packet) {
		Minecraft.getInstance().player.connection.sendPacket(packet);
	}
	public static void SendPacketToClient(IPacket<?> packet) {
		PacketListener.writeToClient(packet);
	}
	public static void SendMessageToClient(String msg) {
		Minecraft.getInstance().player.sendMessage(new StringTextComponent(msg));
	}
	public static void SendStatusMessageToClient(String msg,boolean actionbar) {
		Minecraft.getInstance().player.sendStatusMessage(new StringTextComponent(msg),actionbar);
	}
	public static StringTextComponent ReplaceMCColorChar(String msg){
		StringTextComponent stc = new StringTextComponent(msg);
		Style style = stc.getStyle();

		style.setColor(TextFormatting.DARK_GREEN);

		return stc;
	}
	public static void SendChat(String msg) {
		SendPacketToServer(new CChatMessagePacket(msg));
	}

	public static boolean FilterPacket(IPacket<?> packet,String direction) {
		if(logging.logging_type.equals("off"))return false;
		if(logging.logging_type.equals("in")&&direction.equals("out"))return false;
		if(logging.logging_type.equals("out")&&direction.equals("in"))return false;
		if(!logging.logging_filter)return true;
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
		if (packet instanceof net.minecraft.network.play.server.SUnloadChunkPacket)return false;
		if (packet instanceof net.minecraft.network.play.server.SUpdateLightPacket)return false;

		return true;
	}

	public static void FilterPacketAndWriteToChat(IPacket<?> packet,String direction) {
		if (FilterPacket(packet,direction)) {
			try {
				String classtxt = packet.getClass().toString();
				classtxt = classtxt.split("\\.")[classtxt.split("\\.").length-1];
				SendMessageToClient(classtxt);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	public static PacketBuffer readPacket(IPacket packet){
		byte[] bytes = new byte[8*1024];
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