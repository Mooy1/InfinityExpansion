package io.github.mooy1.infinityexpansion.lists;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.implementation.items.AdvancedAnvil;
import io.github.mooy1.infinityexpansion.implementation.items.InfinityWorkbench;
import io.github.mooy1.infinityexpansion.implementation.items.StrainerBase;
import io.github.mooy1.infinityexpansion.implementation.machines.ConversionMachine;
import io.github.mooy1.infinityexpansion.implementation.machines.EnergyGenerator;
import io.github.mooy1.infinityexpansion.implementation.machines.GearTransformer;
import io.github.mooy1.infinityexpansion.implementation.machines.InfinityReactor;
import io.github.mooy1.infinityexpansion.implementation.machines.ItemUpdater;
import io.github.mooy1.infinityexpansion.implementation.machines.MaterialGenerator;
import io.github.mooy1.infinityexpansion.implementation.machines.PoweredBedrock;
import io.github.mooy1.infinityexpansion.implementation.machines.Quarry;
import io.github.mooy1.infinityexpansion.implementation.machines.ResourceSynthesizer;
import io.github.mooy1.infinityexpansion.implementation.machines.SingularityConstructor;
import io.github.mooy1.infinityexpansion.implementation.machines.StoneworksFactory;
import io.github.mooy1.infinityexpansion.implementation.machines.TreeGrower;
import io.github.mooy1.infinityexpansion.implementation.machines.VirtualFarm;
import io.github.mooy1.infinityexpansion.implementation.machines.VoidHarvester;
import io.github.mooy1.infinityexpansion.implementation.mobdata.MobDataInfuser;
import io.github.mooy1.infinityexpansion.implementation.mobdata.MobSimulationChamber;
import io.github.mooy1.infinityexpansion.implementation.storage.StorageNetworkViewer;
import io.github.mooy1.infinityexpansion.implementation.storage.StorageUnit;
import io.github.mooy1.infinityexpansion.implementation.transport.OutputDuct;
import io.github.mooy1.infinityexpansion.setup.SlimefunConstructors;
import io.github.mooy1.infinityexpansion.utils.LoreUtils;
import io.github.thebusybiscuit.slimefun4.utils.HeadTexture;
import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nullable;
import java.util.logging.Level;

/**
 * Items for this addon
 *
 * @author Mooy1
 */
public final class Items {
    
    private static final FileConfiguration CONFIG = InfinityExpansion.getInstance().getConfig();

    //storage & transport

    public static final SlimefunItemStack ITEM_DUCT = new SlimefunItemStack(
            "ITEM_DUCT",
            Material.WHITE_STAINED_GLASS,
            "&fItem Duct",
            "&7Connects machines, inventories, and output ducts"
    );
    public static final SlimefunItemStack OUTPUT_DUCT = new SlimefunItemStack(
            "OUTPUT_DUCT",
            Material.ORANGE_STAINED_GLASS,
            "&6Output Duct",
            "&7Pushes items into machines and inventories",
            "&7Also serves as an Item Duct",
            "",
            "&6Connects up to &e" + OutputDuct.MAX_INVS + " &6Inventories",
            "&6Range: &e" + OutputDuct.DUCT_LENGTH + " &6blocks"
    );
    public static final SlimefunItemStack STORAGE_FORGE = new SlimefunItemStack(
            "STORAGE_FORGE",
            Material.BEEHIVE,
            "&6Storage Forge",
            "&7Upgrades the tier of Storage Units",
            "&7Retains stored items"
    );
    public static final SlimefunItemStack STORAGE_DUCT = new SlimefunItemStack(
            "STORAGE_DUCT",
            Material.GRAY_STAINED_GLASS,
            "&7Storage Duct",
            "&7Extends the reach of your storage network viewer"
    );

    //Machines
    
    public static final SlimefunItemStack STONEWORKS_FACTORY = new SlimefunItemStack(
            "STONEWORKS_FACTORY",
            Material.BLAST_FURNACE,
            "&8Stoneworks Factory",
            "&7Generates cobblestone and processes it into various materials",
            "",
            LoreUtils.energyPerSecond(StoneworksFactory.ENERGY)
    );

    public static final SlimefunItemStack ITEM_UPDATER = new SlimefunItemStack(
            "ITEM_UPDATER",
            Material.QUARTZ_PILLAR,
            "&6Item Updater",
            "&7Will &creset &7and update the name and lore of",
            "&7slimefun items if they are outdated or broken",
            "&cChargeable and upgradeable items will be reset!",
            "",
            LoreUtils.energy(ItemUpdater.ENERGY) + "per item"
    );
    public static final SlimefunItemStack POWERED_BEDROCK = new SlimefunItemStack(
            "POWERED_BEDROCK",
            Material.NETHERITE_BLOCK,
            "&4Powered Bedrock",
            "&7When powered, transforms into a bedrock",
            "&7Will revert once unpowered or broken",
            "",
            LoreUtils.energyPerSecond(PoweredBedrock.ENERGY)
    );

    public static final SlimefunItemStack VOID_HARVESTER = new SlimefunItemStack(
            "VOID_HARVESTER",
            Material.OBSIDIAN,
            "&8Void Harvester",
            "&7Slowly harvests &8Void &7Bits from nothing...",
            "",
            LoreUtils.speed(VoidHarvester.BASIC_SPEED),
            LoreUtils.energyPerSecond(VoidHarvester.BASIC_ENERGY)
    );
    public static final SlimefunItemStack INFINITE_VOID_HARVESTER = new SlimefunItemStack(
            "INFINITE_VOID_HARVESTER",
            Material.CRYING_OBSIDIAN,
            "&bInfinite &8Void Harvester",
            "&7Harvests &8Void &7Bits from nothing...",
            "",
            LoreUtils.speed(VoidHarvester.INFINITY_SPEED),
            LoreUtils.energyPerSecond(VoidHarvester.INFINITY_ENERGY)
    );

