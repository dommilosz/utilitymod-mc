package com.dommilosz.utilmod.commands.umod.modules;

import com.dommilosz.utilmod.packetIO;

import static com.dommilosz.utilmod.internalcommands.*;

public class auto_try_jump {

    public static boolean enabled;
    public static int delay = 5000;
    public static int minVal = 500;
    public static int maxVal = 60000;

    public static int delayToGo(){
        long timestamp = System.currentTimeMillis();
        return (int) (timestamp-started);
    }
    public static long started = 0;

    public static void execute(String msg, String[] args) {
        if (isElementOn(args, "autotj", 2)) {
            if(!commandActions.isCompatible())return;
            if (isElementOn(args, "start", 3)) {
                commandActions.CAstart();
            }
            if (isElementOn(args, "delay", 3)) {
                commandActions.CAsetDelay(getElementOn(args,4));

            }
            if (isElementOn(args, "reset", 3)) {
                commandActions.CAreset();
            }
            if (isElementOn(args, "back", 3)) {
                commandActions.CAback();
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
    public static class commandActions{
        public static void CAstart(){
            if(enabled){
                packetIO.SendUMODMessageToClient("$errAutoTryjump already Running");
                properexecuted = true;
                return;
            }
            start();
            properexecuted = true;
            packetIO.SendUMODMessageToClient("AutoTryjump Started");
        }
        public static void CAsetDelay(String delayString){
            properexecuted = true;
            if(delayString.equals("")){
                packetIO.SendUMODMessageToClient("AutoTryjump delay is: $&b"+delay+"ms");
                properexecuted = true;
                return;
            }
            if(enabled){
                packetIO.SendUMODMessageToClient("$errAutoTryjump already Running");
                properexecuted = true;
                return;
            }
            try {
                String ds = delayString;
                int di = Integer.parseInt(ds);
                if(di>=minVal&&di<=maxVal){
                    delay = di;
                    packetIO.SendUMODMessageToClient("AutoTryjump delay is now: $&b"+delay+"ms");
                    return;
                }
            }catch (Exception ex){
                packetIO.SendUMODMessageToClient("$errAutoTryjump delay was not int");
                return;
            }
            packetIO.SendUMODMessageToClient("$errAutoTryjump delay was not in range ["+minVal+"-"+maxVal+"] ms");

        }
        public static boolean isCompatible(){
            if(try_jump.issuedEnabled){
                packetIO.SendUMODMessageToClient("$errAutoTryjump is not compatible with TryJump");
                properexecuted = true;
                return false;
            }
            if(macro.enabled){
                packetIO.SendUMODMessageToClient("$errAutoTryjump is not compatible with Macro");
                properexecuted = true;
                return false;
            }
            return true;
        }
        public static void CAreset(){
            if(try_jump.executing){
                packetIO.SendUMODMessageToClient("AutoTryjump already executing -- Executing abort");
                try_jump.executingabort = true;
                properexecuted = true;
                enabled = false;
                try_jump.reset();
                return;
            }
            if(!enabled){
                packetIO.SendUMODMessageToClient("$errAutoTryjump is not enabled");
                properexecuted = true;
                try_jump.reset();
                return;
            }
            enabled = false;
            properexecuted = true;
            try_jump.resetWithTP();
            packetIO.SendUMODMessageToClient("AutoTryjump Reset");
        }
        public static void CAback(){
            if(try_jump.executing){
                packetIO.SendUMODMessageToClient("AutoTryjump already executing -- Executing abort");
                try_jump.executingabort = true;
                properexecuted = true;
                try_jump.reset();
                return;
            }
            if(!enabled){
                packetIO.SendUMODMessageToClient("$errAutoTryjump is not enabled");
                properexecuted = true;
                try_jump.reset();
                return;
            }
            properexecuted = true;
            try_jump.resetWithTP();
            packetIO.SendUMODMessageToClient("AutoTryjump Backed");
        }
    }
}
