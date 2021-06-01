package io.github.mooy1.infinityexpansion.items;

import lombok.experimental.UtilityClass;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.categories.Categories;
import io.github.mooy1.infinityexpansion.items.blocks.InfinityWorkbench;
import io.github.mooy1.infinityexpansion.items.generators.EnergyGenerator;
import io.github.mooy1.infinityexpansion.items.generators.GenerationType;
import io.github.mooy1.infinityexpansion.items.generators.InfinityReactor;
import io.github.mooy1.infinitylib.presets.LorePreset;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

@UtilityClass
public final class Generators {

    private static final int HYDRO_ENERGY = 5;
    private static final int ADVANCED_HYDRO_ENERGY = 45;
    private static final int GEO_ENERGY = 35;
    private static final int ADVANCED_GEO_ENERGY = 210;
    private static final int BASIC_SOLAR_ENERGY = 9;
    private static final int ADVANCED_SOLAR_ENERGY = 150;
    private static final int CELESTIAL_ENERGY = 750;
    private static final int VOID_ENERGY = 3000;
    private static final int INFINITY_ENERGY = 60_000;
    private static final int INFINITY_REACTOR_ENERGY = 120_000;
    
    public static final SlimefunItemStack INFINITY_REACTOR = new SlimefunItemStack(
            "INFINITY_REACTOR",
            Material.BEACON,
            "&bInfinity Reactor",
            "&7Generates power through the compression",
            "&7of &8Void &7and &bInfinity &7Ingots",
            "",
            LorePreset.energyBuffer(INFINITY_REACTOR_ENERGY * 1000),
            LorePreset.energyPerSecond(INFINITY_REACTOR_ENERGY)
    );
    public static final SlimefunItemStack HYDRO = new SlimefunItemStack(
            "HYDRO_GENERATOR",
            Material.PRISMARINE_WALL,
            "&9Hydro Generator",
            "&7Generates energy from the movement of water",
            "",
            LorePreset.energyBuffer(HYDRO_ENERGY * 100),
            LorePreset.energyPerSecond(HYDRO_ENERGY)
    );
    public static final SlimefunItemStack REINFORCED_HYDRO = new SlimefunItemStack(
            "REINFORCED_HYDRO_GENERATOR",
            Material.END_STONE_BRICK_WALL,
            "&fReinforced &9Hydro Gen",
            "&7Generates large amounts of energy",
            "&7from the movement of water",
            "",
            LorePreset.energyBuffer(ADVANCED_HYDRO_ENERGY * 100),
            LorePreset.energyPerSecond(ADVANCED_HYDRO_ENERGY)
    );
    public static final SlimefunItemStack GEOTHERMAL = new SlimefunItemStack(
            "GEOTHERMAL_GENERATOR",
            Material.MAGMA_BLOCK,
            "&cGeothermal Generator",
            "&7Generates energy from the heat of the world",
            "",
            LorePreset.energyBuffer(GEO_ENERGY * 100),
            LorePreset.energyPerSecond(GEO_ENERGY)
    );
    public static final SlimefunItemStack REINFORCED_GEOTHERMAL = new SlimefunItemStack(
            "REINFORCED_GEOTHERMAL_GENERATOR",
            Material.SHROOMLIGHT,
            "&fReinforced &cGeothermal Gen",
            "&7Generates large amounts of energy",
            "&7from the heat of the world",
            "",
            LorePreset.energyBuffer(ADVANCED_GEO_ENERGY * 100),
            LorePreset.energyPerSecond(ADVANCED_GEO_ENERGY)
    );
    public static final SlimefunItemStack BASIC_PANEL = new SlimefunItemStack(
            "BASIC_PANEL",
            Material.BLUE_GLAZED_TERRACOTTA,
            "&9Basic Solar Panel",
            "&7Generates energy from the sun",
            "",
            LorePreset.energyBuffer(BASIC_SOLAR_ENERGY * 100),
            LorePreset.energyPerSecond(BASIC_SOLAR_ENERGY)
    );
    public static final SlimefunItemStack ADVANCED_PANEL = new SlimefunItemStack(
            "ADVANCED_PANEL",
            Material.RED_GLAZED_TERRACOTTA,
            "&cAdvanced Solar Panel",
            "&7Generates energy from the sun",
            "",
            LorePreset.energyBuffer(ADVANCED_SOLAR_ENERGY * 100),
            LorePreset.energyPerSecond(ADVANCED_SOLAR_ENERGY)
    );
    public static final SlimefunItemStack CELESTIAL_PANEL = new SlimefunItemStack(
            "CELESTIAL_PANEL",
            Material.YELLOW_GLAZED_TERRACOTTA,
            "&eCelestial Panel",
            "&7Generates energy from the sun",
            "",
            LorePreset.energyBuffer(CELESTIAL_ENERGY * 100),
            LorePreset.energyPerSecond(CELESTIAL_ENERGY)
    );
    public static final SlimefunItemStack VOID_PANEL = new SlimefunItemStack(
            "VOID_PANEL",
            Material.LIGHT_GRAY_GLAZED_TERRACOTTA,
            "&8Void Panel",
            "&7Generates energy from darkness",
            "",
            LorePreset.energyBuffer(VOID_ENERGY * 100),
            LorePreset.energyPerSecond(VOID_ENERGY)
    );
    public static final SlimefunItemStack INFINITE_PANEL = new SlimefunItemStack(
            "INFINITE_PANEL",
            Material.LIGHT_BLUE_GLAZED_TERRACOTTA,
            "&bInfinity Panel",
            "&7Generates energy from the cosmos",
            "",
            LorePreset.energyBuffer(INFINITY_ENERGY * 100),
            LorePreset.energyPerSecond(INFINITY_ENERGY)
    );
    
