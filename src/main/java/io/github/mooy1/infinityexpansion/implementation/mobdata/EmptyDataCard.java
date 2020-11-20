package io.github.mooy1.infinityexpansion.implementation.mobdata;

import io.github.mooy1.infinityexpansion.lists.Categories;
import io.github.mooy1.infinityexpansion.lists.Items;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.inventory.ItemStack;

public class EmptyDataCard extends SlimefunItem {
    public EmptyDataCard() {
        super(Categories.MOB_SIMULATION, Items.EMPTY_DATA_CARD, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                SlimefunItems.MAGNESIUM_INGOT, Items.MACHINE_CIRCUIT, SlimefunItems.MAGNESIUM_INGOT,
                SlimefunItems.SYNTHETIC_SAPPHIRE, SlimefunItems.SYNTHETIC_DIAMOND, SlimefunItems.SYNTHETIC_EMERALD,
                SlimefunItems.MAGNESIUM_INGOT, Items.MACHINE_CIRCUIT, SlimefunItems.MAGNESIUM_INGOT
        }, new SlimefunItemStack(Items.EMPTY_DATA_CARD, 3));
    }
}
