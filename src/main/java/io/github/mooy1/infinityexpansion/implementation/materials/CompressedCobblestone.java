package io.github.mooy1.infinityexpansion.implementation.materials;

import io.github.mooy1.infinityexpansion.lists.Items;
import io.github.mooy1.infinityexpansion.utils.RecipeUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import io.github.mooy1.infinityexpansion.lists.Categories;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * Compressed cobbles
 *
 * @author Mooy1
 */
public class CompressedCobblestone extends SlimefunItem {

    public CompressedCobblestone(Type type) {
        super(Categories.INFINITY_MAIN, type.getItem(), RecipeType.ENHANCED_CRAFTING_TABLE,
                type.getRecipe());
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)

    public enum Type {
        ONE(Items.COMPRESSED_COBBLESTONE_1, RecipeUtils.Compress(new ItemStack(Material.COBBLESTONE))),
        TWO(Items.COMPRESSED_COBBLESTONE_2, RecipeUtils.Compress(Items.COMPRESSED_COBBLESTONE_1)),
        THREE(Items.COMPRESSED_COBBLESTONE_3, RecipeUtils.Compress(Items.COMPRESSED_COBBLESTONE_2)),
        FOUR(Items.COMPRESSED_COBBLESTONE_4, RecipeUtils.Compress(Items.COMPRESSED_COBBLESTONE_3)),
        FIVE(Items.COMPRESSED_COBBLESTONE_5, RecipeUtils.Compress(Items.COMPRESSED_COBBLESTONE_4));

        @Nonnull
        private final SlimefunItemStack item;
        private final ItemStack[] recipe;
    }
}