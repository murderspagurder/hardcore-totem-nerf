package dev.spagurder.htn.data;

import dev.spagurder.htn.HardcoreTotemNerf;
import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.UUID;

public class HTNState {

    public static final HashMap<UUID, PlayerData> playerState = new HashMap<>();

    private static final Gson GSON = new Gson();

    public static void unloadAndSavePlayerData(UUID uuid) {
        savePlayerData(playerState.remove(uuid), uuid);
    }

    public static void savePlayerData(UUID uuid) {
        savePlayerData(playerState.get(uuid), uuid);
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

    public static void loadPlayerData(UUID uuid) {
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
        Path dataFile = Paths.get(HardcoreTotemNerf.MOD_ID, uuid.toString() + ".json");
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
