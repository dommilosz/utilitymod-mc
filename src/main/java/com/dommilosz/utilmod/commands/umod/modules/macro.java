package com.dommilosz.utilmod.commands.umod.modules;

import com.dommilosz.utilmod.packetIO;
import com.google.gson.Gson;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraft.util.math.MathHelper;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.dommilosz.utilmod.internalcommands.*;
import static com.dommilosz.utilmod.playerFunctions.*;

public class macro {

    public static boolean enabled;

    public static void execute(String msg, String[] args) {
        if (isElementOn(args, "macro", 2)) {
            if (!commandActions.isCompatible()) return;
            if (isElementOn(args, "load", 3)) {
                commandActions.CAload(getElementOn(args, 4));
            }
            if (isElementOn(args, "save", 3)) {
                commandActions.CAsave(getElementOn(args, 4));
            }
            if (isElementOn(args, "reset", 3)) {
                commandActions.CAreset();
            }
            if (isElementOn(args, "record", 3)) {
                commandActions.CArecord();
            }
            if (isElementOn(args, "endrecord", 3)) {
                commandActions.CAendrecord();
            }
            if (isElementOn(args, "play", 3)) {
                commandActions.CAplay();
            }
            if (isElementOn(args, "loadplay", 3)) {
                commandActions.CAloadplay(getElementOn(args, 4));
            }
            if (isElementOn(args, "rotloadplay", 3)) {
                commandActions.CArotloadplay(getElementOn(args, 4));

            }
        }
    }


    public static class MacroManager {
        public static List<RelPacket> loadedPackets;
        public static Pos aligninfo = new Pos();

        public static boolean load(String filename) {
            try {
                File file = new File(Minecraft.getInstance().gameDir.getAbsolutePath() + "/UMOD/macros/" + filename + ".mcr");
                if (!file.exists()) {
                    throw new Exception();
                }
                FileReader fr = new FileReader(file);
                Scanner s = new Scanner(fr);
                String aligninfostr = s.nextLine();
                JSONObject alignobj = new JSONObject(aligninfostr);
                aligninfo.X = alignobj.getDouble("X");
                aligninfo.Y = alignobj.getDouble("Y");
                aligninfo.Z = alignobj.getDouble("Z");
                aligninfo.Yaw = alignobj.getFloat("Yaw");
                aligninfo.Pitch = alignobj.getFloat("Pitch");
                Gson gson = new Gson();
                List<RelPacket> packets = new ArrayList<>();
                while (s.hasNextLine()) {
                    String jsonInString = s.nextLine();
                    String classname = jsonInString.split("####")[0];
                    String Properjson = jsonInString.split("####")[1];

                    JSONObject obj = new JSONObject(Properjson);

                    String packetjson = obj.getJSONObject("packet").toString();
                    IPacket p = (IPacket) gson.fromJson(packetjson, Class.forName(classname.split(" ")[1]));

                    RelPacket relPacket = new RelPacket(p, packets, obj.getInt("timediff"));
                }
                MacroManager.loadedPackets = packets;

            } catch (Exception ex) {
                packetIO.SendUMODMessageToClient("$errError when loading macro!");
                return false;
            }
            packetIO.SendUMODMessageToClient("$sucMacro successfully loaded!");
            return true;

        }

