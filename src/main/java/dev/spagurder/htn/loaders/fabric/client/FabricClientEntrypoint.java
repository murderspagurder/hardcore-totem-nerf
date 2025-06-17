//? if fabric {
/*package dev.spagurder.htn.loaders.fabric.client;

import dev.spagurder.htn.HardcoreTotemNerf;
import dev.spagurder.htn.client.KeyMappings;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

public class FabricClientEntrypoint  implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        KeyBindingHelper.registerKeyBinding(KeyMappings.CONFIG_SCREEN);
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (KeyMappings.CONFIG_SCREEN.consumeClick()) {
                client.setScreen(
                        MidnightConfig.getScreen(client.screen, HardcoreTotemNerf.MOD_ID)
                );
            }
        });
    }

}
*///?}