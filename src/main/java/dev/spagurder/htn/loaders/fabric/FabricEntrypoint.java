//? if fabric {
package dev.spagurder.htn.loaders.fabric;

import dev.spagurder.htn.HTNCommands;
import dev.spagurder.htn.HardcoreTotemNerf;
import dev.spagurder.htn.data.HTNState;
import dev.spagurder.htn.event.PlayerDeathHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.level.ServerPlayer;

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
        ServerLivingEntityEvents.AFTER_DEATH.register(((entity, damageSource) -> {
            if (entity instanceof ServerPlayer player) {
                PlayerDeathHandler.onPlayerDeath(player);
            }
        }));
        CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> {
            HTNCommands.register(dispatcher);
        }));
    }

}
//?}
