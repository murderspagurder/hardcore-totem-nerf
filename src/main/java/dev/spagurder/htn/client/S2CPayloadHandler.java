package dev.spagurder.htn.client;

import dev.spagurder.htn.Config;
import dev.spagurder.htn.network.S2CInsightsPayload;
import net.minecraft.client.player.LocalPlayer;

public class S2CPayloadHandler {

    public static void onInsights(S2CInsightsPayload payload, LocalPlayer player) {
        if (payload.remainingUsages() == 0 || payload.totemCooldown() == 0 || !Config.enableTotemCooldownTimer) {
            InsightsState.totemCooldownExpiryTick = -1;
            return;
        }
        InsightsState.totemCooldownExpiryTick = player.level().getGameTime() + (payload.totemCooldown() * 20);
    }

}