        public static void save(String filename) {
            try {
                File file = new File(Minecraft.getInstance().gameDir.getAbsolutePath() + "/UMOD");
                if (!file.exists()) file.mkdir();
                file = new File(Minecraft.getInstance().gameDir.getAbsolutePath() + "/UMOD/macros");
                if (!file.exists()) file.mkdir();
                file = new File(Minecraft.getInstance().gameDir.getAbsolutePath() + "/UMOD/macros/" + filename + ".mcr");
                if (file.exists()) {
                    file.delete();
                }
                file.createNewFile();
                FileWriter fw = new FileWriter(file);
                BufferedWriter writer = new BufferedWriter(fw);
                Gson gson = new Gson();
                JSONObject alignobj = new JSONObject();
                PlayerEntity pl = Minecraft.getInstance().player;
                alignobj.put("X", aligninfo.X);
                alignobj.put("Y", aligninfo.Y);
                alignobj.put("Z", aligninfo.Z);
                alignobj.put("Yaw", aligninfo.Yaw);
                alignobj.put("Pitch", aligninfo.Pitch);
                writer.append(alignobj.toString() + "\n");
                for (RelPacket p : MacroManager.loadedPackets) {
                    String json = gson.toJson(p);
                    writer.append(p.packet.getClass() + "####" + json + "\n");
                }

                writer.flush();
                writer.close();
            } catch (Exception ex) {
                packetIO.SendUMODMessageToClient("$errError when saving macro!");
                return;
            }

            packetIO.SendUMODMessageToClient("$sucMacro successfully saved!");
        }

        public static void RotateAndAlign() {
            double X = MacroManager.aligninfo.X;
            double Y = MacroManager.aligninfo.Y;
            double Z = MacroManager.aligninfo.Z;
            PlayerEntity player = Minecraft.getInstance().player;
            int blockX = MathHelper.floor(player.getPosX());
            int blockY = MathHelper.floor(player.getPosY()) - 1;
            int blockZ = MathHelper.floor(player.getPosZ());
            X += blockX;
            Y += blockY;
            Z += blockZ;

            float Yaw = MacroManager.aligninfo.Yaw;
            float Pitch = MacroManager.aligninfo.Pitch;


            double XP = player.getPosX();
            double YP = player.getPosY();
            double ZP = player.getPosZ();
            float YawP = player.getYaw(0);
            float PitchP = player.getPitch(0);
            boolean sneaking = Minecraft.getInstance().player.isSneaking();
            Minecraft.getInstance().player.setSneaking(true);
            boolean XGood = false;
            boolean YGood = false;
            boolean ZGood = false;
            while (XP != X || YP != Y || ZP != Z) {
                XP = player.getPosX();
                YP = player.getPosY();
                ZP = player.getPosZ();

                double XD = XP;
                double YD = YP;
                double ZD = ZP;


                if (XGood && YGood && ZGood) break;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (XP > X) {
                    if ((XP - X) < 0.1) {
                        XD = X;
                        XGood = true;
                    } else {
                        XD -= 0.1;
                    }
                }
                if (YP > Y) {
                    if ((YP - Y) < 0.1) {
                        YD = Y;
                        YGood = true;
                    } else {
                        YD -= 0.1;
                    }
                }
                if (ZP > Z) {
                    if ((ZP - Z) < 0.1) {
                        ZD = Z;
                        ZGood = true;
                    } else {
                        ZD -= 0.1;
                    }
                }
                if (XP < X) {
                    if ((X - XP) < 0.1) {
                        XD = X;
                        XGood = true;
                    } else {
                        XD += 0.1;
                    }
                }
                if (YP < Y) {
                    if ((Y - YP) < 0.1) {
                        YD = Y;
                        YGood = true;
                    } else {
                        YD += 0.1;
                    }
                }
                if (ZP < Z) {
                    if ((Z - ZP) < 0.1) {
                        ZD = Z;
                        ZGood = true;
                    } else {
                        ZD += 0.1;
                    }
                }
                PF_setPosition(XD, YD, ZD);
            }
            Minecraft.getInstance().player.setSneaking(sneaking);
            boolean YawGood = false;
            boolean PitchGood = false;
            PF_setRot(Yaw, Pitch);
        }

