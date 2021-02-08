package io.github.mooy1.infinityexpansion.implementation.materials;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.implementation.machines.SingularityConstructor;
import io.github.mooy1.infinityexpansion.setup.categories.Categories;
import io.github.mooy1.infinityexpansion.utils.Triplet;
import io.github.mooy1.infinitylib.items.StackUtils;
import io.github.thebusybiscuit.slimefun4.implementation.items.blocks.UnplaceableBlock;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.collections.Pair;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Singularities and there recipe displays
 *
 * @author Mooy1
 */
public final class Singularity extends UnplaceableBlock {
    
    public static final SlimefunItemStack COPPER = new SlimefunItemStack(
            "COPPER_SINGULARITY",
            Material.BRICKS,
            "&6Copper Singularity"
    );
    public static final SlimefunItemStack ZINC= new SlimefunItemStack(
            "ZINC_SINGULARITY",
            Material.IRON_BLOCK,
            "&7Zinc Singularity"
    );
    public static final SlimefunItemStack TIN = new SlimefunItemStack(
            "TIN_SINGULARITY",
            Material.IRON_BLOCK,
            "&7Tin Singularity"
    );
    public static final SlimefunItemStack ALUMINUM = new SlimefunItemStack(
            "ALUMINUM_SINGULARITY",
            Material.IRON_BLOCK,
            "&7Aluminum Singularity"
    );
    public static final SlimefunItemStack SILVER = new SlimefunItemStack(
            "SILVER_SINGULARITY",
            Material.IRON_BLOCK,
            "&7Silver Singularity"
    );
    public static final SlimefunItemStack MAGNESIUM = new SlimefunItemStack(
            "MAGNESIUM_SINGULARITY",
            Material.NETHER_BRICKS,
            "&5Magnesium Singularity"
    );
    public static final SlimefunItemStack LEAD = new SlimefunItemStack(
            "LEAD_SINGULARITY",
            Material.IRON_BLOCK,
            "&8Lead Singularity"
    );
    public static final SlimefunItemStack GOLD = new SlimefunItemStack(
            "GOLD_SINGULARITY",
            Material.GOLD_BLOCK,
            "&6Gold Singularity"
    );
    public static final SlimefunItemStack IRON = new SlimefunItemStack(
            "IRON_SINGULARITY",
            Material.IRON_BLOCK,
            "&7Iron Singularity"
    );
    public static final SlimefunItemStack DIAMOND = new SlimefunItemStack(
            "DIAMOND_SINGULARITY",
            Material.DIAMOND_BLOCK,
            "&bDiamond Singularity"
    );
    public static final SlimefunItemStack EMERALD = new SlimefunItemStack(
            "EMERALD_SINGULARITY",
            Material.EMERALD_BLOCK,
            "&aEmerald Singularity"
    );
    public static final SlimefunItemStack NETHERITE = new SlimefunItemStack(
            "NETHERITE_SINGULARITY",
            Material.NETHERITE_BLOCK,
            "&4Netherite Singularity"
    );
    public static final SlimefunItemStack COAL = new SlimefunItemStack(
            "COAL_SINGULARITY",
            Material.COAL_BLOCK,
            "&8Coal Singularity"
    );
    public static final SlimefunItemStack REDSTONE = new SlimefunItemStack(
            "REDSTONE_SINGULARITY",
            Material.REDSTONE_BLOCK,
            "&cRedstone Singularity"
    );
    public static final SlimefunItemStack LAPIS= new SlimefunItemStack(
            "LAPIS_SINGULARITY",
            Material.LAPIS_BLOCK,
            "&9Lapis Singularity"
    );
    public static final SlimefunItemStack QUARTZ = new SlimefunItemStack(
            "QUARTZ_SINGULARITY",
            Material.QUARTZ_BLOCK,
            "&fQuartz Singularity"
    );
    public static final SlimefunItemStack INFINITY = new SlimefunItemStack(
            "INFINITY_SINGULARITY",
            Material.SMOOTH_QUARTZ,
            "&bInfinity Singularity"
    );
    
    @Getter
    private static final List<Triplet<SlimefunItemStack, String, Integer>> RECIPES = new ArrayList<>();
    private static final Map<String, Pair<Integer, Triplet<SlimefunItemStack, String, Integer>>> MAP = new HashMap<>();
    
    public static Triplet<SlimefunItemStack, String, Integer> getRecipeByIndex(@Nonnull Integer id) {
        return RECIPES.get(id);
    }
    
    public static Pair<Integer, Triplet<SlimefunItemStack, String, Integer>> getRecipeByID(@Nonnull String id) {
        return MAP.get(id);
    }
    
    static {
        addRecipe(COPPER, "COPPER_INGOT", 3000);
        addRecipe(ZINC, "ZINC_INGOT", 3000);
        addRecipe(TIN, "TIN_INGOT", 3000);
        addRecipe(ALUMINUM, "ALUMINUM_INGOT", 3000);
        addRecipe(SILVER, "SILVER_INGOT", 3000);
        addRecipe(MAGNESIUM, "MAGNESIUM_INGOT", 3000);
        addRecipe(LEAD, "LEAD_INGOT", 3000);

        addRecipe(GOLD, "GOLD_INGOT", 2000);
        addRecipe(IRON, "IRON_INGOT", 2000);
        addRecipe(DIAMOND, "DIAMOND", 500);
        addRecipe(EMERALD, "EMERALD", 500);
        addRecipe(NETHERITE, "NETHERITE_INGOT", 200);

        addRecipe(COAL, "COAL", 1500);
        addRecipe(REDSTONE, "REDSTONE", 1500);
        addRecipe(LAPIS, "LAPIS_LAZULI", 1500);
        addRecipe(QUARTZ, "QUARTZ", 1500);

        addRecipe(INFINITY, "INFINITE_INGOT", 100);
    }
    
    private static void addRecipe(SlimefunItemStack item, String id, int amount) {
        Triplet<SlimefunItemStack, String, Integer> triplet = new Triplet<>(item, id, (int) (amount * InfinityExpansion.getDifficulty()));
        RECIPES.add(triplet);
        MAP.put(id, new Pair<>(RECIPES.size() - 1, triplet));
    }
    
    public static void setup(InfinityExpansion plugin) {
        for (Triplet<SlimefunItemStack, String, Integer> triplet : RECIPES) {
            new Singularity(triplet).register(plugin);
        }
    }

    private Singularity(Triplet<SlimefunItemStack, String, Integer> triplet) {
        super(Categories.INFINITY_MATERIALS, triplet.getA(), SingularityConstructor.TYPE, makeRecipe(triplet.getB(), triplet.getC()));
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
