package dev.spagurder.htn;

import eu.midnightdust.lib.config.MidnightConfig;

public class Config extends MidnightConfig {

    public static final String LIMITS = "limits";
    public static final String MAX_HEALTH = "maxHealth";
    public static final String BUFFS = "buffs";
    public static final String DEBUFFS = "debuffs";
    public static final String INSTANT_HEALTH = "instantHealth";

    @Entry(category = LIMITS) public static boolean useCooldown = true;
    @Condition(requiredOption = "useCooldown", requiredValue = "true")
    @Entry(category = LIMITS) public static int usageCooldown = 60 * 15; // 15 minutes

    @Entry(category = LIMITS) public static boolean useUsageLimit = false;
    @Condition(requiredOption = "useUsageLimit", requiredValue = "true")
    @Entry(category = LIMITS) public static int usageLimit = 5;

    @Entry(category = LIMITS) public static boolean enableWarnings = true;
    // @Entry(category = LIMITS) public static boolean allowPlayerInsights = true;

    // Allows totem usage to reduce max health.
    @Entry(category = MAX_HEALTH) public static boolean usageReducesMaxHealth = true;
    @Condition(requiredOption = "usageReducesMaxHealth", requiredValue = "true")
    @Entry(category = MAX_HEALTH) public static float maxHealthReductionAmount = 2f; // 1 heart
    @Condition(requiredOption = "usageReducesMaxHealth", requiredValue = "true")
    @Entry(category = MAX_HEALTH) public static float minimumMaxHealth = 0f; // half heart

    // Allows enchanted golden apples to restore max health.
    @Entry(category = MAX_HEALTH) public static boolean notchAppleRestoresMaxHealth = true;
    @Condition(requiredOption = "notchAppleRestoresMaxHealth", requiredValue = "true")
    @Entry(category = MAX_HEALTH) public static float maxHealthRestorationAmount = 2f;
    @Condition(requiredOption = "notchAppleRestoresMaxHealth", requiredValue = "true")
    @Entry(category = MAX_HEALTH) public static float maximumMaxHealth = 20f; // 10 hearts

    // Disables buffs
    @Entry(category = BUFFS) public static boolean disableAbsorption = true;
    @Entry(category = BUFFS) public static boolean disableFireResistance = true;
    @Entry(category = BUFFS) public static boolean disableRegeneration = true;

    // Debuffs
    @Entry(category = DEBUFFS) public static boolean enableBlindness = true;
    @Condition(requiredOption = "enableBlindness", requiredValue = "true")
    @Entry(category = DEBUFFS) public static int blindnessDuration = 200; // ten seconds
    @Condition(requiredOption = "enableBlindness", requiredValue = "true")
    @Entry(category = DEBUFFS) public static int blindnessLevel = 1;

    @Entry(category = DEBUFFS) public static boolean enableSlowness = true;
    @Condition(requiredOption = "enableSlowness", requiredValue = "true")
    @Entry(category = DEBUFFS) public static int slownessDuration = 600; // 30 seconds
    @Condition(requiredOption = "enableSlowness", requiredValue = "true")
    @Entry(category = DEBUFFS) public static int slownessLevel = 1;

    @Entry(category = DEBUFFS) public static boolean enableWeakness = true;
    @Condition(requiredOption = "enableWeakness", requiredValue = "true")
    @Entry(category = DEBUFFS) public static int weaknessDuration = 600;
    @Condition(requiredOption = "enableWeakness", requiredValue = "true")
    @Entry(category = DEBUFFS) public static int weaknessLevel = 1;

    @Entry(category = DEBUFFS) public static boolean enableMiningFatigue = true;
    @Condition(requiredOption = "enableMiningFatigue", requiredValue = "true")
    @Entry(category = DEBUFFS) public static int miningFatigueDuration = 600;
    @Condition(requiredOption = "enableMiningFatigue", requiredValue = "true")
    @Entry(category = DEBUFFS) public static int miningFatigueLevel = 1;

    // Instant healing config
    @Entry(category = INSTANT_HEALTH) public static float minInstantHealthValue = 0.5f;
    @Entry(category = INSTANT_HEALTH) public static float maxInstantHealthValue = 20f;
    @Entry(category = INSTANT_HEALTH) public static float instantHealthDivider = 2f;

}