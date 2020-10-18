package me.mooy1.infinityexpansion.setup;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mooy1.infinityexpansion.Items;
import me.mooy1.infinityexpansion.utils.IDUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class InfinityRecipes {

    public static final ItemStack[] OUTPUTS = {
            Items.INFINITE_MACHINE_CORE,
            Items.INFINITE_MACHINE_CIRCUIT,
            Items.INFINITE_PANEL,
            Items.POWERED_BEDROCK,
            Items.INFINITY_REACTOR,
            Items.INFINITY_BLADE,
            Items.INFINITY_PICKAXE,
            Items.INFINITY_AXE,
            Items.INFINITY_SHOVEL,
            Items.INFINITY_CROWN,
            Items.INFINITY_CHESTPLATE,
            Items.INFINITY_LEGGINGS,
            Items.INFINITY_BOOTS,
            Items.INFINITY_WINGS,
            Items.INFINITY_SHIELD,
            Items.INFINITY_BOW
    };

    public static int getRecipeID(ItemStack item) {
        String itemID = IDUtils.getIDFromItem(item);
        int i = 0;
        for (ItemStack output : OUTPUTS) {
            if (itemID.equals(IDUtils.getIDFromItem(output))) return i;
            i++;
        }
        return 0;
    }

    private static final ItemStack m_circuit = Items.MACHINE_CIRCUIT;
    private static final ItemStack m_plate = Items.MACHINE_PLATE;
    private static final ItemStack m_core = Items.MACHINE_CORE;
    private static final ItemStack voii = Items.VOID_INGOT;
    private static final ItemStack iing = Items.INFINITE_INGOT;
    private static final ItemStack i_circuit = Items.INFINITE_MACHINE_CIRCUIT;
    private static final ItemStack i_core = Items.INFINITE_MACHINE_CORE;
    private static final ItemStack v_panel = Items.VOID_PANEL;
    private static final ItemStack c_panel = Items.CELESTIAL_PANEL;
    private static final ItemStack c_5 = Items.COMPRESSED_COBBLESTONE_5;
    private static final ItemStack reactor = SlimefunItems.NETHER_STAR_REACTOR;
    private static final ItemStack elytra = new ItemStack(Material.ELYTRA);
    private static final ItemStack e_c = SlimefunItems.ENERGIZED_CAPACITOR;

    public static final ItemStack[][] RECIPES = {
            {
                    iing, iing, iing, iing, iing, iing,
                    iing, voii, voii, voii, voii, iing,
                    iing, voii, m_circuit, m_circuit, voii, iing,
                    iing, voii, m_circuit, m_circuit, voii, iing,
                    iing, voii, voii, voii, voii, iing,
                    iing, iing, iing, iing, iing, iing
            },
            {
                    m_plate, iing, iing, iing, iing, m_plate,
                    iing, m_plate, m_circuit, m_circuit, m_plate, iing,
                    iing, m_circuit, m_core, m_core, m_circuit, iing,
                    iing, m_circuit, m_core, m_core, m_circuit, iing,
                    iing, m_plate, m_circuit, m_circuit, m_plate, iing,
                    m_plate, iing, iing, iing, iing, m_plate
            },
            {
                    c_panel, c_panel, c_panel, c_panel, c_panel, c_panel,
                    c_panel, c_panel, c_panel, c_panel, c_panel, c_panel,
                    iing, iing, iing, iing, iing, iing,
                    iing, i_circuit, i_core, i_core, i_circuit, iing,
                    iing, iing, iing, iing, iing, iing,
                    v_panel, v_panel, v_panel, v_panel, v_panel, v_panel
            },
            {
                    c_5, c_5, c_5, c_5, c_5, c_5,
                    c_5, m_plate, voii, voii, m_plate, c_5,
                    c_5, voii, e_c, e_c, voii, c_5,
                    c_5, voii, i_core, i_circuit, voii, c_5,
                    c_5, m_plate, voii, voii, m_plate, c_5,
                    c_5, c_5, c_5, c_5, c_5, c_5,
            },
            {
                    null, iing, iing, iing, iing, null,
                    iing, iing, voii, voii, iing, iing,
                    iing, m_plate, m_plate, m_plate, m_plate, iing,
                    iing, m_plate, reactor, reactor, m_plate, iing,
                    iing, m_plate, m_plate, m_plate, m_plate, iing,
                    iing, i_circuit, i_core, i_core, i_circuit, iing
            },
            {
                    null, null, null, null, iing, iing,
                    null, null, null, iing, voii, iing,
                    null, null, iing, voii, iing, null,
                    iing, iing, voii, iing, null, null,
                    null, voii, iing, null, null, null,
                    voii, null, iing, null, null, null
            },
            {
                    null, voii, iing, iing, iing, null,
                    null, null, null, iing, voii, iing,
                    null, null, null, voii, iing, iing,
                    null, null, voii, null, null, iing,
                    null, voii, null, null, null, voii,
                    voii, null, null, null, null, null
            },
            {
                    null, voii, iing, iing, null, null,
                    voii, iing, iing, iing, voii, null,
                    null, iing, iing, voii, iing, iing,
                    null, null, voii, iing, iing, iing,
                    null, voii, null, iing, iing, voii,
                    voii, null, null, null, voii, null
            },
            {
                    null, null, null, iing, iing, iing,
                    null, null, iing, iing, iing, iing,
                    null, null, iing, voii, iing, iing,
                    null, null, voii, iing, iing, null,
                    null, voii, null, null, null, null,
                    voii, null, null, null, null, null
            },
            {
                    null, iing, iing, iing, iing, null,
                    iing, iing, iing, iing, iing, iing,
                    iing, voii, iing, iing, voii, iing,
                    null, iing, null, null, iing, null,
                    null, null, null, null, null, null,
                    null, null, null, null, null, null
            },
            {
                    null, iing, null, null, iing, null,
                    iing, voii, iing, iing, voii, iing,
                    voii, iing, iing, iing, iing, voii,
                    voii, iing, voii, voii, iing, voii,
                    null, iing, iing, iing, iing, null,
                    null, iing, iing, iing, iing, null
            },
            {
                    null, iing, iing, iing, iing, null,
                    iing, iing, iing, iing, iing, iing,
                    voii, iing, null, null, iing, voii,
                    voii, iing, null, null, iing, voii,
                    voii, iing, null, null, iing, voii,
                    null, iing, null, null, iing, null
            },
            {
                    null, null, null, null, null, null,
                    iing, iing, null, null, iing, iing,
                    iing, iing, null, null, iing, iing,
                    voii, voii, null, null, voii, voii,
                    iing, iing, null, null, iing, iing,
                    iing, iing, null, null, iing, iing
            },
            {
                    iing, null, null, null, null, iing,
                    voii, voii, voii, voii, voii, voii,
                    null, voii, elytra, elytra, voii, null,
                    voii, voii, iing, iing, voii, voii,
                    voii, voii, null, null, voii, voii,
                    iing, null, null, null, null, iing
            },
            {
                    iing, iing, null, null, iing, iing,
                    iing, voii, iing, iing, voii, iing,
                    iing, voii, iing, iing, voii, iing,
                    iing, voii, iing, iing, voii, iing,
                    null, iing, iing, iing, iing, null,
                    null, iing, iing, iing, iing, null
            },
            {
                    null, iing, iing, voii, null, null,
                    iing, null, iing, iing, voii, null,
                    voii, null, null, null, iing, voii,
                    null, voii, null, null, iing, iing,
                    null, null, voii, null, null, iing,
                    null, null, null, voii, iing, null
            },
    };

    public static final ItemStack[] CIRCUIT = RECIPES[0];
    public static final ItemStack[] CORE = RECIPES[1];
    public static final ItemStack[] PANEL = RECIPES[2];
    public static final ItemStack[] BEDROCK = RECIPES[3];
    public static final ItemStack[] REACTOR = RECIPES[4];
    public static final ItemStack[] BLADE = RECIPES[5];
    public static final ItemStack[] PICK = RECIPES[6];
    public static final ItemStack[] AXE = RECIPES[7];
    public static final ItemStack[] SHOVEL = RECIPES[8];
    public static final ItemStack[] CROWN = RECIPES[9];
    public static final ItemStack[] CHEST = RECIPES[10];
    public static final ItemStack[] LEGS = RECIPES[11];
    public static final ItemStack[] BOOTS = RECIPES[12];
    public static final ItemStack[] WINGS = RECIPES[13];
    public static final ItemStack[] SHIELD = RECIPES[14];
    public static final ItemStack[] BOW = RECIPES[15];
}
