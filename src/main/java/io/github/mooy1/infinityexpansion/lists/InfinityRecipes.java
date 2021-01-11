package io.github.mooy1.infinityexpansion.lists;

import com.google.common.collect.HashBiMap;
import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinitylib.filter.FilterType;
import io.github.mooy1.infinitylib.filter.MultiFilter;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.collections.Pair;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
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
public final class InfinityRecipes {

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

    public static final HashBiMap<MultiFilter, SlimefunItemStack> RECIPES = HashBiMap.create();
    public static final LinkedHashMap<String, Pair<SlimefunItemStack, ItemStack[]>> ITEMS = new LinkedHashMap<>();
    public static final List<String> IDS = new ArrayList<>();

    /**
     * This method gets the enabled infinity items and
     * adds them and their recipe to the machine
     */
    public static void postItems(@Nonnull InfinityExpansion instance) {
        instance.getLogger().log(Level.INFO, "Loading Infinity Recipes...");
        
        //check for disabled items
        Iterator<SlimefunItemStack> iterator = RECIPES.values().iterator();
        while (iterator.hasNext()) {
            SlimefunItemStack stack = iterator.next();
            if (Objects.requireNonNull(stack.getItem()).isDisabled()) {
                iterator.remove();
                ITEMS.remove(stack.getItemId());
                IDS.remove(stack.getItemId());
                instance.getLogger().log(Level.INFO, "Infinity Item " + stack.getItemId() + " disabled!");
            }
        }
    }
    
    @Nonnull
    public static ItemStack[] getRecipe(@Nonnull SlimefunItemStack stack) {
        return ITEMS.get(stack.getItemId()).getSecondValue();
    }
    
