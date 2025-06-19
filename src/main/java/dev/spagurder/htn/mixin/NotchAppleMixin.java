package dev.spagurder.htn.mixin;

import dev.spagurder.htn.Config;
import dev.spagurder.htn.HardcoreTotemNerf;
import dev.spagurder.htn.data.HTNState;
import dev.spagurder.htn.data.PlayerData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class NotchAppleMixin {

    @Inject(method = "completeUsingItem", at = @At("HEAD"))
    private void afterCompleteUsingItem(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (Config.notchAppleRestoresMaxHealth) {
            if (entity instanceof ServerPlayer player) {
                if (player.getUseItem().is(Items.ENCHANTED_GOLDEN_APPLE)) {
                    AttributeInstance maxHealthAttribute = player.getAttribute(Attributes.MAX_HEALTH);
                    if (maxHealthAttribute == null) {
                        HardcoreTotemNerf.LOGGER.error("MAX_HEALTH attribute missing from player {}", player.getUUID());
                        return;
                    }
                    PlayerData playerData = HTNState.playerState.get(player.getUUID());
                    float restorationAmount = Config.restorationTracking
                            ? Math.min(playerData.maxHealthDeficit, Config.maxHealthRestorationAmount)
                            : Config.maxHealthRestorationAmount;
                    float maxHealth = player.getMaxHealth() + restorationAmount;
                    if (!(Config.restorationTracking && Config.trackingOverridesMaxHealthCap)) {
                        maxHealth = Math.min(maxHealth, Config.maximumMaxHealth);
                    }
                    if (maxHealth > player.getMaxHealth()) {
                        if (maxHealth == Config.maximumMaxHealth) {
                            playerData.maxHealthDeficit = 0f;
                        } else {
                            playerData.maxHealthDeficit += player.getMaxHealth() - maxHealth;
                        }
                        maxHealthAttribute.setBaseValue(maxHealth);
                        HTNState.savePlayerData(player.getUUID());
                    }
                }
            }
        }
    }

}