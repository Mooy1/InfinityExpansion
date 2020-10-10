package me.mooy1.infinityexpansion.materials;

import me.mooy1.infinityexpansion.setup.Categories;
import me.mooy1.infinityexpansion.Items;
import me.mooy1.infinityexpansion.utils.RecipeUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class EnderEssence extends SlimefunItem {

    private final ItemStack[] recipe = RecipeUtils.MiddleItem(new CustomItem(
            Material.PAPER,
            "&fGEO-Miner Material",
            "&aFound in the End and Void biomes",
            "&aMake sure to GEO-Scan the chunk first"
    ));

    public EnderEssence() {
        super(Categories.INFINITY_MATERIALS, Items.END_ESSENCE, RecipeType.GEO_MINER, null);
    }
}