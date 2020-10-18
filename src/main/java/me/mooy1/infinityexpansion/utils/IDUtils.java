package me.mooy1.infinityexpansion.utils;

import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public final class IDUtils {

    private IDUtils() {}

    public static String getIDFromItem(ItemStack item) {
        if (SlimefunItem.getByItem(item) != null) {
            return SlimefunItem.getByItem(item).getId();
        } else if (item != null){
            return item.getType().toString();
        } else {
            return "";
        }
    }

    public static ItemStack getItemFromID(String id, int amount) {
        if (SlimefunItem.getByID(id) != null) {
            return new CustomItem(SlimefunItem.getByID(id).getItem(), amount);
        } else {
            return new ItemStack(Material.getMaterial(id), amount);
        }
    }

    public static ItemStack getCustomItemFromID(String id, int amount, String name, String... lore) {
        return new CustomItem(getItemFromID(id, amount), name, lore);
    }
}
