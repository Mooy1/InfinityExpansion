package io.github.mooy1.infinityexpansion.implementation.gear;

import io.github.mooy1.infinityexpansion.lists.Categories;
import io.github.mooy1.infinityexpansion.lists.Items;
import io.github.thebusybiscuit.slimefun4.implementation.items.blocks.UnplaceableBlock;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * fire aspect
 *
 * @author Mooy1
 */
public class EnderFlame extends UnplaceableBlock {
    
    public EnderFlame() {
        super(Categories.MAIN_MATERIALS, Items.ENDER_FLAME, RecipeType.MAGIC_WORKBENCH, new ItemStack[]{
                Items.ENDER_ESSENCE, Items.ENDER_ESSENCE, Items.ENDER_ESSENCE,
                Items.ENDER_ESSENCE, new ItemStack(Material.BOOK), Items.ENDER_ESSENCE,
                Items.ENDER_ESSENCE, Items.ENDER_ESSENCE, Items.ENDER_ESSENCE,
        });
    }
}
