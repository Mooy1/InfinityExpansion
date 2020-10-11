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
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;


public class OtherMaterials extends SlimefunItem {

    public OtherMaterials(Type type) {
        super(Categories.INFINITY_MAIN, type.getItem(), type.getRecipetype(), type.getRecipe());
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Type {

        FORTUNE(Items.FORTUNE_SINGULARITY, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            Items.DIAMOND_SINGULARITY,
            Items.EMERALD_SINGULARITY,
            Items.GOLD_SINGULARITY,
            Items.NETHERITE_SINGULARITY,
            Items.MAGNONIUM_INGOT,
            Items.REDSTONE_SINGULARITY,
            Items.LAPIS_SINGULARITY,
            Items.QUARTZ_SINGULARITY,
            Items.SILVER_SINGULARITY
        }),
        EARTH(Items.EARTH_SINGULARITY, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            Items.COAL_SINGULARITY,
            Items.TIN_SINGULARITY,
            Items.COPPER_SINGULARITY,
            Items.LEAD_SINGULARITY,
            Items.COMPRESSED_COBBLESTONE_5,
            Items.IRON_SINGULARITY,
            Items.ALUMINUM_SINGULARITY,
            Items.ZINC_SINGULARITY,
            Items.MAGNESIUM_SINGULARITY
        }),

        MAGSTEEL(Items.MAGSTEEL, RecipeType.SMELTERY, new ItemStack[] {
                SlimefunItems.MAGNESIUM_INGOT,
                SlimefunItems.STEEL_INGOT,
                null, null, null, null, null, null, null
        }),
        MAGNONIUM(Items.MAGNONIUM_INGOT, RecipeType.SMELTERY, new ItemStack[] {
                Items.MAGSTEEL,
                Items.MAGNESIUM_SINGULARITY,
                Items.END_ESSENCE,
                SlimefunItems.REINFORCED_ALLOY_INGOT,
                SlimefunItems.REDSTONE_ALLOY,
                null, null, null, null
        });

        @Nonnull
        private final SlimefunItemStack item;
        private final RecipeType recipetype;
        private final ItemStack[] recipe;
    }
}
