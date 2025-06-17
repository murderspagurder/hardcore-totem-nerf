//? if neoforge {
package dev.spagurder.htn.loaders.neoforge.client;

import dev.spagurder.htn.HardcoreTotemNerf;
import dev.spagurder.htn.client.KeyMappings;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@EventBusSubscriber(
        modid = HardcoreTotemNerf.MOD_ID,
        bus = EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT
)
public class NeoforgeClientKeybinds {

    @SubscribeEvent
    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(KeyMappings.CONFIG_SCREEN);
    }

}
//?}