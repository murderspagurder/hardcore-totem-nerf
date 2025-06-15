package dev.spagurder.htn.events;

import dev.spagurder.htn.data.HTNState;
import dev.architectury.event.events.common.PlayerEvent;

public class EventHandlers {

    public static void register() {
        PlayerEvent.PLAYER_JOIN.register(player -> HTNState.loadPlayerData(player.getUUID()) );
        PlayerEvent.PLAYER_QUIT.register(player -> HTNState.unloadAndSavePlayerData(player.getUUID()));
    }

}
