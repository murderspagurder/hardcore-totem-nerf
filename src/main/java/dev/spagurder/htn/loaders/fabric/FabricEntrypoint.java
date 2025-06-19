//? if fabric {
package dev.spagurder.htn.loaders.fabric;

import dev.spagurder.htn.HTNCommands;
import dev.spagurder.htn.HardcoreTotemNerf;
import dev.spagurder.htn.data.HTNState;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

public class FabricEntrypoint implements ModInitializer {

    @Override
    public void onInitialize() {
        HardcoreTotemNerf.initialize();
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            HTNState.loadPlayerData(handler.getPlayer().getUUID());
        });
        ServerPlayConnectionEvents.DISCONNECT.register((handler, sender) -> {
            HTNState.unloadAndSavePlayerData(handler.getPlayer().getUUID());
        });
        CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> {
            HTNCommands.register(dispatcher);
        }));
    }

}
//?}
