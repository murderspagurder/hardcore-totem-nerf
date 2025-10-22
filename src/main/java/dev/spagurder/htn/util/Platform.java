package dev.spagurder.htn.util;

import dev.spagurder.htn.HardcoreTotemNerf;
import dev.spagurder.htn.network.NetworkPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;

public class Platform {

    public static String getModVersion() {
        //? fabric {
        return net.fabricmc.loader.api.FabricLoader.getInstance().getModContainer(HardcoreTotemNerf.MOD_ID)
                .get().getMetadata()
                .getVersion().getFriendlyString();
        //?}
        //? neoforge {
        /*return net.neoforged.fml.ModList.get().getModContainerById(HardcoreTotemNerf.MOD_ID)
                .get().getModInfo()
                .getVersion().toString();
        *///?}
    }

    public static void sendPayloadToPlayer(ServerPlayer player, NetworkPayload payload) {
        //? fabric {
        //? >=1.21 {
        net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking.send(player, payload);
        //?} else {
        /*net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking.send(player, payload.id(), payload.getBuffer());
        *///?}
        //?}
        //? neoforge {
        /*net.neoforged.neoforge.network.PacketDistributor.sendToPlayer(player, payload);
        *///?}
    }

    @Nullable
    public static MinecraftServer getServerInstance() {
        //? fabric {
        return dev.spagurder.htn.loaders.fabric.FabricEntrypoint.server;
        //?}
        //? neoforge {
        /*return net.neoforged.neoforge.server.ServerLifecycleHooks.getCurrentServer();
         *///?}
    }

}
