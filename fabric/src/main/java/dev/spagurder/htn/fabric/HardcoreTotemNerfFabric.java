package dev.spagurder.htn.fabric;

import dev.spagurder.htn.HardcoreTotemNerf;
import net.fabricmc.api.ModInitializer;

public final class HardcoreTotemNerfFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        HardcoreTotemNerf.init();
    }
}
