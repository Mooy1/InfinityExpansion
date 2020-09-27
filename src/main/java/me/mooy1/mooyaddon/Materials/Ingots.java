package me.mooy1.mooyaddon.Materials;

import me.mooy1.mooyaddon.MooyItems;
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
        super(MooyItems.MOOYMATERIALS, type.getItem(), type.getRecipetype(), type.getRecipe());
        this.type = type;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Type {

        MAGNONIUM(MooyItems.MAGNONIUM_INGOT, RecipeType.SMELTERY, new ItemStack[] {
                MooyItems.MAGNESIUM_CORE, MooyItems.VOID_DUST, null, null, null, null, null, null, null
        }),
        INFINITY(MooyItems.INFINITY_INGOT, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                MooyItems.MAGNESIUM_CORE,
                MooyItems.COPPER_CORE,
                MooyItems.SILVER_CORE,
                MooyItems.GOLD_CORE,
                MooyItems.IRON_CORE,
                MooyItems.ALUMINUM_CORE,
                MooyItems.ZINC_CORE,
                MooyItems.TIN_CORE,
                MooyItems.LEAD_CORE,
        });


        @Nonnull
        private final SlimefunItemStack item;
        private final RecipeType recipetype;
        private final ItemStack[] recipe;
    }
}