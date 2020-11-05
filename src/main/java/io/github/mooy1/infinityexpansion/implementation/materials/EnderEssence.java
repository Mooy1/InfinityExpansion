package io.github.mooy1.infinityexpansion.implementation.materials;

import io.github.mooy1.infinityexpansion.lists.Categories;
import io.github.mooy1.infinityexpansion.lists.Items;
import io.github.mooy1.infinityexpansion.utils.RecipeUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;

/**
 * Guide item for ender essence geo-resource
 *
 * @author Mooy1
 */
public class EnderEssence extends SlimefunItem {

    public EnderEssence() {
        super(Categories.INFINITY_MAIN, Items.ENDER_ESSENCE, RecipeType.GEO_MINER, RecipeUtils.MiddleItem(
                new CustomItem(Material.BLAZE_POWDER, "Ender Essence", "&8Ignore this its for SF Calc")));
    }
}