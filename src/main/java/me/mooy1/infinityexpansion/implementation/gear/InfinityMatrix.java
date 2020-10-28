package me.mooy1.infinityexpansion.implementation.gear;

import me.mooy1.infinityexpansion.lists.Categories;
import me.mooy1.infinityexpansion.lists.InfinityRecipes;
import me.mooy1.infinityexpansion.lists.Items;
import me.mooy1.infinityexpansion.lists.RecipeTypes;
import me.mooy1.infinityexpansion.utils.MessageUtils;
import me.mooy1.infinityexpansion.utils.StackUtils;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InfinityMatrix extends SlimefunItem {
    public InfinityMatrix() {
        super(Categories.INFINITY_CHEAT, Items.INFINITY_MATRIX, RecipeTypes.INFINITY_WORKBENCH, InfinityRecipes.getRecipe(Items.INFINITY_MATRIX));
    }

    public static void handler(ItemStack item, Player p) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return;
        }
        List<String> lore = meta.getLore();
        if (lore == null) {
            return;
        }

        for (String line : lore) {
            if (ChatColor.stripColor(line).contains("UUID: ")) {
                Player owner = Bukkit.getOfflinePlayer(UUID.fromString(ChatColor.stripColor(line).substring(6))).getPlayer();
                if (!p.equals(owner)) {
                    MessageUtils.message(p,  ChatColor.YELLOW + "You do not own this matrix!");
                    return;
                }

                if (p.isSneaking()) { //remove owner
                    List<String> replaceLore = new ArrayList<>();
                    replaceLore.add(null);
                    replaceLore.add(null);
                    StackUtils.insertLore(item, replaceLore, "UUID: ", -1);
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

        List<String> addLore = new ArrayList<>();
        addLore.add("");
        addLore.add(ChatColor.GREEN + "UUID: " + p.getUniqueId().toString());
        StackUtils.addLore(item, addLore);
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
