package me.mooy1.infinityexpansion.utils;

import org.bukkit.entity.Player;

public class PlayerUtils {

    private PlayerUtils() {}

    public static void message(Player p, String message) {
        p.sendMessage("&8[&bInfinity&7Expansion&8] &7" + message);
    }
}
