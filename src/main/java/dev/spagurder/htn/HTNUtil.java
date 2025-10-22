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

    public static boolean minorVersionCheck(String v1, String v2) {
        String[] a = v1.split("[-+]")[0].split("\\.");
        String[] b = v2.split("[-+]")[0].split("\\.");
        return Integer.parseInt(a[0]) == Integer.parseInt(b[0])
                && Integer.parseInt(a[1]) == Integer.parseInt(b[1]);
    }

    public static String formatDuration(long seconds) {
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long sec = seconds % 60;
        if (hours > 0) {
            return String.format("%d:%02d:%02d", hours, minutes, sec);
        } else {
            return String.format("%d:%02d", minutes, sec);
        }
    }

}
