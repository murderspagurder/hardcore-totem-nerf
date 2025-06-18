//? if neoforge {
/*package dev.spagurder.htn.loaders.neoforge;

import dev.spagurder.htn.HardcoreTotemNerf;
import dev.spagurder.htn.data.HTNState;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@Mod(HardcoreTotemNerf.MOD_ID)
@EventBusSubscriber
public class NeoforgeEntrypoint {

    public NeoforgeEntrypoint() {
        HardcoreTotemNerf.initialize();
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            HTNState.loadPlayerData(player.getUUID());
        }
    }

    @SubscribeEvent
    public static void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            HTNState.unloadAndSavePlayerData(player.getUUID());
        }
    }

}
*///?}
