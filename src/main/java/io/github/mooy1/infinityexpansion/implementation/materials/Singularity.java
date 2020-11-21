package io.github.mooy1.infinityexpansion.implementation.materials;

import io.github.mooy1.infinityexpansion.utils.StackUtils;
import io.github.mooy1.infinityexpansion.implementation.machines.SingularityConstructor;
import io.github.mooy1.infinityexpansion.lists.Categories;
import io.github.mooy1.infinityexpansion.lists.RecipeTypes;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Singularities and there recipe displays
 *
 * @author Mooy1
 */
public class Singularity extends SlimefunItem implements NotPlaceable {

    public Singularity(int id) {
        super(Categories.INFINITY_MATERIALS, SingularityConstructor.RECIPES.getA().get(id), RecipeTypes.SINGULARITY_CONSTRUCTOR, makeRecipe(id));
    }

    @Nonnull
    private static ItemStack[] makeRecipe(int id) {
        ItemStack item = StackUtils.getItemFromID(SingularityConstructor.RECIPES.getB().get(id), 1);
        if (item == null) return new ItemStack[9];

        List<ItemStack> recipe = new ArrayList<>();
        int amount = SingularityConstructor.RECIPES.getC().get(id);
        
        int stacks = (int) Math.floor(amount / 64D);
        int extra = amount % 64;

        for (int i = 0 ; i < stacks ; i++) {
            recipe.add(new CustomItem(item, 64));
        }
        
        recipe.add(new CustomItem(item, extra));
        
        while (recipe.size() < 9) {
            recipe.add(null);
        }
        
        return recipe.toArray(new ItemStack[0]);
    }
}
