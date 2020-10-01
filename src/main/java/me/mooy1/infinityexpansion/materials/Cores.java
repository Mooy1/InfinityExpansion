package me.mooy1.infinityexpansion.materials;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.food.FortuneCookie;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mooy1.infinityexpansion.Categories;
import me.mooy1.infinityexpansion.Items;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class Cores extends SlimefunItem {

    private final Core core;

    public Cores(Cores.Core core) {
        super(Categories.INFINITY_CORES, core.getItem(), RecipeType.ENHANCED_CRAFTING_TABLE, core.getRecipe());
        this.core = core;
    }

    public static ItemStack[] Compress(ItemStack item) {
        return new ItemStack[] {
            item, item, item, item, item, item, item, item, item,
        };
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Core {

        //Infinity singularities
        FORTUNE(Items.FORTUNE_SINGULARITY, new ItemStack[] {
                Items.DIAMOND_SINGULARITY,
                Items.EMERALD_SINGULARITY,
                Items.GOLD_SINGULARITY,
                Items.NETHERITE_SINGULARITY,
                null,
                null,
                null,
                null,
                null
        }),
        METAL(Items.METAL_SINGULARITY, new ItemStack[] {
                Items.IRON_SINGULARITY,
                Items.SILVER_SINGULARITY,
                Items.ALUMINUM_SINGULARITY,
                Items.ZINC_SINGULARITY,
                null,
                null,
                null,
                null,
                null
        }),
        EARTH(Items.EARTH_SINGULARITY, new ItemStack[] {
                Items.COAL_SINGULARITY,
                Items.TIN_SINGULARITY,
                Items.COPPER_SINGULARITY,
                Items.LEAD_SINGULARITY,
                null,
                null,
                null,
                null,
                null
        }),
        MAGIC(Items.MAGIC_SINGULARITY, new ItemStack[] {
                Items.REDSTONE_SINGULARITY,
                Items.LAPIS_SINGULARITY,
                Items.QUARTZ_SINGULARITY,
                Items.MAGNESIUM_SINGULARITY,
                null,
                null,
                null,
                null,
                null
        }),

        //singularities

        scMAGNESIUM(Items.MAGNESIUM_SINGULARITY, Compress(Items.MAGNESIUM_COMPRESSED_CORE)),
        scCOPPER(Items.COPPER_SINGULARITY, Compress(Items.COPPER_COMPRESSED_CORE)),
        scSILVER(Items.SILVER_SINGULARITY, Compress(Items.SILVER_COMPRESSED_CORE)),
        scALUMINUM(Items.ALUMINUM_SINGULARITY, Compress(Items.ALUMINUM_COMPRESSED_CORE)),
        scLEAD(Items.LEAD_SINGULARITY, Compress(Items.LEAD_COMPRESSED_CORE)),
        scZINC(Items.ZINC_SINGULARITY, Compress(Items.ZINC_COMPRESSED_CORE)),
        scTIN(Items.TIN_SINGULARITY, Compress(Items.TIN_COMPRESSED_CORE)),
        scIRON(Items.IRON_SINGULARITY, Compress(Items.IRON_COMPRESSED_CORE)),
        scGOLD(Items.GOLD_SINGULARITY, Compress(Items.GOLD_COMPRESSED_CORE)),
        scNETHERITE(Items.NETHERITE_SINGULARITY, Compress(Items.NETHERITE_COMPRESSED_CORE)),
        scDIAMOND(Items.DIAMOND_SINGULARITY, Compress(Items.DIAMOND_COMPRESSED_CORE)),
        scEMERALD(Items.EMERALD_SINGULARITY, Compress(Items.EMERALD_COMPRESSED_CORE)),
        scCOAL(Items.COAL_SINGULARITY, Compress(Items.COAL_COMPRESSED_CORE)),
        scLAPIS(Items.LAPIS_SINGULARITY, Compress(Items.LAPIS_COMPRESSED_CORE)),
        scREDSTONE(Items.REDSTONE_SINGULARITY, Compress(Items.REDSTONE_COMPRESSED_CORE)),
        scQUARTZ(Items.QUARTZ_SINGULARITY, Compress(Items.QUARTZ_COMPRESSED_CORE)),

        //compressed cores

        cMAGNESIUM(Items.MAGNESIUM_COMPRESSED_CORE, Compress(Items.MAGNESIUM_CORE)),
        cCOPPER(Items.COPPER_COMPRESSED_CORE, Compress(Items.COPPER_CORE)),
        cSILVER(Items.SILVER_COMPRESSED_CORE, Compress(Items.SILVER_CORE)),
        cALUMINUM(Items.ALUMINUM_COMPRESSED_CORE, Compress(Items.ALUMINUM_CORE)),
        cLEAD(Items.LEAD_COMPRESSED_CORE, Compress(Items.LEAD_CORE)),
        cZINC(Items.ZINC_COMPRESSED_CORE, Compress(Items.ZINC_CORE)),
        cTIN(Items.TIN_COMPRESSED_CORE, Compress(Items.TIN_CORE)),
        cIRON(Items.IRON_COMPRESSED_CORE, Compress(Items.IRON_CORE)),
        cGOLD(Items.GOLD_COMPRESSED_CORE, Compress(Items.GOLD_CORE)),
        cNETHERITE(Items.NETHERITE_COMPRESSED_CORE, Compress(Items.NETHERITE_CORE)),
        cDIAMOND(Items.DIAMOND_COMPRESSED_CORE, Compress(Items.DIAMOND_CORE)),
        cEMERALD(Items.EMERALD_COMPRESSED_CORE, Compress(Items.EMERALD_CORE)),
        cCOAL(Items.COAL_COMPRESSED_CORE, Compress(Items.COAL_CORE)),
        cLAPIS(Items.LAPIS_COMPRESSED_CORE, Compress(Items.LAPIS_CORE)),
        cREDSTONE(Items.REDSTONE_COMPRESSED_CORE, Compress(Items.REDSTONE_CORE)),
        cQUARTZ(Items.QUARTZ_COMPRESSED_CORE, Compress(Items.QUARTZ_CORE)),

        //cores

        MAGNESIUM(Items.MAGNESIUM_CORE, Compress(Items.MAGNESIUM_BLOCK)),
        COPPER(Items.COPPER_CORE, Compress(Items.COPPER_BLOCK)),
        SILVER(Items.SILVER_CORE, Compress(Items.SILVER_BLOCK)),
        ALUMINUM(Items.ALUMINUM_CORE, Compress(Items.ALUMINUM_BLOCK)),
        LEAD(Items.LEAD_CORE, Compress(Items.LEAD_BLOCK)),
        ZINC(Items.ZINC_CORE, Compress(Items.ZINC_BLOCK)),
        TIN(Items.TIN_CORE, Compress(Items.TIN_BLOCK)),
        IRON(Items.IRON_CORE, Compress(new ItemStack(Material.IRON_BLOCK))),
        GOLD(Items.GOLD_CORE, new ItemStack[] {
                new ItemStack(Material.GOLD_BLOCK),
                new ItemStack(Material.GOLD_BLOCK),
                new ItemStack(Material.GOLD_BLOCK),
                new ItemStack(Material.GOLD_BLOCK),
                SlimefunItems.GOLD_24K_BLOCK,
                new ItemStack(Material.GOLD_BLOCK),
                new ItemStack(Material.GOLD_BLOCK),
                new ItemStack(Material.GOLD_BLOCK),
                new ItemStack(Material.GOLD_BLOCK)
        }),
        NETHERITE(Items.NETHERITE_CORE, Compress(new ItemStack(Material.NETHERITE_INGOT))),
        DIAMOND(Items.DIAMOND_CORE, Compress(new ItemStack(Material.DIAMOND_BLOCK))),
        EMERALD(Items.EMERALD_CORE, Compress(new ItemStack(Material.EMERALD_BLOCK))),
        COAL(Items.COAL_CORE, Compress(new ItemStack(Material.COAL_BLOCK))),
        LAPIS(Items.LAPIS_CORE, Compress(new ItemStack(Material.LAPIS_BLOCK))),
        REDSTONE(Items.REDSTONE_CORE, Compress(new ItemStack(Material.REDSTONE_BLOCK))),
        QUARTZ(Items.QUARTZ_CORE, Compress(new ItemStack(Material.QUARTZ_BLOCK)));

        @Nonnull
        private final SlimefunItemStack item;
        private final ItemStack[] recipe;

    }
}
