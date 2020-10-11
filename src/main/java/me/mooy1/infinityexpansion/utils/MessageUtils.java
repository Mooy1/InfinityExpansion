package me.mooy1.infinityexpansion.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessageUtils {

    private MessageUtils() {}

    public static void message(Player p, String message) {
        p.sendMessage(ChatColor.DARK_GRAY + "["
                + ChatColor.AQUA + "Infinity"
                + ChatColor.GRAY + "Expansion"
                + ChatColor.DARK_GRAY + "]"
                + ChatColor.WHITE + " " + message);
    }

    public static void broadcast(String message) {
        Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "["
                + ChatColor.AQUA + "Infinity"
                + ChatColor.GRAY + "Expansion"
                + ChatColor.DARK_GRAY + "]"
                + ChatColor.WHITE + " " + message);
    }
}
