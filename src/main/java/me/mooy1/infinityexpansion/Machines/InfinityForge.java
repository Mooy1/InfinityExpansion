package me.mooy1.infinityexpansion.Machines;

import me.mooy1.infinityexpansion.Items;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class InfinityForge extends SlimefunItem {
    public InfinityForge() {
        super(Items.MOOYMACHINES,
                Items.INFINITY_FORGE,
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                        Items.MAGNONIUM_INGOT, Items.MAGNONIUM_INGOT, Items.MAGNONIUM_INGOT,
                        Items.MAGNONIUM_INGOT, new ItemStack(Material.ANVIL), Items.MAGNONIUM_INGOT,
                        Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
                });
    }
}
