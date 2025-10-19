package dev.spagurder.htn;

import dev.spagurder.htn.data.HTNState;
import dev.spagurder.htn.data.PlayerData;
import net.minecraft.server.level.ServerPlayer;

import java.time.Instant;

public class PreTotemHandler {

    public static boolean doCheck(ServerPlayer player) {
        // Load player data
        PlayerData playerData = HTNState.playerState.get(player.getUUID());

        // Check cooldown
        long currentTime = Instant.now().getEpochSecond();
        if (Config.useCooldown) {
            if (currentTime - playerData.totemLastUsed < Config.usageCooldown) {
                HTNUtil.sendMessage(player, "The totem cooldown has not ended.");
                return false;
            }
        }

        // Check usages
        if (Config.useUsageLimit) {
            if (playerData.totemUsages >= Config.usageLimit) {
                HTNUtil.sendMessage(player, "The totem usage limit has been exceeded.");
                return false;
            }
        }

        // Check if out of max health
        return !playerData.outOfMaxHealth;
    }

}
