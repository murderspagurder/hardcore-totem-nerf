package dev.spagurder.htn;

import com.mojang.logging.LogUtils;
import dev.spagurder.htn.util.MidnightConfigUpdateCallbackRegistry;
import dev.spagurder.htn.util.Platform;
import eu.midnightdust.lib.config.MidnightConfig;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.slf4j.Logger;

public class HardcoreTotemNerf {

    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String MOD_ID = "hardcoretotemnerf";

    public static void initialize() {
        MidnightConfig.init(MOD_ID, Config.class);
        MidnightConfigUpdateCallbackRegistry.registerCallback(MOD_ID, () -> {
            MinecraftServer server = Platform.getServerInstance();
            if (server == null) return;
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                HTNInsights.send(player);
            }
        });
    }
}
