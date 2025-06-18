package dev.spagurder.htn;

import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public class HTNUtil {

    public static void sendMessage(ServerPlayer player, String message, boolean actionBar) {
        player.displayClientMessage(Component.literal(message), actionBar);
    }

    public static void sendMessage(ServerPlayer player, String message) {
        sendMessage(player, message, false);
    }

    public static void broadcastMessage(MinecraftServer server, String message, boolean actionBar) {
        for (ServerPlayer p :  server.getPlayerList().getPlayers()) {
            sendMessage(p, message, actionBar);
        }
    }

    public static void broadcastMessage(MinecraftServer server, String message) {
        broadcastMessage(server, message, false);
    }

}
