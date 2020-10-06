package me.mooy1.infinityexpansion;

import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Utils {

    public static String getIDofItem(ItemStack item) {
        if (SlimefunItem.getByItem(item) != null) {
            return SlimefunItem.getByItem(item).getID();
        } else {
            return item.getType().toString();
        }
    }

    public static ItemStack getItemFromID(String id, int amount) {
        if (SlimefunItem.getByID(id) != null) {
            return new CustomItem(SlimefunItem.getByID(id).getItem(), amount);
        } else {
            return new ItemStack(Material.getMaterial(id), amount);
        }
    }

    public static ItemStack[] Compress(ItemStack item) {
        return new ItemStack[]{
                item, item, item, item, item, item, item, item, item,
        };
    }

    public static ItemStack[] MiddleItem(ItemStack item) {
        return new ItemStack[] {
                null, null, null, null , item, null, null, null, null
        };
    }
}
