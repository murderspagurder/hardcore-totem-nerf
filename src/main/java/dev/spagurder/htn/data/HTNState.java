package dev.spagurder.htn.data;

import dev.spagurder.htn.HTNInsights;
import dev.spagurder.htn.HardcoreTotemNerf;
import com.google.gson.Gson;
import dev.spagurder.htn.util.Platform;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.storage.LevelResource;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class HTNState {

    public static final HashMap<UUID, PlayerData> playerState = new HashMap<>();
    public static final HashSet<UUID> networkedPlayers = new HashSet<>();

    private static final Gson GSON = new Gson();

    public static void unloadAndSavePlayerData(UUID uuid) {
        networkedPlayers.remove(uuid);
        savePlayerData(playerState.remove(uuid), uuid);
    }

    public static void savePlayerData(ServerPlayer player) {
        savePlayerData(playerState.get(player.getUUID()), player.getUUID());
        HTNInsights.send(player);
    }

    public static void savePlayerData(PlayerData playerData, UUID uuid) {
        if (playerData == null) {
            HardcoreTotemNerf.LOGGER.error("Player missing during save: {}", uuid);
            return;
        }
        Path dataFile = getPlayerDataPath(uuid);
        if (dataFile == null) {
            return;
        }
        try (FileWriter writer = new FileWriter(dataFile.toFile())) {
            GSON.toJson(playerData, writer);
        } catch (IOException e) {
            HardcoreTotemNerf.LOGGER.error("Failed to save player data: {}", uuid);
            HardcoreTotemNerf.LOGGER.error(e.getMessage());
        }
    }

    public static void loadPlayerData(ServerPlayer player, UUID uuid) {
        Path dataFile = getPlayerDataPath(uuid);
        if (dataFile != null) {
            if (dataFile.toFile().exists()) {
                try (FileReader reader = new FileReader(dataFile.toFile())) {
                    playerState.put(uuid, GSON.fromJson(reader, PlayerData.class));
                    HardcoreTotemNerf.LOGGER.info("Player data loaded: {}", uuid);
                    return;
                } catch (IOException e) {
                    HardcoreTotemNerf.LOGGER.error("Failed to load player data: {}", uuid);
                    HardcoreTotemNerf.LOGGER.error(e.getMessage());
                }
            }
        }

        HardcoreTotemNerf.LOGGER.info("Creating new player data: {}", uuid);
        playerState.put(uuid, new PlayerData(uuid));
    }

    private static Path getPlayerDataPath(UUID uuid) {
        MinecraftServer server = Platform.getServerInstance();
        if (server == null) {
            HardcoreTotemNerf.LOGGER.error("Failed to get player data path: server is null");
            return null;
        }
        Path worldDir = server.getWorldPath(LevelResource.ROOT);
        Path dataDir = worldDir.resolve(HardcoreTotemNerf.MOD_ID);
        Path dataFile = dataDir.resolve(uuid.toString() + ".json");
        try {
            Files.createDirectories(dataFile.getParent());
            return dataFile;
        } catch (IOException e) {
            HardcoreTotemNerf.LOGGER.error("Failed to create data folder");
            HardcoreTotemNerf.LOGGER.error(e.getMessage());
        }
        return null;
    }

}
