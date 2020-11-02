package io.github.mooy1.infinityexpansion.lists;

import io.github.mooy1.infinityexpansion.utils.StackUtils;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

/**
 * The recipes for the Infinity Workbench
 *
 * @author Mooy1
 *
 * Items and recipes can be added by:
 *
 * - adding the item's @link ItemStack to OUTPUTS
 * - adding the recipe ItemStack[] to RECIPES
 * - setting the Category of the Item to Categories.INFINITY_CHEAT
 * - setting the RecipeType to RecipeTypes.INFINITY_WORKBENCH
 * - setting the Recipe to InfinityRecipes.getRecipe([Item's ItemStack])
 *
 */
public class InfinityRecipes {

    private InfinityRecipes() {}

    public static ItemStack[] OUTPUTS = {
            Items.INFINITE_MACHINE_CORE,
            Items.INFINITE_MACHINE_CIRCUIT,
            Items.INFINITE_PANEL,
            Items.POWERED_BEDROCK,
            Items.INFINITY_REACTOR,
            Items.INFINITY_QUARRY,
            Items.INFINITY_CONSTRUCTOR,
            Items.INFINITY_TREE_GROWER,
            Items.INFINITY_VIRTUAL_FARM,
            Items.INFINITE_VOID_HARVESTER,
            Items.INFINITY_CAPACITOR,
            Items.INFINITY_ENCHANTER,
            Items.INFINITY_DISENCHANTER,
            Items.INFINITY_CHARGER,
            Items.INFINITY_BLADE,
            Items.INFINITY_BOW,
            Items.INFINITY_PICKAXE,
            Items.INFINITY_AXE,
            Items.INFINITY_SHOVEL,
            Items.INFINITY_CROWN,
            Items.INFINITY_CHESTPLATE,
            Items.INFINITY_LEGGINGS,
            Items.INFINITY_BOOTS,
            Items.INFINITY_MATRIX,
            Items.INFINITY_SHIELD,
    };

    public static int getRecipeID(@Nullable ItemStack item) {
        String itemID = StackUtils.getIDFromItem(item);
        if (itemID != null) {
            int i = 0;
            for (ItemStack output : OUTPUTS) {
                if (itemID.equals(StackUtils.getIDFromItem(output))) return i;
                i++;
            }
        }
        return 0;
    }

    @NonNull
    public static ItemStack[] getRecipe(@NonNull ItemStack item) {
        return RECIPES[getRecipeID(item)];
    }

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
    private static final ItemStack tree = SlimefunItems.TREE_GROWTH_ACCELERATOR;
    private static final ItemStack crop = SlimefunItems.CROP_GROWTH_ACCELERATOR_2;
    private static final ItemStack glass = SlimefunItems.WITHER_PROOF_GLASS;
    private static final ItemStack podzol = new ItemStack(Material.PODZOL);
    private static final ItemStack grass = new ItemStack(Material.GRASS_BLOCK);

