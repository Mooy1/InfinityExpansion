package io.github.mooy1.infinityexpansion.utils;

import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.cscorelib2.chat.ChatColors;
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
 * 
 */
public class MessageUtils {
    
    private static final Map<Player, Long> coolDowns = new HashMap<>();
    public static final String NAME = ChatColor.AQUA + "Infinity" + ChatColor.GRAY + "Expansion";
    public static final String PREFIX = (ChatColor.DARK_GRAY + "[" + NAME + ChatColor.DARK_GRAY + "]" + ChatColor.WHITE + " ");

    public static void message(@Nonnull Player p, @Nonnull String... messages) {
        for (String m : messages) {
            p.sendMessage(PREFIX + ChatColors.color(m));
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
        
        message(p, messages);
    }

    public static void messagePlayersInInv(@Nonnull BlockMenu inv, @Nonnull String... messages) {
        if (inv.hasViewer()) {
            for (HumanEntity viewer : inv.toInventory().getViewers().toArray(new HumanEntity[1])) {
                message((Player) viewer, messages);
            }
        }
    }
}
