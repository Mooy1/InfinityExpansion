package io.github.mooy1.infinityexpansion.implementation.gear;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.categories.Categories;
import io.github.mooy1.infinityexpansion.implementation.blocks.InfinityWorkbench;
import io.github.mooy1.infinityexpansion.implementation.materials.Items;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import io.github.thebusybiscuit.slimefun4.core.attributes.Soulbound;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.List;

/**
 * Flight
 * 
 */
public final class InfinityMatrix extends SimpleSlimefunItem<ItemUseHandler> implements Listener, Soulbound, NotPlaceable {
    
    public static final SlimefunItemStack ITEM = new SlimefunItemStack(
            "INFINITY_MATRIX",
            Material.NETHER_STAR,
            "&fInfinity Matrix",
            "&6Gives Unlimited Flight",
            "&7Right-Click to enable/disable and claim",
            "&7Crouch and Right-Click to remove ownership",
            "&bSoulbound"
    );
    
    public InfinityMatrix() {
        super(Categories.INFINITY_CHEAT, ITEM, InfinityWorkbench.TYPE, new ItemStack[] {
                Items.INFINITY, null, Items.INFINITY, Items.INFINITY, null, Items.INFINITY,
                Items.INFINITY, Items.VOID_INGOT, Items.VOID_INGOT, Items.VOID_INGOT, Items.VOID_INGOT, Items.INFINITY,
                Items.VOID_INGOT, Items.VOID_INGOT, new ItemStack(Material.ELYTRA), new ItemStack(Material.ELYTRA), Items.VOID_INGOT, Items.VOID_INGOT,
                Items.VOID_INGOT, Items.VOID_INGOT, Items.INFINITY, Items.INFINITY, Items.VOID_INGOT, Items.VOID_INGOT,
                Items.INFINITY, Items.VOID_INGOT, Items.VOID_INGOT, Items.VOID_INGOT, Items.VOID_INGOT, Items.INFINITY,
                Items.INFINITY, null, Items.INFINITY, Items.INFINITY, null, Items.INFINITY
        });
        InfinityExpansion.inst().registerListener(this);
    }

    private static void disableFlight(Player p) {
        p.sendMessage( ChatColor.RED + "Infinity Flight Disabled!");
        p.setAllowFlight(false);
    }

    private static void enableFlight(Player p) {
        p.sendMessage( ChatColor.GREEN + "Infinity Flight Enabled!");
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
                        p.sendMessage( ChatColor.YELLOW + "You do not own this matrix!");
                        return;
                    }

                    if (p.isSneaking()) { //remove owner
                        iterator.remove();
                        meta.setLore(lore);
                        item.setItemMeta(meta);
                        p.sendMessage( ChatColor.GOLD + "Ownership removed!");
                        disableFlight(p);

                    } else if (p.getAllowFlight()) {
                        disableFlight(p);
                    } else {
                        enableFlight(p);
                    }

                    return;
                }
            }

            lore.add(ChatColor.GREEN + "UUID: " + p.getUniqueId().toString());
            meta.setLore(lore);
            item.setItemMeta(meta);
            p.sendMessage( ChatColor.GOLD + "Ownership claimed!");
            enableFlight(p);
        };
    }

}