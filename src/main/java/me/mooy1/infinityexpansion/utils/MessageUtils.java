package me.mooy1.infinityexpansion.utils;

import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.HumanEntity;
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

    public static void messagePlayersInInv(BlockMenu inv, String message) {
        if (!inv.toInventory().getViewers().isEmpty()) {

            HumanEntity[] viewers = inv.toInventory().getViewers().toArray(new HumanEntity[0]);
            Player[] players = new Player[viewers.length];

            int i = 0;
            for (HumanEntity viewer : viewers) {
                players[i] = (Player) viewer;
                i++;
            }

            for (Player player : players) {
                MessageUtils.message(player, message);
            }
        }
    }
}