    public static void setup(InfinityExpansion plugin) {
        new InfinityReactor(Categories.INFINITY_CHEAT, INFINITY_REACTOR, InfinityWorkbench.TYPE, new ItemStack[]  {
                null, Materials.INFINITE_INGOT, Materials.INFINITE_INGOT, Materials.INFINITE_INGOT, Materials.INFINITE_INGOT, null,
                Materials.INFINITE_INGOT, Materials.INFINITE_INGOT, Materials.VOID_INGOT, Materials.VOID_INGOT, Materials.INFINITE_INGOT, Materials.INFINITE_INGOT,
                Materials.INFINITE_INGOT, Materials.MACHINE_PLATE, Materials.MACHINE_PLATE, Materials.MACHINE_PLATE, Materials.MACHINE_PLATE, Materials.INFINITE_INGOT,
                Materials.INFINITE_INGOT, Materials.MACHINE_PLATE, SlimefunExtension.ADVANCED_NETHER_STAR_REACTOR, SlimefunExtension.ADVANCED_NETHER_STAR_REACTOR, Materials.MACHINE_PLATE, Materials.INFINITE_INGOT,
                Materials.INFINITE_INGOT, Materials.MACHINE_PLATE, Materials.MACHINE_PLATE, Materials.MACHINE_PLATE, Materials.MACHINE_PLATE, Materials.INFINITE_INGOT,
                Materials.INFINITE_INGOT, Materials.INFINITE_CIRCUIT, Materials.INFINITE_CORE, Materials.INFINITE_CORE, Materials.INFINITE_CIRCUIT, Materials.INFINITE_INGOT
        }, INFINITY_REACTOR_ENERGY).register(plugin);
        
        new EnergyGenerator(Categories.BASIC_MACHINES, HYDRO, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Materials.MAGSTEEL, Materials.MACHINE_CIRCUIT, Materials.MAGSTEEL,
                new ItemStack(Material.BUCKET), SlimefunItems.ELECTRO_MAGNET, new ItemStack(Material.BUCKET),
                Materials.MAGSTEEL, Materials.MACHINE_CIRCUIT, Materials.MAGSTEEL
        }, HYDRO_ENERGY, GenerationType.HYDROELECTRIC).register(plugin);
        new EnergyGenerator(Categories.ADVANCED_MACHINES, REINFORCED_HYDRO, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                HYDRO, Materials.MACHINE_CIRCUIT, HYDRO,
                Materials.MAGSTEEL_PLATE, Materials.MACHINE_CORE, Materials.MAGSTEEL_PLATE,
                HYDRO, Materials.MACHINE_CIRCUIT, HYDRO
        }, ADVANCED_HYDRO_ENERGY, GenerationType.HYDROELECTRIC).register(plugin);

        new EnergyGenerator(Categories.ADVANCED_MACHINES, GEOTHERMAL, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Materials.MAGSTEEL_PLATE, Materials.MAGSTEEL_PLATE, Materials.MAGSTEEL_PLATE,
                SlimefunItems.LAVA_GENERATOR_2, SlimefunItems.LAVA_GENERATOR_2, SlimefunItems.LAVA_GENERATOR_2,
                Materials.MACHINE_CIRCUIT, Materials.MACHINE_CORE, Materials.MACHINE_CIRCUIT
        }, GEO_ENERGY, GenerationType.GEOTHERMAL).register(plugin);
        new EnergyGenerator(Categories.ADVANCED_MACHINES, REINFORCED_GEOTHERMAL, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                GEOTHERMAL, Materials.MACHINE_CIRCUIT, GEOTHERMAL,
                Materials.MACHINE_PLATE, Materials.MACHINE_CORE, Materials.MACHINE_PLATE,
                GEOTHERMAL, Materials.MACHINE_CIRCUIT, GEOTHERMAL
        }, ADVANCED_GEO_ENERGY, GenerationType.GEOTHERMAL).register(plugin);

        new EnergyGenerator(Categories.BASIC_MACHINES, BASIC_PANEL, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Materials.MAGSTEEL, Materials.MAGSTEEL_PLATE, Materials.MAGSTEEL,
                SlimefunItems.SOLAR_PANEL, SlimefunItems.SOLAR_PANEL, SlimefunItems.SOLAR_PANEL,
                Materials.MACHINE_CIRCUIT, Materials.MACHINE_CIRCUIT, Materials.MACHINE_CIRCUIT
        }, BASIC_SOLAR_ENERGY, GenerationType.SOLAR).register(plugin);
        new EnergyGenerator(Categories.ADVANCED_MACHINES, ADVANCED_PANEL, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                BASIC_PANEL, BASIC_PANEL, BASIC_PANEL,
                Materials.TITANIUM, SlimefunItems.SOLAR_GENERATOR_4, Materials.TITANIUM,
                Materials.MACHINE_CIRCUIT, Materials.MACHINE_CIRCUIT, Materials.MACHINE_CIRCUIT
        }, ADVANCED_SOLAR_ENERGY, GenerationType.SOLAR).register(plugin);

        new EnergyGenerator(Categories.ADVANCED_MACHINES, CELESTIAL_PANEL, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Materials.MACHINE_PLATE, Materials.MACHINE_PLATE, Materials.MACHINE_PLATE,
                ADVANCED_PANEL, ADVANCED_PANEL, ADVANCED_PANEL,
                Materials.MACHINE_CIRCUIT, Materials.MACHINE_CORE, Materials.MACHINE_CIRCUIT
        }, CELESTIAL_ENERGY, GenerationType.SOLAR).register(plugin);
        new EnergyGenerator(Categories.ADVANCED_MACHINES, VOID_PANEL, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Materials.VOID_INGOT, Materials.VOID_INGOT, Materials.VOID_INGOT,
                CELESTIAL_PANEL, CELESTIAL_PANEL, CELESTIAL_PANEL,
                Materials.MAGNONIUM, Materials.MAGNONIUM, Materials.MAGNONIUM
        }, VOID_ENERGY, GenerationType.LUNAR).register(plugin);

        new EnergyGenerator(Categories.INFINITY_CHEAT, INFINITE_PANEL, InfinityWorkbench.TYPE, new ItemStack[] {
                CELESTIAL_PANEL, CELESTIAL_PANEL, CELESTIAL_PANEL, CELESTIAL_PANEL, CELESTIAL_PANEL, CELESTIAL_PANEL,
                CELESTIAL_PANEL, CELESTIAL_PANEL, CELESTIAL_PANEL, CELESTIAL_PANEL, CELESTIAL_PANEL, CELESTIAL_PANEL,
                Materials.INFINITE_INGOT, Materials.INFINITE_INGOT, Materials.INFINITE_INGOT, Materials.INFINITE_INGOT, Materials.INFINITE_INGOT, Materials.INFINITE_INGOT,
                Materials.INFINITE_INGOT, Materials.INFINITE_CIRCUIT, Materials.INFINITE_CORE, Materials.INFINITE_CORE, Materials.INFINITE_CIRCUIT, Materials.INFINITE_INGOT,
                Materials.INFINITE_INGOT, Materials.INFINITE_INGOT, Materials.INFINITE_INGOT, Materials.INFINITE_INGOT, Materials.INFINITE_INGOT, Materials.INFINITE_INGOT,
                VOID_PANEL, VOID_PANEL, VOID_PANEL, VOID_PANEL, VOID_PANEL, VOID_PANEL
        }, INFINITY_ENERGY, GenerationType.INFINITY).register(plugin);
    }
    
}
