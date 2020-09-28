package me.mooy1.infinityexpansion.Gear;

import me.mooy1.infinityexpansion.Items;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class VoidFlame extends SlimefunItem {
    public VoidFlame() {
        super(Items.MOOYGEAR,
                Items.VOID_FLAME,
                RecipeType.MAGIC_WORKBENCH,
                new ItemStack[] {
                        Items.VOID_DUST, Items.VOID_DUST, Items.VOID_DUST,
                        Items.VOID_DUST, new ItemStack(Material.BOOK), Items.VOID_DUST,
                        Items.VOID_DUST, Items.VOID_DUST, Items.VOID_DUST,
        });
    }
}
