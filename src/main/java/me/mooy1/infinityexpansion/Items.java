package me.mooy1.infinityexpansion;

import io.github.thebusybiscuit.slimefun4.utils.HeadTexture;
import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder;
import me.mooy1.infinityexpansion.storage.StorageUnit;
import me.mooy1.infinityexpansion.machines.*;
import me.mooy1.infinityexpansion.setup.ItemSetup;
import me.mooy1.infinityexpansion.utils.LoreUtils;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.ItemMeta;

public final class Items {

    //Machines

    public static final SlimefunItemStack ITEM_UPDATER = new SlimefunItemStack(
            "ITEM_UPDATER",
            Material.QUARTZ_PILLAR,
            "&6Item Updater",
            "&7Will &creset &7and update the name and lore of",
            "&7slimefun items if they are outdated or broken",
            "&cAll enchants will be removed, disenchant first!",
            "&cChargeable and upgradeable items will be reset!",
            "",
            LoreUtils.energyPer(ItemUpdater.ENERGY) + "per item",
            ""
    );
    public static final SlimefunItemStack POWERED_BEDROCK = new SlimefunItemStack(
            "POWERED_BEDROCK",
            Material.NETHERITE_BLOCK,
            "&4Powered &8Bedrock",
            "&7When powered, transforms into a bedrock",
            "&7Will revert once unpowered or broken",
            "",
            LoreUtils.energyPerSecond(PoweredBedrock.ENERGY),
            ""
    );

    public static final SlimefunItemStack VOID_HARVESTER = new SlimefunItemStack(
        "VOID_HARVESTER",
        Material.OBSIDIAN,
        "&8Void &7Harvester",
        "&7Slowly harvests &8Void &7Bits from nothing...",
            "",
        LoreUtils.speed(VoidHarvester.BASIC_SPEED),
        LoreUtils.energyPerSecond(VoidHarvester.BASIC_ENERGY),
        ""
    );
    public static final SlimefunItemStack INFINITE_VOID_HARVESTER = new SlimefunItemStack(
        "INFINITE_VOID_HARVESTER",
        Material.CRYING_OBSIDIAN,
        "&bInfinite &8Void &7Harvester",
        "&7Harvests &8Void &7Bits from nothing...",
            "",
        LoreUtils.speed(VoidHarvester.INFINITY_SPEED),
        LoreUtils.energyPerSecond(VoidHarvester.INFINITY_ENERGY),
        ""
    );

    public static final SlimefunItemStack SINGULARITY_CONSTRUCTOR = new SlimefunItemStack(
        "SINGULARITY_CONSTRUCTOR",
        Material.QUARTZ_BRICKS,
        "&fSingularity &7Constructor",
        LoreUtils.speed(SingularityConstructor.BASIC_SPEED),
        LoreUtils.energyPerSecond(SingularityConstructor.BASIC_ENERGY),
        ""
    );
    public static final SlimefunItemStack INFINITY_CONSTRUCTOR = new SlimefunItemStack(
        "INFINITY_CONSTRUCTOR",
        Material.CHISELED_QUARTZ_BLOCK,
        "&bInfinity &7Constructor",
        LoreUtils.speed(SingularityConstructor.INFINITY_SPEED),
        LoreUtils.energyPerSecond(SingularityConstructor.INFINITY_ENERGY),
        ""
    );

    public static final SlimefunItemStack RESOURCE_SYNTHESIZER = new SlimefunItemStack(
        "RESOURCE_SYNTHESIZER",
        Material.FURNACE,
        "&6Resource &7Synthesizer",
        "&7Creates resources out of singularities",
            "",
        LoreUtils.energyPer(ResourceSynthesizer.ENERGY) + "per use",
        ""
    );

    public static final SlimefunItemStack INFINITY_REACTOR = new SlimefunItemStack(
        "INFINITY_REACTOR",
        Material.BEACON,
        "&bInfinity Reactor",
        "&7Generates power through the compression",
        "&7of &8Void &7and &bInfinity &7Ingots",
        "",
        LoreUtils.energyPerSecond(InfinityReactor.ENERGY),
        LoreBuilder.powerBuffer(InfinityReactor.STORAGE),
            ""
    );

    public static final SlimefunItemStack ADVANCED_CHARGER = new SlimefunItemStack(
            "ADVANCED_CHARGER",
            Material.CRIMSON_NYLIUM,
            "&cAdvanced &7Charger",
            "&7Quickly charges items",
            "",
            LoreUtils.speed(ItemSetup.ADVANCED_CHARGER_SPEED),
            LoreUtils.energyPerSecond(ItemSetup.ADVANCED_CHARGER_ENERGY),
            ""
    );
    public static final SlimefunItemStack INFINITY_CHARGER = new SlimefunItemStack(
            "INFINITY_CHARGER",
            Material.WARPED_NYLIUM,
            "&bInfinity &7Charger",
            "&7Instantly charges items",
            "",
            LoreUtils.speed(ItemSetup.INFINITY_CHARGER_SPEED),
            LoreUtils.energyPerSecond(ItemSetup.INFINITY_CHARGER_ENERGY),
            ""
    );

