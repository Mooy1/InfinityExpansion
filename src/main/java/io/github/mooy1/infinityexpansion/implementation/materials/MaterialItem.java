package io.github.mooy1.infinityexpansion.implementation.materials;

import io.github.mooy1.infinityexpansion.setup.categories.Categories;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.inventory.ItemStack;

abstract class MaterialItem extends SlimefunItem implements NotPlaceable {

    MaterialItem(SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(Categories.MAIN_MATERIALS, item, recipeType, recipe);
    }

}