    public static void preItems() {
        addRecipe(new ItemStack[] {
                m_plate, m_core, infinite, infinite, m_core, m_plate,
                m_core, m_plate, m_circuit, m_circuit, m_plate, m_core,
                infinite, m_circuit, infinite, infinite, m_circuit, infinite,
                infinite, m_circuit, infinite, infinite, m_circuit, infinite,
                m_core, m_plate, m_circuit, m_circuit, m_plate, m_core,
                m_plate, m_core, infinite, infinite, m_core, m_plate
        }, Items.INFINITE_MACHINE_CORE);
        addRecipe(new ItemStack[] {
                m_circuit, infinite, m_circuit, m_circuit, infinite, m_circuit,
                voidIng, m_circuit, voidIng, voidIng, m_circuit, voidIng,
                infinite, voidIng, m_circuit, m_circuit, voidIng, infinite,
                infinite, voidIng, m_circuit, m_circuit, voidIng, infinite,
                voidIng, m_circuit, voidIng, voidIng, m_circuit, voidIng,
                m_circuit, infinite, m_circuit, m_circuit, infinite, m_circuit
        }, Items.INFINITE_MACHINE_CIRCUIT);
        addRecipe(new ItemStack[] {
                c_panel, c_panel, c_panel, c_panel, c_panel, c_panel,
                c_panel, c_panel, c_panel, c_panel, c_panel, c_panel,
                infinite, infinite, infinite, infinite, infinite, infinite,
                infinite, i_circuit, i_core, i_core, i_circuit, infinite,
                infinite, infinite, infinite, infinite, infinite, infinite,
                v_panel, v_panel, v_panel, v_panel, v_panel, v_panel
        }, Items.INFINITE_PANEL);
        addRecipe(new ItemStack[] {
                c_5, c_5, c_5, c_5, c_5, c_5,
                c_5, m_plate, voidIng, voidIng, m_plate, c_5,
                c_5, voidIng, e_c, e_c, voidIng, c_5,
                c_5, voidIng, i_core, i_circuit, voidIng, c_5,
                c_5, m_plate, voidIng, voidIng, m_plate, c_5,
                c_5, c_5, c_5, c_5, c_5, c_5
        }, Items.POWERED_BEDROCK);
        addRecipe(new ItemStack[] {
                null, infinite, infinite, infinite, infinite, null,
                infinite, infinite, voidIng, voidIng, infinite, infinite,
                infinite, m_plate, m_plate, m_plate, m_plate, infinite,
                infinite, m_plate, reactor, reactor, m_plate, infinite,
                infinite, m_plate, m_plate, m_plate, m_plate, infinite,
                infinite, i_circuit, i_core, i_core, i_circuit, infinite
        }, Items.INFINITY_REACTOR);
        addRecipe(new ItemStack[] {
                null, m_plate, m_plate, m_plate, m_plate, null,
                m_plate, i_pick, i_circuit, i_circuit, i_pick, m_plate,
                m_plate, voidIng, i_core, i_core, voidIng, m_plate,
                voidIng, null, infinite, infinite, null, voidIng,
                voidIng, null, infinite, infinite, null, voidIng,
                voidIng, null, infinite, infinite, null, voidIng
        }, Items.INFINITY_QUARRY);
        addRecipe(new ItemStack[] {
                null, m_plate, m_plate, m_plate, m_plate, null,
                null, voidIng, i_circuit, i_circuit, voidIng, null,
                null, voidIng, s_c, s_c, voidIng, null,
                null, voidIng, s_c, s_c, voidIng, null,
                null, infinite, i_core, i_core, infinite, null,
                infinite, infinite, infinite, infinite, infinite, infinite
        }, Items.INFINITY_CONSTRUCTOR);
        addRecipe(new ItemStack[] {
                glass, glass, glass, glass, glass, glass,
                glass, tree, null, null, tree, glass,
                glass, grower, null, null, grower, glass,
                glass, tree, null, null, tree, glass,
                m_plate, podzol, podzol, podzol, podzol, m_plate,
                m_plate, i_circuit, i_core, i_core, i_circuit, m_plate
        }, Items.INFINITY_TREE_GROWER);
        addRecipe(new ItemStack[] {
                glass, glass, glass, glass, glass, glass,
                glass, null, null, null, null, glass,
                glass, null, null, null, null, glass,
                glass, grass, grass, grass, grass, glass,
                m_plate, crop, virtual, virtual, crop, m_plate,
                m_plate, i_circuit, i_core, i_core, i_circuit, m_plate
        }, Items.INFINITY_VIRTUAL_FARM);
        addRecipe(new ItemStack[] {
                m_plate, m_plate, m_plate, m_plate, m_plate, m_plate,
                mag, voidIng, voidIng, voidIng, voidIng, mag,
                mag, voidIng, i_circuit, i_circuit, voidIng, mag,
                mag, voidIng, i_core, i_core, voidIng, mag,
                mag, voidIng, voidIng, voidIng, voidIng, mag,
                m_plate, m_plate, m_plate, m_plate, m_plate, m_plate
        }, Items.INFINITE_VOID_HARVESTER);
        addRecipe(new ItemStack[] {
                null, infinite, voidIng, voidIng, infinite, null,
                null, infinite, i_circuit, i_circuit, infinite, null,
                null, infinite, e_c, e_c, infinite, null,
                null, infinite, e_c, e_c, infinite, null,
                null, infinite, i_circuit, i_circuit, infinite, null,
                null, infinite, voidIng, voidIng, infinite, null
        }, Items.INFINITY_CAPACITOR);
        addRecipe(new ItemStack[] {
                null, null, null, null, null, null,
                voidIng, null, null, null, null, voidIng,
                voidIng, voidIng, en, en, voidIng, voidIng,
                m_plate, voidIng, i_circuit, i_circuit, voidIng, m_plate,
                m_plate, voidIng, i_core, i_core, voidIng, m_plate,
                m_plate, infinite, infinite, infinite, infinite, m_plate
        }, Items.INFINITY_ENCHANTER);
        addRecipe(new ItemStack[] {
                null, null, null, null, null, null,
                voidIng, null, null, null, null, voidIng,
                voidIng, voidIng, dis, dis, voidIng, voidIng,
                m_plate, voidIng, i_circuit, i_circuit, voidIng, m_plate,
                m_plate, voidIng, i_core, i_core, voidIng, m_plate,
                m_plate, infinite, infinite, infinite, infinite, m_plate
        }, Items.INFINITY_DISENCHANTER);
        addRecipe(new ItemStack[] {
                null, null, null, null, null, null,
                voidIng, m_circuit, m_circuit, m_circuit, m_circuit, voidIng,
                voidIng, m_circuit, charger, charger, m_circuit, voidIng,
                voidIng, m_circuit, charger, charger, m_circuit, voidIng,
                voidIng, i_circuit, i_core, i_core, i_circuit, voidIng,
                infinite, infinite, infinite, infinite, infinite, infinite
        }, Items.INFINITY_CHARGER);
        addRecipe(new ItemStack[] {
                null, null, null, null, infinite, infinite,
                null, null, null, infinite, voidIng, infinite,
                null, null, infinite, voidIng, infinite, null,
                infinite, infinite, voidIng, infinite, null, null,
                null, voidIng, infinite, null, null, null,
                voidIng, null, infinite, null, null, null
        }, Items.INFINITY_BLADE);
        addRecipe(new ItemStack[] {
                null, infinite, infinite, voidIng, null, null,
                infinite, null, infinite, infinite, voidIng, null,
                voidIng, null, null, Items.ENDER_FLAME, infinite, voidIng,
                null, voidIng, null, null, infinite, infinite,
                null, null, voidIng, null, null, infinite,
                null, null, null, voidIng, infinite, null
        }, Items.INFINITY_BOW);
        addRecipe(new ItemStack[] {
                null, voidIng, infinite, infinite, infinite, null,
                null, null, null, infinite, voidIng, infinite,
                null, null, null, voidIng, infinite, infinite,
                null, null, voidIng, null, null, infinite,
                null, voidIng, null, null, null, voidIng,
                voidIng, null, null, null, null, null
        }, Items.INFINITY_PICKAXE);
        addRecipe(new ItemStack[] {
                null, voidIng, infinite, infinite, null, null,
                voidIng, infinite, infinite, infinite, voidIng, null,
                null, infinite, infinite, voidIng, infinite, infinite,
                null, null, voidIng, infinite, infinite, infinite,
                null, voidIng, null, infinite, infinite, voidIng,
                voidIng, null, null, null, voidIng, null
        }, Items.INFINITY_AXE);
        addRecipe(new ItemStack[] {
                null, null, null, infinite, infinite, infinite,
                null, null, infinite, infinite, infinite, infinite,
                null, null, infinite, voidIng, infinite, infinite,
                null, null, voidIng, infinite, infinite, null,
                null, voidIng, null, null, null, null,
                voidIng, null, null, null, null, null
        }, Items.INFINITY_SHOVEL);
        addRecipe(new ItemStack[] {
                null, infinite, infinite, infinite, infinite, null,
                infinite, infinite, infinite, infinite, infinite, infinite,
                infinite, voidIng, infinite, infinite, voidIng, infinite,
                null, infinite, null, null, infinite, null,
                null, null, null, null, null, null,
                null, null, null, null, null, null
        }, Items.INFINITY_CROWN);
        addRecipe(new ItemStack[] {
                null, infinite, null, null, infinite, null,
                infinite, voidIng, infinite, infinite, voidIng, infinite,
                voidIng, infinite, infinite, infinite, infinite, voidIng,
                voidIng, infinite, voidIng, voidIng, infinite, voidIng,
                null, infinite, infinite, infinite, infinite, null,
                null, infinite, infinite, infinite, infinite, null
        }, Items.INFINITY_CHESTPLATE);
        addRecipe(new ItemStack[] {
                null, infinite, infinite, infinite, infinite, null,
                infinite, infinite, infinite, infinite, infinite, infinite,
                voidIng, infinite, null, null, infinite, voidIng,
                voidIng, infinite, null, null, infinite, voidIng,
                voidIng, infinite, null, null, infinite, voidIng,
                null, infinite, null, null, infinite, null
        }, Items.INFINITY_LEGGINGS);
        addRecipe(new ItemStack[] {
                null, null, null, null, null, null,
                infinite, infinite, null, null, infinite, infinite,
                infinite, infinite, null, null, infinite, infinite,
                voidIng, voidIng, null, null, voidIng, voidIng,
                infinite, infinite, null, null, infinite, infinite,
                infinite, infinite, null, null, infinite, infinite
        }, Items.INFINITY_BOOTS);
        addRecipe(new ItemStack[] {
                infinite, null, infinite, infinite, null, infinite,
                infinite, voidIng, voidIng, voidIng, voidIng, infinite,
                voidIng, voidIng, elytra, elytra, voidIng, voidIng,
                voidIng, voidIng, infinite, infinite, voidIng, voidIng,
                infinite, voidIng, voidIng, voidIng, voidIng, infinite,
                infinite, null, infinite, infinite, null, infinite
        }, Items.INFINITY_MATRIX);
        addRecipe(new ItemStack[] {
                infinite, infinite, null, null, infinite, infinite,
                infinite, voidIng, infinite, infinite, voidIng, infinite,
                infinite, voidIng, infinite, infinite, voidIng, infinite,
                infinite, voidIng, infinite, infinite, voidIng, infinite,
                null, infinite, voidIng, voidIng, infinite, null,
                null, infinite, voidIng, voidIng, infinite, null
        }, Items.INFINITY_SHIELD);
    }
    
    private static void addRecipe(ItemStack[] recipe, SlimefunItemStack stack) {
        RECIPES.put(new MultiFilter(FilterType.IGNORE_AMOUNT , recipe), stack);
        ITEMS.put(stack.getItemId(), new Pair<>(stack, recipe));
        IDS.add(stack.getItemId()); 
    }

}