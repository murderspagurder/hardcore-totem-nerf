package dev.spagurder.htn;

import com.mojang.logging.LogUtils;
import eu.midnightdust.lib.config.MidnightConfig;
import org.slf4j.Logger;

public class HardcoreTotemNerf {

    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String MOD_ID = "hardcoretotemnerf";

    public static void initialize() {
        MidnightConfig.init(MOD_ID, Config.class);
    }
}
