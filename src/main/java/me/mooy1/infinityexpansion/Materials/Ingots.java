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
                Items.MAGNESIUM_CORE,
                Items.VOID_DUST,
                SlimefunItems.REINFORCED_ALLOY_INGOT,
                SlimefunItems.REDSTONE_ALLOY,
                null, null, null, null, null
        }),
        INFINITY(Items.INFINITY_INGOT, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                new SlimefunItemStack(Items.MAGNESIUM_CORE, 64),
                new SlimefunItemStack(Items.COPPER_CORE, 64),
                new SlimefunItemStack(Items.SILVER_CORE, 64),
                new SlimefunItemStack(Items.GOLD_CORE, 64),
                new SlimefunItemStack(Items.IRON_CORE, 64),
                new SlimefunItemStack(Items.ALUMINUM_CORE, 64),
                new SlimefunItemStack(Items.DIAMOND_CORE, 64),
                new SlimefunItemStack(Items.NETHERITE_CORE, 64),
                new SlimefunItemStack(Items.LEAD_CORE, 64),
        });


        @Nonnull
        private final SlimefunItemStack item;
        private final RecipeType recipetype;
        private final ItemStack[] recipe;
    }
}