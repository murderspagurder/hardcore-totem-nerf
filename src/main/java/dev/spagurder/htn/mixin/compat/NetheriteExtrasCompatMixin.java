package dev.spagurder.htn.mixin.compat;

import dev.spagurder.htn.PostTotemHandler;
import dev.spagurder.htn.PreTotemHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "xyz.hafemann.netheriteextras.event.ModEvents")
public class NetheriteExtrasCompatMixin {

    //? if fabric {
    @Inject(
            method = "lambda$registerModEvents$1",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private static void beforeAllowDeathHandler(
            LivingEntity entity, DamageSource damageSource,
            float damageAmount, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof ServerPlayer player) {
            if (!PreTotemHandler.doCheck(player)) {
                cir.cancel();
            }
        }
    }

    @Inject(
            method = "lambda$registerModEvents$1",
            at = @At("RETURN"),
            remap = false
    )
    private static void afterAllowDeathHandler(
            LivingEntity entity, DamageSource damageSource,
            float damageAmount, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof ServerPlayer player) {
            if (!cir.getReturnValue()) {
                PostTotemHandler.handlePostTotem(player);
            }
        }
    }
    //?}

}