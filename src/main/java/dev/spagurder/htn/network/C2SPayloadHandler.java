package dev.spagurder.htn.network;

import dev.spagurder.htn.HTNInsights;
import dev.spagurder.htn.HTNUtil;
import dev.spagurder.htn.data.HTNState;
import dev.spagurder.htn.util.Platform;
import net.minecraft.server.level.ServerPlayer;

public class C2SPayloadHandler {

    public static void onHello(C2SHelloPayload payload, ServerPlayer player) {
        if (HTNUtil.minorVersionCheck(payload.modVersion(), Platform.getModVersion())) {
            HTNState.networkedPlayers.add(player.getUUID());
            HTNInsights.send(player);
        } else {
            HTNUtil.sendMessage(player, "Hardcore Totem Nerf Version Mismatch." +
                    "\nServer version: " + Platform.getModVersion() +
                    "\nClient version: " + payload.modVersion() +
                    "\nClient side features are disabled.");
        }
    }

}
