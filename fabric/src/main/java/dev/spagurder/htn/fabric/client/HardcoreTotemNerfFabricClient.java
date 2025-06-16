package dev.spagurder.htn.fabric.client;

import dev.spagurder.htn.client.HardcoreTotemNerfClient;
import net.fabricmc.api.ClientModInitializer;

public class HardcoreTotemNerfFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        HardcoreTotemNerfClient.init();
    }

}
