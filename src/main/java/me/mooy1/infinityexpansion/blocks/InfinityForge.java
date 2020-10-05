package me.mooy1.infinityexpansion.blocks;

import me.mooy1.infinityexpansion.Categories;
import me.mooy1.infinityexpansion.Items;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class InfinityForge extends SlimefunItem {

    public InfinityForge() {
        super(Categories.INFINITY_MACHINES, Items.INFINITY_FORGE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            Items.MAGSTEEL_PLATE, Items.MAGSTEEL_PLATE, Items.MAGSTEEL_PLATE,
            Items.MAGSTEEL_PLATE, new ItemStack(Material.SMITHING_TABLE), Items.MAGSTEEL_PLATE,
            Items.MAGNONIUM_INGOT, Items.MACHINE_CORE, Items.MAGNONIUM_INGOT
        });
    }
}
