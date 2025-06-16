package dev.spagurder.htn.client;

import com.mojang.blaze3d.platform.InputConstants;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import dev.spagurder.htn.HardcoreTotemNerf;
import eu.midnightdust.lib.config.MidnightConfig;
import net.minecraft.client.KeyMapping;

public class HardcoreTotemNerfClient {

    public static final KeyMapping CONFIG_KEYMAPPING = new KeyMapping(
            "key." + HardcoreTotemNerf.MOD_ID + ".openConfig",
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_MINUS,
            "category." + HardcoreTotemNerf.MOD_ID + ".keybinds"
    );

    public static void init() {
        KeyMappingRegistry.register(CONFIG_KEYMAPPING);
        ClientTickEvent.CLIENT_POST.register((minecraft) -> {
            while (CONFIG_KEYMAPPING.consumeClick()) {
                minecraft.setScreen(
                        MidnightConfig.getScreen(minecraft.screen, HardcoreTotemNerf.MOD_ID)
                );
            }
        });
    }

}
