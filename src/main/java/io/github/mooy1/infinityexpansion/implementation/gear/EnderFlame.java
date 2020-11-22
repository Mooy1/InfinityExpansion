package io.github.mooy1.infinityexpansion.implementation.gear;

import io.github.mooy1.infinityexpansion.lists.Items;
import io.github.thebusybiscuit.slimefun4.implementation.items.blocks.UnplaceableBlock;
import io.github.mooy1.infinityexpansion.lists.Categories;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

/**
 * fire aspect
 *
 * @author Mooy1
 */
public class EnderFlame extends UnplaceableBlock {
    
    private static final ItemStack OUTPUT = new CustomItem(Material.ENCHANTED_BOOK, "&cEnder Flame");
    
    static  {
        OUTPUT.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 5);
    }

    public EnderFlame() {
        super(Categories.MAIN, Items.ENDER_FLAME, RecipeType.MAGIC_WORKBENCH, new ItemStack[]{
                Items.ENDER_ESSENCE, Items.ENDER_ESSENCE, Items.ENDER_ESSENCE,
                Items.ENDER_ESSENCE, new ItemStack(Material.BOOK), Items.ENDER_ESSENCE,
                Items.ENDER_ESSENCE, Items.ENDER_ESSENCE, Items.ENDER_ESSENCE,
        }, OUTPUT);
    }
}
