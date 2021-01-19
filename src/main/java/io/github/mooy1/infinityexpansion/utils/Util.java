package io.github.mooy1.infinityexpansion.utils;

import io.github.mooy1.infinitylib.items.LoreUtils;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public final class Util {

    public static final int[] largeOutput = {
            13, 14, 15, 16,
            22, 23, 24, 25,
            31, 32, 33, 34,
            40, 41, 42, 43
    };
    
    public static final int[] largeOutputBorder = {
            3, 4, 5, 6, 7, 8,
            12, 17,
            21, 26,
            30, 35,
            39, 44,
            48, 49, 50, 51, 52, 53
            
    };
    
    @Nonnull
    public static ItemStack getDisplayItem(@Nonnull ItemStack output) {
        LoreUtils.addLore(output, "", "&a-------------------", "&a\u21E8 Click to craft", "&a-------------------");
        return output;
    }
    
}
