package me.mooy1.mooyaddon.Items;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mooy1.mooyaddon.MooyItems;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.inventory.ItemStack;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.annotation.Nonnull;

public class MagnoniumResource extends SlimefunItem{

    private final Type type;

    public MagnoniumResource(MagnoniumResource.Type type) {
        super(MooyItems.MOOYMAIN, type.getItem(), type.getRecipetype(), type.getRecipe());
        this.type = type;
    }

    private static final ItemStack ingot = SlimefunItems.MAGNESIUM_INGOT;
    private static final ItemStack dust = MooyItems.VOID_DUST;
    private static final ItemStack block = MooyItems.MAGNESIUM_BLOCK;
    private static final ItemStack core = MooyItems.MAGNESIUM_CORE;

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Type {

        BLOCK(MooyItems.MAGNESIUM_BLOCK, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                ingot, ingot, ingot,
                ingot, ingot, ingot,
                ingot, ingot, ingot,
        }),
        CORE(MooyItems.MAGNESIUM_CORE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                block, block, block,
                block, block, block,
                block, block, block
        }),
        ALLOY(MooyItems.MAGNONIUM_ALLOY, RecipeType.SMELTERY, new ItemStack[] {
                core, dust, null,
                null, null, null,
                null, null, null,
        });

        @Nonnull
        private final SlimefunItemStack item;
        private final RecipeType recipetype;
        private final ItemStack[] recipe;

    }
}
