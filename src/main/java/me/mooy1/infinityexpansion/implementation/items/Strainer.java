package me.mooy1.infinityexpansion.implementation.items;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mooy1.infinityexpansion.lists.Items;
import me.mooy1.infinityexpansion.lists.Categories;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class Strainer extends SlimefunItem {

    private static final ItemStack stick = new ItemStack(Material.STICK);
    private static final ItemStack string = new ItemStack(Material.STRING);

    public Strainer(Type type) {
        super(Categories.BASIC_MACHINES, type.getItem(), RecipeType.ENHANCED_CRAFTING_TABLE, type.getRecipe());
    }

    @Getter
    @AllArgsConstructor
    public enum Type {

        BASIC(Items.BASIC_STRAINER, StrainerBase.BASIC_SPEED, new ItemStack[]{
                stick, string, stick,
                string, stick, string,
                stick, string, stick
        }),
        ADVANCED(Items.ADVANCED_STRAINER, StrainerBase.ADVANCED_SPEED, new ItemStack[]{
                Items.MAGSTEEL, Items.MAGSTEEL, Items.MAGSTEEL,
                Items.MAGSTEEL, Items.STRAINER_BASE, Items.MAGSTEEL,
                Items.MAGSTEEL, Items.MAGSTEEL, Items.MAGSTEEL
        }),
        REINFORCED(Items.REINFORCED_STRAINER, StrainerBase.REINFORCED_SPEED, new ItemStack[]{
                SlimefunItems.REINFORCED_ALLOY_INGOT, SlimefunItems.REINFORCED_ALLOY_INGOT, SlimefunItems.REINFORCED_ALLOY_INGOT,
                SlimefunItems.REINFORCED_ALLOY_INGOT, Items.ADVANCED_STRAINER, SlimefunItems.REINFORCED_ALLOY_INGOT,
                SlimefunItems.REINFORCED_ALLOY_INGOT, SlimefunItems.REINFORCED_ALLOY_INGOT, SlimefunItems.REINFORCED_ALLOY_INGOT
        });

        @Nonnull
        private final SlimefunItemStack item;
        private final int speed;
        private final ItemStack[] recipe;
    }
}
