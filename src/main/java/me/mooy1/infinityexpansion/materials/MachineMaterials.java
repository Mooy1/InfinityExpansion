package me.mooy1.infinityexpansion.materials;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mooy1.infinityexpansion.setup.Categories;
import me.mooy1.infinityexpansion.Items;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class MachineMaterials extends SlimefunItem {

    public MachineMaterials(Type type) {
        super(Categories.INFINITY_MACHINES, type.getItem(), type.getRecipeType(), type.getRecipe());
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Type {

        MAGPLATE(Items.MAGSTEEL_PLATE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.MAGSTEEL, Items.MAGSTEEL, Items.MAGSTEEL,
                Items.MAGSTEEL, SlimefunItems.STEEL_PLATE, Items.MAGSTEEL,
                Items.MAGSTEEL, Items.MAGSTEEL, Items.MAGSTEEL
        }),
        PLATE(Items.MACHINE_PLATE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            SlimefunItems.STEEL_PLATE, SlimefunItems.REINFORCED_PLATE, SlimefunItems.STEEL_PLATE,
            SlimefunItems.REINFORCED_PLATE, Items.MAGSTEEL_PLATE, SlimefunItems.REINFORCED_PLATE,
            SlimefunItems.STEEL_PLATE, SlimefunItems.REINFORCED_PLATE, SlimefunItems.STEEL_PLATE
        }),
        CIRCUIT(Items.MACHINE_CIRCUIT, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            SlimefunItems.COPPER_INGOT, SlimefunItems.GOLD_24K, SlimefunItems.COPPER_INGOT,
            SlimefunItems.COPPER_INGOT, SlimefunItems.ADVANCED_CIRCUIT_BOARD, SlimefunItems.COPPER_INGOT,
            SlimefunItems.COPPER_INGOT, SlimefunItems.GOLD_24K, SlimefunItems.COPPER_INGOT
        }),
        CORE(Items.MACHINE_CORE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            SlimefunItems.STEEL_PLATE, Items.MACHINE_PLATE, SlimefunItems.STEEL_PLATE,
            Items.MACHINE_CIRCUIT, new ItemStack(Material.IRON_BLOCK), Items.MACHINE_CIRCUIT,
            SlimefunItems.STEEL_PLATE, Items.MACHINE_PLATE, SlimefunItems.STEEL_PLATE
        }),
        ICIRCUIT(Items.INFINITE_MACHINE_CIRCUIT, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            Items.INFINITE_INGOT, Items.MAGNONIUM_INGOT, Items.INFINITE_INGOT,
            Items.VOID_INGOT, Items.MACHINE_CIRCUIT, Items.VOID_INGOT,
            Items.INFINITE_INGOT, Items.MAGNONIUM_INGOT, Items.INFINITE_INGOT
        }),
        ICORE(Items.INFINITE_MACHINE_CORE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            Items.MACHINE_PLATE, Items.VOID_INGOT, Items.MACHINE_PLATE,
            Items.INFINITE_MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.INFINITE_MACHINE_CIRCUIT,
            Items.MACHINE_PLATE, Items.VOID_INGOT, Items.MACHINE_PLATE
        });

        @Nonnull
        private final SlimefunItemStack item;
        private final RecipeType recipeType;
        private final ItemStack[] recipe;
    }
}