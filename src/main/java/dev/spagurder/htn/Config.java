package dev.spagurder.htn;

import eu.midnightdust.lib.config.MidnightConfig;

public class Config extends MidnightConfig {

    public static final String LIMITS = "limits";
    public static final String MAX_HEALTH = "maxHealth";
    public static final String BUFFS = "buffs";
    public static final String DEBUFFS = "debuffs";
    public static final String INSTANT_HEALTH = "instantHealth";

    @Entry(category = LIMITS) public static boolean useCooldown = true;
    @Entry(category = LIMITS) public static int usageCooldown = 60 * 15; // 15 minutes

    @Entry(category = LIMITS) public static boolean useUsageLimit = false;
    @Entry(category = LIMITS) public static int usageLimit = 5;

    // Allows totem usage to reduce max health.
    @Entry(category = MAX_HEALTH) public static boolean usageReducesMaxHealth = true;
    @Entry(category = MAX_HEALTH) public static float maxHealthReductionAmount = 2f; // 1 heart
    @Entry(category = MAX_HEALTH) public static float minimumMaxHealth = 0f; // half heart

    // Allows enchanted golden apples to restore max health.
    @Entry(category = MAX_HEALTH) public static boolean notchAppleRestoresMaxHealth = true;
    @Entry(category = MAX_HEALTH) public static float maxHealthRestorationAmount = 2f;
    @Entry(category = MAX_HEALTH) public static float maximumMaxHealth = 20f; // 10 hearts

    // Disables buffs
    @Entry(category = BUFFS) public static boolean disableAbsorption = true;
    @Entry(category = BUFFS) public static boolean disableFireResistance = true;
    @Entry(category = BUFFS) public static boolean disableRegeneration = true;

    // Debuffs
    @Entry(category = DEBUFFS) public static boolean enableBlindness = true;
    @Entry(category = DEBUFFS) public static int blindnessDuration = 200; // ten seconds
    @Entry(category = DEBUFFS) public static int blindnessLevel = 1;

    @Entry(category = DEBUFFS) public static boolean enableSlowness = true;
    @Entry(category = DEBUFFS) public static int slownessDuration = 600; // 30 seconds
    @Entry(category = DEBUFFS) public static int slownessLevel = 1;

    @Entry(category = DEBUFFS) public static boolean enableWeakness = true;
    @Entry(category = DEBUFFS) public static int weaknessDuration = 600;
    @Entry(category = DEBUFFS) public static int weaknessLevel = 1;

    @Entry(category = DEBUFFS) public static boolean enableMiningFatigue = true;
    @Entry(category = DEBUFFS) public static int miningFatigueDuration = 600;
    @Entry(category = DEBUFFS) public static int miningFatigueLevel = 1;

    // Instant healing config
    @Entry(category = INSTANT_HEALTH) public static float minInstantHealthValue = 0.5f;
    @Entry(category = INSTANT_HEALTH) public static float maxInstantHealthValue = 20f;
    @Entry(category = INSTANT_HEALTH) public static float instantHealthDivider = 2f;

}