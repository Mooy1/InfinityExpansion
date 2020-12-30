package io.github.mooy1.infinityexpansion.implementation.gear;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.implementation.abstracts.LoreStorage;
import io.github.mooy1.infinityexpansion.lists.Categories;
import io.github.mooy1.infinityexpansion.lists.InfinityRecipes;
import io.github.mooy1.infinityexpansion.lists.Items;
import io.github.mooy1.infinityexpansion.lists.RecipeTypes;
import io.github.mooy1.infinitylib.items.LoreUtils;
import io.github.mooy1.infinitylib.player.MessageUtils;
import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import io.github.thebusybiscuit.slimefun4.core.attributes.Soulbound;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;

/**
 * Flight
 * 
 */
public class InfinityMatrix extends SlimefunItem implements Listener, LoreStorage, Soulbound, NotPlaceable {

    public InfinityMatrix(InfinityExpansion plugin) {
        super(Categories.INFINITY_CHEAT, Items.INFINITY_MATRIX, RecipeTypes.INFINITY_WORKBENCH, InfinityRecipes.getRecipe(Items.INFINITY_MATRIX));
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    private static void onRightClick(PlayerRightClickEvent e) {
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

    @Override
    public int getOffset() {
        return -1;
    }

    @Override
    public int getLines() {
        return 2;
    }

    @Nonnull
    @Override
    public String getTarget() {
        return "UUID:";
    }
}