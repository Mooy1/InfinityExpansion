package me.mooy1.infinityexpansion.implementation.materials;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mooy1.infinityexpansion.lists.Categories;
import me.mooy1.infinityexpansion.lists.Items;
import me.mooy1.infinityexpansion.setup.RecipeTypes;
import me.mooy1.infinityexpansion.utils.RecipeUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;


public class MainMaterials extends SlimefunItem {

    public MainMaterials(Type type) {
        super(type.getCategory(), type.getItem(), type.getRecipetype(), type.getRecipe());
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Type {

        INFINITY(Categories.INFINITY_MAIN, Items.INFINITE_INGOT, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.EARTH_SINGULARITY,
                Items.MYTHRIL,
                Items.FORTUNE_SINGULARITY,
                Items.MAGIC_SINGULARITY,
                Items.VOID_INGOT,
                Items.METAL_SINGULARITY,
                null, null, null
        }),

        BIT(Categories.INFINITY_MAIN, Items.VOID_BIT, RecipeTypes.VOID_HARVESTER,
                RecipeUtils.MiddleItem(new CustomItem(Material.IRON_NUGGET, "Void Bit", "&8Ignore this its for SF Calc"))),

        DUST(Categories.INFINITY_MAIN, Items.VOID_DUST, RecipeType.ENHANCED_CRAFTING_TABLE, RecipeUtils.Compress(Items.VOID_BIT)),
        INGOT(Categories.INFINITY_MAIN, Items.VOID_INGOT, RecipeType.ENHANCED_CRAFTING_TABLE, RecipeUtils.Compress(Items.VOID_DUST)),

        FORTUNE(Categories.INFINITY_MATERIALS, Items.FORTUNE_SINGULARITY, RecipeType.SMELTERY, new ItemStack[] {
                Items.GOLD_SINGULARITY,
                Items.DIAMOND_SINGULARITY,
                Items.EMERALD_SINGULARITY,
                Items.NETHERITE_SINGULARITY,
                Items.ADAMANTITE,
                null, null, null, null
        }),
        MAGIC(Categories.INFINITY_MATERIALS, Items.MAGIC_SINGULARITY, RecipeType.SMELTERY, new ItemStack[] {
                Items.REDSTONE_SINGULARITY,
                Items.LAPIS_SINGULARITY,
                Items.QUARTZ_SINGULARITY,
                Items.MAGNESIUM_SINGULARITY,
                Items.MAGNONIUM,
                null, null, null, null
        }),
        EARTH(Categories.INFINITY_MATERIALS, Items.EARTH_SINGULARITY, RecipeType.SMELTERY, new ItemStack[] {
                Items.COMPRESSED_COBBLESTONE_4,
                Items.COAL_SINGULARITY,
                Items.IRON_SINGULARITY,
                Items.COPPER_SINGULARITY,
                Items.LEAD_SINGULARITY,
                null, null, null, null
        }),
        METAL(Categories.INFINITY_MATERIALS, Items.METAL_SINGULARITY, RecipeType.SMELTERY, new ItemStack[] {
                Items.SILVER_SINGULARITY,
                Items.ALUMINUM_SINGULARITY,
                Items.TIN_SINGULARITY,
                Items.ZINC_SINGULARITY,
                Items.TITANIUM,
                null, null, null, null
        }),
        MAGSTEEL(Categories.INFINITY_MAIN, Items.MAGSTEEL, RecipeType.SMELTERY, new ItemStack[] {
                SlimefunItems.MAGNESIUM_INGOT,
                SlimefunItems.STEEL_INGOT,
                SlimefunItems.MAGNESIUM_DUST,
                null, null, null, null, null, null
        }),
        TITANIUM(Categories.INFINITY_MAIN, Items.TITANIUM, RecipeType.SMELTERY, new ItemStack[] {
                SlimefunItems.REINFORCED_ALLOY_INGOT,
                Items.IRON_SINGULARITY,
                SlimefunItems.HARDENED_METAL_INGOT,
                null, null, null, null, null, null
        }),
        MYTHRIL(Categories.INFINITY_MAIN, Items.MYTHRIL, RecipeType.SMELTERY, new ItemStack[] {
                SlimefunItems.REINFORCED_ALLOY_INGOT,
                Items.DIAMOND_SINGULARITY,
                SlimefunItems.HARDENED_METAL_INGOT,
                null, null, null, null, null, null
        }),
        ADAMANTITE(Categories.INFINITY_MAIN, Items.ADAMANTITE, RecipeType.SMELTERY, new ItemStack[] {
                SlimefunItems.REDSTONE_ALLOY,
                Items.REDSTONE_SINGULARITY,
                Items.MAGSTEEL,
                null, null, null, null, null, null
        }),
        MAGNONIUM(Categories.INFINITY_MAIN, Items.MAGNONIUM, RecipeType.SMELTERY, new ItemStack[] {
                Items.MAGSTEEL,
                Items.MAGNESIUM_SINGULARITY,
                Items.END_ESSENCE,
                null, null, null, null, null, null
        });

        @Nonnull
        private final Category category;
        private final SlimefunItemStack item;
        private final RecipeType recipetype;
        private final ItemStack[] recipe;
    }
}