    public static final SlimefunItemStack SINGULARITY_CONSTRUCTOR = new SlimefunItemStack(
            "SINGULARITY_CONSTRUCTOR",
            Material.QUARTZ_BRICKS,
            "&fSingularity Constructor",
            "&7Condenses large amounts of resources",
            "",
            LoreUtils.speed(SingularityConstructor.BASIC_SPEED),
            LoreUtils.energyPerSecond(SingularityConstructor.BASIC_ENERGY)
    );
    public static final SlimefunItemStack INFINITY_CONSTRUCTOR = new SlimefunItemStack(
            "INFINITY_CONSTRUCTOR",
            Material.CHISELED_QUARTZ_BLOCK,
            "&bInfinity &fConstructor",
            "&7Quickly condenses large amounts of resources",
            "",
            LoreUtils.speed(SingularityConstructor.INFINITY_SPEED),
            LoreUtils.energyPerSecond(SingularityConstructor.INFINITY_ENERGY)
    );

    public static final SlimefunItemStack RESOURCE_SYNTHESIZER = new SlimefunItemStack(
            "RESOURCE_SYNTHESIZER",
            Material.LODESTONE,
            "&6Resource Synthesizer",
            "&7Creates resources out of singularities",
            "",
            LoreUtils.energy(ResourceSynthesizer.ENERGY) + "per use"
    );

    public static final SlimefunItemStack INFINITY_REACTOR = new SlimefunItemStack(
            "INFINITY_REACTOR",
            Material.BEACON,
            "&bInfinity Reactor",
            "&7Generates power through the compression",
            "&7of &8Void &7and &bInfinity &7Ingots",
            "",
            LoreUtils.energyBuffer(InfinityReactor.STORAGE),
            LoreUtils.energyPerSecond(InfinityReactor.ENERGY)
    );

    public static final SlimefunItemStack ADVANCED_CHARGER = new SlimefunItemStack(
            "ADVANCED_CHARGER",
            Material.HONEYCOMB_BLOCK,
            "&cAdvanced Charger",
            "&7Quickly charges items",
            "",
            LoreUtils.speed(SlimefunConstructors.ADVANCED_CHARGER_SPEED),
            LoreUtils.energyPerSecond(SlimefunConstructors.ADVANCED_CHARGER_ENERGY)
    );
    public static final SlimefunItemStack INFINITY_CHARGER = new SlimefunItemStack(
            "INFINITY_CHARGER",
            Material.SEA_LANTERN,
            "&bInfinity Charger",
            "&7Instantly charges items",
            "",
            LoreUtils.speed(SlimefunConstructors.INFINITY_CHARGER_SPEED),
            LoreUtils.energy(SlimefunConstructors.INFINITY_CHARGER_ENERGY) + "per use"
    );
    public static final SlimefunItemStack ADVANCED_NETHER_STAR_REACTOR = new SlimefunItemStack(
            "ADVANCED_NETHER_STAR_REACTOR",
            HeadTexture.NETHER_STAR_REACTOR,
            "&cAdvanced Nether Star Reactor",
            "&fRuns on Nether Stars",
            "&bMust be surrounded by Water",
            "&bMust be supplied with Nether Ice Coolant Cells",
            "&4Causes nearby Entities to get Withered",
            "",
            LoreUtils.energyBuffer(SlimefunConstructors.STAR_BUFFER),
            LoreUtils.energyPerSecond(SlimefunConstructors.STAR_ENERGY)
    );
    
    public static final SlimefunItemStack EXTREME_FREEZER = new SlimefunItemStack(
            "EXTREME_FREEZER",
            Material.LIGHT_BLUE_CONCRETE,
            "&bExtreme Freezer",
            "&7Converts ice into coolant",
            "",
            LoreUtils.speed(ConversionMachine.FREEZER_SPEED),
            LoreUtils.energyPerSecond(ConversionMachine.FREEZER_ENERGY)
    );
    public static final SlimefunItemStack DUST_EXTRACTOR = new SlimefunItemStack(
            "DUST_EXTRACTOR",
            Material.FURNACE,
            "&8Dust Extractor",
            "&7Converts cobble into dusts",
            "",
            LoreUtils.speed(ConversionMachine.DUST_SPEED),
            LoreUtils.energyPerSecond(ConversionMachine.DUST_ENERGY)
    );

    public static final SlimefunItemStack URANIUM_EXTRACTOR = new SlimefunItemStack(
            "URANIUM_EXTRACTOR",
            Material.LIME_CONCRETE,
            "&aUranium Extractor",
            "&7Converts cobble into uranium",
            "",
            LoreUtils.speed(ConversionMachine.URANIUM_SPEED),
            LoreUtils.energyPerSecond(ConversionMachine.URANIUM_ENERGY)
    );


