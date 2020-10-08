package me.mooy1.infinityexpansion.materials;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mooy1.infinityexpansion.Categories;
import me.mooy1.infinityexpansion.Items;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

import me.mooy1.infinityexpansion.utils.RecipeUtils;

public class CompressedCobblestone extends SlimefunItem {

    public CompressedCobblestone(Type type) {
        super(Categories.INFINITY_MATERIALS, type.getItem(), RecipeType.ENHANCED_CRAFTING_TABLE,
                type.getRecipe());
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)

    public enum Type {
        ONE(Items.COMPRESSED_COBBLESTONE_1, RecipeUtils.Compress(new ItemStack(Material.COBBLESTONE))),
        TWO(Items.COMPRESSED_COBBLESTONE_2, RecipeUtils.Compress(Items.COMPRESSED_COBBLESTONE_1)),
        THREE(Items.COMPRESSED_COBBLESTONE_3, RecipeUtils.Compress(Items.COMPRESSED_COBBLESTONE_2)),
        FOUR(Items.COMPRESSED_COBBLESTONE_4, RecipeUtils.Compress(Items.COMPRESSED_COBBLESTONE_3)),
        FIVE(Items.COMPRESSED_COBBLESTONE_5, RecipeUtils.Compress(Items.COMPRESSED_COBBLESTONE_4)),
        SIX(Items.COMPRESSED_COBBLESTONE_6, RecipeUtils.Compress(Items.COMPRESSED_COBBLESTONE_5)),
        SEVEN(Items.COMPRESSED_COBBLESTONE_7, RecipeUtils.Compress(Items.COMPRESSED_COBBLESTONE_6)),
        EIGHT(Items.COMPRESSED_COBBLESTONE_8, RecipeUtils.Compress(Items.COMPRESSED_COBBLESTONE_7)),
        NINE(Items.COMPRESSED_COBBLESTONE_9, RecipeUtils.Compress(Items.COMPRESSED_COBBLESTONE_8));

        @Nonnull
        private final SlimefunItemStack item;
        private final ItemStack[] recipe;
    }
}