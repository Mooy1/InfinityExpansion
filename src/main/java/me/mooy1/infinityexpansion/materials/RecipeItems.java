package me.mooy1.infinityexpansion.materials;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mooy1.infinityexpansion.setup.Categories;
import me.mooy1.infinityexpansion.Items;
import me.mooy1.infinityexpansion.utils.RecipeUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class RecipeItems extends SlimefunItem {

    public RecipeItems(Type type) {
        super(Categories.HIDDEN_RECIPES,
                type.getItem(),
                RecipeType.NULL,
                null
        );
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Type {

        VOID(Items.VOID_BIT_DUMMY),
        COPPER(Items.COPPER_AMOUNT),
        ZINC(Items.ZINC_AMOUNT),
        TIN(Items.TIN_AMOUNT),
        ALUMINUM(Items.ALUMINUM_AMOUNT),
        SILVER(Items.SILVER_AMOUNT),
        MAGNESIUM(Items.MAGNESIUM_AMOUNT),
        LEAD(Items.LEAD_AMOUNT),

        GOLD(Items.GOLD_AMOUNT),
        IRON(Items.IRON_AMOUNT),
        DIAMOND(Items.DIAMOND_AMOUNT),
        EMERALD(Items.EMERALD_AMOUNT),
        NETHERITE(Items.NETHERITE_AMOUNT),

        COAL(Items.COAL_AMOUNT),
        LAPIS(Items.LAPIS_AMOUNT),
        REDSTONE(Items.REDSTONE_AMOUNT),
        QUARTZ(Items.QUARTZ_AMOUNT);

        @Nonnull
        private final SlimefunItemStack item;

    }
}
