package com.dommilosz.utilmod.commands.umod.modules;

import com.dommilosz.utilmod.packetIO;

import static com.dommilosz.utilmod.internalcommands.*;

public class auto_try_jump {

    public static boolean enabled;
    public static int delay = 5000;
    public static int delayToGo(){
        long timestamp = System.currentTimeMillis();
        return (int) (timestamp-started);
    }
    public static long started = 0;

    public static void execute(String msg, String[] args) {
        if (isElementOn(args, "autotj", 2)) {
            if(try_jump.issuedEnabled){
                packetIO.SendMessageToClient("[UMOD] AutoTryjump is not compatible with TryJump");
                properexecuted = true;
                return;
            }
            if(macro.enabled){
                packetIO.SendMessageToClient("[UMOD] AutoTryjump is not compatible with Macro");
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
                if(try_jump.executing){
                    packetIO.SendMessageToClient("[UMOD] AutoTryjump already executing -- Executing abort");
                    try_jump.executingabort = true;
                    properexecuted = true;
                    enabled = false;
                    try_jump.reset();
                    return;
                }
                if(!enabled){
                    packetIO.SendMessageToClient("[UMOD] AutoTryjump is not enabled");
                    properexecuted = true;
                    try_jump.reset();
                    return;
                }
                enabled = false;
                properexecuted = true;
                try_jump.resetWithTP();
                packetIO.SendMessageToClient("[UMOD] AutoTryjump Reset");
            }
            if (isElementOn(args, "back", 3)) {
                if(try_jump.executing){
                    packetIO.SendMessageToClient("[UMOD] AutoTryjump already executing -- Executing abort");
                    try_jump.executingabort = true;
                    properexecuted = true;
                    try_jump.reset();
                    return;
                }
                if(!enabled){
                    packetIO.SendMessageToClient("[UMOD] AutoTryjump is not enabled");
                    properexecuted = true;
                    try_jump.reset();
                    return;
                }
                properexecuted = true;
                try_jump.resetWithTP();
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
                    try_jump.start();
                    waitForEndOrDelay();
                    if(!enabled)return;
                    try_jump.executePacketsSync();
                }
                try_jump.reset();
            }
        }).start();

    }
    public static void waitForEndOrDelay() {
        long timestamp = System.currentTimeMillis();
        long timestampANDdelay = timestamp+delay;
        while (timestamp<timestampANDdelay){
            if(!try_jump.enabled){
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
