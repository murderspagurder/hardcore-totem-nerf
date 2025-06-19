//? if neoforge {
/*package dev.spagurder.htn.loaders.neoforge.client;

import dev.spagurder.htn.HardcoreTotemNerf;
import dev.spagurder.htn.client.KeyMappings;
import eu.midnightdust.lib.config.MidnightConfig;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
//? if >= 1.20.5 {
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
//?} else {
/^import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.TickEvent;
^///?}

//? if >=1.20.5 {
@EventBusSubscriber(
        modid = HardcoreTotemNerf.MOD_ID,
        bus = EventBusSubscriber.Bus.GAME,
        value = Dist.CLIENT
)
//?} else {
/^@Mod.EventBusSubscriber(
        modid = HardcoreTotemNerf.MOD_ID,
        bus = Mod.EventBusSubscriber.Bus.FORGE,
        value = Dist.CLIENT
)
^///?}
public class NeoforgeClientGameBusSubscriber {

    //? if >=1.20.5 {
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        processConfigKey();
    }
    //?} else {
    /^@SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            processConfigKey();
        }
    }
    ^///?}

    private static void processConfigKey() {
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