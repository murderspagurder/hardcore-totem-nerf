package com.github.murderspagurder.htn.fabric;

import com.github.murderspagurder.htn.HardcoreTotemNerf;
import net.fabricmc.api.ModInitializer;

import com.github.murderspagurder.htn.HardcoreTotemNerf;

public final class HardcoreTotemNerfFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        HardcoreTotemNerf.init();
    }
}
