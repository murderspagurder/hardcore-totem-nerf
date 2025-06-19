//? if neoforge {
/*package dev.spagurder.htn.loaders.neoforge;

import dev.spagurder.htn.HTNCommands;
import dev.spagurder.htn.HardcoreTotemNerf;
import dev.spagurder.htn.data.HTNState;
import dev.spagurder.htn.event.PlayerDeathHandler;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
//? if >=1.20.5 {
import net.neoforged.fml.common.EventBusSubscriber;
//?}
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

//? if >=1.20.5 {
@Mod(HardcoreTotemNerf.MOD_ID)
@EventBusSubscriber
//?} else {
/^@Mod.EventBusSubscriber(modid = HardcoreTotemNerf.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
^///?}
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

}
*///?}
