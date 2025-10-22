package dev.spagurder.htn;

import dev.spagurder.htn.data.HTNState;
import dev.spagurder.htn.data.PlayerData;
import dev.spagurder.htn.network.S2CInsightsPayload;
import dev.spagurder.htn.util.Platform;
import net.minecraft.server.level.ServerPlayer;

import java.time.Instant;

public class HTNInsights {

    public static void send(ServerPlayer player) {
        if (!Config.allowPlayerInsights) return;

        if (!HTNState.networkedPlayers.contains(player.getUUID())) return;

        PlayerData playerData = HTNState.playerState.get(player.getUUID());
        if (playerData == null) return;

        long currentTime = Instant.now().getEpochSecond();
        long cooldownRemaining = Config.useCooldown
                ? Math.max(Config.usageCooldown - (currentTime - playerData.totemLastUsed), 0)
                : 0;
        int remainingUsages = Config.useUsageLimit ? Math.max(Config.usageLimit - playerData.totemUsages, 0) : 1;

        Platform.sendPayloadToPlayer(player, new S2CInsightsPayload(cooldownRemaining, remainingUsages));
    }

}