        public static List<try_jump.TJPacket> toTJPackets() {
            PlayerEntity player = Minecraft.getInstance().player;
            double X = player.getPosX();
            double Y = player.getPosY();
            double Z = player.getPosZ();
            double Yaw = player.getYaw(0);
            double Pitch = player.getPitch(0);
            if (loadedPackets == null) loadedPackets = new ArrayList<>();
            IPacket firstPacket = null;
            for (RelPacket p : MacroManager.loadedPackets) {
                if (p.packet instanceof CPlayerPacket.PositionRotationPacket) {
                    firstPacket = p.packet;
                    break;
                }
                if (p.packet instanceof CPlayerPacket.PositionPacket) {
                    firstPacket = p.packet;
                    break;
                }
            }
            double firstX = 0;
            double firstY = 0;
            double firstZ = 0;
            if (firstPacket instanceof CPlayerPacket.PositionPacket) {
                firstX = ((CPlayerPacket.PositionPacket) firstPacket).getX(0);
                firstY = ((CPlayerPacket.PositionPacket) firstPacket).getY(0);
                firstZ = ((CPlayerPacket.PositionPacket) firstPacket).getZ(0);
            }
            if (firstPacket instanceof CPlayerPacket.PositionRotationPacket) {
                firstX = ((CPlayerPacket.PositionRotationPacket) firstPacket).getX(0);
                firstY = ((CPlayerPacket.PositionRotationPacket) firstPacket).getY(0);
                firstZ = ((CPlayerPacket.PositionRotationPacket) firstPacket).getZ(0);
            }
            List<try_jump.TJPacket> tjPackets = new ArrayList<>();
            for (RelPacket p : MacroManager.loadedPackets) {
                boolean handled = false;
                if (p.packet instanceof CPlayerPacket.PositionPacket) {
                    double XP = ((CPlayerPacket.PositionPacket) p.packet).getX(0);
                    double YP = ((CPlayerPacket.PositionPacket) p.packet).getY(0);
                    double ZP = ((CPlayerPacket.PositionPacket) p.packet).getZ(0);
                    boolean onG = ((CPlayerPacket.PositionPacket) p.packet).isOnGround();
                    new try_jump.TJPacket(new CPlayerPacket.PositionPacket(XP + X, YP + Y, ZP + Z, onG), tjPackets, (int) p.timediff);
                    handled = true;
                }
                if (p.packet instanceof CPlayerPacket.PositionRotationPacket) {
                    double XP = ((CPlayerPacket.PositionRotationPacket) p.packet).getX(0);
                    double YP = ((CPlayerPacket.PositionRotationPacket) p.packet).getY(0);
                    double ZP = ((CPlayerPacket.PositionRotationPacket) p.packet).getZ(0);
                    float YawP = ((CPlayerPacket.PositionRotationPacket) p.packet).getYaw(0);
                    float PitchP = ((CPlayerPacket.PositionRotationPacket) p.packet).getPitch(0);
                    boolean onG = ((CPlayerPacket.PositionRotationPacket) p.packet).isOnGround();
                    new try_jump.TJPacket(new CPlayerPacket.PositionRotationPacket(XP + X, YP + Y, ZP + Z, YawP, PitchP, onG), tjPackets, (int) p.timediff);
                    handled = true;
                }
                if (!handled) {
                    new try_jump.TJPacket(p.packet, tjPackets, (int) p.timediff);
                }
            }
            return tjPackets;
        }

