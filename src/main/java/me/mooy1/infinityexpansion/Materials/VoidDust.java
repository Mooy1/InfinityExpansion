package me.mooy1.infinityexpansion.Materials;

import me.mooy1.infinityexpansion.Categories;
import me.mooy1.infinityexpansion.Items;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class VoidDust extends SlimefunItem {

    private static final ItemStack voiddust = new CustomItem(
            Material.PAPER,
            "&fGEO-Miner Material",
            "&aFound in the End and Void biomes",
            "&aMake sure to GEO-Scan the chunk first"
    );

    public VoidDust() {
        super(Categories.INFINITY_MATERIALS, Items.VOID_DUST, RecipeType.GEO_MINER, new ItemStack[] {
                        null, null, null,
                        null, voiddust, null,
                        null, null, null
                }
        );
    }
}