    public static final SlimefunItemStack BASIC_QUARRY = new SlimefunItemStack(
            "BASIC_QUARRY",
            Material.CHISELED_SANDSTONE,
            "&9Basic Quarry",
            "&7Automatically mines vanilla overworld ores",
            "",
            LoreUtils.speed(Quarry.BASIC_SPEED),
            LoreUtils.energyPerSecond(Quarry.BASIC_ENERGY)
    );
    public static final SlimefunItemStack ADVANCED_QUARRY = new SlimefunItemStack(
            "ADVANCED_QUARRY",
            Material.CHISELED_RED_SANDSTONE,
            "&cAdvanced Quarry",
            "&7Smelts vanilla ores and can mine nether ores",
            "",
            LoreUtils.speed(Quarry.ADVANCED_SPEED),
            LoreUtils.energyPerSecond(Quarry.ADVANCED_ENERGY)
    );
    public static final SlimefunItemStack VOID_QUARRY = new SlimefunItemStack(
            "VOID_QUARRY",
            Material.CHISELED_NETHER_BRICKS,
            "&8Void Quarry",
            "&7Can mine sifted ores or 24 karat gold occasionally",
            "",
            LoreUtils.speed(Quarry.VOID_SPEED),
            LoreUtils.energyPerSecond(Quarry.VOID_ENERGY)
    );
    public static final SlimefunItemStack INFINITY_QUARRY = new SlimefunItemStack(
            "INFINITY_QUARRY",
            Material.CHISELED_POLISHED_BLACKSTONE,
            "&bInfinity Quarry",
            "&7Can mine and smelt Slimefun ingots",
            "",
            LoreUtils.speed(Quarry.INFINITY_SPEED),
            LoreUtils.energyPerSecond(Quarry.INFINITY_ENERGY)
    );
    public static final SlimefunItemStack ADVANCED_ENCHANTER = new SlimefunItemStack(
            "ADVANCED_ENCHANTER",
            Material.ENCHANTING_TABLE,
            "&cAdvanced Enchanter",
            "",
            LoreUtils.speed(SlimefunConstructors.ADVANCED_EN_SPEED),
            LoreUtils.energyPerSecond(SlimefunConstructors.ADVANCED_EN_ENERGY)
    );
    public static final SlimefunItemStack ADVANCED_DISENCHANTER = new SlimefunItemStack(
            "ADVANCED_DISENCHANTER",
            Material.ENCHANTING_TABLE,
            "&cAdvanced Disenchanter",
            "",
            LoreUtils.speed(SlimefunConstructors.ADVANCED_DIS_SPEED),
            LoreUtils.energyPerSecond(SlimefunConstructors.ADVANCED_DIS_ENERGY)
    );
    public static final SlimefunItemStack INFINITY_ENCHANTER = new SlimefunItemStack(
            "INFINITY_ENCHANTER",
            Material.ENCHANTING_TABLE,
            "&bInfinity Enchanter",
            "",
            LoreUtils.speed(SlimefunConstructors.INFINITY_EN_SPEED),
            LoreUtils.energy(SlimefunConstructors.INFINITY_EN_ENERGY) + "per use"
    );
    public static final SlimefunItemStack INFINITY_DISENCHANTER = new SlimefunItemStack(
            "INFINITY_DISENCHANTER",
            Material.ENCHANTING_TABLE,
            "&bInfinity Disenchanter",
            "",
            LoreUtils.speed(SlimefunConstructors.INFINITY_DIS_SPEED),
            LoreUtils.energy(SlimefunConstructors.INFINITY_DIS_ENERGY) + "per use"
    );
    public static final SlimefunItemStack INFINITY_WORKBENCH = new SlimefunItemStack(
            "INFINITY_FORGE",
            Material.RESPAWN_ANCHOR,
            "&6Infinity Workbench",
            "&7Used to craft infinity items",
            "",
            LoreUtils.energy(InfinityWorkbench.ENERGY) + "per item"
    );

    public static final SlimefunItemStack ADVANCED_ANVIL = new SlimefunItemStack(
            "ADVANCED_ANVIL",
            Material.SMITHING_TABLE,
            "&cAdvanced Anvil",
            "&7Combines tools and gear enchants and sometimes upgrades them",
            "&bWorks with Slimefun items",
            "",
            LoreUtils.energy(AdvancedAnvil.ENERGY) + "per use"

    );
    public static final SlimefunItemStack DECOMPRESSOR = new SlimefunItemStack(
            "DECOMPRESSOR",
            Material.TARGET,
            "&7Decompressor",
            "&7Reduces blocks to their base material",
            "",
            LoreUtils.speed(ConversionMachine.DECOM_SPEED),
            LoreUtils.energyPerSecond(ConversionMachine.DECOM_ENERGY)
    );

