package me.mooy1.mooyaddon.Items;

import me.mooy1.mooyaddon.MooyItems;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class VoidDust extends SlimefunItem {

    private static final ItemStack voiddust = new CustomItem(
            Material.PAPER,
            "&fHint!",
            "&a&oMake sure to first GEO-Scan the chunk in which you are",
            "&a&omining to discover harvest Void Dust");

    public VoidDust() {
        super(MooyItems.MOOYMAIN, MooyItems.VOID_DUST, RecipeType.GEO_MINER, new ItemStack[] {
                        null, null, null,
                        null, voiddust, null,
                        null, null, null
                }
        );
    }
}
