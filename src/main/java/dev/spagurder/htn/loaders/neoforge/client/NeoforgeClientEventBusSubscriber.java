//? if neoforge {
/*package dev.spagurder.htn.loaders.neoforge.client;

import dev.spagurder.htn.HardcoreTotemNerf;
import dev.spagurder.htn.client.ClientTickHandler;
import dev.spagurder.htn.client.KeyMappings;
import eu.midnightdust.lib.config.MidnightConfig;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import dev.spagurder.htn.network.C2SHelloPayload;
import dev.spagurder.htn.util.Platform;

//? >1.21.6 {
import dev.spagurder.htn.client.S2CPayloadHandler;
import dev.spagurder.htn.network.S2CInsightsPayload;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.client.network.event.RegisterClientPayloadHandlersEvent;
//?} else {
/^import net.neoforged.neoforge.network.PacketDistributor;
^///?}

@EventBusSubscriber(
        modid = HardcoreTotemNerf.MOD_ID,
        value = Dist.CLIENT
)
public class NeoforgeClientEventBusSubscriber {

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        processConfigKey();
        processTotemDisabledEffect();
    }

    private static void processConfigKey() {
        while (KeyMappings.CONFIG_SCREEN.consumeClick()) {
            Minecraft client = Minecraft.getInstance();
            if (client.player == null || client.level == null) return;
            client.setScreen(
                    MidnightConfig.getScreen(client.screen, HardcoreTotemNerf.MOD_ID)
            );
        }
    }

    @SubscribeEvent
    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(KeyMappings.CONFIG_SCREEN);
    }

    private static void processTotemDisabledEffect() {
        Minecraft client = Minecraft.getInstance();
        ClientTickHandler.onTick(client);
    }

    @SubscribeEvent
    public static void onJoin(ClientPlayerNetworkEvent.LoggingIn event) {
        //? >1.21.6 {
        ClientPacketDistributor
        //?} else {
        /^PacketDistributor
        ^///?}
                .sendToServer(new C2SHelloPayload(Platform.getModVersion()));
    }

    //? >1.21.6 {
    @SubscribeEvent
    public static void registerClientPayloadHandlers(RegisterClientPayloadHandlersEvent event) {
        event.register(
                S2CInsightsPayload.TYPE,
                (payload, context) -> S2CPayloadHandler.onInsights(payload, (LocalPlayer) context.player())
        );
    }
    //?}

}
*///?}