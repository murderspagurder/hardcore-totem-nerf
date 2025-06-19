package dev.spagurder.htn.data;

import java.util.UUID;

public class PlayerData {

    public UUID uuid;
    public long totemLastUsed = 0L;
    public int totemUsages = 0;
    public float maxHealthDeficit = 0f;

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
    }

}