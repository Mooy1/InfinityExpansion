package me.mooy1.infinityexpansion.utils;

import org.bukkit.inventory.ItemStack;

public final class RecipeUtils {

    private RecipeUtils() {}

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
