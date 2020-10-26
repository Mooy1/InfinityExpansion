package me.mooy1.infinityexpansion.implementation.storage;

import me.mooy1.infinityexpansion.lists.Categories;
import me.mooy1.infinityexpansion.lists.Items;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import org.bukkit.inventory.ItemStack;

public class StorageDuct extends SlimefunItem{
    public StorageDuct() {
        super(Categories.STORAGE_TRANSPORT, Items.STORAGE_DUCT, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {

        });
    }
}
