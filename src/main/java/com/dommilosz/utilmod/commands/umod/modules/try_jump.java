package com.dommilosz.utilmod.commands.umod.modules;

import com.dommilosz.utilmod.packetIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.network.play.client.CPlayerAbilitiesPacket;
import net.minecraft.network.play.client.CPlayerPacket;
import com.dommilosz.utilmod.packetevent.PacketEvent;

import java.util.ArrayList;
import java.util.List;

import static com.dommilosz.utilmod.internalcommands.isElementOn;
import static com.dommilosz.utilmod.internalcommands.properexecuted;
import static com.dommilosz.utilmod.colorhandler.Color;

public class try_jump {
	public static boolean enabled = false;
	public static boolean issuedEnabled = false;
	public static boolean executing = false;
	public static boolean executingabort = false;
	public static long startedtime = 0;

	public static void execute(String msg, String[] args) {
		if (isElementOn(args, "tryjump", 2)) {
			if(auto_try_jump.enabled){
				packetIO.SendMessageToClient("[UMOD] Tryjump is not compatible with AutoTryJump");
				properexecuted = true;
				return;
			}
			if (isElementOn(args, "start", 3)) {
				if(enabled){
					packetIO.SendMessageToClient("[UMOD] Tryjump already Running");
					properexecuted = true;
					return;
				}
				start();
				issuedEnabled = true;
				properexecuted = true;
				packetIO.SendMessageToClient("[UMOD] Tryjump Started");

			}
			if (isElementOn(args, "do", 3)) {
				if(executing){
					packetIO.SendMessageToClient("[UMOD] Tryjump already Executing");
					properexecuted = true;
					return;
				}
				packetIO.SendMessageToClient("[UMOD] Tryjump Executing Started");
				executePackets();
				properexecuted = true;
				packetIO.SendMessageToClient("[UMOD] Tryjump Executing Ended");
			}
			if (isElementOn(args, "reset", 3)) {
				if(executing){
					packetIO.SendMessageToClient("[UMOD] Tryjump already executing -- Executing abort");
					executingabort = true;
					properexecuted = true;
					reset();
					return;
				}
				if(!enabled){
					packetIO.SendMessageToClient("[UMOD] Tryjump is not enabled");
					properexecuted = true;
					reset();
					return;
				}
				issuedEnabled = false;
				properexecuted = true;
				resetWithTP();
				packetIO.SendMessageToClient("[UMOD] Tryjump Reset");
			}
		}
	}
	public static void executePackets(){
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				executePacketsSync();
			}
		});
		t.start();
	}
	public static void executePacketsSync(){
		if(executing)return;
		executing = true;
		executingabort = false;
		Minecraft.getInstance().player.setNoGravity(true);
		backToStart();
		for (int i = 0; i < TJPacket.packets.size(); i++) {
			TJPacket p = TJPacket.packets.get(i);

			if(executingabort){break;}
			IPacket packet = p.packet;
			try {
				Thread.sleep(p.timediff);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			TJPacket.allowedpackets.add(packet);
			packetIO.SendPacketToServer(packet);
			String msg = "";
			if(i+1==TJPacket.packets.size()) msg = Color.BLUE+ "Sending packets: ["+Color.YELLOW+"DONE"+Color.BLUE+"]";
			else msg = Color.BLUE+"Sending packets: ["+Color.YELLOW + (i + 1) + "/" + TJPacket.packets.size() + Color.BLUE+"] - "+Color.YELLOW+toGotime(i)+"ms";

			packetIO.SendStatusMessageToClient(msg,true);
			if(packet instanceof CPlayerPacket.PositionPacket){
				Minecraft.getInstance().player.setPosition(((CPlayerPacket.PositionPacket) packet).getX(0),((CPlayerPacket.PositionPacket) packet).getY(0),((CPlayerPacket.PositionPacket) packet).getZ(0));
				exelastPos.X = ((CPlayerPacket.PositionPacket) packet).getX(0);
				exelastPos.Y = ((CPlayerPacket.PositionPacket) packet).getY(0);
				exelastPos.Z = ((CPlayerPacket.PositionPacket) packet).getZ(0);
			}
			if(packet instanceof CPlayerPacket.RotationPacket){
				Minecraft.getInstance().player.setPositionAndRotation(exelastPos.X,exelastPos.Y,exelastPos.Z,((CPlayerPacket.RotationPacket) packet).getYaw(0),((CPlayerPacket.RotationPacket) packet).getPitch(0));
			}
			if(packet instanceof CPlayerPacket.PositionRotationPacket){
				Minecraft.getInstance().player.setPositionAndRotation(((CPlayerPacket.PositionRotationPacket) packet).getX(0),((CPlayerPacket.PositionRotationPacket) packet).getY(0),((CPlayerPacket.PositionRotationPacket) packet).getZ(0),((CPlayerPacket.PositionRotationPacket) packet).getYaw(0),((CPlayerPacket.PositionRotationPacket) packet).getPitch(0));
			}
		}
		Minecraft.getInstance().player.setNoGravity(false);
		reset();
		executingabort = false;
		executing = false;
	}



	public static void start(){
		startedtime = System.currentTimeMillis();
		if(enabled)return;
		ClientPlayerEntity playerEntity = Minecraft.getInstance().player;
		lastPos.X= playerEntity.getPosX();
		lastPos.Y= playerEntity.getPosY();
		lastPos.Z= playerEntity.getPosZ();
		lastPos.Pitch = playerEntity.getPitch(0);
		lastPos.Yaw = playerEntity.getYaw(0);
		lastPos.onGround = playerEntity.onGround;
		enabled = true;
	}
	public static void reset(){
		TJPacket.packets.clear();
		executing = false;
		enabled = false;
		issuedEnabled = false;
		if(Minecraft.getInstance().player!=null){
			Minecraft.getInstance().player.setNoGravity(false);
		}

	}
	public static void resetWithTP(){
		backToStart();
		reset();
	}
	public static void backToStart(){
		if(enabled){
			setPosRot(lastPos.X,lastPos.Y,lastPos.Z,lastPos.Yaw,lastPos.Pitch);
		}
	}
	public static class lastPos{
		public static double X = 0;
		public static double Y = 0;
		public static double Z = 0;
		public static float Pitch = 0;
		public static float Yaw = 0;
		public static boolean onGround = true;
	}
	public static class exelastPos{
		public static double X = 0;
		public static double Y = 0;
		public static double Z = 0;
		public static float Pitch = 0;
		public static float Yaw = 0;
		public static boolean onGround = true;
	}
	public static void ModifyPacketTryJump(IPacket packet, PacketEvent event){
		if(enabled){
			if(TJPacket.allowedpackets.contains(packet)){
				TJPacket.allowedpackets.remove(packet);
				return;
			}
			if(packet instanceof CPlayerPacket.PositionPacket){
				if(executing){event.setCanceled(true);return;}
				new TJPacket(packet);
				event.setPacket(new CPlayerPacket.PositionPacket(lastPos.X,lastPos.Y,lastPos.Z,lastPos.onGround));

			}
			if(packet instanceof CPlayerAbilitiesPacket){
				if(executing){event.setCanceled(true);return;}
				new TJPacket(packet);
				event.setCanceled(true);
			}
			if(packet instanceof CPlayerPacket.RotationPacket){
				if(executing){event.setCanceled(true);return;}
				new TJPacket(packet);
				//event.setPacket(new CPlayerPacket.RotationPacket(lastPos.Pitch,lastPos.Yaw,lastPos.onGround));
				event.setCanceled(true);
			}
			if(packet instanceof CPlayerPacket.PositionRotationPacket){
				if(executing){event.setCanceled(true);return;}
				new TJPacket(packet);
				//event.setPacket(new CPlayerPacket.PositionRotationPacket(lastPos.X,lastPos.Y,lastPos.Z,lastPos.Pitch,lastPos.Yaw,lastPos.onGround));
				event.setCanceled(true);
			}
			if(packet instanceof CEntityActionPacket){
				if(executing){event.setCanceled(true);return;}
				new TJPacket(packet);
				event.setCanceled(true);
			}
		}

	}
	public static class TJPacket{
		public static List<TJPacket> packets = new ArrayList<TJPacket>();
		public static List<IPacket> allowedpackets = new ArrayList<IPacket>();
		public IPacket packet;
		public long timediff;
		public long timestamp;
		public TJPacket(IPacket packet){
			TJPacket lastpacket = new TJPacket();
			long timestamp = System.currentTimeMillis();
			lastpacket.timediff = 0;
			lastpacket.timestamp = timestamp;
			this.timestamp = timestamp;
			if(packets.size()>0){
				lastpacket = packets.get(packets.size() - 1);
			}
			timediff = (timestamp-lastpacket.timestamp);
			this.packet = packet;
			String msg = Color.RED+ "Recording packets: ["+Color.YELLOW+TJPacket.packets.size()+Color.RED+"] - "+Color.YELLOW+pasttime()+"ms";
			packetIO.SendStatusMessageToClient(msg,true);
			packets.add(this);
		}

		public TJPacket(){

		}
	}
	public static int pasttime() {
		long timestamp = System.currentTimeMillis();
		return (int) (timestamp-startedtime);
	}
	public static int toGotime(int i) {
		List<TJPacket> packets = TJPacket.packets;
		int sumtime = 0;
		for (; i < packets.size(); i++) {
			TJPacket packet = packets.get(i);
			sumtime+=packet.timediff;
		}
		return sumtime;
	}
	public static void setPosition(double x,double y,double z){
		Minecraft.getInstance().player.setPosition(x,y,z);

	}
	public static void setPosRot(double x,double y,double z,float yaw,float pitch){
		Minecraft.getInstance().player.setPositionAndRotation(x,y,z,yaw,pitch);
	}
	public static void setRot(float yaw,float pitch){
		ClientPlayerEntity playerEntity = Minecraft.getInstance().player;
		double x = playerEntity.getPosX();
		double y= playerEntity.getPosY();
		double z= playerEntity.getPosZ();
		Minecraft.getInstance().player.setPositionAndRotation(x,y,z,yaw,pitch);
	}
}
