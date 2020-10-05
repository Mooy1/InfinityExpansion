package me.mooy1.infinityexpansion.materials;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.food.FortuneCookie;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mooy1.infinityexpansion.Categories;
import me.mooy1.infinityexpansion.Items;
import me.mooy1.infinityexpansion.machines.SingularityConstructor;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class Singularities extends SlimefunItem {

    public Singularities(Type type) {
        super(Categories.INFINITY_MATERIALS,
                type.getItem(),
                SingularityConstructor.SINGULARITY_CONSTRUCTOR,
                MiddleItem(new CustomItem(Material.PAPER, "&fContructed with &e" + type.getAmount() + " " + type.getResource())));
    }

    public static ItemStack[] MiddleItem(ItemStack item) {
        return new ItemStack[] {
                null, null, null, null , item, null, null, null, null
        };
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Type {

        COPPER(Items.COPPER_SINGULARITY, 16000, "&6Copper Ingots"),
        ZINC(Items.ZINC_SINGULARITY, 16000, "&7Zinc Ingots"),
        TIN(Items.TIN_SINGULARITY, 16000, "&7Tin Ingots"),
        ALUMINUM(Items.ALUMINUM_SINGULARITY, 16000, "&7Aluminum Ingots"),
        SILVER(Items.SILVER_SINGULARITY, 16000, "&7Silver Ingots"),
        MAGNESIUM(Items.MAGNESIUM_SINGULARITY, 16000, "&5Magnesium Ingots"),
        LEAD(Items.LEAD_SINGULARITY, 16000, "&7Lead Ingots"),

        GOLD(Items.GOLD_SINGULARITY, 16000, "&6Gold Ingots"),
        IRON(Items.IRON_SINGULARITY, 32000, "&7Iron Ingots"),
        DIAMOND(Items.DIAMOND_SINGULARITY, 8000, "&bDiamonds"),
        EMERALD(Items.EMERALD_SINGULARITY, 8000, "&aEmeralds"),
        NETHERITE(Items.NETHERITE_SINGULARITY, 800, "&8Netherite Ingots"),

        COAL(Items.COAL_SINGULARITY, 16000, "&8Coal"),
        LAPIS(Items.LAPIS_SINGULARITY, 32000, "&9Lapis"),
        REDSTONE(Items.REDSTONE_SINGULARITY, 32000, "&cRedstone"),
        QUARTZ(Items.QUARTZ_SINGULARITY, 32000, "&fQuartz"),

        INFINITY(Items.INFINITY_SINGULARITY, 1000, "&bInfinity Ingots");

        @Nonnull
        private final SlimefunItemStack item;
        private final int amount;
        private final String resource;

    }
}