    public static final SlimefunItemStack BASIC_QUARRY = new SlimefunItemStack(
        "BASIC_QUARRY",
        Material.CHISELED_SANDSTONE,
        "&9Basic &7Quarry",
        "&7Automatically mines vanilla overworld ores",
        "",
        LoreUtils.speed(Quarry.BASIC_SPEED),
        LoreUtils.energyPerSecond(Quarry.BASIC_ENERGY),
        ""
    );
    public static final SlimefunItemStack ADVANCED_QUARRY = new SlimefunItemStack(
        "ADVANCED_QUARRY",
        Material.CHISELED_RED_SANDSTONE,
        "&cAdvanced &7Quarry",
        "&7Smelts vanilla ores and can mine nether ores",
            "",
        LoreUtils.speed(Quarry.ADVANCED_SPEED),
        LoreUtils.energyPerSecond(Quarry.ADVANCED_ENERGY),
        ""
    );
    public static final SlimefunItemStack VOID_QUARRY = new SlimefunItemStack(
        "VOID_QUARRY",
        Material.CHISELED_NETHER_BRICKS,
        "&8Void &7Quarry",
        "&7Can mine sifted ores or 24 karat gold occasionally",
            "",
        LoreUtils.speed(Quarry.VOID_SPEED),
        LoreUtils.energyPerSecond(Quarry.VOID_ENERGY),
        ""
    );
    public static final SlimefunItemStack INFINITY_QUARRY = new SlimefunItemStack(
        "INFINITY_QUARRY",
        Material.CHISELED_POLISHED_BLACKSTONE,
        "&bInfinity &7Quarry",
        "&7Can mine and smelt Slimefun ingots",
            "",
        LoreUtils.speed(Quarry.INFINITY_SPEED),
        LoreUtils.energyPerSecond(Quarry.INFINITY_ENERGY),
        ""
    );
    public static final SlimefunItemStack ADVANCED_ENCHANTER = new SlimefunItemStack(
        "ADVANCED_ENCHANTER",
        Material.ENCHANTING_TABLE,
        "&cAdvanced &7Enchanter",
        LoreUtils.speed(ItemSetup.ADVANCED_EN_SPEED),
        LoreUtils.energyPerSecond(ItemSetup.ADVANCED_EN_ENERGY),
        ""
    );
    public static final SlimefunItemStack ADVANCED_DISENCHANTER = new SlimefunItemStack(
        "ADVANCED_DISENCHANTER",
        Material.ENCHANTING_TABLE,
        "&cAdvanced &7Disenchanter",
        LoreUtils.speed(ItemSetup.ADVANCED_DIS_SPEED),
        LoreUtils.energyPerSecond(ItemSetup.ADVANCED_DIS_ENERGY),
        ""
    );
    public static final SlimefunItemStack INFINITY_ENCHANTER = new SlimefunItemStack(
        "INFINITY_ENCHANTER",
        Material.ENCHANTING_TABLE,
        "&bInfinity &7Enchanter",
        LoreUtils.speed(ItemSetup.INFINITY_EN_SPEED),
        LoreUtils.energyPerSecond(ItemSetup.INFINITY_EN_ENERGY),
        ""

    );
    public static final SlimefunItemStack INFINITY_DISENCHANTER = new SlimefunItemStack(
        "INFINITY_DISENCHANTER",
        Material.ENCHANTING_TABLE,
        "&bInfinity &7Disenchanter",
        LoreUtils.speed(ItemSetup.INFINITY_DIS_SPEED),
        LoreUtils.energyPerSecond(ItemSetup.INFINITY_DIS_ENERGY),
        ""
    );
    public static final SlimefunItemStack INFINITY_FORGE = new SlimefunItemStack(
        "INFINITY_FORGE",
        Material.RESPAWN_ANCHOR,
        "&bInfinity &7Forge",
        "&7Used to forge infinity items",
            "",
        LoreUtils.energyPer(InfinityForge.ENERGY) + "per item",
        "",
            "&cNot yet functional"
    );

    public static final SlimefunItemStack ADVANCED_ANVIL = new SlimefunItemStack(
        "ADVANCED_ANVIL",
        Material.SMITHING_TABLE,
        "&cAdvanced &7Anvil",
            "",
        LoreUtils.energyPer(AdvancedAnvil.ENERGY) + "per use",
        "",
            "&cNot yet functional"
    );

    public static final SlimefunItemStack INFINITY_CAPACITOR = new SlimefunItemStack(
        "INFINITY_CAPACITOR",
        HeadTexture.CAPACITOR_25,
        "&bInfinite &7Capacitor",
        "&c&oDo not use more than ",
        "&c&o1 per energy network",
        "",
        "&8\u21E8 &e\u26A1 &bInfinite &7J Capacity",
        ""

    );
    public static final SlimefunItemStack GEOTHERMAL_GENERATOR = new SlimefunItemStack(
        "GEOTHERMAL_GENERATOR",
        Material.MAGMA_BLOCK,
        "&cGeoThermal Generator",
        "&7Generates energy from the heat of the world",
            "",
        LoreBuilder.powerBuffer(Generators.GEO_STORAGE),
        LoreUtils.energyPerSecond(Generators.GEO_RATE),
        ""
    );
    public static final SlimefunItemStack CELESTIAL_PANEL = new SlimefunItemStack(
        "CELESTIAL_PANEL",
        Material.WHITE_GLAZED_TERRACOTTA,
        "&eCelestial Panel",
        "&7Generates during the day",
            "",
        LoreBuilder.powerBuffer(Generators.CELE_STORAGE),
        LoreUtils.energyPerSecond(Generators.CELE_RATE),
        ""
    );
    public static final SlimefunItemStack VOID_PANEL = new SlimefunItemStack(
        "VOID_PANEL",
        Material.LIGHT_GRAY_GLAZED_TERRACOTTA,
        "&8Void Panel",
        "&7Generates during the night",
            "",
        LoreBuilder.powerBuffer(Generators.VOID_STORAGE),
        LoreUtils.energyPerSecond(Generators.VOID_RATE),
        ""
    );
    public static final SlimefunItemStack INFINITE_PANEL = new SlimefunItemStack(
        "INFINITE_PANEL",
        Material.LIGHT_BLUE_GLAZED_TERRACOTTA,
        "&bInfinity Panel",
        "&7Always generates",
            "",
        LoreBuilder.powerBuffer(Generators.INFINITY_STORAGE),
        LoreUtils.energyPerSecond(Generators.INFINITY_RATE),
        ""
    );

