package dev.spagurder.htn;

import dev.spagurder.htn.data.HTNState;
import dev.spagurder.htn.data.PlayerData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.time.Instant;

public class PostTotemHandler {

    public static void handlePostTotem(ServerPlayer player) {
        // Update player data
        PlayerData playerData = HTNState.playerState.get(player.getUUID());
        playerData.totemLastUsed = Instant.now().getEpochSecond();
        playerData.totemUsages++;

        if (Config.enableWarnings) {
            if (Config.useCooldown) {
                HTNUtil.sendMessage(player, "A totem cooldown of " + Config.usageCooldown + " seconds has been applied.");
            }
            if (Config.useUsageLimit) {
                HTNUtil.sendMessage(player, "You have " + (Config.usageLimit - playerData.totemUsages) + " totem usages left.");
            }
        }

        // Max health reduction
        if (Config.usageReducesMaxHealth) {
            AttributeInstance maxHealthAttribute = player.getAttribute(Attributes.MAX_HEALTH);
            if (maxHealthAttribute != null) {
                float prospectiveMaxHealth = player.getMaxHealth() - Config.maxHealthReductionAmount;
                float maxHealth = Math.max(prospectiveMaxHealth, Config.minimumMaxHealth);
                playerData.maxHealthDeficit += player.getMaxHealth() - maxHealth;
                if (maxHealth <= 0) {
                    MinecraftServer server = player.getServer();
                    if (server != null) {
                        HTNUtil.broadcastMessage(server, player.getName().getString() + " used too many totems.");
                        HTNUtil.broadcastMessage(server, player.getName().getString() + " is now incapable of living.");
                        maxHealthAttribute.setBaseValue(1);
                        player.removeAllEffects();
                        server.execute(() -> {
                            if (!player.isDeadOrDying()) {
                                //? if >=1.21.6 {
                                player.hurtServer(player.level(), player.damageSources().generic(), Float.MAX_VALUE);
                                //?} else if >=1.21.2 {
                                /*player.hurtServer(player.serverLevel(), player.damageSources().generic(), Float.MAX_VALUE);
                                 *///?} else {
                                /*player.hurt(player.damageSources().generic(), Float.MAX_VALUE);
                                 *///?}
                            }
                        });
                    } else {
                        HardcoreTotemNerf.LOGGER.error("Unable to get server instance");
                    }
                    return;
                } else {
                    maxHealthAttribute.setBaseValue(maxHealth);
                    player.setHealth(Math.min(player.getHealth(), maxHealth));
                }
            } else {
                HardcoreTotemNerf.LOGGER.error("MAX_HEALTH attribute missing from player {}", player.getUUID());
            }
        }

        // Disable buffs
        if (Config.disableAbsorption) {
            player.removeEffect(MobEffects.ABSORPTION);
        }
        if (Config.disableFireResistance) {
            player.removeEffect(MobEffects.FIRE_RESISTANCE);
        }
        if (Config.disableRegeneration) {
            player.removeEffect(MobEffects.REGENERATION);
        }

        // Apply debuffs
        if (Config.enableBlindness) {
            player.addEffect(
                    new MobEffectInstance(
                            MobEffects.BLINDNESS,
                            Config.blindnessDuration,
                            Config.blindnessLevel)
            );
        }
        if (Config.enableSlowness) {
            player.addEffect(
                    new MobEffectInstance(
                            //? if >=1.21.5 {
                            MobEffects.SLOWNESS,
                            //?} else {
                            /*MobEffects.MOVEMENT_SLOWDOWN,
                             *///?}
                            Config.slownessDuration,
                            Config.slownessLevel)
            );
        }
        if (Config.enableWeakness) {
            player.addEffect(
                    new MobEffectInstance(
                            MobEffects.WEAKNESS,
                            Config.weaknessDuration,
                            Config.weaknessLevel)
            );
        }
        if (Config.enableMiningFatigue) {
            player.addEffect(
                    new MobEffectInstance(
                            //? if >=1.21.5 {
                            MobEffects.MINING_FATIGUE,
                            //?} else {
                            /*MobEffects.DIG_SLOWDOWN,
                             *///?}
                            Config.miningFatigueDuration,
                            Config.miningFatigueLevel)
            );
        }

        // Update health
        float health = player.getMaxHealth() / Config.instantHealthDivider;
        health = Math.min(health, Config.maxInstantHealthValue);
        health = Math.max(health, Config.minInstantHealthValue);
        player.setHealth(health);
        HTNState.savePlayerData(player.getUUID());
    }

}
