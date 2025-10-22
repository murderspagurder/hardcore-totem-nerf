package dev.spagurder.htn.mixin.compat;

import com.mojang.datafixers.util.Pair;
import dev.spagurder.htn.PostTotemHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "com.illusivesoulworks.charmofundying.CharmOfUndyingCommonMod")
public class CharmOfUndyingCompatMixin {

    @Inject(method = "useTotem", at = @At("RETURN"), remap = false)
    private static void afterUseTotem(Pair<?, ?> totem, DamageSource damageSource, LivingEntity livingEntity, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue()) {
            PostTotemHandler.handlePostTotem((ServerPlayer) livingEntity);
        }
    }

}
