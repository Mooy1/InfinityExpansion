package io.github.mooy1.infinityexpansion.items.gear;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Nonnull;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.mooy1.infinitylib.common.Events;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import io.github.thebusybiscuit.slimefun4.core.attributes.Soulbound;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;

public final class InfinityMatrix extends SimpleSlimefunItem<ItemUseHandler> implements Listener, Soulbound, NotPlaceable {

    public InfinityMatrix(ItemGroup category, SlimefunItemStack item, RecipeType type, ItemStack[] recipe) {
        super(category, item, type, recipe);
        Events.registerListener(this);
    }

    private static void disableFlight(Player p) {
        p.sendMessage(ChatColor.RED + "Infinity Flight Disabled!");
        p.setAllowFlight(false);
    }

    private static void enableFlight(Player p) {
        p.sendMessage(ChatColor.GREEN + "Infinity Flight Enabled!");
        p.setAllowFlight(true);
    }

    @Nonnull
    @Override
    public ItemUseHandler getItemHandler() {
        return e -> {
            ItemStack item = e.getItem();
            if (!item.hasItemMeta()) {
                return;
            }
            ItemMeta meta = item.getItemMeta();
            if (!meta.hasLore()) {
                return;
            }
            List<String> lore = meta.getLore();

            Player p = e.getPlayer();

            Iterator<String> iterator = lore.listIterator();

            while (iterator.hasNext()) {
                String line = iterator.next();

                if (ChatColor.stripColor(line).contains("UUID: ")) {
                    String uuid = ChatColor.stripColor(line).substring(6);

                    if (!p.getUniqueId().toString().equals(uuid)) {
                        p.sendMessage(ChatColor.YELLOW + "You do not own this matrix!");
                        return;
                    }

                    if (p.isSneaking()) { //remove owner
                        iterator.remove();
                        meta.setLore(lore);
                        item.setItemMeta(meta);
                        p.sendMessage(ChatColor.GOLD + "Ownership removed!");
                        disableFlight(p);

                    }
                    else if (p.getAllowFlight()) {
                        disableFlight(p);
                    }
                    else {
                        enableFlight(p);
                    }

                    return;
                }
            }

            lore.add(ChatColor.GREEN + "UUID: " + p.getUniqueId());
            meta.setLore(lore);
            item.setItemMeta(meta);
            p.sendMessage(ChatColor.GOLD + "Ownership claimed!");
            enableFlight(p);
        };
    }

}