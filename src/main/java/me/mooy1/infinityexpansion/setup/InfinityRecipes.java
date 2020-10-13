package me.mooy1.infinityexpansion.setup;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mooy1.infinityexpansion.Items;
import org.bukkit.inventory.ItemStack;

public class InfinityRecipes {

    static ItemStack m_circuit = Items.MACHINE_CIRCUIT;
    static ItemStack m_plate = Items.MACHINE_PLATE;
    static ItemStack m_core = Items.MACHINE_CORE;
    static ItemStack v_ingot = Items.VOID_INGOT;
    static ItemStack i_ingot = Items.INFINITE_INGOT;
    static ItemStack i_circuit = Items.INFINITE_MACHINE_CIRCUIT;
    static ItemStack i_core = Items.INFINITE_MACHINE_CORE;
    static ItemStack v_panel = Items.VOID_PANEL;
    static ItemStack c_panel = Items.CELESTIAL_PANEL;
    static ItemStack f_s = Items.FORTUNE_SINGULARITY;
    static ItemStack m_s = Items.METAL_SINGULARITY;
    static ItemStack a_s = Items.MAGIC_SINGULARITY;
    static ItemStack e_s = Items.EARTH_SINGULARITY;
    static ItemStack wpo = SlimefunItems.WITHER_PROOF_OBSIDIAN;
    static ItemStack c_5 = Items.COMPRESSED_COBBLESTONE_5;
    static ItemStack n_reactor = SlimefunItems.NETHER_STAR_REACTOR;
    static ItemStack e_c = SlimefunItems.ENERGIZED_CAPACITOR;
    
    public static final ItemStack[][] RECIPES = {
            {
                    m_s, a_s, a_s, a_s, a_s, f_s,
                    m_s, m_s, v_ingot, v_ingot, f_s, f_s,
                    m_s, e_s, e_s, e_s, e_s, f_s,
                    null, null, null, null, null, null,
                    null, null, null, null, null, null,
                    null, null, null, null, null, null
            },
            {
                    i_ingot, m_circuit, i_ingot, i_ingot, m_circuit, i_ingot,
                    m_circuit, v_ingot, v_ingot, v_ingot, v_ingot, m_circuit,
                    i_ingot, v_ingot, m_circuit, m_circuit, v_ingot, i_ingot,
                    i_ingot, v_ingot, m_circuit, m_circuit, v_ingot, i_ingot,
                    m_circuit, v_ingot, v_ingot, v_ingot, v_ingot, m_circuit,
                    i_ingot, m_circuit, i_ingot, i_ingot, m_circuit, i_ingot
            },
            {
                    v_ingot, m_plate, m_plate, m_plate, m_plate, v_ingot,
                    m_plate, i_ingot, i_ingot, i_ingot, i_ingot, m_plate,
                    m_plate, i_circuit, m_core, m_core, i_circuit, m_plate,
                    m_plate, i_circuit, m_core, m_core, i_circuit, m_plate,
                    m_plate, i_ingot, i_ingot, i_ingot, i_ingot, m_plate,
                    v_ingot, m_plate, m_plate, m_plate, m_plate, v_ingot
            },
            {
                    v_ingot, v_ingot, v_ingot, v_ingot, v_ingot, v_ingot,
                    c_panel, c_panel, c_panel, c_panel, c_panel, c_panel,
                    v_panel, v_panel, v_panel, v_panel, v_panel, v_panel,
                    i_circuit, i_circuit, i_core, i_core, i_circuit, i_circuit,
                    i_circuit, i_circuit, i_core, i_core, i_circuit, i_circuit,
                    i_ingot, i_ingot, i_ingot, i_ingot, i_ingot, i_ingot
            },
            {
                    wpo, wpo, wpo, wpo, wpo, wpo,
                    wpo, v_ingot, i_circuit, i_circuit, v_ingot, wpo,
                    wpo, e_c, c_5, c_5, e_c, wpo,
                    wpo, e_c, c_5, c_5, e_c, wpo,
                    wpo, v_ingot, i_core, i_core, v_ingot, wpo,
                    wpo, wpo, wpo, wpo, wpo, wpo,
            },
            {
                    null, null, null, null, null, null,
                    m_plate, m_plate, m_plate, m_plate, m_plate, m_plate,
                    m_plate, v_ingot, v_ingot, v_ingot, v_ingot, m_plate,
                    m_plate, v_ingot, n_reactor, n_reactor, v_ingot, m_plate,
                    m_plate, v_ingot, i_core, i_core, v_ingot, m_plate,
                    i_ingot, i_ingot, i_ingot, i_ingot, i_ingot, i_ingot,
            },
    };


    public static final String[] OUTPUTS = {
            "INFINITE_INGOT",
            "INFINITE_MACHINE_CORE",
            "INFINITE_MACHINE_CIRCUIT",
            "INFINITE_PANEL",
            "POWERED_BEDROCK",
            "INFINITY_REACTOR"
    };

    public static final ItemStack[] INGOT_RECIPE = RECIPES[0];
    public static final ItemStack[] CORE_RECIPE = RECIPES[1];
    public static final ItemStack[] CIRCUIT_RECIPE = RECIPES[2];
    public static final ItemStack[] PANEL_RECIPE = RECIPES[3];
    public static final ItemStack[] BEDROCK_RECIPE = RECIPES[4];
    public static final ItemStack[] REACTOR_RECIPE = RECIPES[5];
}
