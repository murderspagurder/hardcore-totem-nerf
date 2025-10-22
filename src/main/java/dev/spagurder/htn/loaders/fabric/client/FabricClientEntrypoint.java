//? if fabric {
package dev.spagurder.htn.loaders.fabric.client;

import dev.spagurder.htn.HardcoreTotemNerf;
import dev.spagurder.htn.client.ClientTickHandler;
import dev.spagurder.htn.client.KeyMappings;
import dev.spagurder.htn.client.S2CPayloadHandler;
import dev.spagurder.htn.network.C2SHelloPayload;
import dev.spagurder.htn.network.S2CInsightsPayload;
import dev.spagurder.htn.util.Platform;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class FabricClientEntrypoint  implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        KeyBindingHelper.registerKeyBinding(KeyMappings.CONFIG_SCREEN);
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (KeyMappings.CONFIG_SCREEN.consumeClick()) {
                client.setScreen(
                        MidnightConfig.getScreen(client.screen, HardcoreTotemNerf.MOD_ID)
                );
            }
            ClientTickHandler.onTick(client);
        });
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            C2SHelloPayload payload = new C2SHelloPayload(Platform.getModVersion());
            //? >=1.21 {
            if (ClientPlayNetworking.canSend(C2SHelloPayload.TYPE)) {
                ClientPlayNetworking.send(payload);
            }
            //?} else {
            /*if (ClientPlayNetworking.canSend(payload.id())) {
                ClientPlayNetworking.send(payload.id(), payload.getBuffer());
            }
            *///?}
        });
        //? >=1.21 {
        ClientPlayNetworking.registerGlobalReceiver(S2CInsightsPayload.TYPE, (payload, context) -> {
            S2CPayloadHandler.onInsights(payload, context.player());
        });
        //?} else {
        /*ClientPlayNetworking.registerGlobalReceiver(
                S2CInsightsPayload.INSIGHTS_PAYLOAD_ID,
                (client, handler, buf, responseSender) -> {
                    long totemCooldown = buf.readLong();
                    int remainingUsages = buf.readInt();
                    S2CPayloadHandler.onInsights(new S2CInsightsPayload(totemCooldown, remainingUsages), client.player);
                }
        );
        *///?}
    }

}
//?}