    //Deep storage units

    public static final SlimefunItemStack BASIC_STORAGE = new SlimefunItemStack(
            "BASIC_STORAGE",
            Material.OAK_WOOD,
            "&9Basic &7Storage Unit",
            LoreUtils.stores(StorageUnit.BASIC),
            "&aWorks with cargo",
            ""
    );
    public static final SlimefunItemStack ADVANCED_STORAGE = new SlimefunItemStack(
            "ADVANCED_STORAGE",
            Material.DARK_OAK_WOOD,
            "&cAdvanced &7Storage Unit",
            LoreUtils.stores(StorageUnit.ADVANCED),
            "&aWorks with cargo",
            ""
    );
    public static final SlimefunItemStack REINFORCED_STORAGE = new SlimefunItemStack(
            "REINFORCED_STORAGE",
            Material.ACACIA_WOOD,
            "&fReinforced &7Storage Unit",
            LoreUtils.stores(StorageUnit.REINFORCED),
            "&aWorks with cargo",
            ""
    );
    public static final SlimefunItemStack VOID_STORAGE = new SlimefunItemStack(
            "VOID_STORAGE",
            Material.CRIMSON_HYPHAE,
            "&8Void &7Storage Unit",
            LoreUtils.stores(StorageUnit.VOID),
            "&aWorks with cargo",
            ""
    );
    public static final SlimefunItemStack INFINITY_STORAGE = new SlimefunItemStack(
            "INFINITY_STORAGE",
            Material.WARPED_HYPHAE,
            "&bInfinity &7Storage Unit",
            "&6Stores: &bInfinite &7items",
            "&aWorks with cargo",
            ""
    );
    
    //drives

    public static final SlimefunItemStack STORAGE_NETWORK_CORE = new SlimefunItemStack(
            "STORAGE_NETWORK_CORE",
            Material.LODESTONE,
            "&7Storage Network Core",
            "&7Gives access to many storage units at once",
            "",
            "&cNot yet functional"
    );

    public static final SlimefunItemStack BASIC_STORAGE_DRIVE = new SlimefunItemStack(
            "BASIC_STORAGE_DRIVE",
            Material.LEATHER_CHESTPLATE,
            "&9Basic &7Storage Drive",
            "&6Put in a storage output to store items",
            "&3Put in a storage input to withdraw items",
            "",
            LoreUtils.storedItem(""),
            LoreUtils.stored(0, StorageUnit.BASIC),
            "",
            "&cNot yet functional"
    );
    public static final SlimefunItemStack ADVANCED_STORAGE_DRIVE = new SlimefunItemStack(
            "ADVANCED_STORAGE_DRIVE",
            Material.LEATHER_CHESTPLATE,
            Color.fromRGB(82, 57, 42), //52392A
            "&cAdvanced &7Storage Drive",
            "&6Put in a storage output to store items",
            "&3Put in a storage input to withdraw items",
            "",
            LoreUtils.storedItem(""),
            LoreUtils.stored(0, StorageUnit.ADVANCED),
            "",
            "&cNot yet functional"
    );
    public static final SlimefunItemStack REINFORCED_STORAGE_DRIVE = new SlimefunItemStack(
            "REINFORCED_STORAGE_DRIVE",
            Material.LEATHER_CHESTPLATE,
            Color.fromRGB(115, 119, 117), //737775
            "&fReinforced &7Storage Drive",
            "&6Put in a storage output to store items",
            "&3Put in a storage input to withdraw items",
            "",
            LoreUtils.storedItem(""),
            LoreUtils.stored(0, StorageUnit.REINFORCED),
            "",
            "&cNot yet functional"
    );
    public static final SlimefunItemStack VOID_STORAGE_DRIVE = new SlimefunItemStack(
            "VOID_STORAGE_DRIVE",
            Material.LEATHER_CHESTPLATE,
            Color.fromRGB(176, 46, 38), //B02E26
            "&8Void &7Storage Drive",
            "&6Put in a storage output to store items",
            "&3Put in a storage input to withdraw items",
            "",
            LoreUtils.storedItem(""),
            LoreUtils.stored(0,StorageUnit.VOID),
            "",
            "&cNot yet functional"
    );
    public static final SlimefunItemStack INFINITY_STORAGE_DRIVE = new SlimefunItemStack(
            "INFINITY_STORAGE_DRIVE",
            Material.LEATHER_CHESTPLATE,
            Color.fromRGB(88, 213, 195), //58D5C3
            "&bInfinity &7Storage Drive",
            "&6Put in a storage output to store items",
            "&3Put in a storage input to withdraw items",
            "",
            LoreUtils.storedItem(""),
            LoreUtils.stored(0, StorageUnit.INFINITY),
            "",
            "&cNot yet functional"
    );