    public static final SlimefunItemStack INFINITY_CAPACITOR = new SlimefunItemStack(
            "INFINITY_CAPACITOR",
            HeadTexture.CAPACITOR_25,
            "&bInfinite Capacitor",
            "&c&oDo not use more than ",
            "&c&o1 per energy network",
            "",
            "&8\u21E8 &e\u26A1 &bInfinite &7J Capacity"
    );
    public static final SlimefunItemStack VOID_CAPACITOR = new SlimefunItemStack(
            "VOID_CAPACITOR",
            HeadTexture.CAPACITOR_25,
            "&8Void Capacitor",
            "",
            "&8\u21E8 &e\u26A1 " + LoreUtils.roundHundreds(SlimefunConstructors.VOID_CAPACITOR) + " &7J Capacity"
    );
    public static final SlimefunItemStack HYDRO_GENERATOR = new SlimefunItemStack(
            "HYDRO_GENERATOR",
            Material.PRISMARINE_WALL,
            "&9Hydro Generator",
            "&7Generates energy from the movement of water",
            "",
            LoreUtils.energyBuffer(EnergyGenerator.WATER_STORAGE),
            LoreUtils.energyPerSecond(EnergyGenerator.WATER_RATE)
    );
    public static final SlimefunItemStack REINFORCED_HYDRO_GENERATOR = new SlimefunItemStack(
            "REINFORCED_HYDRO_GENERATOR",
            Material.END_STONE_BRICK_WALL,
            "&fReinforced &9Hydro Gen",
            "&7Generates large amounts of energy",
            "&7from the movement of water",
            "",
            LoreUtils.energyBuffer(EnergyGenerator.WATER_STORAGE2),
            LoreUtils.energyPerSecond(EnergyGenerator.WATER_RATE2)
    );
    public static final SlimefunItemStack GEOTHERMAL_GENERATOR = new SlimefunItemStack(
            "GEOTHERMAL_GENERATOR",
            Material.MAGMA_BLOCK,
            "&cGeothermal Generator",
            "&7Generates energy from the heat of the world",
            "",
            LoreUtils.energyBuffer(EnergyGenerator.GEO_STORAGE),
            LoreUtils.energyPerSecond(EnergyGenerator.GEO_RATE)
    );
    public static final SlimefunItemStack REINFORCED_GEOTHERMAL_GENERATOR = new SlimefunItemStack(
            "REINFORCED_GEOTHERMAL_GENERATOR",
            Material.SHROOMLIGHT,
            "&fReinforced &cGeothermal Gen",
            "&7Generates large amounts of energy",
            "&7from the heat of the world",
            "",
            LoreUtils.energyBuffer(EnergyGenerator.GEO_STORAGE2),
            LoreUtils.energyPerSecond(EnergyGenerator.GEO_RATE2)
    );
    public static final SlimefunItemStack BASIC_PANEL = new SlimefunItemStack(
            "BASIC_PANEL",
            Material.BLUE_GLAZED_TERRACOTTA,
            "&9Basic Solar Panel",
            "&7Generates energy from the sun",
            "",
            LoreUtils.energyBuffer(EnergyGenerator.BASIC_STORAGE),
            LoreUtils.energyPerSecond(EnergyGenerator.BASIC_RATE)
    );
    public static final SlimefunItemStack ADVANCED_PANEL = new SlimefunItemStack(
            "ADVANCED_PANEL",
            Material.RED_GLAZED_TERRACOTTA,
            "&cAdvanced Solar Panel",
            "&7Generates energy from the sun",
            "",
            LoreUtils.energyBuffer(EnergyGenerator.ADVANCED_STORAGE),
            LoreUtils.energyPerSecond(EnergyGenerator.ADVANCED_RATE)
    );
    public static final SlimefunItemStack CELESTIAL_PANEL = new SlimefunItemStack(
            "CELESTIAL_PANEL",
            Material.YELLOW_GLAZED_TERRACOTTA,
            "&eCelestial Panel",
            "&7Generates energy from the sun",
            "",
            LoreUtils.energyBuffer(EnergyGenerator.CELE_STORAGE),
            LoreUtils.energyPerSecond(EnergyGenerator.CELE_RATE)
    );
    public static final SlimefunItemStack VOID_PANEL = new SlimefunItemStack(
            "VOID_PANEL",
            Material.LIGHT_GRAY_GLAZED_TERRACOTTA,
            "&8Void Panel",
            "&7Generates energy from darkness",
            "",
            LoreUtils.energyBuffer(EnergyGenerator.VOID_STORAGE),
            LoreUtils.energyPerSecond(EnergyGenerator.VOID_RATE)
    );
    public static final SlimefunItemStack INFINITE_PANEL = new SlimefunItemStack(
            "INFINITE_PANEL",
            Material.LIGHT_BLUE_GLAZED_TERRACOTTA,
            "&bInfinity Panel",
            "&7Generates energy from the cosmos",
            "",
            LoreUtils.energyBuffer(EnergyGenerator.INFINITY_STORAGE),
            LoreUtils.energyPerSecond(EnergyGenerator.INFINITY_RATE)
    );
    
    public static final SlimefunItemStack BASIC_TREE_GROWER = new SlimefunItemStack(
            "BASIC_TREE_GROWER",
            Material.STRIPPED_OAK_WOOD,
            "&9Basic &2Tree Grower",
            "&7Automatically grows, harvests, and replants trees",
            "",
            LoreUtils.speed(TreeGrower.SPEED1),
            LoreUtils.energyPerSecond(TreeGrower.ENERGY1)
    );
    public static final SlimefunItemStack ADVANCED_TREE_GROWER = new SlimefunItemStack(
            "ADVANCED_TREE_GROWER",
            Material.STRIPPED_ACACIA_WOOD,
            "&cAdvanced &2Tree Grower",
            "&7Automatically grows, harvests, and replants trees",
            "",
            LoreUtils.speed(TreeGrower.SPEED2),
            LoreUtils.energyPerSecond(TreeGrower.ENERGY2)
    );
    public static final SlimefunItemStack INFINITY_TREE_GROWER = new SlimefunItemStack(
            "INFINITY_TREE_GROWER",
            Material.STRIPPED_WARPED_HYPHAE,
            "&bInfinity &2Tree Grower",
            "&7Automatically grows, harvests, and replants trees",
            "",
            LoreUtils.speed(TreeGrower.SPEED3),
            LoreUtils.energyPerSecond(TreeGrower.ENERGY3)
    );

    public static final SlimefunItemStack BASIC_VIRTUAL_FARM = new SlimefunItemStack(
            "BASIC_VIRTUAL_FARM",
            Material.GRASS_BLOCK,
            "&9Basic &aVirtual Farm",
            "&7Automatically grows, harvests, and replants crops",
            "",
            LoreUtils.speed(VirtualFarm.SPEED1),
            LoreUtils.energyPerSecond(VirtualFarm.ENERGY1)
    );
    public static final SlimefunItemStack ADVANCED_VIRTUAL_FARM = new SlimefunItemStack(
            "ADVANCED_VIRTUAL_FARM",
            Material.CRIMSON_NYLIUM,
            "&cAdvanced &aVirtual Farm",
            "&7Automatically grows, harvests, and replants crops",
            "",
            LoreUtils.speed(VirtualFarm.SPEED2),
            LoreUtils.energyPerSecond(VirtualFarm.ENERGY2)
    );
    public static final SlimefunItemStack INFINITY_VIRTUAL_FARM = new SlimefunItemStack(
            "INFINITY_VIRTUAL_FARM",
            Material.WARPED_NYLIUM,
            "&bInfinity &aVirtual Farm",
            "&7Automatically grows, harvests, and replants crops",
            "",
            LoreUtils.speed(VirtualFarm.SPEED3),
            LoreUtils.energyPerSecond(VirtualFarm.ENERGY3)
    );

    public static final SlimefunItemStack GEAR_TRANSFORMER = new SlimefunItemStack(
            "GEAR_TRANSFORMER",
            Material.EMERALD_BLOCK,
            "&7Gear Transformer",
            "&7Changes the material of tools and gear",
            "",
            LoreUtils.energy(GearTransformer.ENERGY) + "Per Use"
    );

