package dev.spagurder.htn.mixin;

import dev.spagurder.htn.Config;
import dev.spagurder.htn.HTNUtil;
import dev.spagurder.htn.HardcoreTotemNerf;
import dev.spagurder.htn.data.PlayerData;
import dev.spagurder.htn.data.HTNState;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.time.Instant;

@Mixin(LivingEntity.class)
public abstract class TotemMixin {

    @Inject(method = "checkTotemDeathProtection", at = @At("HEAD"), cancellable = true)
    private void beforeCheckTotemDeathProtection(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity instanceof ServerPlayer player) {
            // Load player data
            PlayerData playerData = HTNState.playerState.get(player.getUUID());

            // Check cooldown
            long currentTime = Instant.now().getEpochSecond();
            if (Config.useCooldown) {
                if (currentTime - playerData.totemLastUsed < Config.usageCooldown) {
                    HTNUtil.sendMessage(player, "The totem cooldown has not ended.");
                    cir.setReturnValue(false);
                    return;
                }
            }

            // Check usages
            if (Config.useUsageLimit) {
                if (playerData.totemUsages >= Config.usageLimit) {
                    HTNUtil.sendMessage(player, "The totem usage limit has been exceeded.");
                    cir.setReturnValue(false);
                }
            }
        }
    }

    @Inject(method = "checkTotemDeathProtection", at = @At("RETURN"))
    private void afterCheckTotemDeathProtection(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (cir.getReturnValue() && entity instanceof ServerPlayer player) {
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
                    float prospectiveMaxHealth = entity.getMaxHealth() - Config.maxHealthReductionAmount;
                    float maxHealth = Math.max(prospectiveMaxHealth, Config.minimumMaxHealth);
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
        }
    }

}