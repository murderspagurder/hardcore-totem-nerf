package dev.spagurder.htn.mixin.compat;

import dev.spagurder.htn.PostTotemHandler;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
//? if fabric {
@Mixin(targets = "com.natamus.inventorytotem_common_fabric.events.TotemEvent")
//?} else if neoforge {
/*@Mixin(targets = "com.natamus.inventorytotem_common_neoforge.events.TotemEvent")
*///?} else if forge {
/*@Mixin(targets = "com.natamus.inventorytotem_common_forge.events.TotemEvent")
*///?}
public class InventoryTotemCompatMixin {

    @Inject(method = "allowPlayerDeath", at = @At("RETURN"))
    private static void afterAllowPlayerDeath(ServerLevel world, ServerPlayer player, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue()) {
            PostTotemHandler.handlePostTotem(player);
        }
    }

}
