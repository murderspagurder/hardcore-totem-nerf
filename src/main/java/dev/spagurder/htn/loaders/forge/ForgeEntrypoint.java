//? if forge {
/*package dev.spagurder.htn.loaders.forge;

import dev.spagurder.htn.HTNCommands;
import dev.spagurder.htn.HardcoreTotemNerf;
import dev.spagurder.htn.client.KeyMappings;
import dev.spagurder.htn.data.HTNState;
import dev.spagurder.htn.event.PlayerDeathHandler;
import eu.midnightdust.lib.config.MidnightConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(HardcoreTotemNerf.MOD_ID)
public class ForgeEntrypoint {

    @SuppressWarnings("removal")
    public ForgeEntrypoint() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        HardcoreTotemNerf.initialize();
    }

    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            HTNState.loadPlayerData(player.getUUID());
        }
    }

    @SubscribeEvent
    public void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            HTNState.unloadAndSavePlayerData(player.getUUID());
        }
    }

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            PlayerDeathHandler.onPlayerDeath(player);
        }
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        HTNCommands.register(event.getDispatcher());
    }

    @Mod.EventBusSubscriber(
            modid = HardcoreTotemNerf.MOD_ID,
            bus = Mod.EventBusSubscriber.Bus.MOD,
            value = Dist.CLIENT)
    public static class ClientModBusSubscriber {
        @SubscribeEvent
        public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
            event.register(KeyMappings.CONFIG_SCREEN);
        }
    }

    @Mod.EventBusSubscriber(
            modid = HardcoreTotemNerf.MOD_ID,
            bus = Mod.EventBusSubscriber.Bus.FORGE,
            value = Dist.CLIENT)
    public static class ClientForgeBusSubscriber {
        @SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event) {
            if (event.phase == TickEvent.Phase.END) {
                while (KeyMappings.CONFIG_SCREEN.consumeClick()) {
                    Minecraft client = Minecraft.getInstance();
                    if (client.player == null || client.level == null) return;
                    client.setScreen(
                            MidnightConfig.getScreen(client.screen, HardcoreTotemNerf.MOD_ID)
                    );
                }
            }
        }
    }

}
*///?}
