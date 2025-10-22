package dev.spagurder.htn.client;

import dev.spagurder.htn.Config;
import dev.spagurder.htn.HTNUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class ClientTickHandler {

    public static void onTick(Minecraft client) {
        if (client.level == null || client.player == null) return;
        if (!Config.enableTotemCooldownTimer) return;
        if (InsightsState.totemCooldownExpiryTick < 0) return;
        long remainingTotemTicks = InsightsState.totemCooldownExpiryTick - client.level.getGameTime();
        if (remainingTotemTicks <= 0) {
            InsightsState.totemCooldownExpiryTick = -1;
            client.player.displayClientMessage(Component.empty(), true);
            return;
        }
        if (remainingTotemTicks % 20 == 0) {
            client.player.displayClientMessage(
                    Component.literal(
                            "Totem Cooldown: " + HTNUtil.formatDuration(remainingTotemTicks / 20)
                    ).withStyle(ChatFormatting.RED),
                    true
            );
        }
    }

}
