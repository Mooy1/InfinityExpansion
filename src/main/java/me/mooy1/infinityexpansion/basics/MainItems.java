package me.mooy1.infinityexpansion.basics;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mooy1.infinityexpansion.Categories;
import me.mooy1.infinityexpansion.Items;
import me.mooy1.infinityexpansion.machines.VoidHarvester;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

import static me.mooy1.infinityexpansion.Utils.Compress;
import static me.mooy1.infinityexpansion.Utils.MiddleItem;

public class MainItems extends SlimefunItem {

    public MainItems(Type type) {
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
        BIT(Items.VOID_BIT, VoidHarvester.RECIPE_TYPE, MiddleItem(new CustomItem(Material.PAPER, "&fHarvested by &8Void &7Harvesters"))),
        DUST(Items.VOID_DUST, RecipeType.ENHANCED_CRAFTING_TABLE, Compress(Items.VOID_BIT)),
        INGOT(Items.VOID_INGOT, RecipeType.ENHANCED_CRAFTING_TABLE, Compress(Items.VOID_DUST));

        @Nonnull
        private final SlimefunItemStack item;
        private final RecipeType recipetype;
        private final ItemStack[] recipe;
    }
}
