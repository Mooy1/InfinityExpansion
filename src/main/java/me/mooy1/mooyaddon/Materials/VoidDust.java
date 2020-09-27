package me.mooy1.mooyaddon.Materials;

import me.mooy1.mooyaddon.MooyItems;
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
        super(MooyItems.MOOYMATERIALS, MooyItems.VOID_DUST, RecipeType.GEO_MINER, new ItemStack[] {
                        null, null, null,
                        null, voiddust, null,
                        null, null, null
                }
        );
    }
}
