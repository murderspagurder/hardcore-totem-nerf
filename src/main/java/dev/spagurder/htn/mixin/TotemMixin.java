package dev.spagurder.htn.mixin;

import dev.spagurder.htn.Config;
import dev.spagurder.htn.HTNUtil;
import dev.spagurder.htn.PostTotemHandler;
import dev.spagurder.htn.PreTotemHandler;
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
            if (!PreTotemHandler.doCheck(player)) {
                cir.setReturnValue(false);
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