package io.github.mooy1.infinityexpansion.implementation.template;

import io.github.mooy1.infinityexpansion.utils.StackUtils;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public interface LoreStorage {
    int getOffset();
    int getLines();
    @Nonnull
    String getTarget();
    
    default void transfer(ItemStack output, ItemStack input) {
        StackUtils.transferLore(output, input, getTarget(), getOffset(), getLines());
    }
}
