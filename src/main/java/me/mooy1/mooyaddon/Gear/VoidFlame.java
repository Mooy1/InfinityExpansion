package me.mooy1.mooyaddon.Gear;

import me.mooy1.mooyaddon.MooyItems;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class VoidFlame extends SlimefunItem {
    public VoidFlame() {
        super(MooyItems.MOOYMAIN,
                MooyItems.VOID_FLAME,
                RecipeType.MAGIC_WORKBENCH,
                new ItemStack[] {
                        MooyItems.VOID_DUST, MooyItems.VOID_DUST, MooyItems.VOID_DUST,
                        MooyItems.VOID_DUST, new ItemStack(Material.BOOK), MooyItems.VOID_DUST,
                        MooyItems.VOID_DUST, MooyItems.VOID_DUST, MooyItems.VOID_DUST,
        });
    }
}