    //generators

    public static final SlimefunItemStack BASIC_COBBLE_GEN = new SlimefunItemStack(
            "BASIC_COBBLE_GEN",
            Material.LIGHT_GRAY_CONCRETE,
            "&9Basic &8Cobble Generator",
            "",
            LoreUtils.speed(MaterialGenerator.COBBLE_SPEED),
            LoreUtils.energyPerSecond(MaterialGenerator.COBBLE_ENERGY)
    );
    public static final SlimefunItemStack ADVANCED_COBBLE_GEN = new SlimefunItemStack(
            "ADVANCED_COBBLE_GEN",
            Material.GRAY_CONCRETE,
            "&cAdvanced &8Cobble Generator",
            "",
            LoreUtils.speed(MaterialGenerator.COBBLE2_SPEED),
            LoreUtils.energyPerSecond(MaterialGenerator.COBBLE2_ENERGY)
    );
    public static final SlimefunItemStack BASIC_OBSIDIAN_GEN = new SlimefunItemStack(
            "BASIC_OBSIDIAN_GEN",
            Material.BLACK_CONCRETE,
            "&8Obsidian Generator",
            "",
            LoreUtils.speed(MaterialGenerator.OBSIDIAN_SPEED),
            LoreUtils.energyPerSecond(MaterialGenerator.OBSIDIAN_ENERGY)
    );

    //Deep storage units

    public static final SlimefunItemStack BASIC_STORAGE = new SlimefunItemStack(
            "BASIC_STORAGE",
            Material.OAK_WOOD,
            "&9Basic &8Storage Unit",
            LoreUtils.storesItem(StorageUnit.BASIC)
    );
    public static final SlimefunItemStack ADVANCED_STORAGE = new SlimefunItemStack(
            "ADVANCED_STORAGE",
            Material.DARK_OAK_WOOD,
            "&cAdvanced &8Storage Unit",
            LoreUtils.storesItem(StorageUnit.ADVANCED)
    );
    public static final SlimefunItemStack REINFORCED_STORAGE = new SlimefunItemStack(
            "REINFORCED_STORAGE",
            Material.ACACIA_WOOD,
            "&fReinforced &8Storage Unit",
            LoreUtils.storesItem(StorageUnit.REINFORCED)
    );
    public static final SlimefunItemStack VOID_STORAGE = new SlimefunItemStack(
            "VOID_STORAGE",
            Material.CRIMSON_HYPHAE,
            "&8Void &8Storage Unit",
            LoreUtils.storesItem(StorageUnit.VOID)
    );
    public static final SlimefunItemStack INFINITY_STORAGE = new SlimefunItemStack(
            "INFINITY_STORAGE",
            Material.WARPED_HYPHAE,
            "&bInfinity &8Storage Unit",
            LoreUtils.storesInfinity()
    );

    public static final SlimefunItemStack STORAGE_NETWORK_VIEWER = new SlimefunItemStack(
            "STORAGE_NETWORK_VIEWER",
            Material.CHISELED_STONE_BRICKS,
            "&6Storage Network Viewer",
            "&7Shows status of any Storage Units",
            "&7connected through storage ducts or other storage units",
            "",
            "&6Connects up to &e" + StorageNetworkViewer.MAX + " &6Inventories",
            "&6Range: &e" + StorageNetworkViewer.RANGE + " &6blocks"
    );

    public static final SlimefunItemStack VEIN_MINER_RUNE = new SlimefunItemStack(
            "VEIN_MINER_RUNE",
            Material.DIAMOND,
            "&bVein Miner Rune",
            "&7Upgrades a tool to vein-mine certain materials"
    );
    
    public static final SlimefunItemStack ADVANCED_GEO_MINER = new SlimefunItemStack(
            "ADVANCED_GEO_MINER",
            HeadTexture.GEO_MINER,
            "&cAdvanced &fGeoMiner",
            "&7A faster geo-miner",
            "",
            LoreUtils.speed(SlimefunConstructors.ADVANCED_GEO_SPEED),
            LoreUtils.energyPerSecond(SlimefunConstructors.ADVANCED_GEO_ENERGY)
    );

    //strainers

    public static final SlimefunItemStack STRAINER_BASE = new SlimefunItemStack(
            "STRAINER_BASE",
            Material.SANDSTONE_WALL,
            "&7Strainer Base"
    );
    public static final SlimefunItemStack BASIC_STRAINER = new SlimefunItemStack(
            "BASIC_STRAINER",
            Material.FISHING_ROD,
            "&9Basic Strainer",
            "&7Collects materials from flowing water",
            "",
            LoreBuilder.speed(StrainerBase.BASIC_SPEED)
    );
    public static final SlimefunItemStack ADVANCED_STRAINER = new SlimefunItemStack(
            "ADVANCED_STRAINER",
            Material.FISHING_ROD,
            "&cAdvanced Strainer",
            "&7Collects materials from flowing water",
            "",
            LoreBuilder.speed(StrainerBase.ADVANCED_SPEED)
    );
    public static final SlimefunItemStack REINFORCED_STRAINER = new SlimefunItemStack(
            "REINFORCED_STRAINER",
            Material.FISHING_ROD,
            "&fReinforced Strainer",
            "&7Collects materials from flowing water",
            "",
            LoreBuilder.speed(StrainerBase.REINFORCED_SPEED)
    );
    
    //mob stuff

