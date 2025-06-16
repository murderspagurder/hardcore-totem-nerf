package dev.spagurder.htn;

import dev.spagurder.htn.events.EventHandlers;
import eu.midnightdust.lib.config.MidnightConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class HardcoreTotemNerf {

    public static final String MOD_ID = "hardcoretotemnerf";
    public static final String MOD_NAME = "Hardcore Totem Nerf";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static void init() {
        MidnightConfig.init(MOD_ID, Config.class);
        EventHandlers.register();
    }

}
