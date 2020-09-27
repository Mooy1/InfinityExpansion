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

        CROW(MooyItems.MAGNONIUM_CROWN, new ItemStack[] {
                ingot, ingot, ingot,
                ingot, null, ingot,
                null, null, null
        });

        @Nonnull
        private final SlimefunItemStack item;
        private final ItemStack[] recipe;
    }
}