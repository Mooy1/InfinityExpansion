package me.mooy1.infinityexpansion.Materials;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mooy1.infinityexpansion.Items;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.annotation.Nonnull;

public class Cores extends SlimefunItem{

    private final Core core;

    public Cores(Cores.Core core) {
        super(Items.MOOYMATERIALS, core.getItem(), core.getRecipetype(), core.getRecipe());
        this.core = core;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Core {

        MAGNESIUM(Items.MAGNESIUM_CORE, RecipeType.ENHANCED_CRAFTING_TABLE, Compress(new SlimefunItemStack(SlimefunItems.MAGNESIUM_INGOT, 64))),
        COPPER(Items.COPPER_CORE, RecipeType.ENHANCED_CRAFTING_TABLE, Compress(new SlimefunItemStack(SlimefunItems.COPPER_INGOT, 64))),
        SILVER(Items.SILVER_CORE, RecipeType.ENHANCED_CRAFTING_TABLE, Compress(new SlimefunItemStack(SlimefunItems.SILVER_INGOT, 64))),
        ALUMINUM(Items.ALUMINUM_CORE, RecipeType.ENHANCED_CRAFTING_TABLE, Compress(new SlimefunItemStack(SlimefunItems.ALUMINUM_INGOT, 64))),
        LEAD(Items.LEAD_CORE, RecipeType.ENHANCED_CRAFTING_TABLE, Compress(new SlimefunItemStack(SlimefunItems.LEAD_INGOT, 64))),
        ZINC(Items.ZINC_CORE, RecipeType.ENHANCED_CRAFTING_TABLE, Compress(new SlimefunItemStack(SlimefunItems.ZINC_INGOT, 64))),
        TIN(Items.TIN_CORE, RecipeType.ENHANCED_CRAFTING_TABLE, Compress(new SlimefunItemStack(SlimefunItems.TIN_INGOT, 64))),
        IRON(Items.IRON_CORE, RecipeType.ENHANCED_CRAFTING_TABLE, Compress(new ItemStack(Material.IRON_INGOT, 64))),
        GOLD(Items.GOLD_CORE, RecipeType.ENHANCED_CRAFTING_TABLE, Compress(new ItemStack(Material.GOLD_INGOT, 64))),
        NETHERITE(Items.NETHERITE_CORE, RecipeType.ENHANCED_CRAFTING_TABLE, Compress(new ItemStack(Material.NETHERITE_INGOT, 16))),
        DIAMOND(Items.DIAMOND_CORE, RecipeType.ENHANCED_CRAFTING_TABLE, Compress(new ItemStack(Material.DIAMOND, 32))),
        EMERALD(Items.EMERALD_CORE, RecipeType.ENHANCED_CRAFTING_TABLE, Compress(new ItemStack(Material.EMERALD, 32))),
        COAL(Items.COAL_CORE, RecipeType.ENHANCED_CRAFTING_TABLE, Compress(new ItemStack(Material.COAL, 64))),
        LAPIS(Items.LAPIS_CORE, RecipeType.ENHANCED_CRAFTING_TABLE, Compress(new ItemStack(Material.LAPIS_BLOCK, 64))),
        REDSTONE(Items.REDSTONE_CORE, RecipeType.ENHANCED_CRAFTING_TABLE, Compress(new ItemStack(Material.REDSTONE_BLOCK, 64)));

        @Nonnull
        private final SlimefunItemStack item;
        private final RecipeType recipetype;
        private final ItemStack[] recipe;

    }

    public static ItemStack[] Compress(ItemStack item) {
        return new ItemStack [] {
                item, item, item, item, item, item, item, item, item,
        };
    }
}