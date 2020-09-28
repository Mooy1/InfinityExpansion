package me.mooy1.infinityexpansion.Materials;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mooy1.infinityexpansion.Items;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.inventory.ItemStack;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.annotation.Nonnull;

public class Ingots extends SlimefunItem{

    private final Type type;

    public Ingots(Ingots.Type type) {
        super(Items.MOOYMATERIALS, type.getItem(), type.getRecipetype(), type.getRecipe());
        this.type = type;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Type {

        MAGNONIUM(Items.MAGNONIUM_INGOT, RecipeType.SMELTERY, new ItemStack[] {
                Items.MAGNESIUM_CORE, Items.VOID_DUST, SlimefunItems.REINFORCED_ALLOY_INGOT, SlimefunItems.REDSTONE_ALLOY, null, null, null, null, null
        }),
        INFINITY(Items.INFINITY_INGOT, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.MAGNESIUM_CORE,
                Items.COPPER_CORE,
                Items.SILVER_CORE,
                Items.GOLD_CORE,
                Items.IRON_CORE,
                Items.ALUMINUM_CORE,
                Items.ZINC_CORE,
                Items.TIN_CORE,
                Items.LEAD_CORE,
        });


        @Nonnull
        private final SlimefunItemStack item;
        private final RecipeType recipetype;
        private final ItemStack[] recipe;
    }
}