    //Compressed Cobblestones

    public static final SlimefunItemStack COMPRESSED_COBBLESTONE_1 = new SlimefunItemStack(
        "COMPRESSED_COBBLESTONE_1",
        Material.COBBLESTONE,
        "&71x Compressed Cobblestone",
        "&89 cobblestone combined",
        ""
    );
    public static final SlimefunItemStack COMPRESSED_COBBLESTONE_2 = new SlimefunItemStack(
        "COMPRESSED_COBBLESTONE_2",
        Material.ANDESITE,
        "&72x Compressed Cobblestone",
        "&881 cobblestone combined",
        ""
    );
    public static final SlimefunItemStack COMPRESSED_COBBLESTONE_3 = new SlimefunItemStack(
        "COMPRESSED_COBBLESTONE_3",
        Material.ANDESITE,
        "&73x Compressed Cobblestone",
        "&8243 cobblestone combined",
        ""
    );
    public static final SlimefunItemStack COMPRESSED_COBBLESTONE_4 = new SlimefunItemStack(
        "COMPRESSED_COBBLESTONE_4",
        Material.STONE,
        "&74x Compressed Cobblestone",
        "&86,561 cobblestone combined",
        ""
    );
    public static final SlimefunItemStack COMPRESSED_COBBLESTONE_5 = new SlimefunItemStack(
        "COMPRESSED_COBBLESTONE_5",
        Material.STONE,
        "&75x Compressed Cobblestone",
        "&859,049 cobblestone combined",
        ""
    );
    public static final SlimefunItemStack COMPRESSED_COBBLESTONE_6 = new SlimefunItemStack(
        "COMPRESSED_COBBLESTONE_6",
        Material.POLISHED_ANDESITE,
        "&76x Compressed Cobblestone",
        "&8531,441 cobblestone combined",
        ""
    );
    public static final SlimefunItemStack COMPRESSED_COBBLESTONE_7 = new SlimefunItemStack(
        "COMPRESSED_COBBLESTONE_7",
        Material.POLISHED_ANDESITE,
        "&77x Compressed Cobblestone",
        "&84,782,969 cobblestone combined",
        ""
    );

    //singularities

    public static final SlimefunItemStack COPPER_SINGULARITY = new SlimefunItemStack(
            "COPPER_SINGULARITY",
            Material.BRICKS,
            "&6Copper Singularity",
            ""
    );
    public static final SlimefunItemStack COPPER_AMOUNT = new SlimefunItemStack(
            "COPPER_AMOUNT",
            Material.BRICK,
            "&7" + SingularityConstructor.outputTimes[0] + " &6Copper Ingots",
            ""
    );
    public static final SlimefunItemStack ZINC_SINGULARITY = new SlimefunItemStack(
            "ZINC_SINGULARITY",
            Material.IRON_BLOCK,
            "&7Zinc Singularity",
            ""
    );
    public static final SlimefunItemStack ZINC_AMOUNT = new SlimefunItemStack(
            "ZINC_AMOUNT",
            Material.IRON_INGOT,
            "&7" + SingularityConstructor.outputTimes[1] + " &7Zinc Ingots",
            ""
    );
    public static final SlimefunItemStack TIN_SINGULARITY = new SlimefunItemStack(
            "TIN_SINGULARITY",
            Material.IRON_BLOCK,
            "&7Tin Singularity",
            ""
    );
    public static final SlimefunItemStack TIN_AMOUNT = new SlimefunItemStack(
            "TIN_AMOUNT",
            Material.IRON_INGOT,
            "&7" + SingularityConstructor.outputTimes[2] + " &7Tin Ingots",
            ""
    );
    public static final SlimefunItemStack ALUMINUM_SINGULARITY = new SlimefunItemStack(
            "ALUMINUM_SINGULARITY",
            Material.IRON_BLOCK,
            "&7Aluminum Singularity",
            ""
    );
    public static final SlimefunItemStack ALUMINUM_AMOUNT = new SlimefunItemStack(
            "ALUMINUM_AMOUNT",
            Material.IRON_INGOT,
            "&7" + SingularityConstructor.outputTimes[3] + " &7Aluminum Ingots",
            ""
    );
    public static final SlimefunItemStack SILVER_SINGULARITY = new SlimefunItemStack(
            "SILVER_SINGULARITY",
            Material.IRON_BLOCK,
            "&7Silver Singularity",
            ""
    );
    public static final SlimefunItemStack SILVER_AMOUNT = new SlimefunItemStack(
            "SILVER_AMOUNT",
            Material.IRON_INGOT,
            "&7" + SingularityConstructor.outputTimes[4] + " &7Silver Ingots",
            ""
    );
    public static final SlimefunItemStack MAGNESIUM_SINGULARITY = new SlimefunItemStack(
            "MAGNESIUM_SINGULARITY",
            Material.NETHER_BRICKS,
            "&5Magnesium Singularity",
            ""
    );
    public static final SlimefunItemStack MAGNESIUM_AMOUNT = new SlimefunItemStack(
            "MAGNESIUM_AMOUNT",
            Material.NETHER_BRICK,
            "&7" + SingularityConstructor.outputTimes[5] + " &5Magnesium Ingots",
            ""
    );
    public static final SlimefunItemStack LEAD_SINGULARITY = new SlimefunItemStack(
            "LEAD_SINGULARITY",
            Material.IRON_BLOCK,
            "&8Lead Singularity",
            ""
    );
    public static final SlimefunItemStack LEAD_AMOUNT = new SlimefunItemStack(
            "LEAD_AMOUNT",
            Material.IRON_INGOT,
            "&7" + SingularityConstructor.outputTimes[6] + " &8Lead Ingots",
            ""
    );


