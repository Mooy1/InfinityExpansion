package io.github.mooy1.infinityexpansion.lists;

import com.google.common.collect.HashBiMap;
import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinitylib.PluginUtils;
import io.github.mooy1.infinitylib.filter.FilterType;
import io.github.mooy1.infinitylib.filter.ItemFilter;
import io.github.mooy1.infinitylib.filter.MultiFilter;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.collections.Pair;
import me.mrCookieSlime.Slimefun.cscorelib2.inventory.ItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;

/**
 * The recipes for the Infinity Workbench
 *
 * @author Mooy1
 *
 * Items and recipes can be added by:
 * - adding the item and recipe to map
 * - setting the Category of the Item to Categories.INFINITY_CHEAT
 * - setting the RecipeType to RecipeTypes.INFINITY_WORKBENCH
 * - setting the Recipe to InfinityRecipes.getRecipe(item)
 */
public class InfinityRecipes {

    private static final ItemStack m_circuit = Items.MACHINE_CIRCUIT;
    private static final ItemStack m_plate = Items.MACHINE_PLATE;
    private static final ItemStack m_core = Items.MACHINE_CORE;
    private static final ItemStack voidIng = Items.VOID_INGOT;
    private static final ItemStack infinite = Items.INFINITE_INGOT;
    private static final ItemStack mag = Items.MAGNONIUM;
    private static final ItemStack i_circuit = Items.INFINITE_MACHINE_CIRCUIT;
    private static final ItemStack i_core = Items.INFINITE_MACHINE_CORE;
    private static final ItemStack v_panel = Items.VOID_PANEL;
    private static final ItemStack c_panel = Items.CELESTIAL_PANEL;
    private static final ItemStack c_5 = Items.COMPRESSED_COBBLESTONE_5;
    private static final ItemStack reactor = SlimefunItems.NETHER_STAR_REACTOR;
    private static final ItemStack elytra = new ItemStack(Material.ELYTRA);
    private static final ItemStack e_c = Items.VOID_CAPACITOR;
    private static final ItemStack i_pick = Items.INFINITY_PICKAXE;
    private static final ItemStack en = Items.ADVANCED_ENCHANTER;
    private static final ItemStack dis = Items.ADVANCED_DISENCHANTER;
    private static final ItemStack s_c = Items.SINGULARITY_CONSTRUCTOR;
    private static final ItemStack charger = Items.ADVANCED_CHARGER;
    private static final ItemStack virtual = Items.ADVANCED_VIRTUAL_FARM;
    private static final ItemStack grower = Items.ADVANCED_TREE_GROWER;
    private static final ItemStack tree = SlimefunItems.TREE_GROWTH_ACCELERATOR;
    private static final ItemStack crop = SlimefunItems.CROP_GROWTH_ACCELERATOR_2;
    private static final ItemStack glass = SlimefunItems.WITHER_PROOF_GLASS;
    private static final ItemStack podzol = new ItemStack(Material.PODZOL);
    private static final ItemStack grass = new ItemStack(Material.GRASS_BLOCK);

    public static final HashBiMap<SlimefunItemStack, MultiFilter> RECIPES = HashBiMap.create();
    public static final Map<String, Integer> STRINGS = new HashMap<>();
    public static final List<Pair<SlimefunItemStack, ItemStack[]>> IDS = new ArrayList<>();

    /**
     * This method gets the enabled infinity items and
     * adds them and their recipe to the machine
     */
    public static void setup(@Nonnull InfinityExpansion instance) {
        instance.getLogger().log(Level.INFO, "Loading Infinity Recipes...");
        
        //check for disabled items
        try {
            int i = 0;
            for (SlimefunItemStack stack : new HashSet<>(RECIPES.keySet())) {
                if (Objects.requireNonNull(stack.getItem()).isDisabled()) {
                    RECIPES.remove(stack);
                    instance.getLogger().log(Level.INFO, ChatColor.RED + "Infinity Item " + ItemUtils.getItemName(stack) + " disabled!");
                } else {
                    STRINGS.put(stack.getItemId(), i);
                    IDS.add(new Pair<>(stack, getRecipe(Objects.requireNonNull(RECIPES.get(stack)))));
                    i++;
                }
            }
        } catch (Exception e) {
            PluginUtils.log(Level.SEVERE, "There was an error Loading Infinity Recipes");
        }
    }

