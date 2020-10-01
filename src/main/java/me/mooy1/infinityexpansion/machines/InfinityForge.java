package me.mooy1.infinityexpansion.machines;

import me.mooy1.infinityexpansion.Categories;
import me.mooy1.infinityexpansion.Items;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class InfinityForge extends SlimefunItem {

    public InfinityForge() {
        super(Categories.INFINITY_MACHINES, Items.INFINITY_FORGE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            Items.MACHINE_PLATE, Items.MACHINE_PLATE, Items.MACHINE_PLATE,
                Items.MACHINE_PLATE, new ItemStack(Material.ANVIL), Items.MACHINE_PLATE,
            Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        });
    }
}