        public static List<RelPacket> toRelPackets(List<try_jump.TJPacket> TJpackets) {
            PlayerEntity player = Minecraft.getInstance().player;
            double X = player.getPosX();
            double Y = player.getPosY();
            double Z = player.getPosZ();
            double Yaw = player.getYaw(0);
            double Pitch = player.getPitch(0);

            IPacket firstPacket = null;
            for (try_jump.TJPacket p : TJpackets) {
                if (p.packet instanceof CPlayerPacket.PositionRotationPacket) {
                    firstPacket = p.packet;
                    break;
                }
                if (p.packet instanceof CPlayerPacket.PositionPacket) {
                    firstPacket = p.packet;
                    break;
                }
            }
            double firstX = 0;
            double firstY = 0;
            double firstZ = 0;
            if (firstPacket instanceof CPlayerPacket.PositionPacket) {
                firstX = ((CPlayerPacket.PositionPacket) firstPacket).getX(0);
                firstY = ((CPlayerPacket.PositionPacket) firstPacket).getY(0);
                firstZ = ((CPlayerPacket.PositionPacket) firstPacket).getZ(0);
            }
            if (firstPacket instanceof CPlayerPacket.PositionRotationPacket) {
                firstX = ((CPlayerPacket.PositionRotationPacket) firstPacket).getX(0);
                firstY = ((CPlayerPacket.PositionRotationPacket) firstPacket).getY(0);
                firstZ = ((CPlayerPacket.PositionRotationPacket) firstPacket).getZ(0);
            }
            List<RelPacket> tjPackets = new ArrayList<>();
            for (try_jump.TJPacket p : TJpackets) {
                boolean handled = false;
                if (p.packet instanceof CPlayerPacket.PositionPacket) {
                    double XP = ((CPlayerPacket.PositionPacket) p.packet).getX(0);
                    double YP = ((CPlayerPacket.PositionPacket) p.packet).getY(0);
                    double ZP = ((CPlayerPacket.PositionPacket) p.packet).getZ(0);
                    boolean onG = ((CPlayerPacket.PositionPacket) p.packet).isOnGround();
                    new RelPacket(new CPlayerPacket.PositionPacket(XP - firstX, YP - firstY, ZP - firstZ, onG), tjPackets, (int) p.timediff);
                    handled = true;
                }
                if (p.packet instanceof CPlayerPacket.PositionRotationPacket) {
                    double XP = ((CPlayerPacket.PositionRotationPacket) p.packet).getX(0);
                    double YP = ((CPlayerPacket.PositionRotationPacket) p.packet).getY(0);
                    double ZP = ((CPlayerPacket.PositionRotationPacket) p.packet).getZ(0);
                    float YawP = ((CPlayerPacket.PositionRotationPacket) p.packet).getYaw(0);
                    float PitchP = ((CPlayerPacket.PositionRotationPacket) p.packet).getPitch(0);
                    boolean onG = ((CPlayerPacket.PositionRotationPacket) p.packet).isOnGround();
                    new RelPacket(new CPlayerPacket.PositionRotationPacket(XP - firstX, YP - firstY, ZP - firstZ, YawP, PitchP, onG), tjPackets, (int) p.timediff);
                    handled = true;
                }
                if (!handled) {
                    new RelPacket(p.packet, tjPackets, (int) p.timediff);
                }
            }
            return tjPackets;
        }
    }

    public static class RelPacket {
        public IPacket packet;
        public long timediff;
        public long timestamp;

        public RelPacket(IPacket packet, List<RelPacket> packets) {
            RelPacket lastpacket = new RelPacket();
            long timestamp = System.currentTimeMillis();
            lastpacket.timediff = 0;
            lastpacket.timestamp = timestamp;
            this.timestamp = timestamp;
            if (packets.size() > 0) {
                lastpacket = packets.get(packets.size() - 1);
            }
            timediff = (timestamp - lastpacket.timestamp);
            this.packet = packet;
            packets.add(this);
        }

        public RelPacket() {

        }

        public RelPacket(IPacket packet, List<RelPacket> packets, int timediff) {
            this.timestamp = System.currentTimeMillis();
            this.timediff = timediff;
            this.packet = packet;
            packets.add(this);
        }
    }

    private static void start() {
        enabled = true;
        new Thread(new Runnable() {
            @Override
            public void run() {

                packetIO.SendUMODMessageToClient("Macro execution started");
                MacroManager.RotateAndAlign();
                try_jump.start();
                try_jump.TJpackets = MacroManager.toTJPackets();
                try_jump.executePackets();
                waitForEndOrDelay();
                enabled = false;
                packetIO.SendUMODMessageToClient("Macro execution ended");
            }
        }).start();

    }

