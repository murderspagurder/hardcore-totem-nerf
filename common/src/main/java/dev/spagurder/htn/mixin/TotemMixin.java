package dev.spagurder.htn.mixin;

import dev.spagurder.htn.HardcoreTotemNerf;
import dev.spagurder.htn.data.PlayerData;
import dev.spagurder.htn.data.HTNState;
import net.minecraft.network.chat.Component;
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
            if (HardcoreTotemNerf.config.useCooldown) {
                if (currentTime - playerData.totemLastUsed < HardcoreTotemNerf.config.usageCooldown) {
                    cir.setReturnValue(false);
                    return;
                }
            }

            // Check usages
            if (HardcoreTotemNerf.config.useUsageLimit) {
                if (playerData.totemUsages >= HardcoreTotemNerf.config.usageLimit) {
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

            // Max health reduction
            if (HardcoreTotemNerf.config.usageReducesMaxHealth) {
                AttributeInstance maxHealthAttribute = player.getAttribute(Attributes.MAX_HEALTH);
                if (maxHealthAttribute != null) {
                    float prospectiveMaxHealth = entity.getMaxHealth() - HardcoreTotemNerf.config.maxHealthReductionAmount;
                    float maxHealth = Math.max(prospectiveMaxHealth, HardcoreTotemNerf.config.minimumMaxHealth);
                    if (maxHealth <= 0) {
                        MinecraftServer server = player.getServer();
                        if (server != null) {
                            for (ServerPlayer p : server.getPlayerList().getPlayers()) {
                                p.displayClientMessage(Component.literal(player.getName().getString() + " used too many totems."), false);
                                p.displayClientMessage(Component.literal(player.getName().getString() + " is now incapable of living."), false);
                            }
                            maxHealthAttribute.setBaseValue(1);
                            player.removeAllEffects();
                            server.execute(() -> {
                                if (!player.isDeadOrDying()) {
                                    player.hurtServer(player.serverLevel(), player.damageSources().generic(), Float.MAX_VALUE);
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
            if (HardcoreTotemNerf.config.disableAbsorption) {
                player.removeEffect(MobEffects.ABSORPTION);
            }
            if (HardcoreTotemNerf.config.disableFireResistance) {
                player.removeEffect(MobEffects.FIRE_RESISTANCE);
            }
            if (HardcoreTotemNerf.config.disableRegeneration) {
                player.removeEffect(MobEffects.REGENERATION);
            }

            // Apply debuffs
            if (HardcoreTotemNerf.config.enableBlindness) {
                player.addEffect(
                        new MobEffectInstance(
                                MobEffects.BLINDNESS,
                                HardcoreTotemNerf.config.blindnessDuration,
                                HardcoreTotemNerf.config.blindnessLevel)
                );
            }
            if (HardcoreTotemNerf.config.enableSlowness) {
                player.addEffect(
                        new MobEffectInstance(
                                MobEffects.MOVEMENT_SLOWDOWN,
                                HardcoreTotemNerf.config.slownessDuration,
                                HardcoreTotemNerf.config.slownessLevel)
                );
            }
            if (HardcoreTotemNerf.config.enableWeakness) {
                player.addEffect(
                        new MobEffectInstance(
                                MobEffects.WEAKNESS,
                                HardcoreTotemNerf.config.weaknessDuration,
                                HardcoreTotemNerf.config.weaknessLevel)
                );
            }
            if (HardcoreTotemNerf.config.enableMiningFatigue) {
                player.addEffect(
                        new MobEffectInstance(
                                MobEffects.DIG_SLOWDOWN,
                                HardcoreTotemNerf.config.miningFatigueDuration,
                                HardcoreTotemNerf.config.miningFatigueLevel)
                );
            }

            // Update health
            float health = player.getMaxHealth() / HardcoreTotemNerf.config.instantHealthDivider;
            health = Math.min(health, HardcoreTotemNerf.config.maxInstantHealthValue);
            health = Math.max(health, HardcoreTotemNerf.config.minInstantHealthValue);
            player.setHealth(health);
        }
    }

}