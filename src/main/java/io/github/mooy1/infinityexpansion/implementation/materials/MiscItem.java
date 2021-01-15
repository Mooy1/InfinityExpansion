package io.github.mooy1.infinityexpansion.implementation.materials;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.implementation.machines.VoidHarvester;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public final class MiscItem extends MaterialItem {
    
    public static final SlimefunItemStack VOID_BIT = new SlimefunItemStack(
            "VOID_BIT",
            Material.IRON_NUGGET,
            "&8Void Bit",
            "&7&oIt feels... empty"
    );
    
    public static void setup(InfinityExpansion plugin) {
        new MiscItem(VOID_BIT, VoidHarvester.TYPE, null);
    }
    
    MiscItem(SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(item, recipeType, recipe);
    }

}
