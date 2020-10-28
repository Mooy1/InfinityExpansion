package me.mooy1.infinityexpansion.lists;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import me.mooy1.infinityexpansion.InfinityExpansion;
import me.mooy1.infinityexpansion.implementation.gear.InfinityMatrix;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

/**
 * events for addon
 *
 * @author Mooy1
 */
public final class Events implements Listener {

    public Events(InfinityExpansion plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    private static void onRightClick(PlayerRightClickEvent e) {
        ItemStack item = e.getItem();
        if (SlimefunItem.getByItem(item) instanceof InfinityMatrix) {
            InfinityMatrix.handler(item, e.getPlayer());
        }
    }
}