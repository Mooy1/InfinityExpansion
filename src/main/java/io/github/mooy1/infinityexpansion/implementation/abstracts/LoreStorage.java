package io.github.mooy1.infinityexpansion.implementation.abstracts;

import io.github.mooy1.infinitylib.items.LoreUtils;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public interface LoreStorage {
    
    int getOffset();
    int getLines();
    @Nonnull
    String getTarget();
    
    default void transfer(ItemStack output, ItemStack input) {
        LoreUtils.transferLore(output, input, getTarget(), getOffset(), getLines());
    }
    
}
