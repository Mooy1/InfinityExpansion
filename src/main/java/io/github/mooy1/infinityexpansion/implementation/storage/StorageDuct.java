package io.github.mooy1.infinityexpansion.implementation.storage;

import io.github.mooy1.infinityexpansion.lists.Items;
import io.github.mooy1.infinityexpansion.lists.Categories;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Extends a storage network viewers range
 *
 * @author Mooy1
 */
public class StorageDuct extends SlimefunItem {
    private static final ItemStack glass = new ItemStack(Material.GRAY_STAINED_GLASS);
    private static final ItemStack hopper = new ItemStack(Material.HOPPER);

    public StorageDuct() {
        super(Categories.STORAGE_TRANSPORT, Items.STORAGE_DUCT, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                glass, glass, glass,
                hopper, hopper, hopper,
                glass, glass, glass
        }, new CustomItem(Items.STORAGE_DUCT, 8));
    }
}
