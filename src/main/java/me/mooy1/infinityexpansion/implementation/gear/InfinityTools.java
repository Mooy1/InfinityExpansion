package me.mooy1.infinityexpansion.implementation.gear;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mooy1.infinityexpansion.lists.Categories;
import me.mooy1.infinityexpansion.lists.Items;
import me.mooy1.infinityexpansion.lists.InfinityRecipes;
import me.mooy1.infinityexpansion.lists.RecipeTypes;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

import javax.annotation.Nonnull;

/**
 * String tools lul
 *
 * @author Mooy1
 */
public class InfinityTools extends SlimefunItem {

    public InfinityTools(@Nonnull Type type) {
        super(Categories.INFINITY_CHEAT, type.getItem(), RecipeTypes.INFINITY_WORKBENCH, InfinityRecipes.getRecipe(type.getItem()));
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Type {

        PICKAXE(Items.INFINITY_PICKAXE),
        WINGS(Items.INFINITY_MATRIX),
        BOW(Items.INFINITY_BOW),
        SHOVEL(Items.INFINITY_SHOVEL),
        AXE(Items.INFINITY_AXE),
        SHIELD(Items.INFINITY_SHIELD),
        BLADE(Items.INFINITY_BLADE);

        @Nonnull
        private final SlimefunItemStack item;
    }
}