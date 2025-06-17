# Hardcore Totem Nerf

Hardcore Totem Nerf is a mod for Fabric and NeoForge that aims to make
Totems of Undying more suitable for hardcore worlds. It does so by applying
several hardcore-inspired nerfs and debuffs to totems. Every modification is
configurable, and includes:

- Enforce a cooldown on totem usage
- Enforce a total usage limit (disabled by default)
- Reduce the player's total hearts whenever a totem is popped
    - By default, a player can die from losing hearts
- Restore player hearts when they consume an enchanted golden apple
    - Limited to 10 total hearts by default
- Disable vanilla totem buffs, including:
    - Absorption
    - Fire Resistance
    - Regeneration
- Apply debuffs fitting of someone barely brought back from the brink of death:
    - Blindness
    - Slowness
    - Weakness
    - Mining Fatigue
- Limit instant health regeneration (default half of max health)

All of these options can be fine-tuned in `config/hardcoretotemnerf.json`. Additionally, thanks to MidnightLib, this can be configured in realtime using the config screen (bound to '-' by default), Mod Menu, or the `/midnightconfig hardcoretotem` command.

While this was built with the hardcore game mode in mind, this would certainly
fit well in non-hardcore worlds and servers as well with some configuration
tweaks.