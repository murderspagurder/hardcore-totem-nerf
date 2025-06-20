package dev.spagurder.htn.mixin;

import dev.spagurder.htn.Config;
import dev.spagurder.htn.HTNUtil;
import dev.spagurder.htn.PostTotemHandler;
import dev.spagurder.htn.data.PlayerData;
import dev.spagurder.htn.data.HTNState;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
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
            PostTotemHandler.handlePostTotem(player);
        }
    }

}