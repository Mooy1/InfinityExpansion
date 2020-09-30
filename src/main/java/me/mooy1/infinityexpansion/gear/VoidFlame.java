package me.mooy1.infinityexpansion.gear;

import me.mooy1.infinityexpansion.Categories;
import me.mooy1.infinityexpansion.Items;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class VoidFlame extends SlimefunItem {

    public VoidFlame() {
        super(Categories.INFINITY_GEAR, Items.VOID_FLAME, RecipeType.MAGIC_WORKBENCH, new ItemStack[] {
            Items.VOID_DUST, Items.VOID_DUST, Items.VOID_DUST,
            Items.VOID_DUST, new ItemStack(Material.BOOK), Items.VOID_DUST,
            Items.VOID_DUST, Items.VOID_DUST, Items.VOID_DUST,
        });
    }
}
