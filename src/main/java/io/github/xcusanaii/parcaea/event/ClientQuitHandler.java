package io.github.xcusanaii.parcaea.event;

import io.github.xcusanaii.parcaea.model.config.ConfigLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientDisconnectionFromServerEvent;

public class ClientQuitHandler {

    @SubscribeEvent
    public void onClientDisconnect(ClientDisconnectionFromServerEvent event) {
        TickHandler.advancedInputHandler.tweakSprint();
        ConfigLoader.saveConfig();
    }
}
