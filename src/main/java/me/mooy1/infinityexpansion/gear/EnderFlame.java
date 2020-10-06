package me.mooy1.infinityexpansion.gear;

import me.mooy1.infinityexpansion.Categories;
import me.mooy1.infinityexpansion.Items;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class EnderFlame extends SlimefunItem {

    public EnderFlame() {
        super(Categories.INFINITY_GEAR, Items.ENDER_FLAME, RecipeType.MAGIC_WORKBENCH, new ItemStack[] {
            Items.END_ESSENCE, Items.END_ESSENCE, Items.END_ESSENCE,
            Items.END_ESSENCE, new ItemStack(Material.BOOK), Items.END_ESSENCE,
            Items.END_ESSENCE, Items.END_ESSENCE, Items.END_ESSENCE,
        });
    }
}
