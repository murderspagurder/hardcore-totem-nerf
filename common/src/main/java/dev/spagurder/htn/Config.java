package dev.spagurder.htn;

import com.moandjiezana.toml.Toml;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Config {

    public boolean useCooldown = true;
    public int usageCooldown = 60 * 15; // 15 minutes

    public boolean useUsageLimit = false;
    public int usageLimit = 5;

    // Allows totem usage to reduce max health.
    public boolean usageReducesMaxHealth = true;
    public float maxHealthReductionAmount = 2f; // 1 heart
    public float minimumMaxHealth = 0f; // half heart

    // Allows enchanted golden apples to restore max health.
    public boolean notchAppleRestoresMaxHealth = true;
    public float maxHealthRestorationAmount = 2f;
    public float maximumMaxHealth = 20f; // 10 hearts

    // Disables buffs
    public boolean disableAbsorption = true;
    public boolean disableFireResistance = true;
    public boolean disableRegeneration = true;

    // Debuffs
    public boolean enableBlindness = true;
    public int blindnessDuration = 200; // ten seconds
    public int blindnessLevel = 1;

    public boolean enableSlowness = true;
    public int slownessDuration = 600; // 30 seconds
    public int slownessLevel = 1;

    public boolean enableWeakness = true;
    public int weaknessDuration = 600;
    public int weaknessLevel = 1;

    public boolean enableMiningFatigue = true;
    public int miningFatigueDuration = 600;
    public int miningFatigueLevel = 1;

    // Instant healing config
    public float minInstantHealthValue = 0.5f;
    public float maxInstantHealthValue = 20f;
    public float instantHealthDivider = 2f;

    public static Config load() {
        if (copyDefaultConfig() != 0) {
            return new Config();
        }
        try (Reader reader = Files.newBufferedReader(HardcoreTotemNerf.CONFIG_FILE)) {
            return new Toml().read(reader).to(Config.class);
        } catch (IOException e) {
            HardcoreTotemNerf.LOGGER.error(e.getMessage());
            return new Config();
        }
    }

    private static int copyDefaultConfig() {
        if (Files.notExists(HardcoreTotemNerf.CONFIG_FILE)) {
            try (InputStream inputStream = Config.class.getResourceAsStream(HardcoreTotemNerf.DEFAULT_CONFIG_RESOURCE)) {
                if (inputStream == null) {
                    HardcoreTotemNerf.LOGGER.error("Default config file missing from jar!");
                    return 1;
                }
                Files.createDirectories(HardcoreTotemNerf.CONFIG_FILE.getParent());
                Files.copy(inputStream, HardcoreTotemNerf.CONFIG_FILE, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                HardcoreTotemNerf.LOGGER.error(e.getMessage());
                return 1;
            }
        }
        return 0;
    }

}
