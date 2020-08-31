package com.dommilosz.utilmod;


import com.dommilosz.utilmod.packetevent.EventSubscribers;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import com.dommilosz.utilmod.packetevent.ChannelHandlerInput;

@Mod("umod")
public class umod {
    public umod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
    }

    private void doClientStuff(FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new EventSubscribers());
        MinecraftForge.EVENT_BUS.register(new ChannelHandlerInput());
    }
}