    public static final SlimefunItemStack GOLD_SINGULARITY = new SlimefunItemStack(
            "GOLD_SINGULARITY",
            Material.GOLD_BLOCK,
            "&6Gold Singularity",
            ""
    );
    public static final SlimefunItemStack GOLD_AMOUNT = new SlimefunItemStack(
            "GOLD_AMOUNT",
            Material.GOLD_INGOT,
            "&7" + SingularityConstructor.outputTimes[7] + " &6Gold Ingots",
            ""
    );
    public static final SlimefunItemStack IRON_SINGULARITY = new SlimefunItemStack(
            "IRON_SINGULARITY",
            Material.IRON_BLOCK,
            "&7Iron Singularity",
            ""
    );
    public static final SlimefunItemStack IRON_AMOUNT = new SlimefunItemStack(
            "IRON_AMOUNT",
            Material.IRON_INGOT,
            "&7" + SingularityConstructor.outputTimes[8] + " &7Iron Ingots",
            ""
    );
    public static final SlimefunItemStack DIAMOND_SINGULARITY = new SlimefunItemStack(
            "DIAMOND_SINGULARITY",
            Material.DIAMOND_BLOCK,
            "&bDiamond Singularity",
            ""
    );
    public static final SlimefunItemStack DIAMOND_AMOUNT = new SlimefunItemStack(
            "DIAMOND_AMOUNT",
            Material.DIAMOND,
            "&7" + SingularityConstructor.outputTimes[9] + " &bDiamonds",
            ""
    );
    public static final SlimefunItemStack EMERALD_SINGULARITY = new SlimefunItemStack(
            "EMERALD_SINGULARITY",
            Material.EMERALD_BLOCK,
            "&aEmerald Singularity",
            ""
    );
    public static final SlimefunItemStack EMERALD_AMOUNT = new SlimefunItemStack(
            "EMERALD_AMOUNT",
            Material.EMERALD,
            "&7" + SingularityConstructor.outputTimes[10] + " &aEmeralds",
            ""
    );
    public static final SlimefunItemStack NETHERITE_SINGULARITY = new SlimefunItemStack(
            "NETHERITE_SINGULARITY",
            Material.NETHERITE_BLOCK,
            "&4Netherite Singularity",
            ""
    );
    public static final SlimefunItemStack NETHERITE_AMOUNT = new SlimefunItemStack(
            "NETHERITE_AMOUNT",
            Material.NETHERITE_INGOT,
            "&7" + SingularityConstructor.outputTimes[11] + " &4Netherite Ingots",
            ""
    );
    public static final SlimefunItemStack COAL_SINGULARITY = new SlimefunItemStack(
            "COAL_SINGULARITY",
            Material.COAL_BLOCK,
            "&8Coal Singularity",
            ""
    );
    public static final SlimefunItemStack COAL_AMOUNT = new SlimefunItemStack(
            "COAL_AMOUNT",
            Material.COAL,
            "&7" + SingularityConstructor.outputTimes[12] + " &8Coal",
            ""
    );
    public static final SlimefunItemStack REDSTONE_SINGULARITY = new SlimefunItemStack(
            "REDSTONE_SINGULARITY",
            Material.REDSTONE_BLOCK,
            "&cRedstone Singularity",
            ""
    );
    public static final SlimefunItemStack REDSTONE_AMOUNT = new SlimefunItemStack(
            "REDSTONE_AMOUNT",
            Material.REDSTONE,
            "&7" + SingularityConstructor.outputTimes[13] + " &cRedstone",
            ""
    );
    public static final SlimefunItemStack LAPIS_SINGULARITY = new SlimefunItemStack(
            "LAPIS_SINGULARITY",
            Material.LAPIS_BLOCK,
            "&9Lapis Singularity",
            ""
    );
    public static final SlimefunItemStack LAPIS_AMOUNT = new SlimefunItemStack(
            "LAPIS_AMOUNT",
            Material.LAPIS_LAZULI,
            "&7" + SingularityConstructor.outputTimes[14] + " &9Lapis",
            ""
    );
    public static final SlimefunItemStack QUARTZ_SINGULARITY = new SlimefunItemStack(
            "QUARTZ_SINGULARITY",
            Material.QUARTZ_BLOCK,
            "&fQuartz Singularity",
            ""
    );
    public static final SlimefunItemStack QUARTZ_AMOUNT = new SlimefunItemStack(
            "QUARTZ_AMOUNT",
            Material.QUARTZ,
            "&7" + SingularityConstructor.outputTimes[15] + " &fQuartz",
            ""
    );
    public static final SlimefunItemStack INFINITY_SINGULARITY = new SlimefunItemStack(
        "INFINITY_SINGULARITY",
        Material.NETHER_STAR,
        "&fInfinity Singularity",
        ""
    );
    public static final SlimefunItemStack INFINITY_AMOUNT = new SlimefunItemStack(
        "INFINITY_AMOUNT",
        Material.IRON_INGOT,
        "&7" + SingularityConstructor.outputTimes[16] + " &fInfinite Ingots",
        ""
    );

