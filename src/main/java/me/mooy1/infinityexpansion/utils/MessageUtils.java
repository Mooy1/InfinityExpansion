package me.mooy1.infinityexpansion.utils;

import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class MessageUtils {

    private MessageUtils() {}

    public static final String NAME = ChatColor.AQUA + "Infinity" + ChatColor.GRAY + "Expansion";
    public static final String PREFIX = (ChatColor.DARK_GRAY + "[" + NAME + ChatColor.DARK_GRAY + "]" + ChatColor.WHITE + " ");

    public static void message(@Nonnull Player p, @Nonnull String message) {
        p.sendMessage(PREFIX + message);
    }

    public static void broadcast(@Nonnull String message) {
        Bukkit.broadcastMessage(PREFIX + message);
    }

    public static void messagePlayersInInv(@Nonnull BlockMenu inv, @Nonnull String message) {
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
