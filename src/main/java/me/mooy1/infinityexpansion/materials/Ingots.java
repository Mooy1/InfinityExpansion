package me.mooy1.infinityexpansion.materials;

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

import static me.mooy1.infinityexpansion.materials.Cores.Compress;

public class Ingots extends SlimefunItem {

    public Ingots(Type type) {
        super(Categories.INFINITY_MATERIALS, type.getItem(), type.getRecipetype(), type.getRecipe());
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Type {

        MAGSTEEL(Items.MAGSTEEL, RecipeType.SMELTERY, new ItemStack[] {
            SlimefunItems.MAGNESIUM_INGOT,
            SlimefunItems.STEEL_INGOT,
            null, null, null, null, null, null, null
        }),
        MAGNONIUM(Items.MAGNONIUM_INGOT, RecipeType.SMELTERY, new ItemStack[] {
            Items.MAGSTEEL,
            Items.MAGNESIUM_COMPRESSED_CORE,
            Items.ENDER_ESSENCE,
            SlimefunItems.REINFORCED_ALLOY_INGOT,
            SlimefunItems.REDSTONE_ALLOY,
            SlimefunItems.COMPRESSED_CARBON,
            null, null, null
        }),
        INFINITY(Items.INFINITE_INGOT, RecipeType.SMELTERY, new ItemStack[] {
            Items.FORTUNE_SINGULARITY,
            Items.METAL_SINGULARITY,
            Items.MAGIC_SINGULARITY,
            Items.EARTH_SINGULARITY,
            Items.COMPRESSED_COBBLESTONE_7,
            Items.VOID_INGOT,
            null,
            null,
            null
        }),
        BIT(Items.VOID_BIT, VoidHarvester.RECIPE_TYPE, new ItemStack[] {
                null, null, null,
                null, new CustomItem(Material.PAPER, "&aHarvested by &8Void &7Harvesters"), null,
                null, null, null,
        }),
        DUST(Items.VOID_DUST, RecipeType.ENHANCED_CRAFTING_TABLE, Compress(Items.VOID_BIT)),
        INGOT(Items.VOID_INGOT, RecipeType.ENHANCED_CRAFTING_TABLE, Compress(Items.VOID_DUST));

        @Nonnull
        private final SlimefunItemStack item;
        private final RecipeType recipetype;
        private final ItemStack[] recipe;
    }
}
