package me.mooy1.infinityexpansion.basics;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mooy1.infinityexpansion.Categories;
import me.mooy1.infinityexpansion.Items;
import me.mooy1.infinityexpansion.machines.VoidHarvester;
import me.mooy1.infinityexpansion.utils.RecipeUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class MainMaterials extends SlimefunItem {

    public MainMaterials(Type type) {
        super(Categories.INFINITY_BASICS, type.getItem(), type.getRecipetype(), type.recipe);
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Type {

        INFINITY(Items.INFINITE_INGOT, RecipeType.SMELTERY, new ItemStack[] {
                Items.EARTH_SINGULARITY,
                Items.VOID_INGOT,
                Items.FORTUNE_SINGULARITY,
                null,
                null,
                null,
                null,
                null,
                null
        }),
        BIT(Items.VOID_BIT, VoidHarvester.RECIPE_TYPE, null),
        DUST(Items.VOID_DUST, RecipeType.ENHANCED_CRAFTING_TABLE, RecipeUtils.Compress(Items.VOID_BIT)),
        INGOT(Items.VOID_INGOT, RecipeType.ENHANCED_CRAFTING_TABLE, RecipeUtils.Compress(Items.VOID_DUST));

        @Nonnull
        private final SlimefunItemStack item;
        private final RecipeType recipetype;
        private final ItemStack[] recipe;
    }
}
