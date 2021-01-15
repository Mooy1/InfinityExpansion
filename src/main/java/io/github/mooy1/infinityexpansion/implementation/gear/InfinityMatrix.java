package io.github.mooy1.infinityexpansion.implementation.gear;

import io.github.mooy1.infinityexpansion.implementation.blocks.InfinityWorkbench;
import io.github.mooy1.infinityexpansion.implementation.materials.CompressedItem;
import io.github.mooy1.infinityexpansion.implementation.materials.SmelteryItem;
import io.github.mooy1.infinityexpansion.setup.categories.Categories;
import io.github.mooy1.infinitylib.PluginUtils;
import io.github.mooy1.infinitylib.items.LoreUtils;
import io.github.mooy1.infinitylib.player.MessageUtils;
import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import io.github.thebusybiscuit.slimefun4.core.attributes.Soulbound;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.UUID;

/**
 * Flight
 * 
 */
public final class InfinityMatrix extends SlimefunItem implements Listener, Soulbound, NotPlaceable {
    
    public static final SlimefunItemStack ITEM = new SlimefunItemStack(
            "INFINITY_MATRIX",
            Material.NETHER_STAR,
            "&fInfinity Matrix",
            "&6Gives Unlimited Flight",
            "&7Right-Click to enable/disable and claim",
            "&7Crouch and Right-Click to remove ownership"
    );
    
    public InfinityMatrix() { // TODO switch to PDCs
        super(Categories.INFINITY_CHEAT, ITEM, InfinityWorkbench.TYPE, new ItemStack[] {
                SmelteryItem.INFINITY, null, SmelteryItem.INFINITY, SmelteryItem.INFINITY, null, SmelteryItem.INFINITY,
                SmelteryItem.INFINITY, CompressedItem.VOID_INGOT, CompressedItem.VOID_INGOT, CompressedItem.VOID_INGOT, CompressedItem.VOID_INGOT, SmelteryItem.INFINITY,
                CompressedItem.VOID_INGOT, CompressedItem.VOID_INGOT, new ItemStack(Material.ELYTRA), new ItemStack(Material.ELYTRA), CompressedItem.VOID_INGOT, CompressedItem.VOID_INGOT,
                CompressedItem.VOID_INGOT, CompressedItem.VOID_INGOT, SmelteryItem.INFINITY, SmelteryItem.INFINITY, CompressedItem.VOID_INGOT, CompressedItem.VOID_INGOT,
                SmelteryItem.INFINITY, CompressedItem.VOID_INGOT, CompressedItem.VOID_INGOT, CompressedItem.VOID_INGOT, CompressedItem.VOID_INGOT, SmelteryItem.INFINITY,
                SmelteryItem.INFINITY, null, SmelteryItem.INFINITY, SmelteryItem.INFINITY, null, SmelteryItem.INFINITY
        });
        PluginUtils.registerEvents(this);
    }

    @EventHandler
    private void onRightClick(PlayerRightClickEvent e) {
        ItemStack item = e.getItem();
        if (!(SlimefunItem.getByItem(item) instanceof InfinityMatrix)) {
            return;
        }
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return;
        }
        List<String> lore = meta.getLore();
        if (lore == null) {
            return;
        }

        Player p = e.getPlayer();

        for (String line : lore) {
            if (ChatColor.stripColor(line).contains("UUID: ")) {
                Player owner = Bukkit.getOfflinePlayer(UUID.fromString(ChatColor.stripColor(line).substring(6))).getPlayer();
                if (!p.equals(owner)) {
                    MessageUtils.message(p, ChatColor.YELLOW + "You do not own this matrix!");
                    return;
                }

                if (p.isSneaking()) { //remove owner
                    LoreUtils.removeLore(item, -1, "UUID: ", 2);
                    MessageUtils.message(p, ChatColor.GOLD + "Ownership removed!");
                    disableFlight(p);

                } else if (p.getAllowFlight()) {
                    disableFlight(p);
                } else {
                    enableFlight(p);
                }

                return;
            }
        }
        
        LoreUtils.addLore(item, "", ChatColor.GREEN + "UUID: " + p.getUniqueId().toString());
        MessageUtils.message(p, ChatColor.GOLD + "Ownership claimed!");
        enableFlight(p);
    }

    private static void disableFlight(Player p) {
        MessageUtils.message(p, ChatColor.RED + "Infinity Flight Disabled!");
        p.setAllowFlight(false);
    }

    private static void enableFlight(Player p) {
        MessageUtils.message(p, ChatColor.GREEN + "Infinity Flight Enabled!");
        p.setAllowFlight(true);
    }
    
}