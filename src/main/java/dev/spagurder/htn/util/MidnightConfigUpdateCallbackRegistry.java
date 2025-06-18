package dev.spagurder.htn.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MidnightConfigUpdateCallbackRegistry {

    private static final Map<String, Set<Runnable>> callbacks = new HashMap<>();

    public static void registerCallback(String modid, Runnable r) {
        callbacks.computeIfAbsent(modid, key -> new HashSet<>()).add(r);
    }

    public static void registerGlobalCallback(Runnable r) {
        registerCallback("", r);
    }

    public static void runCallbacks(String modid) {
        for (String key : Set.of("", modid)) {
            Set<Runnable> cbs = callbacks.get(key);
            if (cbs != null) {
                for (Runnable r : cbs) {
                    r.run();
                }
            }
        }
    }

}
