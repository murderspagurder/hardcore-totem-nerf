//? if neoforge {
package dev.spagurder.htn.loaders.neoforge.client;

import dev.spagurder.htn.HardcoreTotemNerf;
import dev.spagurder.htn.client.KeyMappings;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
//? if >=1.20.5 {
/*import net.neoforged.fml.common.EventBusSubscriber;
 *///?} else {
import net.neoforged.fml.common.Mod;
//?}

//? if >=1.20.5 {
/*@EventBusSubscriber(
        modid = HardcoreTotemNerf.MOD_ID,
        bus = EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT
)
*///?} else {
@Mod.EventBusSubscriber(
        modid = HardcoreTotemNerf.MOD_ID,
        bus = Mod.EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT
)
//?}
public class NeoforgeClientModBusSubscriber {

    @SubscribeEvent
    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(KeyMappings.CONFIG_SCREEN);
    }

}
//?}