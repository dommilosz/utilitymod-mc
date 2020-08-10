package com.dommilosz.utilmod;

import com.dommilosz.utilmod.commands.umod.modules.*;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.CChatMessagePacket;
import com.dommilosz.utilmod.packetevent.PacketEvent;

public class packetEvents {
	public static void onIncoming(PacketEvent.Incoming event,IPacket packet){
		signwriter.ModifyPacketsToSignPlacer(packet,event);
	}
	public static void onOutgoing(PacketEvent.Outgoing event,IPacket packet){
		try_jump.ModifyPacketTryJump(packet,event);
		free_interact.ModifyPacketsToFreeInteract(packet, event);
	}
	public static void onEvery(PacketEvent event,IPacket packet,String direction){
		packetIO.FilterPacketAndWriteToChat(event.getPacket(),direction);
	}
	public static void onChat(String msg,PacketEvent event,IPacket packet){
		String message = ((CChatMessagePacket) packet).getMessage();
		internalcommands.executeAll(message);
		if(internalcommands.executed)event.setCanceled(true);
	}
}
