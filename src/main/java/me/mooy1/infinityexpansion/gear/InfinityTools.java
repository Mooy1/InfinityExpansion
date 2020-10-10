package me.mooy1.infinityexpansion.gear;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mooy1.infinityexpansion.setup.Categories;
import me.mooy1.infinityexpansion.Items;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class InfinityTools extends SlimefunItem {

    private static final ItemStack infinityIngot = Items.INFINITE_INGOT;
    private static final ItemStack voidIngot = Items.VOID_INGOT;

    public InfinityTools(@Nonnull Type type) {
        super(Categories.INFINITY_GEAR, type.getItem(), RecipeType.ENHANCED_CRAFTING_TABLE,
            type.getRecipe());
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Type {

        PICKAXE(Items.INFINITY_PICKAXE, new ItemStack[] {
            infinityIngot, infinityIngot, infinityIngot,
            null, infinityIngot, null,
            null, voidIngot, null
        }),
        WINGS(Items.INFINITY_WINGS, new ItemStack[] {
            infinityIngot, null, infinityIngot,
            voidIngot, new ItemStack(Material.ELYTRA), voidIngot,
            infinityIngot, null, infinityIngot
        }),
        BOW(Items.INFINITY_BOW, new ItemStack[] {
            null, infinityIngot, infinityIngot,
            infinityIngot, null, voidIngot,
            infinityIngot, voidIngot, null
        }),
        SHOVEL(Items.INFINITY_SHOVEL, new ItemStack[] {
            null, infinityIngot, infinityIngot,
            null, infinityIngot, infinityIngot,
            voidIngot, null, null
        }),
        AXE(Items.INFINITY_AXE, new ItemStack[] {
            infinityIngot, infinityIngot, voidIngot,
            infinityIngot, infinityIngot, voidIngot,
            null, infinityIngot, null
        }),
        BLADE(Items.INFINITY_BLADE, new ItemStack[] {
            null, infinityIngot, infinityIngot,
            infinityIngot, infinityIngot, infinityIngot,
            voidIngot, infinityIngot, null
        });

        @Nonnull
        private final SlimefunItemStack item;
        private final ItemStack[] recipe;
    }
}