    public static ItemStack[][] RECIPES = {
            {
                    infinite, infinite, infinite, infinite, infinite, infinite,
                    infinite, voidIng, voidIng, voidIng, voidIng, infinite,
                    infinite, voidIng, m_circuit, m_circuit, voidIng, infinite,
                    infinite, voidIng, m_circuit, m_circuit, voidIng, infinite,
                    infinite, voidIng, voidIng, voidIng, voidIng, infinite,
                    infinite, infinite, infinite, infinite, infinite, infinite
            },
            {
                    m_plate, infinite, infinite, infinite, infinite, m_plate,
                    infinite, m_plate, m_circuit, m_circuit, m_plate, infinite,
                    infinite, m_circuit, m_core, m_core, m_circuit, infinite,
                    infinite, m_circuit, m_core, m_core, m_circuit, infinite,
                    infinite, m_plate, m_circuit, m_circuit, m_plate, infinite,
                    m_plate, infinite, infinite, infinite, infinite, m_plate
            },
            {
                    c_panel, c_panel, c_panel, c_panel, c_panel, c_panel,
                    c_panel, c_panel, c_panel, c_panel, c_panel, c_panel,
                    infinite, infinite, infinite, infinite, infinite, infinite,
                    infinite, i_circuit, i_core, i_core, i_circuit, infinite,
                    infinite, infinite, infinite, infinite, infinite, infinite,
                    v_panel, v_panel, v_panel, v_panel, v_panel, v_panel
            },
            {
                    c_5, c_5, c_5, c_5, c_5, c_5,
                    c_5, m_plate, voidIng, voidIng, m_plate, c_5,
                    c_5, voidIng, e_c, e_c, voidIng, c_5,
                    c_5, voidIng, i_core, i_circuit, voidIng, c_5,
                    c_5, m_plate, voidIng, voidIng, m_plate, c_5,
                    c_5, c_5, c_5, c_5, c_5, c_5,
            },
            {
                    null, infinite, infinite, infinite, infinite, null,
                    infinite, infinite, voidIng, voidIng, infinite, infinite,
                    infinite, m_plate, m_plate, m_plate, m_plate, infinite,
                    infinite, m_plate, reactor, reactor, m_plate, infinite,
                    infinite, m_plate, m_plate, m_plate, m_plate, infinite,
                    infinite, i_circuit, i_core, i_core, i_circuit, infinite
            },
            {
                    null, m_plate, m_plate, m_plate, m_plate, null,
                    m_plate, i_pick, i_circuit, i_circuit, i_pick, m_plate,
                    m_plate, voidIng, i_core, i_core, voidIng, m_plate,
                    voidIng, null, infinite, infinite, null, voidIng,
                    voidIng, null, infinite, infinite, null, voidIng,
                    voidIng, null, infinite, infinite, null, voidIng
            },
            {
                    null, m_plate, m_plate, m_plate, m_plate, null,
                    null, voidIng, i_circuit, i_circuit, voidIng, null,
                    null, voidIng, s_c, s_c, voidIng, null,
                    null, voidIng, s_c, s_c, voidIng, null,
                    null, infinite, i_core, i_core, infinite, null,
                    infinite, infinite, infinite, infinite, infinite, infinite
            },
            {
                    glass, glass, glass, glass, glass, glass,
                    glass, tree, null, null, tree, glass,
                    glass, tree, null, null, tree, glass,
                    glass, tree, null, null, tree, glass,
                    m_plate, podzol, podzol, podzol, podzol, m_plate,
                    m_plate, i_circuit, i_core, i_core, i_circuit, m_plate
            },
            {
                    glass, glass, glass, glass, glass, glass,
                    glass, null, null, null, null, glass,
                    glass, null, null, null, null, glass,
                    glass, grass, grass, grass, grass, glass,
                    m_plate, crop, crop, crop, crop, m_plate,
                    m_plate, i_circuit, i_core, i_core, i_circuit, m_plate
            },
            {
                    m_plate, m_plate, m_plate, m_plate, m_plate, m_plate,
                    mag, voidIng, voidIng, voidIng, voidIng, mag,
                    mag, voidIng, i_circuit, i_circuit, voidIng, mag,
                    mag, voidIng, i_core, i_core, voidIng, mag,
                    mag, voidIng, voidIng, voidIng, voidIng, mag,
                    m_plate, m_plate, m_plate, m_plate, m_plate, m_plate
            },
            {
                    null, infinite, voidIng, voidIng, infinite, null,
                    null, infinite, i_circuit, i_circuit, infinite, null,
                    null, infinite, e_c, e_c, infinite, null,
                    null, infinite, e_c, e_c, infinite, null,
                    null, infinite, i_circuit, i_circuit, infinite, null,
                    null, infinite, voidIng, voidIng, infinite, null
            },
            {
                    null, null, null, null, null, null,
                    voidIng, null, null, null, null, voidIng,
                    voidIng, voidIng, en, en, voidIng, voidIng,
                    m_plate, voidIng, i_circuit, i_circuit, voidIng, m_plate,
                    m_plate, voidIng, i_core, i_core, voidIng, m_plate,
                    m_plate, infinite, infinite, infinite, infinite, m_plate
            },
            {
                    null, null, null, null, null, null,
                    voidIng, null, null, null, null, voidIng,
                    voidIng, voidIng, dis, dis, voidIng, voidIng,
                    m_plate, voidIng, i_circuit, i_circuit, voidIng, m_plate,
                    m_plate, voidIng, i_core, i_core, voidIng, m_plate,
                    m_plate, infinite, infinite, infinite, infinite, m_plate

            },
            {
                    null, null, null, null, null, null,
                    voidIng, m_circuit, m_circuit, m_circuit, m_circuit, voidIng,
                    voidIng, m_circuit, charger, charger, m_circuit, voidIng,
                    voidIng, m_circuit, charger, charger, m_circuit, voidIng,
                    voidIng, i_circuit, i_core, i_core, i_circuit, voidIng,
                    infinite, infinite, infinite, infinite, infinite, infinite
            },
            {
                    null, null, null, null, infinite, infinite,
                    null, null, null, infinite, voidIng, infinite,
                    null, null, infinite, voidIng, infinite, null,
                    infinite, infinite, voidIng, infinite, null, null,
                    null, voidIng, infinite, null, null, null,
                    voidIng, null, infinite, null, null, null
            },
            {
                    null, infinite, infinite, voidIng, null, null,
                    infinite, null, infinite, infinite, voidIng, null,
                    voidIng, null, null, Items.ENDER_FLAME, infinite, voidIng,
                    null, voidIng, null, null, infinite, infinite,
                    null, null, voidIng, null, null, infinite,
                    null, null, null, voidIng, infinite, null
            },
            {
                    null, voidIng, infinite, infinite, infinite, null,
                    null, null, null, infinite, voidIng, infinite,
                    null, null, null, voidIng, infinite, infinite,
                    null, null, voidIng, null, null, infinite,
                    null, voidIng, null, null, null, voidIng,
                    voidIng, null, null, null, null, null
            },
            {
                    null, voidIng, infinite, infinite, null, null,
                    voidIng, infinite, infinite, infinite, voidIng, null,
                    null, infinite, infinite, voidIng, infinite, infinite,
                    null, null, voidIng, infinite, infinite, infinite,
                    null, voidIng, null, infinite, infinite, voidIng,
                    voidIng, null, null, null, voidIng, null
            },
            {
                    null, null, null, infinite, infinite, infinite,
                    null, null, infinite, infinite, infinite, infinite,
                    null, null, infinite, voidIng, infinite, infinite,
                    null, null, voidIng, infinite, infinite, null,
                    null, voidIng, null, null, null, null,
                    voidIng, null, null, null, null, null
            },
            {
                    null, infinite, infinite, infinite, infinite, null,
                    infinite, infinite, infinite, infinite, infinite, infinite,
                    infinite, voidIng, infinite, infinite, voidIng, infinite,
                    null, infinite, null, null, infinite, null,
                    null, null, null, null, null, null,
                    null, null, null, null, null, null
            },
            {
                    null, infinite, null, null, infinite, null,
                    infinite, voidIng, infinite, infinite, voidIng, infinite,
                    voidIng, infinite, infinite, infinite, infinite, voidIng,
                    voidIng, infinite, voidIng, voidIng, infinite, voidIng,
                    null, infinite, infinite, infinite, infinite, null,
                    null, infinite, infinite, infinite, infinite, null
            },
            {
                    null, infinite, infinite, infinite, infinite, null,
                    infinite, infinite, infinite, infinite, infinite, infinite,
                    voidIng, infinite, null, null, infinite, voidIng,
                    voidIng, infinite, null, null, infinite, voidIng,
                    voidIng, infinite, null, null, infinite, voidIng,
                    null, infinite, null, null, infinite, null
            },
            {
                    null, null, null, null, null, null,
                    infinite, infinite, null, null, infinite, infinite,
                    infinite, infinite, null, null, infinite, infinite,
                    voidIng, voidIng, null, null, voidIng, voidIng,
                    infinite, infinite, null, null, infinite, infinite,
                    infinite, infinite, null, null, infinite, infinite
            },
            {
                    infinite, null, infinite, infinite, null, infinite,
                    infinite, voidIng, voidIng, voidIng, voidIng, infinite,
                    voidIng, voidIng, elytra, elytra, voidIng, voidIng,
                    voidIng, voidIng, infinite, infinite, voidIng, voidIng,
                    infinite, voidIng, voidIng, voidIng, voidIng, infinite,
                    infinite, null, infinite, infinite, null, infinite
            },
            {
                    infinite, infinite, null, null, infinite, infinite,
                    infinite, voidIng, infinite, infinite, voidIng, infinite,
                    infinite, voidIng, infinite, infinite, voidIng, infinite,
                    infinite, voidIng, infinite, infinite, voidIng, infinite,
                    null, infinite, voidIng, voidIng, infinite, null,
                    null, infinite, voidIng, voidIng, infinite, null
            },
    };
}
