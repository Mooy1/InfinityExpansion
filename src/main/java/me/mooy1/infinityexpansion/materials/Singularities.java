package me.mooy1.infinityexpansion.materials;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mooy1.infinityexpansion.Categories;
import me.mooy1.infinityexpansion.Items;
import me.mooy1.infinityexpansion.Utils;
import me.mooy1.infinityexpansion.machines.SingularityConstructor;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;

import javax.annotation.Nonnull;

public class Singularities extends SlimefunItem {

    public Singularities(Type type) {
        super(Categories.INFINITY_MATERIALS,
                type.getItem(),
                SingularityConstructor.SINGULARITY_CONSTRUCTOR,
                Utils.MiddleItem(new CustomItem(Material.PAPER, "&fContructed with &e" + type.getAmount() + " " + type.getResource())));
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Type {

        COPPER(Items.COPPER_SINGULARITY, 8000, "&6Copper Ingots"),
        ZINC(Items.ZINC_SINGULARITY, 8000, "&7Zinc Ingots"),
        TIN(Items.TIN_SINGULARITY, 8000, "&7Tin Ingots"),
        ALUMINUM(Items.ALUMINUM_SINGULARITY, 8000, "&7Aluminum Ingots"),
        SILVER(Items.SILVER_SINGULARITY, 8000, "&7Silver Ingots"),
        MAGNESIUM(Items.MAGNESIUM_SINGULARITY, 8000, "&5Magnesium Ingots"),
        LEAD(Items.LEAD_SINGULARITY, 8000, "&7Lead Ingots"),

        GOLD(Items.GOLD_SINGULARITY, 8000, "&6Gold Ingots"),
        IRON(Items.IRON_SINGULARITY, 16000, "&7Iron Ingots"),
        DIAMOND(Items.DIAMOND_SINGULARITY, 8000, "&bDiamonds"),
        EMERALD(Items.EMERALD_SINGULARITY, 8000, "&aEmeralds"),
        NETHERITE(Items.NETHERITE_SINGULARITY, 800, "&8Netherite Ingots"),

        COAL(Items.COAL_SINGULARITY, 8000, "&8Coal"),
        LAPIS(Items.LAPIS_SINGULARITY, 16000, "&9Lapis"),
        REDSTONE(Items.REDSTONE_SINGULARITY, 16000, "&cRedstone"),
        QUARTZ(Items.QUARTZ_SINGULARITY, 16000, "&fQuartz"),

        INFINITY(Items.INFINITY_SINGULARITY, 1000, "&bInfinity Ingots");

        @Nonnull
        private final SlimefunItemStack item;
        private final int amount;
        private final String resource;

    }
}
