//? if neoforge {
/*package dev.spagurder.htn.loaders.neoforge;

import dev.spagurder.htn.HTNCommands;
import dev.spagurder.htn.HardcoreTotemNerf;
import dev.spagurder.htn.client.S2CPayloadHandler;
import dev.spagurder.htn.data.HTNState;
import dev.spagurder.htn.event.PlayerDeathHandler;
import dev.spagurder.htn.network.C2SHelloPayload;
import dev.spagurder.htn.network.C2SPayloadHandler;
import dev.spagurder.htn.network.S2CInsightsPayload;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@Mod(HardcoreTotemNerf.MOD_ID)
@EventBusSubscriber
public class NeoforgeEntrypoint {

    public NeoforgeEntrypoint() {
        HardcoreTotemNerf.initialize();
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            HTNState.loadPlayerData(player, player.getUUID());
        }
    }

    @SubscribeEvent
    public static void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            HTNState.unloadAndSavePlayerData(player.getUUID());
        }
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            PlayerDeathHandler.onPlayerDeath(player);
        }
    }

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        HTNCommands.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onRegisterPayloadHandlersEvent(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playToServer(
                C2SHelloPayload.TYPE,
                C2SHelloPayload.CODEC,
                (payload, context) -> {
                    C2SPayloadHandler.onHello(payload, (ServerPlayer) context.player());
                }
        );
        registrar.playToClient(
                S2CInsightsPayload.TYPE,
                S2CInsightsPayload.CODEC
                //? <= 1.21.6 {
                /^, (payload, context) -> S2CPayloadHandler.onInsights(payload, (LocalPlayer) context.player())
                ^///?}
        );
    }

}
*///?}
