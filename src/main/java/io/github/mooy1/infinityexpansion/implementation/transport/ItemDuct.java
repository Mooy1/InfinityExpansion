package io.github.mooy1.infinityexpansion.implementation.transport;

import io.github.mooy1.infinityexpansion.lists.Items;
import io.github.mooy1.infinityexpansion.lists.Categories;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Extends an output duct's range
 *
 * @author Mooy1
 */
public class ItemDuct extends SlimefunItem {
    private static final ItemStack glass = new ItemStack(Material.GLASS);

    public ItemDuct() {
        super(Categories.STORAGE_TRANSPORT, Items.ITEM_DUCT, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                glass, glass, glass,
                new ItemStack(Material.REDSTONE), Items.MAGSTEEL, new ItemStack(Material.QUARTZ),
                glass, glass, glass
        }, new CustomItem(Items.ITEM_DUCT, 4));
    }
}
