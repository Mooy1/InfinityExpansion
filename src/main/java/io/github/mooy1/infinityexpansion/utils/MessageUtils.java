package io.github.mooy1.infinityexpansion.utils;

import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * Collection of utils for sending messages to players and broadcasting
 *
 * @author Mooy1
 */
public class MessageUtils {
    
    private static final Map<Player, Long> coolDowns = new HashMap<>();
    public static final String NAME = ChatColor.AQUA + "Infinity" + ChatColor.GRAY + "Expansion";
    public static final String PREFIX = (ChatColor.DARK_GRAY + "[" + NAME + ChatColor.DARK_GRAY + "]" + ChatColor.WHITE + " ");

    public static void message(@Nonnull Player p, @Nonnull String... messages) {
        for (String m : messages) {
            p.sendMessage(PREFIX + m);
        }
    }

    public static void broadcast(@Nonnull String message) {
        Bukkit.broadcastMessage(PREFIX + message);
    }

    public static void messageWithCD(@Nonnull Player p, long coolDown, @Nonnull String... messages) {
        if (coolDowns.containsKey(p) && System.currentTimeMillis() - coolDowns.get(p) < coolDown) {
            return;
        }
        coolDowns.put(p, System.currentTimeMillis());
        
        for (String m : messages) {
            p.sendMessage(PREFIX + m);
        }
    }

    public static void messagePlayersInInv(@Nonnull BlockMenu inv, @Nonnull String message) {
        if (inv.hasViewer()) {
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
