package me.mooy1.infinityexpansion.gear;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mooy1.infinityexpansion.setup.Categories;
import me.mooy1.infinityexpansion.Items;
import me.mooy1.infinityexpansion.setup.InfinityRecipes;
import me.mooy1.infinityexpansion.setup.RecipeTypes;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class InfinityTools extends SlimefunItem {

    public InfinityTools(@Nonnull Type type) {
        super(Categories.INFINITY_GEAR, type.getItem(), RecipeTypes.INFINITY_WORKBENCH, type.getRecipe());
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Type {

        PICKAXE(Items.INFINITY_PICKAXE, InfinityRecipes.PICK),
        WINGS(Items.INFINITY_WINGS, InfinityRecipes.WINGS),
        BOW(Items.INFINITY_BOW, InfinityRecipes.BOW),
        SHOVEL(Items.INFINITY_SHOVEL, InfinityRecipes.SHOVEL),
        AXE(Items.INFINITY_AXE, InfinityRecipes.AXE),
        SHIELD(Items.INFINITY_SHIELD, InfinityRecipes.SHIELD),
        BLADE(Items.INFINITY_BLADE, InfinityRecipes.BLADE);

        @Nonnull
        private final SlimefunItemStack item;
        private final ItemStack[] recipe;
    }
}