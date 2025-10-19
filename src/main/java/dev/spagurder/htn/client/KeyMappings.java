package dev.spagurder.htn.client;

import com.mojang.blaze3d.platform.InputConstants;
import dev.spagurder.htn.HardcoreTotemNerf;
import net.minecraft.client.KeyMapping;
//? >1.21.8 {
import net.minecraft.resources.ResourceLocation;
//?}

public class KeyMappings {

    public static final KeyMapping CONFIG_SCREEN = new KeyMapping(
            "key." + HardcoreTotemNerf.MOD_ID + ".openConfig",
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_MINUS,
            //? >1.21.8 {
            KeyMapping.Category.register(ResourceLocation.fromNamespaceAndPath(
                    HardcoreTotemNerf.MOD_ID, "keybinds"
            ))
            //?} else {
            /*"category." + HardcoreTotemNerf.MOD_ID + ".keybinds"
            *///?}
    );

}
