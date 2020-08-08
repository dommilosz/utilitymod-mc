package com.dommilosz.utilmod.commands.umod.modules;

import com.dommilosz.utilmod.packetIO;

import static com.dommilosz.utilmod.internalcommands.*;

public class autotryjump {

    public static boolean enabled;
    public static int delay = 5000;
    public static int delayToGo(){
        long timestamp = System.currentTimeMillis();
        return (int) (timestamp-started);
    }
    public static long started = 0;

    public static void execute(String msg, String[] args) {
        if (isElementOn(args, "autotj", 2)) {
            if(tryjump.issuedEnabled){
                packetIO.SendMessageToClient("[UMOD] AutoTryjump is not compatible with TryJump");
                properexecuted = true;
                return;
            }
            if (isElementOn(args, "start", 3)) {
                if(enabled){
                    packetIO.SendMessageToClient("[UMOD] AutoTryjump already Running");
                    properexecuted = true;
                    return;
                }
                start();
                properexecuted = true;
                packetIO.SendMessageToClient("[UMOD] AutoTryjump Started");

            }
            if (isElementOn(args, "delay", 3)) {
                properexecuted = true;
                if(isElementOn(args,"",4)){
                    packetIO.SendMessageToClient("[UMOD] AutoTryjump delay is: "+delay+"ms");
                    properexecuted = true;
                    return;
                }
                if(enabled){
                    packetIO.SendMessageToClient("[UMOD] AutoTryjump already Running");
                    properexecuted = true;
                    return;
                }
                try {
                    String ds = getElementOn(args,4);
                    int di = Integer.parseInt(ds);
                    if(di>=500&&di<=30000){
                        delay = di;
                        packetIO.SendMessageToClient("[UMOD] AutoTryjump delay is now: "+delay+"ms");
                        return;
                    }
                }catch (Exception ex){
                    packetIO.SendMessageToClient("[UMOD] AutoTryjump delay was not int");
                    return;
                }
                packetIO.SendMessageToClient("[UMOD] AutoTryjump delay was not in range [500-30000] ms");


            }
            if (isElementOn(args, "reset", 3)) {
                if(tryjump.executing){
                    packetIO.SendMessageToClient("[UMOD] AutoTryjump already executing -- Executing abort");
                    tryjump.executingabort = true;
                    properexecuted = true;
                    enabled = false;
                    tryjump.reset();
                    return;
                }
                if(!enabled){
                    packetIO.SendMessageToClient("[UMOD] AutoTryjump is not enabled");
                    properexecuted = true;
                    tryjump.reset();
                    return;
                }
                enabled = false;
                properexecuted = true;
                tryjump.resetWithTP();
                packetIO.SendMessageToClient("[UMOD] AutoTryjump Reset");
            }
            if (isElementOn(args, "back", 3)) {
                if(tryjump.executing){
                    packetIO.SendMessageToClient("[UMOD] AutoTryjump already executing -- Executing abort");
                    tryjump.executingabort = true;
                    properexecuted = true;
                    tryjump.reset();
                    return;
                }
                if(!enabled){
                    packetIO.SendMessageToClient("[UMOD] AutoTryjump is not enabled");
                    properexecuted = true;
                    tryjump.reset();
                    return;
                }
                properexecuted = true;
                tryjump.resetWithTP();
                packetIO.SendMessageToClient("[UMOD] AutoTryjump Backed");
            }
        }
    }

    private static void start() {
        enabled = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (enabled) {
                    long timestamp = System.currentTimeMillis();
                    started = timestamp;
                    tryjump.start();
                    waitForEndOrDelay();
                    if(!enabled)return;
                    tryjump.executePacketsSync();
                }
                tryjump.reset();
            }
        }).start();

    }
    public static void waitForEndOrDelay() {
        long timestamp = System.currentTimeMillis();
        long timestampANDdelay = timestamp+delay;
        while (timestamp<timestampANDdelay){
            if(!tryjump.enabled){
                return;
            }

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            timestamp = System.currentTimeMillis();
        }
    }
    public static void reset(){
        enabled = false;
    }
}