    //Infinity Singularities

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

    //Info thingies

    /**
     * Thanks to NCBPFluffyBear for the idea
     */
    public static final SlimefunItemStack ADDON_INFO = new SlimefunItemStack(
            "ADDON_INFO",
            Material.NETHER_STAR,
            "&bAddon Info",
            "&fVersion: &7" + InfinityExpansion.getInstance().getPluginVersion(),
            "",
            "&fDiscord: &b@&7Riley&8#5911",
            "&7discord.gg/slimefun",
            "",
            "&fGithub: &b@&8&7Mooy1",
            "&7" + InfinityExpansion.getInstance().getBugTrackerURL(),
            ""
    );

    //Ingots

    public static final SlimefunItemStack MAGSTEEL = new SlimefunItemStack(
        "MAGSTEEL",
        Material.BRICK,
        "&4MagSteel",
        ""
    );
    public static final SlimefunItemStack MAGNONIUM_INGOT = new SlimefunItemStack(
        "MAGNONIUM_INGOT",
        Material.NETHER_BRICK,
        "&5Magnonium Ingot",
        ""
    );
    public static final SlimefunItemStack INFINITE_INGOT = new SlimefunItemStack(
        "INFINITE_INGOT",
        Material.IRON_INGOT,
        "&dI&cn&6f&ei&an&bi&3t&9y &fIngot",
        "&7&oThe fury of the cosmos in the palm of your hand",
        ""
    );

    //Void ingots

    public static final SlimefunItemStack VOID_BIT = new SlimefunItemStack(
            "VOID_BIT",
            Material.IRON_NUGGET,
            "&8Void &7Bit",
            "&8&oIt feels... empty",
            ""
    );
    public static final SlimefunItemStack VOID_DUST = new SlimefunItemStack(
            "VOID_DUST",
            Material.GUNPOWDER,
            "&8Void &7Dust",
            "&8&oIts starting to take form...",
            ""
    );
    public static final SlimefunItemStack VOID_INGOT = new SlimefunItemStack(
            "VOID_INGOT",
            Material.NETHERITE_INGOT,
            "&8Void &7Ingot",
            "&8&oThe emptiness of the cosmos in the palm of your hand",
            ""
    );

    //Materials

    public static final SlimefunItemStack END_ESSENCE = new SlimefunItemStack(
        "END_ESSENCE",
        Material.BLAZE_POWDER,
        "&5Ender Essence",
        "&8&oFrom the depths of the end...",
        ""
    );

    public static final SlimefunItemStack MAGSTEEL_PLATE = new SlimefunItemStack(
        "MAGSTEEL_PLATE",
        Material.NETHERITE_SCRAP,
        "&4MagSteel &7Plate",
        "&7Machine Component",
        ""
    );
    public static final SlimefunItemStack MACHINE_PLATE = new SlimefunItemStack(
        "MACHINE_PLATE",
        Material.PAPER,
        "&7Machine Plate",
        "&7Machine Component",
        ""
    );
    public static final SlimefunItemStack MACHINE_CIRCUIT = new SlimefunItemStack(
        "MACHINE_CIRCUIT",
        Material.GOLD_INGOT,
        "&6Machine Circuit",
        "&7Machine Component",
        ""
    );
    public static final SlimefunItemStack INFINITE_MACHINE_CIRCUIT = new SlimefunItemStack(
        "INFINITE_MACHINE_CIRCUIT",
        Material.DIAMOND,
        "&bInfinite &6Machine Circuit",
        "&7Machine Component",
        ""
    );
    public static final SlimefunItemStack MACHINE_CORE = new SlimefunItemStack(
        "MACHINE_CORE",
        Material.IRON_BLOCK,
        "&7Machine Core",
        "&7Machine Component",
        ""
    );
    public static final SlimefunItemStack INFINITE_MACHINE_CORE = new SlimefunItemStack(
        "INFINITE_MACHINE_CORE",
        Material.DIAMOND_BLOCK,
        "&bInfinite &7Machine Core",
        "&7Machine Component",
        ""
    );

    //Gear

    public static final SlimefunItemStack ENDER_FLAME = new SlimefunItemStack(
        "ENDER_FLAME",
        Material.ENCHANTED_BOOK,
        "&dEnder &cFlame",
        ""
    );

