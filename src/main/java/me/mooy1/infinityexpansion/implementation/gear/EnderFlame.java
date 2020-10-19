package me.mooy1.infinityexpansion.implementation.gear;

import io.github.thebusybiscuit.slimefun4.implementation.items.blocks.UnplaceableBlock;
import me.mooy1.infinityexpansion.lists.Categories;
import me.mooy1.infinityexpansion.lists.Items;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class EnderFlame extends UnplaceableBlock {

    public EnderFlame() {
        super(Categories.INFINITY_MAIN, Items.ENDER_FLAME, RecipeType.MAGIC_WORKBENCH, new ItemStack[]{
                Items.END_ESSENCE, Items.END_ESSENCE, Items.END_ESSENCE,
                Items.END_ESSENCE, new ItemStack(Material.BOOK), Items.END_ESSENCE,
                Items.END_ESSENCE, Items.END_ESSENCE, Items.END_ESSENCE,
        });
    }
}
