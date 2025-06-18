package dev.spagurder.htn.mixin;

import dev.spagurder.htn.util.MidnightConfigUpdateCallbackRegistry;
import eu.midnightdust.lib.config.MidnightConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MidnightConfig.class, remap = false)
public class MidnightConfigUpdateMixin {

    //? if >=1.20.2 {
    @Inject(method = "writeChanges", at = @At("RETURN"))
    private void writeChanges(String modid, CallbackInfo ci) {
    //?} else {
    /*@Inject(method = "write", at = @At("RETURN"))
    private static void write(String modid, CallbackInfo ci) {
    *///?}
        MidnightConfigUpdateCallbackRegistry.runCallbacks(modid);
    }

}
