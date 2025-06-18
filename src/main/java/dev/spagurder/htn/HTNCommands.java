package dev.spagurder.htn;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import dev.spagurder.htn.data.HTNState;
import dev.spagurder.htn.data.PlayerData;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.time.Instant;

import static net.minecraft.commands.Commands.literal;

public class HTNCommands {

    private static void sendKV(CommandSourceStack source, String label, String value) {
        String line = String.format("§e%-20s §r%s", label, value);
        source.sendSuccess(() -> Component.literal(line), false);
    }

    public static int showPlayerInsights(CommandContext<CommandSourceStack> ctx) {
        CommandSourceStack source = ctx.getSource();
        if (!source.isPlayer()) {
            source.sendFailure(Component.literal("This command can only be run by a player!"));
            return 0;
        }
        ServerPlayer player = source.getPlayer();
        if (player == null) {
            source.sendFailure(Component.literal("Player is null!"));
            return 0;
        }
        PlayerData playerData = HTNState.playerState.get(player.getUUID());
        if  (playerData == null) {
            source.sendFailure(Component.literal("Player data doesn't exist!"));
            return 0;
        }

        source.sendSuccess(() -> Component.literal("§6§l=== Hardcore Totem Nerf ==="), false);

        if (Config.useCooldown) {
            sendKV(source, "Cooldown", Config.usageCooldown + "s");
            long currentTime = Instant.now().getEpochSecond();
            long cooldownRemaining = Math.max(Config.usageCooldown - (currentTime - playerData.totemLastUsed), 0);
            if  (cooldownRemaining > 0) {
                sendKV(source, "Cooldown Remaining", cooldownRemaining + "s");
            } else {
                sendKV(source, "Cooldown Remaining", "§7N/A");
            }
        } else {
            sendKV(source, "Cooldown", "§7N/A");
        }

        source.sendSuccess(() -> Component.literal("§7--------------------------"), false);

        if (Config.useUsageLimit) {
            sendKV(source, "Usage Limit", String.valueOf(Config.usageLimit));
            sendKV(source, "Uses Remaining", String.valueOf(Math.max(Config.usageLimit - playerData.totemUsages, 0)));
        }
        sendKV(source, "Totems Used", String.valueOf(playerData.totemUsages));

        return 1;
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        var base = literal(HardcoreTotemNerf.MOD_ID)
                .requires(source -> Config.allowPlayerInsights)
                .then(literal("insights")
                        .executes(HTNCommands::showPlayerInsights)
                );
        var alias = literal("htn")
                .requires(source -> Config.allowPlayerInsights)
                .redirect(base.build());

        dispatcher.register(base);
        dispatcher.register(alias);
    }

}
