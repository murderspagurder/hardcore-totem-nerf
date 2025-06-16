package dev.spagurder.htn.neoforge.client;

import dev.spagurder.htn.HardcoreTotemNerf;
import dev.spagurder.htn.client.HardcoreTotemNerfClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

@Mod(value = HardcoreTotemNerf.MOD_ID, dist = Dist.CLIENT)
public class HardcoreTotemNerfNeoForgeClient {

    public HardcoreTotemNerfNeoForgeClient() {
        HardcoreTotemNerfClient.init();
    }

}
