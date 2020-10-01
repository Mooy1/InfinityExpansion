package me.mooy1.infinityexpansion.materials;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mooy1.infinityexpansion.Categories;
import me.mooy1.infinityexpansion.Items;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class Ingots extends SlimefunItem {

    private final Type type;

    public Ingots(Ingots.Type type) {
        super(Categories.INFINITY_MATERIALS, type.getItem(), type.getRecipetype(), type.getRecipe());
        this.type = type;
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
            Items.VOID_DUST,
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
            Items.MAGNONIUM_INGOT,
            null,
            null,
            null
        });


        @Nonnull
        private final SlimefunItemStack item;
        private final RecipeType recipetype;
        private final ItemStack[] recipe;
    }
}
