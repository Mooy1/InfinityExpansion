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
                MiddleItem(new CustomItem(Material.PAPER, "&fCombine &e" + type.getAmount() + "&fitems")));
    }

    public static ItemStack[] MiddleItem(ItemStack item) {
        return new ItemStack[] {
                null, null, null, null , item, null, null, null, null
        };
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Type {

        COPPER(Items.COPPER_SINGULARITY, 32000),
        ZINC(Items.ZINC_SINGULARITY, 32000),
        TIN(Items.TIN_SINGULARITY, 32000),
        ALUMINUM(Items.ALUMINUM_SINGULARITY, 32000),
        SILVER(Items.SILVER_SINGULARITY, 32000),
        MAGNESIUM(Items.MAGNESIUM_SINGULARITY, 32000),
        LEAD(Items.LEAD_SINGULARITY, 32000),

        GOLD(Items.GOLD_SINGULARITY, 32000),
        IRON(Items.IRON_SINGULARITY, 64000),
        DIAMOND(Items.DIAMOND_SINGULARITY, 8000),
        EMERALD(Items.EMERALD_SINGULARITY, 8000),
        NETHERITE(Items.NETHERITE_SINGULARITY, 800),

        COAL(Items.COAL_SINGULARITY, 32000),
        LAPIS(Items.LAPIS_SINGULARITY, 64000),
        REDSTONE(Items.REDSTONE_SINGULARITY, 64000),
        QUARTZ(Items.QUARTZ_SINGULARITY, 64000);


        @Nonnull
        private final SlimefunItemStack item;
        private final int amount;

    }
}
