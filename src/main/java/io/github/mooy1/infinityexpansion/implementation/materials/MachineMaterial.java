package io.github.mooy1.infinityexpansion.implementation.materials;

import io.github.mooy1.infinityexpansion.lists.InfinityRecipes;
import io.github.mooy1.infinityexpansion.lists.Items;
import io.github.mooy1.infinityexpansion.utils.RecipeUtils;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import io.github.mooy1.infinityexpansion.lists.Categories;
import io.github.mooy1.infinityexpansion.lists.RecipeTypes;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * machine material items
 *
 * @author Mooy1
 */
public class MachineMaterial extends SlimefunItem {

    public MachineMaterial(Type type) {
        super(type.getCategory(), type.getItem(), type.getRecipeType(), type.getRecipe());
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Type {

        MAGSTEEL_PLATE(Categories.MAIN, Items.MAGSTEEL_PLATE, RecipeType.ENHANCED_CRAFTING_TABLE, RecipeUtils.Compress(Items.MAGSTEEL)
        ),
        PLATE(Categories.MAIN, Items.MACHINE_PLATE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.REINFORCED_ALLOY_INGOT, SlimefunItems.REINFORCED_PLATE, SlimefunItems.REINFORCED_ALLOY_INGOT,
                Items.MAGSTEEL_PLATE, Items.TITANIUM, Items.MAGSTEEL_PLATE,
                SlimefunItems.REINFORCED_ALLOY_INGOT, SlimefunItems.REINFORCED_PLATE, SlimefunItems.REINFORCED_ALLOY_INGOT
        }),
        CIRCUIT(Categories.MAIN, Items.MACHINE_CIRCUIT, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.COPPER_INGOT, SlimefunItems.ELECTRO_MAGNET, SlimefunItems.COPPER_INGOT,
                SlimefunItems.COPPER_INGOT, SlimefunItems.ADVANCED_CIRCUIT_BOARD, SlimefunItems.COPPER_INGOT,
                SlimefunItems.COPPER_INGOT, SlimefunItems.ELECTRO_MAGNET, SlimefunItems.COPPER_INGOT
        }),
        CORE(Categories.MAIN, Items.MACHINE_CORE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                Items.TITANIUM, Items.MACHINE_CIRCUIT, Items.TITANIUM,
                Items.MACHINE_CIRCUIT, Items.MACHINE_PLATE, Items.MACHINE_CIRCUIT,
                Items.TITANIUM, Items.MACHINE_CIRCUIT, Items.TITANIUM,
        }),
        I_CIRCUIT(Categories.INFINITY_CHEAT, Items.INFINITE_MACHINE_CIRCUIT, RecipeTypes.INFINITY_WORKBENCH, InfinityRecipes.getRecipe(Items.INFINITE_MACHINE_CIRCUIT)),
        I_CORE(Categories.INFINITY_CHEAT, Items.INFINITE_MACHINE_CORE, RecipeTypes.INFINITY_WORKBENCH, InfinityRecipes.getRecipe(Items.INFINITE_MACHINE_CORE));

        @Nonnull
        private final Category category;
        private final SlimefunItemStack item;
        private final RecipeType recipeType;
        private final ItemStack[] recipe;
    }
}