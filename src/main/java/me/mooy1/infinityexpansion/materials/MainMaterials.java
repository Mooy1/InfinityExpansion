package me.mooy1.infinityexpansion.materials;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mooy1.infinityexpansion.machines.VoidHarvester;
import me.mooy1.infinityexpansion.setup.Categories;
import me.mooy1.infinityexpansion.Items;
import me.mooy1.infinityexpansion.utils.RecipeUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;


public class MainMaterials extends SlimefunItem {

    public MainMaterials(Type type) {
        super(Categories.INFINITY_MAIN, type.getItem(), type.getRecipetype(), type.getRecipe());
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
        BIT(Items.VOID_BIT, VoidHarvester.RECIPE_TYPE, RecipeUtils.MiddleItem(Items.VOID_BIT_DUMMY)),
        DUST(Items.VOID_DUST, RecipeType.ENHANCED_CRAFTING_TABLE, RecipeUtils.Compress(Items.VOID_BIT)),
        INGOT(Items.VOID_INGOT, RecipeType.ENHANCED_CRAFTING_TABLE, RecipeUtils.Compress(Items.VOID_DUST)),
        FORTUNE(Items.FORTUNE_SINGULARITY, RecipeType.SMELTERY, new ItemStack[] {
                Items.GOLD_SINGULARITY,
                Items.DIAMOND_SINGULARITY,
                Items.EMERALD_SINGULARITY,
                Items.NETHERITE_SINGULARITY
        }),
        MAGIC(Items.MAGIC_SINGULARITY, RecipeType.SMELTERY, new ItemStack[] {
                Items.REDSTONE_SINGULARITY,
                Items.LAPIS_SINGULARITY,
                Items.QUARTZ_SINGULARITY,
                Items.MAGNESIUM_SINGULARITY
        }),
        EARTH(Items.EARTH_SINGULARITY, RecipeType.SMELTERY, new ItemStack[] {
                Items.COMPRESSED_COBBLESTONE_6,
                Items.COAL_SINGULARITY,
                Items.IRON_SINGULARITY,
                Items.COPPER_SINGULARITY
        }),
        METAL(Items.METAL_SINGULARITY, RecipeType.SMELTERY, new ItemStack[] {
                Items.SILVER_SINGULARITY,
                Items.ALUMINUM_SINGULARITY,
                Items.TIN_SINGULARITY,
                Items.ZINC_SINGULARITY,
                Items.LEAD_SINGULARITY
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