    public static final SlimefunItemStack MAGNONIUM_CROWN = new SlimefunItemStack(
        "MAGNONIUM_CROWN",
        Material.NETHERITE_HELMET,
        "&5Magnonium Crown",
        ""
    );
    public static final SlimefunItemStack MAGNONIUM_CHESTPLATE = new SlimefunItemStack(
        "MAGNONIUM_CHESTPLATE",
        Material.NETHERITE_CHESTPLATE,
        "&5Magnonium Chestplate",
        ""
    );
    public static final SlimefunItemStack MAGNONIUM_LEGGINGS = new SlimefunItemStack(
        "MAGNONIUM_LEGGINGS",
        Material.NETHERITE_LEGGINGS,
        "&5Magnonium Leggings",
        ""
    );
    public static final SlimefunItemStack MAGNONIUM_BOOTS = new SlimefunItemStack(
        "MAGNONIUM_BOOTS",
        Material.NETHERITE_BOOTS,
        "&5Magnonium Boots",
        ""
    );
    public static final SlimefunItemStack MAGNONIUM_BLADE = new SlimefunItemStack(
        "MAGNONIUM_BLADE",
        Material.NETHERITE_SWORD,
        "&5Magnonium Blade",
        ""
    );
    public static final SlimefunItemStack MAGNONIUM_PICKAXE = new SlimefunItemStack(
        "MAGNONIUM_PICKAXE",
        Material.NETHERITE_PICKAXE,
        "&5Magnonium Pickaxe",
        ""
    );

    public static final SlimefunItemStack INFINITY_CROWN = new SlimefunItemStack(
        "INFINITY_CROWN",
        Material.NETHERITE_HELMET,
        "&bInfinity Crown",
        "&7&oBreath of the cosmos",
            "",
            "&6 + Saturation",
            "&8 + Night Vision",
            "&9 + Water Breathing"
    );
    public static final SlimefunItemStack INFINITY_CHESTPLATE = new SlimefunItemStack(
        "INFINITY_CHESTPLATE",
        Material.NETHERITE_CHESTPLATE,
        "&bInfinity Chestplate",
        "&7&oStrength of the cosmos",
            "",
            "&a + Health Boost III",
            "&8 + Resistance II",
            "&2 + Fire Resistance",
            "&c + Regeneration"
    );
    public static final SlimefunItemStack INFINITY_LEGGINGS = new SlimefunItemStack(
        "INFINITY_LEGGINGS",
        Material.NETHERITE_LEGGINGS,
        "&bInfinity Leggings",
        "&7&o",
            "",
            "&c + Strength II",
            "&6 + Haste III",
            "&9 + Conduit Power"
    );
    public static final SlimefunItemStack INFINITY_BOOTS = new SlimefunItemStack(
        "INFINITY_BOOTS",
        Material.NETHERITE_BOOTS,
        "&bInfinity Boots",
        "&7&oSpeed of the cosmos",
            "",
            "&b + Speed III",
            "&3 + Dolphins Grace"
    );
    public static final SlimefunItemStack INFINITY_BLADE = new SlimefunItemStack(
        "INFINITY_BLADE",
        Material.NETHERITE_SWORD,
        "&bBlade of the Cosmos",
        "&7&oEdge of infinity",
            ""
    );
    public static final SlimefunItemStack INFINITY_PICKAXE = new SlimefunItemStack(
        "INFINITY_PICKAXE",
        Material.NETHERITE_PICKAXE,
        "&9World Breaker",
        "&3&oThe end of the world",
            ""
    );
    public static final SlimefunItemStack INFINITY_AXE = new SlimefunItemStack(
        "INFINITY_AXE",
        Material.NETHERITE_AXE,
        "&4Nature's Ruin",
        "&c&oThe embodiment of fury",
            ""
    );
    public static final SlimefunItemStack INFINITY_SHOVEL = new SlimefunItemStack(
        "INFINITY_SHOVEL",
        Material.NETHERITE_SHOVEL,
        "&aMountain Eater",
        "&2&o",
            ""
    );
    public static final SlimefunItemStack INFINITY_BOW = new SlimefunItemStack(
        "INFINITY_BOW",
        Material.BOW,
        "&6Sky Piercer",
        "&e&oThe longbow of the Heavens",
            ""
    );
    public static final SlimefunItemStack INFINITY_WINGS = new SlimefunItemStack(
        "INFINITY_WINGS",
        Material.ELYTRA,
        "&dVoid Wings",
        "&8&oInto the void",
            ""
    );
    public static final SlimefunItemStack INFINITY_SHEILD = new SlimefunItemStack(
            "INFINITY_SHEILD",
            Material.SHIELD,
            "&bCosmic Aegis",
            "&7&o",
            ""
    );

    //enchant list

