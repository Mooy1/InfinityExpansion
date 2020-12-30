package io.github.mooy1.infinityexpansion.implementation.gear;

import io.github.mooy1.infinityexpansion.lists.Categories;
import io.github.mooy1.infinityexpansion.lists.InfinityRecipes;
import io.github.mooy1.infinityexpansion.lists.Items;
import io.github.mooy1.infinityexpansion.lists.RecipeTypes;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import io.github.thebusybiscuit.slimefun4.core.attributes.Soulbound;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

import javax.annotation.Nonnull;

/**
 * tools
 *
 * @author Mooy1
 */
public class InfinityTool extends SlimefunItem implements Soulbound, NotPlaceable {
    
    public static final SlimefunItemStack[] ITEMS = {
            Items.INFINITY_PICKAXE, Items.INFINITY_BOW, Items.INFINITY_SHOVEL,
            Items.INFINITY_AXE, Items.INFINITY_SHIELD, Items.INFINITY_BLADE
    };
    
    public InfinityTool(@Nonnull SlimefunItemStack stack) {
        super(Categories.INFINITY_CHEAT, stack, RecipeTypes.INFINITY_WORKBENCH, InfinityRecipes.getRecipe(stack));
    }
    
}