package me.mooy1.infinityexpansion.materials;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mooy1.infinityexpansion.machines.SingularityConstructor;
import me.mooy1.infinityexpansion.setup.Categories;
import me.mooy1.infinityexpansion.Items;
import me.mooy1.infinityexpansion.utils.RecipeUtils;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

import javax.annotation.Nonnull;

public class Singularities extends SlimefunItem {

    public Singularities(Type type) {
        super(Categories.INFINITY_MATERIALS, type.getItem(), SingularityConstructor.RECIPE_TYPE, RecipeUtils.MiddleItem(type.getRecipe()));
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Type {

        COPPER(Items.COPPER_SINGULARITY, Items.COPPER_AMOUNT),
        ZINC(Items.ZINC_SINGULARITY, Items.ZINC_AMOUNT),
        TIN(Items.TIN_SINGULARITY, Items.TIN_AMOUNT),
        ALUMINUM(Items.ALUMINUM_SINGULARITY, Items.ALUMINUM_AMOUNT),
        SILVER(Items.SILVER_SINGULARITY, Items.SILVER_AMOUNT),
        MAGNESIUM(Items.MAGNESIUM_SINGULARITY, Items.MAGNESIUM_AMOUNT),
        LEAD(Items.LEAD_SINGULARITY, Items.LEAD_AMOUNT),

        GOLD(Items.GOLD_SINGULARITY, Items.GOLD_AMOUNT),
        IRON(Items.IRON_SINGULARITY, Items.IRON_AMOUNT),
        DIAMOND(Items.DIAMOND_SINGULARITY, Items.DIAMOND_AMOUNT),
        EMERALD(Items.EMERALD_SINGULARITY, Items.EMERALD_AMOUNT),
        NETHERITE(Items.NETHERITE_SINGULARITY, Items.NETHERITE_AMOUNT),

        COAL(Items.COAL_SINGULARITY, Items.COAL_AMOUNT),
        LAPIS(Items.LAPIS_SINGULARITY, Items.LAPIS_AMOUNT),
        REDSTONE(Items.REDSTONE_SINGULARITY, Items.REDSTONE_AMOUNT),
        QUARTZ(Items.QUARTZ_SINGULARITY, Items.QUARTZ_AMOUNT);

        @Nonnull
        private final SlimefunItemStack item;
        private final SlimefunItemStack recipe;
    }
}
