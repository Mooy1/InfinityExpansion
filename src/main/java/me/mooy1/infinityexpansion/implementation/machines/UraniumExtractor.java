package me.mooy1.infinityexpansion.implementation.machines;

import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

public class UraniumExtractor extends SlimefunItem implements EnergyNetComponent, RecipeDisplayItem {
    public UraniumExtractor(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return null;
    }

    @Override
    public int getCapacity() {
        return 0;
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        return null;
    }
}