    public static final SlimefunItemStack DATA_INFUSER = new SlimefunItemStack(
            "DATA_INFUSER",
            Material.LODESTONE,
            "&8Mob Data Infuser",
            "&7Infused empty data cards with mob items",
            "",
            LoreUtils.energy(MobDataInfuser.ENERGY) + "per use"
    );
    public static final SlimefunItemStack MOB_SIMULATION_CHAMBER = new SlimefunItemStack(
            "MOB_SIMULATION_CHAMBER",
            Material.GILDED_BLACKSTONE,
            "&8Mob Simulation Chamber",
            "&7Use mob data cards to activate",
            "",
            LoreUtils.energyBuffer(MobSimulationChamber.BUFFER),
            LoreUtils.energyPerSecond(MobSimulationChamber.ENERGY)
    );
    public static final SlimefunItemStack EMPTY_DATA_CARD = new SlimefunItemStack(
            "EMPTY_DATA_CARD",
            Material.IRON_CHESTPLATE,
            "&8Empty Data Card",
            "&7Infuse with a mob's items to fill"
    );

    
    //Info thing

    /**
     * Thanks to NCBPFluffyBear for the idea
     */
    public static final SlimefunItemStack INFINITY_ADDON_INFO = new SlimefunItemStack(
            "INFINITY_ADDON_INFO",
            Material.NETHER_STAR,
            "&bAddon Info",
            "&fVersion: &7" + InfinityExpansion.getInstance().getPluginVersion(),
            "",
            "&fDiscord: &b@&7Riley&8#5911",
            "&7discord.gg/slimefun",
            "",
            "&fGithub: &b@&8&7Mooy1",
            "&7" + InfinityExpansion.getInstance().getBugTrackerURL()
    );

    //Materials

    public static final SlimefunItemStack MAGSTEEL = new SlimefunItemStack(
            "MAGSTEEL",
            Material.BRICK,
            "&4MagSteel"
    );
    public static final SlimefunItemStack MAGNONIUM = new SlimefunItemStack(
            "MAGNONIUM",
            Material.NETHER_BRICK,
            "&5Magnonium"
    );
    public static final SlimefunItemStack TITANIUM = new SlimefunItemStack(
            "TITANIUM",
            Material.IRON_INGOT,
            "&7Titanium"
    );
    public static final SlimefunItemStack MYTHRIL = new SlimefunItemStack(
            "MYTHRIL",
            Material.IRON_INGOT,
            "&bMythril"
    );
    public static final SlimefunItemStack ADAMANTITE = new SlimefunItemStack(
            "ADAMANTITE",
            Material.BRICK,
            "&dAdamantite"
    );
    public static final SlimefunItemStack INFINITE_INGOT = new SlimefunItemStack(
            "INFINITE_INGOT",
            Material.IRON_INGOT,
            "&dI&cn&6f&ei&an&bi&3t&9y &fIngot",
            "&7&oThe fury of the cosmos",
            "&7&oin the palm of your hand"
    );
    
    public static final SlimefunItemStack VOID_BIT = new SlimefunItemStack(
            "VOID_BIT",
            Material.IRON_NUGGET,
            "&8Void Bit",
            "&7&oIt feels... empty"
    );
    public static final SlimefunItemStack VOID_DUST = new SlimefunItemStack(
            "VOID_DUST",
            Material.GUNPOWDER,
            "&8Void Dust",
            "&7&oIts starting to take form..."
    );
    public static final SlimefunItemStack VOID_INGOT = new SlimefunItemStack(
            "VOID_INGOT",
            Material.NETHERITE_INGOT,
            "&8Void Ingot",
            "&7&oThe emptiness of the cosmos",
            "&7&oin the palm of your hand"
    );
    
    public static final SlimefunItemStack ENDER_ESSENCE = new SlimefunItemStack(
            "END_ESSENCE",
            Material.BLAZE_POWDER,
            "&5Ender Essence",
            "&8&oFrom the depths of the end..."
    );

    public static final SlimefunItemStack MAGSTEEL_PLATE = new SlimefunItemStack(
            "MAGSTEEL_PLATE",
            Material.NETHERITE_SCRAP,
            "&4MagSteel Plate",
            "&7Machine Component"
    );
    public static final SlimefunItemStack MACHINE_PLATE = new SlimefunItemStack(
            "MACHINE_PLATE",
            Material.PAPER,
            "&fMachine Plate",
            "&7Machine Component"
    );
    public static final SlimefunItemStack MACHINE_CIRCUIT = new SlimefunItemStack(
            "MACHINE_CIRCUIT",
            Material.GOLD_INGOT,
            "&6Machine Circuit",
            "&7Machine Component"
    );
    public static final SlimefunItemStack INFINITE_MACHINE_CIRCUIT = new SlimefunItemStack(
            "INFINITE_MACHINE_CIRCUIT",
            Material.DIAMOND,
            "&bInfinite &6Machine Circuit",
            "&7Machine Component"
    );
    public static final SlimefunItemStack MACHINE_CORE = new SlimefunItemStack(
            "MACHINE_CORE",
            Material.IRON_BLOCK,
            "&fMachine Core",
            "&7Machine Component"
    );
    public static final SlimefunItemStack INFINITE_MACHINE_CORE = new SlimefunItemStack(
            "INFINITE_MACHINE_CORE",
            Material.DIAMOND_BLOCK,
            "&bInfinite Machine Core",
            "&7Machine Component"
    );

    //Compressed Cobblestones

    public static final SlimefunItemStack COMPRESSED_COBBLESTONE_1 = new SlimefunItemStack(
            "COMPRESSED_COBBLESTONE_1",
            Material.ANDESITE,
            "&7Single Compressed Cobblestone",
            "&89 cobblestone combined"
    );
    public static final SlimefunItemStack COMPRESSED_COBBLESTONE_2 = new SlimefunItemStack(
            "COMPRESSED_COBBLESTONE_2",
            Material.ANDESITE,
            "&7Double Compressed Cobblestone",
            "&881 cobblestone combined"
    );
    public static final SlimefunItemStack COMPRESSED_COBBLESTONE_3 = new SlimefunItemStack(
            "COMPRESSED_COBBLESTONE_3",
            Material.STONE,
            "&7Triple Compressed Cobblestone",
            "&8243 cobblestone combined"
    );
    public static final SlimefunItemStack COMPRESSED_COBBLESTONE_4 = new SlimefunItemStack(
            "COMPRESSED_COBBLESTONE_4",
            Material.STONE,
            "&7Quadruple Compressed Cobblestone",
            "&86,561 cobblestone combined"
    );
    public static final SlimefunItemStack COMPRESSED_COBBLESTONE_5 = new SlimefunItemStack(
            "COMPRESSED_COBBLESTONE_5",
            Material.POLISHED_ANDESITE,
            "&7Quintuple Compressed Cobblestone",
            "&859,049 cobblestone combined"
    );

