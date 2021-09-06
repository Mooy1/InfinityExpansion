package io.github.mooy1.infinityexpansion.items.gear;

import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinityexpansion.categories.Groups;
import io.github.mooy1.infinityexpansion.items.blocks.InfinityWorkbench;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import io.github.thebusybiscuit.slimefun4.core.attributes.Soulbound;

/**
 * tools
 *
 * @author Mooy1
 */
public final class InfinityTool extends SlimefunItem implements Soulbound, NotPlaceable {

    public InfinityTool(SlimefunItemStack stack, ItemStack[] recipe) {
        super(Groups.INFINITY_CHEAT, stack, InfinityWorkbench.TYPE, recipe);
    }

}