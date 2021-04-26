package io.github.mooy1.infinityexpansion.implementation.gear;

import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinityexpansion.categories.Categories;
import io.github.mooy1.infinityexpansion.implementation.blocks.InfinityWorkbench;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import io.github.thebusybiscuit.slimefun4.core.attributes.Soulbound;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

/**
 * tools
 *
 * @author Mooy1
 */
public final class InfinityTool extends SlimefunItem implements Soulbound, NotPlaceable {
    
    InfinityTool(SlimefunItemStack stack, ItemStack[] recipe) {
        super(Categories.INFINITY_CHEAT, stack, InfinityWorkbench.TYPE, recipe);
    }
    
}