    private static final Enchantment prot = Enchantment.PROTECTION_ENVIRONMENTAL;
    private static final Enchantment sharp = Enchantment.DAMAGE_ALL;
    private static final Enchantment eff = Enchantment.DIG_SPEED;
    private static final Enchantment unb = Enchantment.DURABILITY;
    private static final Enchantment fire = Enchantment.FIRE_ASPECT;
    private static final Enchantment fort = Enchantment.LOOT_BONUS_BLOCKS;
    private static final Enchantment loot = Enchantment.LOOT_BONUS_MOBS;
    private static final Enchantment silk = Enchantment.SILK_TOUCH;
    private static final Enchantment thorns = Enchantment.THORNS;
    private static final Enchantment soul = Enchantment.SOUL_SPEED;
    private static final Enchantment depth = Enchantment.DEPTH_STRIDER;
    private static final Enchantment aqua = Enchantment.WATER_WORKER;
    private static final Enchantment power = Enchantment.ARROW_DAMAGE;
    private static final Enchantment flame = Enchantment.ARROW_FIRE;
    private static final Enchantment infinity = Enchantment.ARROW_INFINITE;
    static {

        //add enchants

        MAGNONIUM_CROWN.addUnsafeEnchantment(prot, 10);
        MAGNONIUM_CROWN.addUnsafeEnchantment(unb, 10);
        MAGNONIUM_CHESTPLATE.addUnsafeEnchantment(prot, 10);
        MAGNONIUM_CHESTPLATE.addUnsafeEnchantment(unb, 10);
        MAGNONIUM_LEGGINGS.addUnsafeEnchantment(prot, 10);
        MAGNONIUM_LEGGINGS.addUnsafeEnchantment(unb, 10);
        MAGNONIUM_BOOTS.addUnsafeEnchantment(prot, 10);
        MAGNONIUM_BOOTS.addUnsafeEnchantment(unb, 10);
        MAGNONIUM_BLADE.addUnsafeEnchantment(sharp, 10);
        MAGNONIUM_BLADE.addUnsafeEnchantment(unb, 10);
        MAGNONIUM_PICKAXE.addUnsafeEnchantment(eff, 10);
        MAGNONIUM_PICKAXE.addUnsafeEnchantment(unb, 10);

        ENDER_FLAME.addUnsafeEnchantment(fire, 10);

        INFINITY_CROWN.addUnsafeEnchantment(prot, 40);
        INFINITY_CROWN.addUnsafeEnchantment(aqua, 10);

        INFINITY_CHESTPLATE.addUnsafeEnchantment(prot, 40);
        INFINITY_CHESTPLATE.addUnsafeEnchantment(thorns, 20);

        INFINITY_SHEILD.addUnsafeEnchantment(prot, 40);
        INFINITY_SHEILD.addUnsafeEnchantment(thorns, 20);

        INFINITY_LEGGINGS.addUnsafeEnchantment(prot, 40);

        INFINITY_BOOTS.addUnsafeEnchantment(prot, 40);

        INFINITY_PICKAXE.addUnsafeEnchantment(eff, 40);
        INFINITY_PICKAXE.addUnsafeEnchantment(fort, 20);

        INFINITY_SHOVEL.addUnsafeEnchantment(eff, 40);
        INFINITY_SHOVEL.addUnsafeEnchantment(fort, 20);
        INFINITY_SHOVEL.addUnsafeEnchantment(silk, 1);

        INFINITY_AXE.addUnsafeEnchantment(eff, 40);
        INFINITY_AXE.addUnsafeEnchantment(sharp, 20);
        INFINITY_AXE.addUnsafeEnchantment(fire, 20);

        INFINITY_BLADE.addUnsafeEnchantment(sharp, 20);
        INFINITY_BLADE.addUnsafeEnchantment(loot, 20);

        INFINITY_BOW.addUnsafeEnchantment(power, 20);
        INFINITY_BOW.addUnsafeEnchantment(flame, 20);
        INFINITY_BOW.addUnsafeEnchantment(infinity, 10);

        INFINITY_WINGS.addUnsafeEnchantment(prot, 20);

        //add unbreakables

        ItemMeta hat = INFINITY_CROWN.getItemMeta();
        assert hat != null;
        hat.setUnbreakable(true);
        //hat.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        INFINITY_CROWN.setItemMeta(hat);

        ItemMeta shirt = INFINITY_CHESTPLATE.getItemMeta();
        assert shirt != null;
        shirt.setUnbreakable(true);
        //shirt.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        INFINITY_CHESTPLATE.setItemMeta(shirt);

        ItemMeta pants = INFINITY_LEGGINGS.getItemMeta();
        assert pants != null;
        pants.setUnbreakable(true);
        //pants.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        INFINITY_LEGGINGS.setItemMeta(pants);

        ItemMeta shoes = INFINITY_BOOTS.getItemMeta();
        assert shoes != null;
        shoes.setUnbreakable(true);
        //shoes.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        INFINITY_BOOTS.setItemMeta(shoes);

        ItemMeta pick = INFINITY_PICKAXE.getItemMeta();
        assert pick != null;
        pick.setUnbreakable(true);
        //pick.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        INFINITY_PICKAXE.setItemMeta(pick);

        ItemMeta blade = INFINITY_BLADE.getItemMeta();
        assert blade != null;
        blade.setUnbreakable(true);
        //blade.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        INFINITY_BLADE.setItemMeta(blade);

        ItemMeta axe = INFINITY_AXE.getItemMeta();
        assert axe != null;
        axe.setUnbreakable(true);
        //axe.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        INFINITY_AXE.setItemMeta(axe);

        ItemMeta shovel = INFINITY_SHOVEL.getItemMeta();
        assert shovel != null;
        shovel.setUnbreakable(true);
        //shovel.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        INFINITY_SHOVEL.setItemMeta(shovel);

        ItemMeta sheild = INFINITY_SHEILD.getItemMeta();
        assert sheild != null;
        sheild.setUnbreakable(true);
        //sheild.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        INFINITY_SHEILD.setItemMeta(sheild);

        ItemMeta bow = INFINITY_BOW.getItemMeta();
        assert bow != null;
        bow.setUnbreakable(true);
        //bow.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        INFINITY_BOW.setItemMeta(bow);

        ItemMeta wings = INFINITY_WINGS.getItemMeta();
        assert wings != null;
        wings.setUnbreakable(true);
        //wings.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        INFINITY_WINGS.setItemMeta(wings);
    }

    private Items() { }
}