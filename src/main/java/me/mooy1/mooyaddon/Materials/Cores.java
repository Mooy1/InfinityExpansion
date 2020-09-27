package me.mooy1.mooyaddon.Materials;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mooy1.mooyaddon.MooyItems;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.annotation.Nonnull;

public class Cores extends SlimefunItem{

    private final Core core;

    public Cores(Cores.Core core) {
        super(MooyItems.MOOYMATERIALS, core.getItem(), core.getRecipetype(), core.getRecipe());
        this.core = core;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Core {

        MAGNESIUM(MooyItems.MAGNESIUM_CORE, RecipeType.ENHANCED_CRAFTING_TABLE, Compress(new SlimefunItemStack(SlimefunItems.MAGNESIUM_INGOT, 64))),
        COPPER(MooyItems.COPPER_CORE, RecipeType.ENHANCED_CRAFTING_TABLE, Compress(new SlimefunItemStack(SlimefunItems.COPPER_INGOT, 64))),
        SILVER(MooyItems.SILVER_CORE, RecipeType.ENHANCED_CRAFTING_TABLE, Compress(new SlimefunItemStack(SlimefunItems.LEAD_INGOT, 64))),
        ALUMINUM(MooyItems.ALUMINUM_CORE, RecipeType.ENHANCED_CRAFTING_TABLE, Compress(new SlimefunItemStack(SlimefunItems.COPPER_INGOT, 64))),
        LEAD(MooyItems.LEAD_CORE, RecipeType.ENHANCED_CRAFTING_TABLE, Compress(new SlimefunItemStack(SlimefunItems.LEAD_INGOT, 64))),
        ZINC(MooyItems.ZINC_CORE, RecipeType.ENHANCED_CRAFTING_TABLE, Compress(new SlimefunItemStack(SlimefunItems.ZINC_INGOT, 64))),
        TIN(MooyItems.TIN_CORE, RecipeType.ENHANCED_CRAFTING_TABLE, Compress(new SlimefunItemStack(SlimefunItems.TIN_INGOT, 64))),
        IRON(MooyItems.IRON_CORE, RecipeType.ENHANCED_CRAFTING_TABLE, Compress(new ItemStack(Material.IRON_INGOT, 64))),
        GOLD(MooyItems.GOLD_CORE, RecipeType.ENHANCED_CRAFTING_TABLE, Compress(new ItemStack(Material.GOLD_INGOT, 64))),
        NETHERITE(MooyItems.NETHERITE_CORE, RecipeType.ENHANCED_CRAFTING_TABLE, Compress(new ItemStack(Material.NETHERITE_INGOT, 16))),
        DIAMOND(MooyItems.DIAMOND_CORE, RecipeType.ENHANCED_CRAFTING_TABLE, Compress(new ItemStack(Material.DIAMOND, 32))),
        EMERALD(MooyItems.EMERALD_CORE, RecipeType.ENHANCED_CRAFTING_TABLE, Compress(new ItemStack(Material.EMERALD, 32))),
        COAL(MooyItems.COAL_CORE, RecipeType.ENHANCED_CRAFTING_TABLE, Compress(new ItemStack(Material.COAL, 64))),
        LAPIS(MooyItems.LAPIS_CORE, RecipeType.ENHANCED_CRAFTING_TABLE, Compress(new ItemStack(Material.LAPIS_BLOCK, 64))),
        REDSTONE(MooyItems.REDSTONE_CORE, RecipeType.ENHANCED_CRAFTING_TABLE, Compress(new ItemStack(Material.REDSTONE_BLOCK, 64)));

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