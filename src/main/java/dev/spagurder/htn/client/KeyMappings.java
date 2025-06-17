package dev.spagurder.htn.client;

import com.mojang.blaze3d.platform.InputConstants;
import dev.spagurder.htn.HardcoreTotemNerf;
import net.minecraft.client.KeyMapping;

public class KeyMappings {

    public static final KeyMapping CONFIG_SCREEN = new KeyMapping(
            "key." + HardcoreTotemNerf.MOD_ID + ".openConfig",
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_MINUS,
            "category." + HardcoreTotemNerf.MOD_ID + ".keybinds"
    );

}