    //singularities

    public static final SlimefunItemStack COPPER_SINGULARITY = new SlimefunItemStack(
            "COPPER_SINGULARITY",
            Material.BRICKS,
            "&6Copper Singularity"
    );

    public static final SlimefunItemStack ZINC_SINGULARITY = new SlimefunItemStack(
            "ZINC_SINGULARITY",
            Material.IRON_BLOCK,
            "&7Zinc Singularity"
    );

    public static final SlimefunItemStack TIN_SINGULARITY = new SlimefunItemStack(
            "TIN_SINGULARITY",
            Material.IRON_BLOCK,
            "&7Tin Singularity"
    );

    public static final SlimefunItemStack ALUMINUM_SINGULARITY = new SlimefunItemStack(
            "ALUMINUM_SINGULARITY",
            Material.IRON_BLOCK,
            "&7Aluminum Singularity"
    );

    public static final SlimefunItemStack SILVER_SINGULARITY = new SlimefunItemStack(
            "SILVER_SINGULARITY",
            Material.IRON_BLOCK,
            "&7Silver Singularity"
    );

    public static final SlimefunItemStack MAGNESIUM_SINGULARITY = new SlimefunItemStack(
            "MAGNESIUM_SINGULARITY",
            Material.NETHER_BRICKS,
            "&5Magnesium Singularity"
    );

    public static final SlimefunItemStack LEAD_SINGULARITY = new SlimefunItemStack(
            "LEAD_SINGULARITY",
            Material.IRON_BLOCK,
            "&8Lead Singularity"
    );


    public static final SlimefunItemStack GOLD_SINGULARITY = new SlimefunItemStack(
            "GOLD_SINGULARITY",
            Material.GOLD_BLOCK,
            "&6Gold Singularity"
    );

    public static final SlimefunItemStack IRON_SINGULARITY = new SlimefunItemStack(
            "IRON_SINGULARITY",
            Material.IRON_BLOCK,
            "&7Iron Singularity"
    );

    public static final SlimefunItemStack DIAMOND_SINGULARITY = new SlimefunItemStack(
            "DIAMOND_SINGULARITY",
            Material.DIAMOND_BLOCK,
            "&bDiamond Singularity"
    );

    public static final SlimefunItemStack EMERALD_SINGULARITY = new SlimefunItemStack(
            "EMERALD_SINGULARITY",
            Material.EMERALD_BLOCK,
            "&aEmerald Singularity"
    );

    public static final SlimefunItemStack NETHERITE_SINGULARITY = new SlimefunItemStack(
            "NETHERITE_SINGULARITY",
            Material.NETHERITE_BLOCK,
            "&4Netherite Singularity"
    );

    public static final SlimefunItemStack COAL_SINGULARITY = new SlimefunItemStack(
            "COAL_SINGULARITY",
            Material.COAL_BLOCK,
            "&8Coal Singularity"
    );

    public static final SlimefunItemStack REDSTONE_SINGULARITY = new SlimefunItemStack(
            "REDSTONE_SINGULARITY",
            Material.REDSTONE_BLOCK,
            "&cRedstone Singularity"
    );

    public static final SlimefunItemStack LAPIS_SINGULARITY = new SlimefunItemStack(
            "LAPIS_SINGULARITY",
            Material.LAPIS_BLOCK,
            "&9Lapis Singularity"
    );

    public static final SlimefunItemStack QUARTZ_SINGULARITY = new SlimefunItemStack(
            "QUARTZ_SINGULARITY",
            Material.QUARTZ_BLOCK,
            "&fQuartz Singularity"
    );

    public static final SlimefunItemStack INFINITY_SINGULARITY = new SlimefunItemStack(
            "INFINITY_SINGULARITY",
            Material.SMOOTH_QUARTZ,
            "&bInfinity Singularity"
    );

    public static final SlimefunItemStack FORTUNE_SINGULARITY = new SlimefunItemStack(
            "FORTUNE_SINGULARITY",
            Material.NETHER_STAR,
            "&6Fortune Singularity"
    );
    public static final SlimefunItemStack EARTH_SINGULARITY = new SlimefunItemStack(
            "EARTH_SINGULARITY",
            Material.NETHER_STAR,
            "&aEarth Singularity"
    );
    public static final SlimefunItemStack METAL_SINGULARITY = new SlimefunItemStack(
            "METAL_SINGULARITY",
            Material.NETHER_STAR,
            "&8Metal Singularity"
    );
    public static final SlimefunItemStack MAGIC_SINGULARITY = new SlimefunItemStack(
            "MAGIC_SINGULARITY",
            Material.NETHER_STAR,
            "&dMagic Singularity"
    );

    //Gear

    public static final SlimefunItemStack ENDER_FLAME = new SlimefunItemStack(
            "ENDER_FLAME",
            Material.BUBBLE_CORAL_FAN,
            "&cEnder Flame"
    );

