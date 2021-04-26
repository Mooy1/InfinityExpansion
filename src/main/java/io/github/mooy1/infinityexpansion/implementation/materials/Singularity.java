package io.github.mooy1.infinityexpansion.implementation.materials;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

import org.apache.commons.lang.Validate;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.categories.Categories;
import io.github.mooy1.infinityexpansion.implementation.machines.SingularityConstructor;
import io.github.mooy1.infinitylib.items.StackUtils;
import io.github.thebusybiscuit.slimefun4.implementation.items.blocks.UnplaceableBlock;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;

/**
 * Singularities and there recipe displays
 *
 * @author Mooy1
 */
public final class Singularity extends UnplaceableBlock {
    
    private static final double COST_MULTIPLIER = InfinityExpansion.inst().getConfig().getDouble("balance-options.singularity-cost-multiplier", 0.1, 100);
    
    Singularity(SlimefunItemStack item, String id, int amount) {
        super(Categories.INFINITY_MATERIALS, item, SingularityConstructor.TYPE, makeRecipe(id, (int) (amount * COST_MULTIPLIER)));
    }

    @Nonnull
    private static ItemStack[] makeRecipe(String id, int amount) {
        ItemStack item = StackUtils.getItemByIDorType(id);

        Validate.notNull(item, "Failed to make singularity recipe with " + id);
        
        List<ItemStack> recipe = new ArrayList<>();
        
        int stacks = (int) Math.floor(amount / 64D);
        int extra = amount % 64;

        for (int i = 0 ; i < stacks ; i++) {
            recipe.add(new CustomItem(item, 64));
        }
        
        recipe.add(new CustomItem(item, extra));
        
        while (recipe.size() < 9) {
            recipe.add(null);
        }
        
        return recipe.toArray(new ItemStack[9]);
    }
}