    @Nonnull
    private static ItemStack[] getRecipe(@Nonnull MultiFilter filter) {
        ItemFilter[] filters = filter.getFilters();
        ItemStack[] recipe = new ItemStack[filters.length];
        for (int i = 0 ; i < filters.length ; i++) {
            if (filters[i] != null) {
                recipe[i] = filters[i].getItem();
            }
        }
        return recipe;
    }
    
    @Nonnull
    public static ItemStack[] getRecipe(@Nonnull SlimefunItemStack stack) {
        return getRecipe(Objects.requireNonNull(RECIPES.get(stack)));
    }

    static {
        RECIPES.put(Items.INFINITE_MACHINE_CORE, MultiFilter.fromStacks(FilterType.MIN_AMOUNT,
                m_plate, m_core, infinite, infinite, m_core, m_plate,
                m_core, m_plate, m_circuit, m_circuit, m_plate, m_core,
                infinite, m_circuit, infinite, infinite, m_circuit, infinite,
                infinite, m_circuit, infinite, infinite, m_circuit, infinite,
                m_core, m_plate, m_circuit, m_circuit, m_plate, m_core,
                m_plate, m_core, infinite, infinite, m_core, m_plate
        ));
        RECIPES.put(Items.INFINITE_MACHINE_CIRCUIT, MultiFilter.fromStacks(FilterType.MIN_AMOUNT,
                m_circuit, infinite, m_circuit, m_circuit, infinite, m_circuit,
                voidIng, m_circuit, voidIng, voidIng, m_circuit, voidIng,
                infinite, voidIng, m_circuit, m_circuit, voidIng, infinite,
                infinite, voidIng, m_circuit, m_circuit, voidIng, infinite,
                voidIng, m_circuit, voidIng, voidIng, m_circuit, voidIng,
                m_circuit, infinite, m_circuit, m_circuit, infinite, m_circuit
        ));
        RECIPES.put(Items.INFINITE_PANEL, MultiFilter.fromStacks(FilterType.MIN_AMOUNT,
                c_panel, c_panel, c_panel, c_panel, c_panel, c_panel,
                c_panel, c_panel, c_panel, c_panel, c_panel, c_panel,
                infinite, infinite, infinite, infinite, infinite, infinite,
                infinite, i_circuit, i_core, i_core, i_circuit, infinite,
                infinite, infinite, infinite, infinite, infinite, infinite,
                v_panel, v_panel, v_panel, v_panel, v_panel, v_panel
        ));
        RECIPES.put(Items.POWERED_BEDROCK, MultiFilter.fromStacks(FilterType.MIN_AMOUNT,
                c_5, c_5, c_5, c_5, c_5, c_5,
                c_5, m_plate, voidIng, voidIng, m_plate, c_5,
                c_5, voidIng, e_c, e_c, voidIng, c_5,
                c_5, voidIng, i_core, i_circuit, voidIng, c_5,
                c_5, m_plate, voidIng, voidIng, m_plate, c_5,
                c_5, c_5, c_5, c_5, c_5, c_5
        ));
        RECIPES.put(Items.INFINITY_REACTOR, MultiFilter.fromStacks(FilterType.MIN_AMOUNT,
                null, infinite, infinite, infinite, infinite, null,
                infinite, infinite, voidIng, voidIng, infinite, infinite,
                infinite, m_plate, m_plate, m_plate, m_plate, infinite,
                infinite, m_plate, reactor, reactor, m_plate, infinite,
                infinite, m_plate, m_plate, m_plate, m_plate, infinite,
                infinite, i_circuit, i_core, i_core, i_circuit, infinite
        ));
        RECIPES.put(Items.INFINITY_QUARRY, MultiFilter.fromStacks(FilterType.MIN_AMOUNT,
                null, m_plate, m_plate, m_plate, m_plate, null,
                m_plate, i_pick, i_circuit, i_circuit, i_pick, m_plate,
                m_plate, voidIng, i_core, i_core, voidIng, m_plate,
                voidIng, null, infinite, infinite, null, voidIng,
                voidIng, null, infinite, infinite, null, voidIng,
                voidIng, null, infinite, infinite, null, voidIng
        ));
        RECIPES.put(Items.INFINITY_CONSTRUCTOR, MultiFilter.fromStacks(FilterType.MIN_AMOUNT,
                null, m_plate, m_plate, m_plate, m_plate, null,
                null, voidIng, i_circuit, i_circuit, voidIng, null,
                null, voidIng, s_c, s_c, voidIng, null,
                null, voidIng, s_c, s_c, voidIng, null,
                null, infinite, i_core, i_core, infinite, null,
                infinite, infinite, infinite, infinite, infinite, infinite
        ));
        RECIPES.put(Items.INFINITY_TREE_GROWER, MultiFilter.fromStacks(FilterType.MIN_AMOUNT,
                glass, glass, glass, glass, glass, glass,
                glass, tree, null, null, tree, glass,
                glass, grower, null, null, grower, glass,
                glass, tree, null, null, tree, glass,
                m_plate, podzol, podzol, podzol, podzol, m_plate,
                m_plate, i_circuit, i_core, i_core, i_circuit, m_plate
        ));
        RECIPES.put(Items.INFINITY_VIRTUAL_FARM, MultiFilter.fromStacks(FilterType.MIN_AMOUNT,
                glass, glass, glass, glass, glass, glass,
                glass, null, null, null, null, glass,
                glass, null, null, null, null, glass,
                glass, grass, grass, grass, grass, glass,
                m_plate, crop, virtual, virtual, crop, m_plate,
                m_plate, i_circuit, i_core, i_core, i_circuit, m_plate
        ));
        RECIPES.put(Items.INFINITE_VOID_HARVESTER, MultiFilter.fromStacks(FilterType.MIN_AMOUNT,
                m_plate, m_plate, m_plate, m_plate, m_plate, m_plate,
                mag, voidIng, voidIng, voidIng, voidIng, mag,
                mag, voidIng, i_circuit, i_circuit, voidIng, mag,
                mag, voidIng, i_core, i_core, voidIng, mag,
                mag, voidIng, voidIng, voidIng, voidIng, mag,
                m_plate, m_plate, m_plate, m_plate, m_plate, m_plate
        ));
        RECIPES.put(Items.INFINITY_CAPACITOR, MultiFilter.fromStacks(FilterType.MIN_AMOUNT,
                null, infinite, voidIng, voidIng, infinite, null,
                null, infinite, i_circuit, i_circuit, infinite, null,
                null, infinite, e_c, e_c, infinite, null,
                null, infinite, e_c, e_c, infinite, null,
                null, infinite, i_circuit, i_circuit, infinite, null,
                null, infinite, voidIng, voidIng, infinite, null
        ));
        RECIPES.put(Items.INFINITY_ENCHANTER, MultiFilter.fromStacks(FilterType.MIN_AMOUNT,
                null, null, null, null, null, null,
                voidIng, null, null, null, null, voidIng,
                voidIng, voidIng, en, en, voidIng, voidIng,
                m_plate, voidIng, i_circuit, i_circuit, voidIng, m_plate,
                m_plate, voidIng, i_core, i_core, voidIng, m_plate,
                m_plate, infinite, infinite, infinite, infinite, m_plate
        ));
        RECIPES.put(Items.INFINITY_DISENCHANTER, MultiFilter.fromStacks(FilterType.MIN_AMOUNT,
                null, null, null, null, null, null,
                voidIng, null, null, null, null, voidIng,
                voidIng, voidIng, dis, dis, voidIng, voidIng,
                m_plate, voidIng, i_circuit, i_circuit, voidIng, m_plate,
                m_plate, voidIng, i_core, i_core, voidIng, m_plate,
                m_plate, infinite, infinite, infinite, infinite, m_plate
        ));
        RECIPES.put(Items.INFINITY_CHARGER, MultiFilter.fromStacks(FilterType.MIN_AMOUNT,
                null, null, null, null, null, null,
                voidIng, m_circuit, m_circuit, m_circuit, m_circuit, voidIng,
                voidIng, m_circuit, charger, charger, m_circuit, voidIng,
                voidIng, m_circuit, charger, charger, m_circuit, voidIng,
                voidIng, i_circuit, i_core, i_core, i_circuit, voidIng,
                infinite, infinite, infinite, infinite, infinite, infinite
        ));
        RECIPES.put(Items.INFINITY_BLADE, MultiFilter.fromStacks(FilterType.MIN_AMOUNT,
                null, null, null, null, infinite, infinite,
                null, null, null, infinite, voidIng, infinite,
                null, null, infinite, voidIng, infinite, null,
                infinite, infinite, voidIng, infinite, null, null,
                null, voidIng, infinite, null, null, null,
                voidIng, null, infinite, null, null, null
        ));
        RECIPES.put(Items.INFINITY_BOW, MultiFilter.fromStacks(FilterType.MIN_AMOUNT,
                null, infinite, infinite, voidIng, null, null,
                infinite, null, infinite, infinite, voidIng, null,
                voidIng, null, null, Items.ENDER_FLAME, infinite, voidIng,
                null, voidIng, null, null, infinite, infinite,
                null, null, voidIng, null, null, infinite,
                null, null, null, voidIng, infinite, null
        ));
        RECIPES.put(Items.INFINITY_PICKAXE, MultiFilter.fromStacks(FilterType.MIN_AMOUNT,
                null, voidIng, infinite, infinite, infinite, null,
                null, null, null, infinite, voidIng, infinite,
                null, null, null, voidIng, infinite, infinite,
                null, null, voidIng, null, null, infinite,
                null, voidIng, null, null, null, voidIng,
                voidIng, null, null, null, null, null
        ));
        RECIPES.put(Items.INFINITY_AXE, MultiFilter.fromStacks(FilterType.MIN_AMOUNT,
                null, voidIng, infinite, infinite, null, null,
                voidIng, infinite, infinite, infinite, voidIng, null,
                null, infinite, infinite, voidIng, infinite, infinite,
                null, null, voidIng, infinite, infinite, infinite,
                null, voidIng, null, infinite, infinite, voidIng,
                voidIng, null, null, null, voidIng, null
        ));
        RECIPES.put(Items.INFINITY_SHOVEL, MultiFilter.fromStacks(FilterType.MIN_AMOUNT,
                null, null, null, infinite, infinite, infinite,
                null, null, infinite, infinite, infinite, infinite,
                null, null, infinite, voidIng, infinite, infinite,
                null, null, voidIng, infinite, infinite, null,
                null, voidIng, null, null, null, null,
                voidIng, null, null, null, null, null
        ));
        RECIPES.put(Items.INFINITY_CROWN, MultiFilter.fromStacks(FilterType.MIN_AMOUNT,
                null, infinite, infinite, infinite, infinite, null,
                infinite, infinite, infinite, infinite, infinite, infinite,
                infinite, voidIng, infinite, infinite, voidIng, infinite,
                null, infinite, null, null, infinite, null,
                null, null, null, null, null, null,
                null, null, null, null, null, null
        ));
        RECIPES.put(Items.INFINITY_CHESTPLATE, MultiFilter.fromStacks(FilterType.MIN_AMOUNT,
                null, infinite, null, null, infinite, null,
                infinite, voidIng, infinite, infinite, voidIng, infinite,
                voidIng, infinite, infinite, infinite, infinite, voidIng,
                voidIng, infinite, voidIng, voidIng, infinite, voidIng,
                null, infinite, infinite, infinite, infinite, null,
                null, infinite, infinite, infinite, infinite, null
        ));
        RECIPES.put(Items.INFINITY_LEGGINGS, MultiFilter.fromStacks(FilterType.MIN_AMOUNT,
                null, infinite, infinite, infinite, infinite, null,
                infinite, infinite, infinite, infinite, infinite, infinite,
                voidIng, infinite, null, null, infinite, voidIng,
                voidIng, infinite, null, null, infinite, voidIng,
                voidIng, infinite, null, null, infinite, voidIng,
                null, infinite, null, null, infinite, null
        ));
        RECIPES.put(Items.INFINITY_BOOTS, MultiFilter.fromStacks(FilterType.MIN_AMOUNT,
                null, null, null, null, null, null,
                infinite, infinite, null, null, infinite, infinite,
                infinite, infinite, null, null, infinite, infinite,
                voidIng, voidIng, null, null, voidIng, voidIng,
                infinite, infinite, null, null, infinite, infinite,
                infinite, infinite, null, null, infinite, infinite
        ));
        RECIPES.put(Items.INFINITY_MATRIX, MultiFilter.fromStacks(FilterType.MIN_AMOUNT,
                infinite, null, infinite, infinite, null, infinite,
                infinite, voidIng, voidIng, voidIng, voidIng, infinite,
                voidIng, voidIng, elytra, elytra, voidIng, voidIng,
                voidIng, voidIng, infinite, infinite, voidIng, voidIng,
                infinite, voidIng, voidIng, voidIng, voidIng, infinite,
                infinite, null, infinite, infinite, null, infinite
        ));
        RECIPES.put(Items.INFINITY_SHIELD, MultiFilter.fromStacks(FilterType.MIN_AMOUNT,
                infinite, infinite, null, null, infinite, infinite,
                infinite, voidIng, infinite, infinite, voidIng, infinite,
                infinite, voidIng, infinite, infinite, voidIng, infinite,
                infinite, voidIng, infinite, infinite, voidIng, infinite,
                null, infinite, voidIng, voidIng, infinite, null,
                null, infinite, voidIng, voidIng, infinite, null
        ));
    }

}