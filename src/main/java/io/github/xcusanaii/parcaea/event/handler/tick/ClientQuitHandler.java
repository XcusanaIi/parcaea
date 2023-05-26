package io.github.xcusanaii.parcaea.event.handler.tick;

import io.github.xcusanaii.parcaea.event.TickHandler;
import io.github.xcusanaii.parcaea.io.SegmentLoader;
import io.github.xcusanaii.parcaea.model.config.ConfigLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientDisconnectionFromServerEvent;

public class ClientQuitHandler {

    @SubscribeEvent
    public void onClientDisconnect(ClientDisconnectionFromServerEvent event) {
        TickHandler.advInputHandler.tweakSprint();
        ConfigLoader.saveConfig();
        SegmentLoader.saveSegment();
    }
}
