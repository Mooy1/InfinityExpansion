package me.mooy1.mooyaddon.Materials;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mooy1.mooyaddon.MooyItems;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.annotation.Nonnull;

public class MagnoniumMaterials extends SlimefunItem{

    private final Type type;

    public MagnoniumMaterials(MagnoniumMaterials.Type type) {
        super(MooyItems.MOOYMAIN, type.getItem(), type.getRecipetype(), type.getRecipe());
        this.type = type;
    }

    private static final ItemStack ingot = new SlimefunItemStack(SlimefunItems.MAGNESIUM_INGOT, 63);
    private static final ItemStack dust = MooyItems.VOID_DUST;
    private static final ItemStack block = MooyItems.MAGNESIUM_BLOCK;

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Type {

        BLOCK(MooyItems.MAGNESIUM_BLOCK, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                ingot, ingot, ingot,
                ingot, ingot, ingot,
                ingot, ingot, ingot,
        }),
        ALLOY(MooyItems.MAGNONIUM_ALLOY, RecipeType.SMELTERY, new ItemStack[] {
                block, dust, null,
                null, null, null,
                null, null, null
        });

        @Nonnull
        private final SlimefunItemStack item;
        private final RecipeType recipetype;
        private final ItemStack[] recipe;
    }
}