package io.github.mooy1.infinityexpansion.implementation.materials;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.lists.Categories;
import io.github.mooy1.infinityexpansion.lists.Items;
import io.github.mooy1.infinityexpansion.lists.RecipeTypes;
import io.github.mooy1.infinityexpansion.utils.Triplet;
import io.github.mooy1.infinitylib.items.StackUtils;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
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

    public static final List<Triplet<SlimefunItemStack, String, Integer>> RECIPES = new ArrayList<>();
    private static final double SCALE = InfinityExpansion.getVanillaScale();

    static {
        RECIPES.add(new Triplet<>(Items.COPPER_SINGULARITY, "COPPER_INGOT", 2400));
        RECIPES.add(new Triplet<>(Items.ZINC_SINGULARITY, "ZINC_INGOT", 2400));
        RECIPES.add(new Triplet<>(Items.TIN_SINGULARITY, "TIN_INGOT", 2400));
        RECIPES.add(new Triplet<>(Items.ALUMINUM_SINGULARITY, "ALUMINUM_INGOT", 2400));
        RECIPES.add(new Triplet<>(Items.SILVER_SINGULARITY, "SILVER_INGOT", 2400));
        RECIPES.add(new Triplet<>(Items.MAGNESIUM_SINGULARITY, "MAGNESIUM_INGOT", 2400));
        RECIPES.add(new Triplet<>(Items.LEAD_SINGULARITY, "LEAD_INGOT", 2400));

        RECIPES.add(new Triplet<>(Items.GOLD_SINGULARITY, "GOLD_INGOT", (int) (1280 * SCALE)));
        RECIPES.add(new Triplet<>(Items.IRON_SINGULARITY, "IRON_INGOT", (int) (1280 * SCALE)));
        RECIPES.add(new Triplet<>(Items.DIAMOND_SINGULARITY, "DIAMOND", (int) (640 * SCALE)));
        RECIPES.add(new Triplet<>(Items.EMERALD_SINGULARITY, "EMERALD", (int) (640 * SCALE)));
        RECIPES.add(new Triplet<>(Items.NETHERITE_SINGULARITY, "NETHERITE_INGOT", (int) (160 * SCALE)));

        RECIPES.add(new Triplet<>(Items.COAL_SINGULARITY, "COAL", (int) (640 * SCALE)));
        RECIPES.add(new Triplet<>(Items.REDSTONE_SINGULARITY, "REDSTONE", (int) (1280 * SCALE)));
        RECIPES.add(new Triplet<>(Items.LAPIS_SINGULARITY, "LAPIS_LAZULI",(int) (1280 * SCALE)));
        RECIPES.add(new Triplet<>(Items.QUARTZ_SINGULARITY, "QUARTZ", (int) (640 * SCALE)));

        RECIPES.add(new Triplet<>(Items.INFINITY_SINGULARITY, "INFINITE_INGOT",160));
    }
    
    public static void setup(InfinityExpansion plugin) {
        for (Triplet<SlimefunItemStack, String, Integer> triplet : RECIPES) {
            new Singularity(triplet).register(plugin);
        }
    }

    private Singularity(Triplet<SlimefunItemStack, String, Integer> triplet) {
        super(Categories.INFINITY_MATERIALS, triplet.getA(), RecipeTypes.SINGULARITY_CONSTRUCTOR, makeRecipe(triplet.getB(), triplet.getC()));
    }

    @Nonnull
    private static ItemStack[] makeRecipe(String id, int amount) {
        ItemStack item = StackUtils.getItemFromID(id, 1);
        if (item == null) return new ItemStack[9];

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
        
        return recipe.toArray(new ItemStack[0]);
    }
}
