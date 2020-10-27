package me.mooy1.infinityexpansion.implementation.materials;

import me.mooy1.infinityexpansion.lists.Categories;
import me.mooy1.infinityexpansion.lists.Items;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;

/**
 * Guide item for ender essence geo-resource
 *
 * @author Mooy1
 */
public class EnderEssence extends SlimefunItem {

    public EnderEssence() {
        super(Categories.INFINITY_MAIN, Items.END_ESSENCE, RecipeType.GEO_MINER, null);
    }
}