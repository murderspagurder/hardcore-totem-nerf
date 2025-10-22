//? if fabric {
package dev.spagurder.htn.loaders.fabric;

import dev.spagurder.htn.HTNCommands;
import dev.spagurder.htn.HardcoreTotemNerf;
import dev.spagurder.htn.data.HTNState;
import dev.spagurder.htn.event.PlayerDeathHandler;
import dev.spagurder.htn.network.C2SHelloPayload;
import dev.spagurder.htn.network.C2SPayloadHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

//? >=1.21 {
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import dev.spagurder.htn.network.S2CInsightsPayload;
//?}

public class FabricEntrypoint implements ModInitializer {

    public static MinecraftServer server;

    @Override
    public void onInitialize() {
        HardcoreTotemNerf.initialize();
        initializeNetworking();
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            HTNState.loadPlayerData(handler.getPlayer(), handler.getPlayer().getUUID());
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

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            FabricEntrypoint.server = server;
        });
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> {
            FabricEntrypoint.server = null;
        });
    }

    public void initializeNetworking() {
        //? >=1.21 {
        PayloadTypeRegistry.playC2S().register(C2SHelloPayload.TYPE, C2SHelloPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(S2CInsightsPayload.TYPE, S2CInsightsPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(C2SHelloPayload.TYPE, (payload, context) -> {
            C2SPayloadHandler.onHello(payload, context.player());
        });
        //?} else {
        /*ServerPlayNetworking.registerGlobalReceiver(
                C2SHelloPayload.HELLO_PAYLOAD_ID,
                (serv, player, handler, buf, responseSender) -> {
                    String modVersion = buf.readUtf();
                    C2SPayloadHandler.onHello(new C2SHelloPayload(modVersion), player);
                }
        );
        *///?}
    }

}
//?}
