package dev.spagurder.htn.mixin.compat;

import dev.spagurder.htn.PostTotemHandler;
import dev.spagurder.htn.PreTotemHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//? >=1.21 {
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//?} else {
/*import net.minecraft.world.damagesource.DamageSource;
*///?}

@Pseudo
@Mixin(targets = "com.affehund.voidtotem.core.ModUtils")
public class VoidTotemCompatMixin {

    //? >=1.21 {
    @Inject(method = "hasVoidTotem", at = @At("HEAD"), cancellable = true, remap = false)
    private static void beforeHasVoidTotem(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof ServerPlayer player) {
            if (!PreTotemHandler.doCheck(player)) {
                cir.setReturnValue(false);
            }
        }
    }

    @Inject(method = "handleVoidTotem", at = @At("RETURN"), remap = false)
    private static void afterHandleVoidTotem(LivingEntity entity, CallbackInfo ci) {
        if (entity instanceof ServerPlayer player) {
            PostTotemHandler.handlePostTotem(player);
        }
    }

    @Inject(method = "handleDefaultTotemActivation", at = @At("RETURN"), remap = false)
    private static void afterHandleDefaultTotemActivation(LivingEntity entity, CallbackInfo ci) {
        if (entity instanceof ServerPlayer player) {
            PostTotemHandler.handlePostTotem(player);
        }
    }

    //?} else {
    /*@Inject(method = "canProtectFromVoid", at = @At("HEAD"), cancellable = true, remap = false)
    private static void beforeCanProtectFromVoid(LivingEntity entity, DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof ServerPlayer player) {
            if (!PreTotemHandler.doCheck(player)) {
                cir.setReturnValue(false);
            }
        }
    }

    @Inject(method = "canProtectFromVoid", at = @At("RETURN"), remap = false)
    private static void afterCanProtectFromVoid(LivingEntity entity, DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue() && entity instanceof ServerPlayer player) {
            PostTotemHandler.handlePostTotem(player);
        }
    }
    *///?}

}
