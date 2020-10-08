package me.mooy1.infinityexpansion.machines;

import me.mooy1.infinityexpansion.Categories;
import me.mooy1.infinityexpansion.Items;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.AGenerator;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class Reactors extends AGenerator {
    public Reactors() {
        super(Categories.INFINITY_MACHINES,
                Items.INFINITY_REACTOR,
                RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {

        });
    }

    @Nonnull
    @Override
    public ItemStack getProgressBar() {
        return null;
    }

    @Override
    public int getEnergyProduction() {
        return 600_000;
    }

    @Override
    protected void registerDefaultFuelTypes() {

    }


    @Override
    public int getCapacity() {
        return 100_000_000;
    }
}