    public static final SlimefunItemStack INFINITY_CROWN = new SlimefunItemStack(
            "INFINITY_CROWN",
            Material.NETHERITE_HELMET,
            "&bInfinity Crown",
            "&7Saturation I",
            "&7Night Vision I",
            "&7Water Breathing I"
    );
    public static final SlimefunItemStack INFINITY_CHESTPLATE = new SlimefunItemStack(
            "INFINITY_CHESTPLATE",
            Material.NETHERITE_CHESTPLATE,
            "&bInfinity Chestplate",
            "&7Strength II",
            "&7Health Boost III",
            "&7Resistance II"
    );
    public static final SlimefunItemStack INFINITY_LEGGINGS = new SlimefunItemStack(
            "INFINITY_LEGGINGS",
            Material.NETHERITE_LEGGINGS,
            "&bInfinity Leggings",
            "&7Haste III",
            "&7Conduit Power I",
            "&7Regeneration I"
    );
    public static final SlimefunItemStack INFINITY_BOOTS = new SlimefunItemStack(
            "INFINITY_BOOTS",
            Material.NETHERITE_BOOTS,
            "&bInfinity Boots",
            "&7Speed III",
            "&7Dolphins Grace I",
            "&7Fire Resistance I"
    );
    public static final SlimefunItemStack INFINITY_BLADE = new SlimefunItemStack(
            "INFINITY_BLADE",
            Material.NETHERITE_SWORD,
            "&bBlade of the Cosmos",
            "&b&oEdge of infinity"
    );
    public static final SlimefunItemStack INFINITY_PICKAXE = new SlimefunItemStack(
            "INFINITY_PICKAXE",
            Material.NETHERITE_PICKAXE,
            "&9World Breaker",
            "&3&oThe end of the world"
    );
    public static final SlimefunItemStack INFINITY_AXE = new SlimefunItemStack(
            "INFINITY_AXE",
            Material.NETHERITE_AXE,
            "&4Nature's Ruin",
            "&c&oThe embodiment of fury"
    );
    public static final SlimefunItemStack INFINITY_SHOVEL = new SlimefunItemStack(
            "INFINITY_SHOVEL",
            Material.NETHERITE_SHOVEL,
            "&aMountain Eater",
            "&2&oYum"
    );
    public static final SlimefunItemStack INFINITY_BOW = new SlimefunItemStack(
            "INFINITY_BOW",
            Material.BOW,
            "&6Sky Piercer",
            "&e&oThe longbow of the Heavens"
    );
    public static final SlimefunItemStack INFINITY_MATRIX = new SlimefunItemStack(
            "INFINITY_MATRIX",
            Material.NETHER_STAR,
            "&fInfinity Matrix",
            "&6Gives Unlimited Flight",
            "&7Right-Click to enable/disable and claim",
            "&7Crouch and Right-Click to remove ownership"
    );
    public static final SlimefunItemStack INFINITY_SHIELD = new SlimefunItemStack(
            "INFINITY_SHIELD",
            Material.SHIELD,
            "&bCosmic Aegis",
            "&7&o"
    );
    
    static { //add item meta
        
        SlimefunItemStack[] items = new SlimefunItemStack[] {
                INFINITY_CROWN, INFINITY_CHESTPLATE, INFINITY_LEGGINGS, INFINITY_BOOTS, INFINITY_BLADE,
                INFINITY_AXE, INFINITY_PICKAXE, INFINITY_SHOVEL, INFINITY_SHIELD, INFINITY_BOW
        };

        addUnbreakable(items);
        addEnchants(items, "infinity-enchant-levels", "INFINITY");
        
        ENDER_FLAME.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 5);
    }
    
    private static int getInt(String path) {
        int found = CONFIG.getInt(path);
        if (found < 0 || found > Short.MAX_VALUE) return 0;
        return found;
    }
    
    private static void addEnchants(SlimefunItemStack[] items, String section, String gearType) {
        ConfigurationSection typeSection = CONFIG.getConfigurationSection(section);
        
        if (typeSection == null) {
            InfinityExpansion.log(Level.SEVERE, "Config section " + section + " missing, Check the config and report this!");
            return;
        }
        
        for (SlimefunItemStack item : items) {
            String itemPath = item.getItemId().replace(gearType + "_", "").toLowerCase();
            ConfigurationSection itemSection = typeSection.getConfigurationSection(itemPath);
            if (itemSection == null) {
                InfinityExpansion.log(Level.SEVERE, "Config section " + itemPath + " missing, Check the config and report this!");
                continue;
            }
            
            for (String path : itemSection.getKeys(false)) {
                int level = getInt(section + "." + itemPath + "." + path);
                if (level > 0) {
                    Enchantment e = getEnchantFromString(path);
                    if (e == null) {
                        InfinityExpansion.log(Level.WARNING, "Failed to add enchantment " + path + ", your config may be messed up, report this!");
                        continue;
                    }
                    item.addUnsafeEnchantment(e, level);
                }
            }
        }
    }
        
    private static void addUnbreakable(SlimefunItemStack[] items) {
        for (SlimefunItemStack item : items) {
            ItemMeta meta = item.getItemMeta();
            if (meta == null) continue;
            meta.setUnbreakable(true);
            item.setItemMeta(meta);
        }
    }
    
    @Nullable
    private static Enchantment getEnchantFromString(String string) {
        switch (string) {
            case "sharpness": return Enchantment.DAMAGE_ALL;
            case "efficiency": return Enchantment.DIG_SPEED;
            case "protection": return Enchantment.PROTECTION_ENVIRONMENTAL;
            case "fire-aspect": return Enchantment.FIRE_ASPECT;
            case "fortune": return Enchantment.LOOT_BONUS_BLOCKS;
            case "looting": return Enchantment.LOOT_BONUS_MOBS;
            case "silk-touch": return Enchantment.SILK_TOUCH;
            case "thorns": return Enchantment.THORNS;
            case "aqua-affinity": return Enchantment.WATER_WORKER;
            case "power": return Enchantment.ARROW_DAMAGE;
            case "flame": return Enchantment.ARROW_FIRE;
            case "infinity": return Enchantment.ARROW_INFINITE;
            case "punch": return Enchantment.ARROW_KNOCKBACK;
            case "feather-falling": return Enchantment.PROTECTION_FALL;
            default: return null;
        }
    }
}