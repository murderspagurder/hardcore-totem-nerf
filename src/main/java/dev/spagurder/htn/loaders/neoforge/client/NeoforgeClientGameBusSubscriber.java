//? if neoforge {
/*package dev.spagurder.htn.loaders.neoforge.client;

import dev.spagurder.htn.HardcoreTotemNerf;
import dev.spagurder.htn.client.KeyMappings;
import eu.midnightdust.lib.config.MidnightConfig;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

@EventBusSubscriber(
        modid = HardcoreTotemNerf.MOD_ID,
        bus = EventBusSubscriber.Bus.GAME,
        value = Dist.CLIENT
)
public class NeoforgeClientGameBusSubscriber {

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        while (KeyMappings.CONFIG_SCREEN.consumeClick()) {
            Minecraft client = Minecraft.getInstance();
            if (client.player == null || client.level == null) return;
            client.setScreen(
                    MidnightConfig.getScreen(client.screen, HardcoreTotemNerf.MOD_ID)
            );
        }
    }

}
*///?}