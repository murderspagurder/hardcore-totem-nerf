package dev.spagurder.htn;

import eu.midnightdust.lib.config.MidnightConfig;

import java.util.LinkedHashSet;
import java.util.Set;

public class Config extends MidnightConfig {

    public static final String LIMITS = "limits";
    public static final String MAX_HEALTH = "maxHealth";
    public static final String BUFFS = "buffs";
    public static final String DEBUFFS = "debuffs";
    public static final String INSTANT_HEALTH = "instantHealth";

    @Entry(category = LIMITS) public static boolean useCooldown = true;
    //? if >=1.21.4 {
    @Condition(requiredOption = "useCooldown", requiredValue = "true")
    //?}
    @Entry(category = LIMITS) public static int usageCooldown = 60 * 15; // 15 minutes

    @Entry(category = LIMITS) public static boolean useUsageLimit = false;
    //? if >=1.21.4 {
    @Condition(requiredOption = "useUsageLimit", requiredValue = "true")
    //?}
    @Entry(category = LIMITS) public static int usageLimit = 5;

    @Entry(category = LIMITS) public static boolean resetLimitsOnDeath = true;

    @Entry(category = LIMITS) public static boolean enableWarnings = true;
    @Entry(category = LIMITS) public static boolean allowPlayerInsights = true;

    // Allows totem usage to reduce max health.
    @Entry(category = MAX_HEALTH) public static boolean usageReducesMaxHealth = true;
    //? if >=1.21.4 {
    @Condition(requiredOption = "usageReducesMaxHealth", requiredValue = "true")
    //?}
    @Entry(category = MAX_HEALTH) public static float maxHealthReductionAmount = 2f; // 1 heart
    //? if >=1.21.4 {
    @Condition(requiredOption = "usageReducesMaxHealth", requiredValue = "true")
    //?}
    @Entry(category = MAX_HEALTH) public static float minimumMaxHealth = 0f; // half heart

    // Allows enchanted golden apples to restore max health.
    @Entry(category = MAX_HEALTH) public static boolean notchAppleRestoresMaxHealth = true;
    //? if >=1.21.4 {
    @Condition(requiredOption = "notchAppleRestoresMaxHealth", requiredValue = "true")
    //?}
    @Entry(category = MAX_HEALTH) public static float maxHealthRestorationAmount = 2f;
    //? if >=1.21.4 {
    @Condition(requiredOption = "notchAppleRestoresMaxHealth", requiredValue = "true")
    //?}
    @Entry(category = MAX_HEALTH) public static float maximumMaxHealth = 20f; // 10 hearts

    // Limit enchanted golden apples to restoring max health *only* reduced by totem consumption
    //? if >=1.21.4 {
    @Condition(requiredOption = "notchAppleRestoresMaxHealth", requiredValue = "true")
    //?}
    @Entry(category = MAX_HEALTH) public static boolean restorationTracking = false;
    //? if >=1.21.4 {
    @Condition(requiredOption = "notchAppleRestoresMaxHealth", requiredValue = "true")
    //?}
    @Entry(category = MAX_HEALTH) public static boolean trackingOverridesMaxHealthCap = false;
    @Entry(category = MAX_HEALTH) public static boolean resetTrackingOnDeath = false;

    @Entry(category = MAX_HEALTH) public static float resetMaxHealthOnDeath = 0f;

    // Disables buffs
    @Entry(category = BUFFS) public static boolean disableAbsorption = true;
    @Entry(category = BUFFS) public static boolean disableFireResistance = true;
    @Entry(category = BUFFS) public static boolean disableRegeneration = true;

    // Debuffs
    @Entry(category = DEBUFFS) public static boolean enableBlindness = true;
    //? if >=1.21.4 {
    @Condition(requiredOption = "enableBlindness", requiredValue = "true")
    //?}
    @Entry(category = DEBUFFS) public static int blindnessDuration = 200; // ten seconds
    //? if >=1.21.4 {
    @Condition(requiredOption = "enableBlindness", requiredValue = "true")
    //?}
    @Entry(category = DEBUFFS) public static int blindnessLevel = 1;

    @Entry(category = DEBUFFS) public static boolean enableSlowness = true;
    //? if >=1.21.4 {
    @Condition(requiredOption = "enableSlowness", requiredValue = "true")
    //?}
    @Entry(category = DEBUFFS) public static int slownessDuration = 600; // 30 seconds
    //? if >=1.21.4 {
    @Condition(requiredOption = "enableSlowness", requiredValue = "true")
    //?}
    @Entry(category = DEBUFFS) public static int slownessLevel = 1;

    @Entry(category = DEBUFFS) public static boolean enableWeakness = true;
    //? if >=1.21.4 {
    @Condition(requiredOption = "enableWeakness", requiredValue = "true")
    //?}
    @Entry(category = DEBUFFS) public static int weaknessDuration = 600;
    //? if >=1.21.4 {
    @Condition(requiredOption = "enableWeakness", requiredValue = "true")
    //?}
    @Entry(category = DEBUFFS) public static int weaknessLevel = 1;

    @Entry(category = DEBUFFS) public static boolean enableMiningFatigue = true;
    //? if >=1.21.4 {
    @Condition(requiredOption = "enableMiningFatigue", requiredValue = "true")
    //?}
    @Entry(category = DEBUFFS) public static int miningFatigueDuration = 600;
    //? if >=1.21.4 {
    @Condition(requiredOption = "enableMiningFatigue", requiredValue = "true")
    //?}
    @Entry(category = DEBUFFS) public static int miningFatigueLevel = 1;

    // Instant healing config
    @Entry(category = INSTANT_HEALTH) public static float minInstantHealthValue = 0.5f;
    @Entry(category = INSTANT_HEALTH) public static float maxInstantHealthValue = 20f;
    @Entry(category = INSTANT_HEALTH) public static float instantHealthDivider = 2f;

    private static final Set<Runnable> updateCallbacks = new LinkedHashSet<>();

    public static void addUpdateCallback(Runnable r) {
        updateCallbacks.add(r);
    }

    public static void runUpdateCallbacks() {
        for (Runnable r : updateCallbacks) {
            try {
                r.run();
            } catch (Exception e) {
                HardcoreTotemNerf.LOGGER.error("Error while running config update callback: ", e);
            }
        }
    }

}