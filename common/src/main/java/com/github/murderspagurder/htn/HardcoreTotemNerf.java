package com.github.murderspagurder.htn;

import com.github.murderspagurder.htn.events.EventHandlers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class HardcoreTotemNerf {

    public static final String MOD_ID = "hardcoretotemnerf";
    public static final String MOD_NAME = "Hardcore Totem Nerf";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);
    public static final Path CONFIG_FILE = Paths.get("config", MOD_ID + ".toml");
    public static final String DEFAULT_CONFIG_RESOURCE = "/default-config.toml";

    public static Config config;

    public static void init() {
        config = Config.load();
        EventHandlers.register();
    }

}
