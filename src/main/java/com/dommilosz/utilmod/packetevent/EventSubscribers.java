package com.dommilosz.utilmod.packetevent;

import com.dommilosz.utilmod.packetEvents;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.CChatMessagePacket;
import net.minecraft.network.play.client.CTabCompletePacket;
import net.minecraft.network.play.server.STabCompletePacket;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

import static com.dommilosz.utilmod.internalcommands.resetmods;

public class EventSubscribers {
	
	@SubscribeEvent
	public void packetDisplay(PacketEvent event) {

	}
	
	@SubscribeEvent
	public void incoming(PacketEvent.Incoming event) {
		IPacket packet = event.getPacket();
		packetEvents.onIncoming(event,packet);

		onEvery(event,"in");
	}
	
	@SubscribeEvent
	public void outgoing(PacketEvent.Outgoing event) {
		IPacket packet = event.getPacket();
		packetEvents.onOutgoing(event,packet);

		if(packet instanceof CChatMessagePacket) {
			String msg = ((CChatMessagePacket) packet).getMessage();
			packetEvents.onChat(msg,event,packet);
		}
		onEvery(event,"out");
	}
	List<IPacket> tmppackets = new ArrayList<IPacket>();
	public void onEvery(PacketEvent event,String direction){
		IPacket packet = event.getPacket();
		packetEvents.onEvery(event,packet,direction);

		if(packet instanceof STabCompletePacket)tmppackets.add(packet);
		if(packet instanceof CTabCompletePacket)tmppackets.add(packet);

	}
	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event) {
		resetmods();

	}
	@SubscribeEvent
	public void onWorldUnload(WorldEvent.Unload event){
		resetmods();
	}

	
}
