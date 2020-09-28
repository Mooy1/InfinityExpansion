package me.mooy1.mooyaddon.Gear;

import me.mooy1.mooyaddon.MooyItems;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.inventory.ItemStack;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.annotation.Nonnull;

public class InfinityGear extends SlimefunItem{

    private final InfinityTool infinityTool;

    public InfinityGear(InfinityTool infinitytool) {
        super(MooyItems.MOOYGEAR, infinitytool.getItem(), RecipeType.ENHANCED_CRAFTING_TABLE, infinitytool.getRecipe());
        this.infinityTool = infinitytool;
    }

    private static final ItemStack ingot = MooyItems.INFINITY_INGOT;

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum InfinityTool {

        CROWN(MooyItems.INFINITY_CROWN, new ItemStack[] {
                ingot, ingot, ingot,
                ingot, null, ingot,
                null, null, null
        }),
        CHESTPLATE(MooyItems.INFINITY_CHESTPLATE, new ItemStack[] {
                ingot, null, ingot,
                ingot, ingot, ingot,
                ingot, ingot, ingot
        }),
        LEGGINGS(MooyItems.INFINITY_LEGGINGS, new ItemStack[] {
                ingot, ingot, ingot,
                ingot, null, ingot,
                ingot, null, ingot
        }),
        BOOTS(MooyItems.INFINITY_BOOTS, new ItemStack[] {
                null, null, null,
                ingot, null, ingot,
                ingot, null, ingot
        }),
        PICKAXE(MooyItems.INFINITY_PICKAXE, new ItemStack[] {
                ingot, ingot, ingot,
                null, ingot, null,
                null, ingot, null
        }),
        BLADE(MooyItems.INFINITY_BLADE, new ItemStack[] {
                null, ingot, null,
                null, ingot, null,
                null, ingot, null
        });

        @Nonnull
        private final SlimefunItemStack item;
        private final ItemStack[] recipe;
    }
}