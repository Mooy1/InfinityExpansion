package me.mooy1.infinityexpansion.items;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mooy1.infinityexpansion.Items;
import me.mooy1.infinityexpansion.setup.Categories;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class StorageDrive extends SlimefunItem {

    public StorageDrive(Type type) {
        super(Categories.INFINITY_BASICS, type.getItem(), type.getRecipeType(), type.getRecipe());
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Type {
        BASIC(Items.BASIC_STORAGE_DRIVE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.MACHINE_CIRCUIT, Items.MACHINE_CIRCUIT, Items.MACHINE_CIRCUIT,
                Items.MAGSTEEL, Items.MAGSTEEL_PLATE, Items.MAGSTEEL,
                Items.MAGSTEEL, Items.MAGSTEEL, Items.MAGSTEEL
        }),
        ADVANCED(Items.ADVANCED_STORAGE_DRIVE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.MACHINE_CIRCUIT, Items.MACHINE_CIRCUIT, Items.MACHINE_CIRCUIT,
                Items.MAGSTEEL_PLATE, Items.BASIC_STORAGE_DRIVE, Items.MAGSTEEL_PLATE,
                Items.MAGSTEEL_PLATE, Items.MAGSTEEL_PLATE, Items.MAGSTEEL_PLATE
        }),
        REINFORCED(Items.REINFORCED_STORAGE_DRIVE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.MACHINE_CIRCUIT, Items.MACHINE_CIRCUIT, Items.MACHINE_CIRCUIT,
                Items.MACHINE_PLATE, Items.ADVANCED_STORAGE_DRIVE, Items.MACHINE_PLATE,
                Items.MACHINE_PLATE, Items.MACHINE_PLATE, Items.MACHINE_PLATE
        }),
        VOID(Items.VOID_STORAGE_DRIVE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.MACHINE_CIRCUIT, Items.MACHINE_CIRCUIT, Items.MACHINE_CIRCUIT,
                Items.VOID_INGOT, Items.REINFORCED_STORAGE_DRIVE, Items.VOID_INGOT,
                Items.VOID_INGOT, Items.VOID_INGOT, Items.VOID_INGOT
        }),
        INFINITY(Items.INFINITY_STORAGE_DRIVE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.INFINITE_MACHINE_CIRCUIT, Items.INFINITE_MACHINE_CIRCUIT, Items.INFINITE_MACHINE_CIRCUIT,
                Items.INFINITE_INGOT, Items.VOID_STORAGE_DRIVE, Items.INFINITE_INGOT,
                Items.INFINITE_INGOT, Items.INFINITE_INGOT, Items.INFINITE_INGOT
        });

        @Nonnull
        private final SlimefunItemStack item;
        private final RecipeType recipeType;
        private final ItemStack[] recipe;
    }
}