    public static void waitForEndOrDelay() {
        while (try_jump.enabled) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean recording = false;

    public static void reset() {
        enabled = false;
    }

    public static class commandActions {
        public static boolean isCompatible() {
            if (try_jump.issuedEnabled) {
                packetIO.SendUMODMessageToClient("$errMacro is not compatible with TryJump");
                properexecuted = true;
                return false;
            }
            if (auto_try_jump.enabled) {
                packetIO.SendUMODMessageToClient("$errMacro is not compatible with AutoTryJump");
                properexecuted = true;
                return false;
            }
            return true;
        }

        public static void CAload(String filename) {
            if (try_jump.executing) {
                packetIO.SendUMODMessageToClient("$errMacro already Running");
                properexecuted = true;
                return;
            }
            if (filename.equals("")) {
                packetIO.SendUMODMessageToClient("$errInvalid macro file name");
                return;
            }
            MacroManager.load(filename);
            properexecuted = true;
            packetIO.SendUMODMessageToClient("$sucMacro Loaded");

        }

        public static void CAsave(String filename) {
            if (filename.equals("")) {
                packetIO.SendUMODMessageToClient("$errInvalid macro file name");
                return;
            }
            MacroManager.save(filename);
            properexecuted = true;
            packetIO.SendUMODMessageToClient("$sucMacro Saved to " + filename);

        }

        public static void CAreset() {
            if (try_jump.executing) {
                packetIO.SendUMODMessageToClient("Macro already executing -- Executing abort");
                try_jump.executingabort = true;
                properexecuted = true;
                enabled = false;
                try_jump.reset();
                return;
            }
            if (!enabled) {
                packetIO.SendUMODMessageToClient("$errMacro is not enabled");
                properexecuted = true;
                try_jump.reset();
                return;
            }
            enabled = false;
            properexecuted = true;
            try_jump.resetWithTP();
            packetIO.SendUMODMessageToClient("Macro Reset");
        }

        public static void CArecord() {
            recording = true;
            try_jump.start();
            PlayerEntity pl = Minecraft.getInstance().player;

            int blockX = MathHelper.floor(pl.getPosX());
            int blockY = MathHelper.floor(pl.getPosY()) - 1;
            int blockZ = MathHelper.floor(pl.getPosZ());

            MacroManager.aligninfo.X = pl.getPosX() - blockX;
            MacroManager.aligninfo.Y = pl.getPosY() - blockY;
            MacroManager.aligninfo.Z = pl.getPosZ() - blockZ;

            MacroManager.aligninfo.Yaw = pl.getYaw(0);
            MacroManager.aligninfo.Pitch = pl.getPitch(0);

            packetIO.SendUMODMessageToClient("Recording started");
            properexecuted = true;
        }

        public static void CAendrecord() {
            recording = false;
            MacroManager.loadedPackets = MacroManager.toRelPackets(try_jump.TJpackets);
            try_jump.resetWithTP();
            packetIO.SendUMODMessageToClient("Recording stopped");
            properexecuted = true;
            MacroManager.save("0tmp01134");
            MacroManager.load("0tmp01134");
        }

        public static void CAplay() {
            start();
            packetIO.SendUMODMessageToClient("Playback started.");
            properexecuted = true;
        }

        public static void CAloadplay(String filename) {
            if (try_jump.executing) {
                packetIO.SendUMODMessageToClient("$errMacro already Running");
                properexecuted = true;
                return;
            }
            if (filename.equals("")) {
                packetIO.SendUMODMessageToClient("$errInvalid macro file name");
                return;
            }
            if (!MacroManager.load(filename)) return;
            properexecuted = true;
            packetIO.SendUMODMessageToClient("$sucMacro Loaded");
            start();
            packetIO.SendUMODMessageToClient("Playback started.");
            properexecuted = true;
        }

        public static void CArotloadplay(String filename) {
            if (try_jump.executing) {
                packetIO.SendUMODMessageToClient("$errMacro already Running");
                properexecuted = true;
                return;
            }
            if (filename.equals("")) {
                packetIO.SendUMODMessageToClient("$errInvalid macro file name");
                return;
            }
            String rotationStr = PF_getRotationString();

            if (!MacroManager.load(filename + "_" + rotationStr)) return;
            properexecuted = true;
            packetIO.SendUMODMessageToClient("$sucMacro Loaded");
            start();
            packetIO.SendUMODMessageToClient("Playback started.");
            properexecuted = true;
        }
    }
}
