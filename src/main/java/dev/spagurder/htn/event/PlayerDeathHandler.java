package dev.spagurder.htn.event;

import dev.spagurder.htn.Config;
import dev.spagurder.htn.HardcoreTotemNerf;
import dev.spagurder.htn.data.HTNState;
import dev.spagurder.htn.data.PlayerData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class PlayerDeathHandler {

    public static void onPlayerDeath(ServerPlayer player) {
        PlayerData playerData = HTNState.playerState.get(player.getUUID());
        if (Config.resetLimitsOnDeath) {
            playerData.totemUsages = 0;
            playerData.totemLastUsed = 0L;
        }
        if (Config.resetTrackingOnDeath) {
            playerData.maxHealthDeficit = 0f;
        }
        playerData.outOfMaxHealth = false;
        HTNState.savePlayerData(player.getUUID());

        if (Config.resetMaxHealthOnDeath > 0) {
            AttributeInstance maxHealthAttribute = player.getAttribute(Attributes.MAX_HEALTH);
            if (maxHealthAttribute != null) {
                maxHealthAttribute.setBaseValue(Config.resetMaxHealthOnDeath);
            } else {
                HardcoreTotemNerf.LOGGER.error("MAX_HEALTH attribute missing from player {}", player.getUUID());
            }
        }
    }

}
