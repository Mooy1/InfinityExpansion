package me.mooy1.infinityexpansion.implementation.storage;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mooy1.infinityexpansion.lists.Items;
import me.mooy1.infinityexpansion.lists.Categories;
import me.mooy1.infinityexpansion.lists.InfinityRecipes;
import me.mooy1.infinityexpansion.lists.RecipeTypes;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class StorageDrive extends SlimefunItem {

    public StorageDrive(Type type) {
        super(type.getCategory(), type.getItem(), type.getRecipeType(), type.getRecipe());
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Type {
        BASIC(Categories.INFINITY_STORAGE, Items.BASIC_STORAGE_DRIVE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.MACHINE_CIRCUIT, Items.MACHINE_CIRCUIT, Items.MACHINE_CIRCUIT,
                Items.MAGSTEEL, SlimefunItems.ELECTRO_MAGNET, Items.MAGSTEEL,
                Items.MAGSTEEL, Items.MAGSTEEL, Items.MAGSTEEL
        }),
        ADVANCED(Categories.INFINITY_STORAGE, Items.ADVANCED_STORAGE_DRIVE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.MACHINE_CIRCUIT, Items.MACHINE_CIRCUIT, Items.MACHINE_CIRCUIT,
                Items.MAGSTEEL_PLATE, Items.BASIC_STORAGE_DRIVE, Items.MAGSTEEL_PLATE,
                Items.MAGSTEEL_PLATE, Items.MAGSTEEL_PLATE, Items.MAGSTEEL_PLATE
        }),
        REINFORCED(Categories.INFINITY_STORAGE, Items.REINFORCED_STORAGE_DRIVE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.MACHINE_CIRCUIT, Items.MACHINE_CIRCUIT, Items.MACHINE_CIRCUIT,
                Items.MACHINE_PLATE, Items.ADVANCED_STORAGE_DRIVE, Items.MACHINE_PLATE,
                Items.MACHINE_PLATE, Items.MACHINE_PLATE, Items.MACHINE_PLATE
        }),
        VOID(Categories.INFINITY_STORAGE, Items.VOID_STORAGE_DRIVE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.MACHINE_CIRCUIT, Items.MACHINE_CIRCUIT, Items.MACHINE_CIRCUIT,
                Items.VOID_INGOT, Items.REINFORCED_STORAGE_DRIVE, Items.VOID_INGOT,
                Items.VOID_INGOT, Items.VOID_INGOT, Items.VOID_INGOT
        }),
        INFINITY(Categories.INFINITY_CHEAT, Items.INFINITY_STORAGE_DRIVE, RecipeTypes.INFINITY_WORKBENCH, InfinityRecipes.getRecipe(Items.INFINITY_STORAGE_DRIVE));

        @Nonnull
        private final Category category;
        private final SlimefunItemStack item;
        private final RecipeType recipeType;
        private final ItemStack[] recipe;
    }
}
