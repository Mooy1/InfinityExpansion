package me.mooy1.infinityexpansion.machines;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mooy1.infinityexpansion.Categories;
import me.mooy1.infinityexpansion.Items;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class IngotForge extends SlimefunItem {

    public IngotForge() {
        super(Categories.INFINITY_MACHINES, Items.INGOT_FORGE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.MAGSTEEL_PLATE, Items.MAGSTEEL_PLATE, Items.MAGSTEEL_PLATE,
                SlimefunItems.LARGE_CAPACITOR, new ItemStack(Material.SMITHING_TABLE), SlimefunItems.LARGE_CAPACITOR,
                Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        });
    }
}
