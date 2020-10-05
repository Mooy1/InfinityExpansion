package me.mooy1.infinityexpansion.machines;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mooy1.infinityexpansion.Categories;
import me.mooy1.infinityexpansion.Items;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import org.bukkit.inventory.ItemStack;

public class InfinityForge extends SlimefunItem {

    public InfinityForge() {
        super(Categories.INFINITY_MACHINES, Items.INFINITY_FORGE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            Items.VOID_INGOT, Items.VOID_INGOT, Items.VOID_INGOT,
                SlimefunItems.ENERGIZED_CAPACITOR, Items.INGOT_FORGE, SlimefunItems.ENERGIZED_CAPACITOR,
            Items.MAGNONIUM_INGOT, Items.INFINITE_INGOT, Items.MAGNONIUM_INGOT
        });
    }
}
