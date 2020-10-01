package me.mooy1.infinityexpansion;

import io.github.thebusybiscuit.slimefun4.utils.HeadTexture;
import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder;
import me.mooy1.infinityexpansion.gear.InfinityGear;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.ItemMeta;

public final class Items {

    //Machines

    public static final SlimefunItemStack CORE_COMBINER = new SlimefunItemStack(
            "CORE_COMBINER",
            Material.QUARTZ_BRICKS,
            "&7&lCore Combiner",
            LoreBuilder.speed(1),
            LoreBuilder.powerPerSecond(1_000),
            ""
    );
    public static final SlimefunItemStack INFINITY_CORE_COMBINER = new SlimefunItemStack(
            "INFINITY_CORE_COMBINER",
            Material.CHISELED_QUARTZ_BLOCK,
            "&b&lInfinity &7&lCore Combiner",
            LoreBuilder.speed(10),
            LoreBuilder.powerPerSecond(10_000),
            ""
    );

    public static final SlimefunItemStack INGOT_SYNTHESIZER = new SlimefunItemStack(
            "INGOT_SYNTHESIZER",
            Material.STONE_BRICKS,
            "&7&lIngot Synthesizer",
            LoreBuilder.speed(1),
            LoreBuilder.powerPerSecond(1_000),
            ""
    );
    public static final SlimefunItemStack INFINITY_INGOT_SYNTHESIZER = new SlimefunItemStack(
            "INFINITY_INGOT_SYNTHESIZER",
            Material.CHISELED_STONE_BRICKS,
            "&b&lInfinity &7&lIngot Synthesizer",
            "&7Creates advanced Slimefun alloys using cores",
            LoreBuilder.speed(10),
            LoreBuilder.powerPerSecond(10_000),
            ""
    );

    public static final SlimefunItemStack INFINITY_REACTOR = new SlimefunItemStack(
            "INFINITY_REACTOR",
            Material.CRYING_OBSIDIAN,
            "&b&lInfinity Reactor",
            "&7Generates power through the melting of Infinity Ingots",
            LoreBuilder.powerPerSecond(1_000_000),
            ""
    );

    public static final SlimefunItemStack BASIC_QUARRY = new SlimefunItemStack(
        "BASIC_QUARRY",
        Material.CHISELED_SANDSTONE,
        "&8&lBasic &7&lQuarry",
        "&7Automatically mines vanilla overworld ores",
        LoreBuilder.speed(1),
        LoreBuilder.powerPerSecond(4_000),
        ""
    );
    public static final SlimefunItemStack ADVANCED_QUARRY = new SlimefunItemStack(
        "ADVANCED_QUARRY",
        Material.CHISELED_RED_SANDSTONE,
        "&c&lAdvanced &7&lQuarry",
        "&7Smelts vanilla ores and can mine nether ores",
        LoreBuilder.speed(2),
        LoreBuilder.powerPerSecond(12_000),
        ""
    );
    public static final SlimefunItemStack VOID_QUARRY = new SlimefunItemStack(
        "VOID_QUARRY",
        Material.CHISELED_NETHER_BRICKS,
        "&d&lVoid &7&lQuarry",
        "&7Can mine sifted ores or 24 karat gold occasionally",
        LoreBuilder.speed(4),
        LoreBuilder.powerPerSecond(75_000),
        ""
    );
    public static final SlimefunItemStack INFINITY_QUARRY = new SlimefunItemStack(
        "INFINITY_QUARRY",
        Material.CHISELED_POLISHED_BLACKSTONE,
        "&b&lInfinity &7&lQuarry",
        "&7Can mine and smelt Slimefun ingots",
        LoreBuilder.speed(8),
        LoreBuilder.powerPerSecond(400_000),
        ""
    );
    public static final SlimefunItemStack ADVANCED_ENCHANTER = new SlimefunItemStack(
        "ADVANCED_ENCHANTER",
        Material.ENCHANTING_TABLE,
        "&c&lAdvanced &7&lEnchanter",
        LoreBuilder.speed(10),
        LoreBuilder.powerPerSecond(999),
        ""
    );
    public static final SlimefunItemStack ADVANCED_DISENCHANTER = new SlimefunItemStack(
        "ADVANCED_DISENCHANTER",
        Material.ENCHANTING_TABLE,
        "&c&lAdvanced &7&lDisenchanter",
        LoreBuilder.speed(10),
        LoreBuilder.powerPerSecond(999),
        ""
    );
    public static final SlimefunItemStack INFINITY_ENCHANTER = new SlimefunItemStack(
        "INFINITY_ENCHANTER",
        Material.ENCHANTING_TABLE,
        "&b&lInfinity &7&lEnchanter",
        LoreBuilder.speed(100),
        LoreBuilder.powerPerSecond(99999),
        ""

    );
    public static final SlimefunItemStack INFINITY_DISENCHANTER = new SlimefunItemStack(
        "INFINITY_DISENCHANTER",
        Material.ENCHANTING_TABLE,
        "&b&lInfinity &7&lDisenchanter",
        LoreBuilder.speed(100),
        LoreBuilder.powerPerSecond(99999),
        ""
    );
    public static final SlimefunItemStack INFINITY_FORGE = new SlimefunItemStack(
        "INFINITY_FORGE",
        Material.NETHERITE_BLOCK,
        "&c&lInfinity Forge",
        "&c&onot yet functional"
    );
    public static final SlimefunItemStack INFINITY_CAPACITOR = new SlimefunItemStack(
        "INFINITY_CAPACITOR",
        HeadTexture.CAPACITOR_25,
        "&b&lInfinite &7&lCapacitor",
        "&8\u21E8 &e\u26A1 &bInfinite &7J Capacity",
        "&c&oDo not use more than ",
        "&c&o1 per energy network",
        ""
    );
    public static final SlimefunItemStack CELESTIAL_PANEL = new SlimefunItemStack(
        "CELESTIAL_PANEL",
        Material.WHITE_GLAZED_TERRACOTTA,
        "&c&lCelestial Panel",
        "&7Generates during the day",
        LoreBuilder.powerBuffer(300_000),
        LoreBuilder.powerPerSecond(3_000),
        ""
    );
    public static final SlimefunItemStack VOID_PANEL = new SlimefunItemStack(
        "VOID_PANEL",
        Material.LIGHT_GRAY_GLAZED_TERRACOTTA,
        "&c&lVoid Panel",
        "&7Generates during the night",
        LoreBuilder.powerBuffer(900_000),
        LoreBuilder.powerPerSecond(9_000),
        ""
    );
    public static final SlimefunItemStack INFINITE_PANEL = new SlimefunItemStack(
        "INFINITE_PANEL",
        Material.LIGHT_BLUE_GLAZED_TERRACOTTA,
        "&b&lInfinity Panel",
        "&7Always generates",
        LoreBuilder.powerBuffer(20_000_000),
        LoreBuilder.powerPerSecond(200_000),
        ""
    );

    //Compressed Cobblestones

    public static final SlimefunItemStack COMPRESSED_COBBLESTONE_1 = new SlimefunItemStack(
        "COMPRESSED_COBBLESTONE_1",
        Material.COBBLESTONE,
        "&7&l1x Compressed Cobblestone",
        "&89 cobblestone combined",
        ""
    );
    public static final SlimefunItemStack COMPRESSED_COBBLESTONE_2 = new SlimefunItemStack(
        "COMPRESSED_COBBLESTONE_2",
        Material.ANDESITE,
        "&7&l2x Compressed Cobblestone",
        "&881 cobblestone combined",
        ""
    );
    public static final SlimefunItemStack COMPRESSED_COBBLESTONE_3 = new SlimefunItemStack(
        "COMPRESSED_COBBLESTONE_3",
        Material.ANDESITE,
        "&7&l3x Compressed Cobblestone",
        "&8243 cobblestone combined",
        ""
    );
    public static final SlimefunItemStack COMPRESSED_COBBLESTONE_4 = new SlimefunItemStack(
        "COMPRESSED_COBBLESTONE_4",
        Material.STONE,
        "&7&l4x Compressed Cobblestone",
        "&86,561 cobblestone combined",
        ""
    );
    public static final SlimefunItemStack COMPRESSED_COBBLESTONE_5 = new SlimefunItemStack(
        "COMPRESSED_COBBLESTONE_5",
        Material.STONE,
        "&7&l5x Compressed Cobblestone",
        "&859,049 cobblestone combined",
        ""
    );
    public static final SlimefunItemStack COMPRESSED_COBBLESTONE_6 = new SlimefunItemStack(
        "COMPRESSED_COBBLESTONE_6",
        Material.POLISHED_ANDESITE,
        "&7&l6x Compressed Cobblestone",
        "&8531,441 cobblestone combined",
        ""
    );
    public static final SlimefunItemStack COMPRESSED_COBBLESTONE_7 = new SlimefunItemStack(
        "COMPRESSED_COBBLESTONE_7",
        Material.POLISHED_ANDESITE,
        "&7&l7x Compressed Cobblestone",
        "&84,782,969 cobblestone combined",
        ""
    );
    public static final SlimefunItemStack COMPRESSED_COBBLESTONE_8 = new SlimefunItemStack(
        "COMPRESSED_COBBLESTONE_8",
        Material.OBSIDIAN,
        "&7&l8x Compressed Cobblestone",
        "&843,046,721 cobblestone combined",
        ""
    );
    public static final SlimefunItemStack COMPRESSED_COBBLESTONE_9 = new SlimefunItemStack(
        "COMPRESSED_COBBLESTONE_9",
        Material.CRYING_OBSIDIAN,
        "&7&l9x Compressed Cobblestone",
        "&8387,420,489 cobblestone combined",
        ""
    );
    
    //slimefun ingot blocks

    public static final SlimefunItemStack MAGNESIUM_BLOCK = new SlimefunItemStack(
            "MAGNESIUM_BLOCK",
            Material.NETHER_BRICKS,
            "&5&lMagnesium Block",
            ""
    );
    public static final SlimefunItemStack COPPER_BLOCK = new SlimefunItemStack(
            "COPPER_BLOCK",
            Material.BRICKS,
            "&6&lCopper Block",
            ""
    );
    public static final SlimefunItemStack SILVER_BLOCK = new SlimefunItemStack(
            "SILVER_BLOCK",
            Material.IRON_BLOCK,
            "&7&lSilver Block",
            ""
    );
    public static final SlimefunItemStack TIN_BLOCK = new SlimefunItemStack(
            "TIN_BLOCK",
            Material.IRON_BLOCK,
            "&7&lTin Block",
            ""
    );
    public static final SlimefunItemStack LEAD_BLOCK = new SlimefunItemStack(
            "LEAD_BLOCK",
            Material.IRON_BLOCK,
            "&8&lLead Block",
            ""
    );
    public static final SlimefunItemStack ALUMINUM_BLOCK = new SlimefunItemStack(
            "ALUMINUM_BLOCK",
            Material.IRON_BLOCK,
            "&7&lAluminum Block",
            ""
    );
    public static final SlimefunItemStack ZINC_BLOCK = new SlimefunItemStack(
            "ZINC_BLOCK",
            Material.IRON_BLOCK,
            "&7&lZinc Block",
            ""
    );

    //Cores

    public static final SlimefunItemStack MAGNESIUM_CORE = new SlimefunItemStack(
        "MAGNESIUM_CORE",
        Material.NETHER_BRICKS,
        "&5&lMagnesium Core",
        ""
    );
    public static final SlimefunItemStack COPPER_CORE = new SlimefunItemStack(
        "COPPER_CORE",
        Material.BRICKS,
        "&6&lCopper Core",
        ""
    );
    public static final SlimefunItemStack SILVER_CORE = new SlimefunItemStack(
        "SILVER_CORE",
        Material.IRON_BLOCK,
        "&7&lSilver Core",
        ""
    );
    public static final SlimefunItemStack TIN_CORE = new SlimefunItemStack(
        "TIN_CORE",
        Material.IRON_BLOCK,
        "&7&lTin Core",
        ""
    );
    public static final SlimefunItemStack LEAD_CORE = new SlimefunItemStack(
        "LEAD_CORE",
        Material.IRON_BLOCK,
        "&8&lLead Core",
        ""
    );
    public static final SlimefunItemStack ALUMINUM_CORE = new SlimefunItemStack(
        "ALUMINUM_CORE",
        Material.IRON_BLOCK,
        "&7&lAluminum Core",
        ""
    );
    public static final SlimefunItemStack ZINC_CORE = new SlimefunItemStack(
        "ZINC_CORE",
        Material.IRON_BLOCK,
        "&7&lZinc Core",
        ""
    );
    public static final SlimefunItemStack IRON_CORE = new SlimefunItemStack(
        "IRON_CORE",
        Material.IRON_BLOCK,
        "&7&lIron Core",
        ""
    );
    public static final SlimefunItemStack GOLD_CORE = new SlimefunItemStack(
        "GOLD_CORE",
        Material.GOLD_BLOCK,
        "&6&lGold Core",
        ""
    );
    public static final SlimefunItemStack NETHERITE_CORE = new SlimefunItemStack(
        "NETHERITE_CORE",
        Material.NETHERITE_BLOCK,
        "&4&lNetherite Core",
        ""
    );
    public static final SlimefunItemStack EMERALD_CORE = new SlimefunItemStack(
        "EMERALD_CORE",
        Material.EMERALD_BLOCK,
        "&a&lEmerald Core",
        ""
    );
    public static final SlimefunItemStack DIAMOND_CORE = new SlimefunItemStack(
        "DIAMOND_CORE",
        Material.DIAMOND_BLOCK,
        "&b&lDiamond Core",
        ""
    );
    public static final SlimefunItemStack COAL_CORE = new SlimefunItemStack(
        "COAL_CORE",
        Material.COAL_BLOCK,
        "&8&lCoal Core",
        ""
    );
    public static final SlimefunItemStack REDSTONE_CORE = new SlimefunItemStack(
        "REDSTONE_CORE",
        Material.REDSTONE_BLOCK,
        "&c&lRedstone Core",
        ""
    );
    public static final SlimefunItemStack LAPIS_CORE = new SlimefunItemStack(
        "LAPIS_CORE",
        Material.LAPIS_BLOCK,
        "&9&lLapis Core",
        ""
    );
    public static final SlimefunItemStack QUARTZ_CORE = new SlimefunItemStack(
        "QUARTZ_CORE",
        Material.QUARTZ_BLOCK,
        "&f&lQuartz Core",
        ""
    );

    //Compressed cores

    public static final SlimefunItemStack MAGNESIUM_COMPRESSED_CORE = new SlimefunItemStack(
            "MAGNESIUM_COMPRESSED_CORE",
            Material.NETHER_BRICKS,
            "&5&lMagnesium Compressed Core",
            ""
    );
    public static final SlimefunItemStack COPPER_COMPRESSED_CORE = new SlimefunItemStack(
            "COPPER_COMPRESSED_CORE",
            Material.BRICKS,
            "&6&lCopper Compressed Core",
            ""
    );
    public static final SlimefunItemStack SILVER_COMPRESSED_CORE = new SlimefunItemStack(
            "SILVER_COMPRESSED_CORE",
            Material.IRON_BLOCK,
            "&7&lSilver Compressed Core",
            ""
    );
    public static final SlimefunItemStack TIN_COMPRESSED_CORE = new SlimefunItemStack(
            "TIN_COMPRESSED_CORE",
            Material.IRON_BLOCK,
            "&7&lTin Compressed Core",
            ""
    );
    public static final SlimefunItemStack LEAD_COMPRESSED_CORE = new SlimefunItemStack(
            "LEAD_COMPRESSED_CORE",
            Material.IRON_BLOCK,
            "&8&lLead Compressed Core",
            ""
    );
    public static final SlimefunItemStack ALUMINUM_COMPRESSED_CORE = new SlimefunItemStack(
            "ALUMINUM_COMPRESSED_CORE",
            Material.IRON_BLOCK,
            "&7&lAluminum Compressed Core",
            ""
    );
    public static final SlimefunItemStack ZINC_COMPRESSED_CORE = new SlimefunItemStack(
            "ZINC_COMPRESSED_CORE",
            Material.IRON_BLOCK,
            "&7&lZinc Compressed Core",
            ""
    );
    public static final SlimefunItemStack IRON_COMPRESSED_CORE = new SlimefunItemStack(
            "IRON_COMPRESSED_CORE",
            Material.IRON_BLOCK,
            "&7&lIron Compressed Core",
            ""
    );
    public static final SlimefunItemStack GOLD_COMPRESSED_CORE = new SlimefunItemStack(
            "GOLD_COMPRESSED_CORE",
            Material.GOLD_BLOCK,
            "&6&lGold Compressed Core",
            ""
    );
    public static final SlimefunItemStack NETHERITE_COMPRESSED_CORE = new SlimefunItemStack(
            "NETHERITE_COMPRESSED_CORE",
            Material.NETHERITE_BLOCK,
            "&4&lNetherite Compressed Core",
            ""
    );
    public static final SlimefunItemStack EMERALD_COMPRESSED_CORE = new SlimefunItemStack(
            "EMERALD_COMPRESSED_CORE",
            Material.EMERALD_BLOCK,
            "&a&lEmerald Compressed Core",
            ""
    );
    public static final SlimefunItemStack DIAMOND_COMPRESSED_CORE = new SlimefunItemStack(
            "DIAMOND_COMPRESSED_CORE",
            Material.DIAMOND_BLOCK,
            "&b&lDiamond Compressed Core",
            ""
    );
    public static final SlimefunItemStack COAL_COMPRESSED_CORE = new SlimefunItemStack(
            "COAL_COMPRESSED_CORE",
            Material.COAL_BLOCK,
            "&8&lCoal Compressed Core",
            ""
    );
    public static final SlimefunItemStack REDSTONE_COMPRESSED_CORE = new SlimefunItemStack(
            "REDSTONE_COMPRESSED_CORE",
            Material.REDSTONE_BLOCK,
            "&c&lRedstone Compressed Core",
            ""
    );
    public static final SlimefunItemStack LAPIS_COMPRESSED_CORE = new SlimefunItemStack(
            "LAPIS_COMPRESSED_CORE",
            Material.LAPIS_BLOCK,
            "&9&lLapis Compressed Core",
            ""
    );
    public static final SlimefunItemStack QUARTZ_COMPRESSED_CORE = new SlimefunItemStack(
            "QUARTZ_COMPRESSED_CORE",
            Material.QUARTZ_BLOCK,
            "&f&lQuartz Compressed Core",
            ""
    );

    //singularities

    public static final SlimefunItemStack MAGNESIUM_SINGULARITY = new SlimefunItemStack(
            "MAGNESIUM_SINGULARITY",
            Material.NETHER_BRICKS,
            "&5&lMagnesium Singularity",
            ""
    );
    public static final SlimefunItemStack COPPER_SINGULARITY = new SlimefunItemStack(
            "COPPER_SINGULARITY",
            Material.BRICKS,
            "&6&lCopper Singularity",
            ""
    );
    public static final SlimefunItemStack SILVER_SINGULARITY = new SlimefunItemStack(
            "SILVER_SINGULARITY",
            Material.IRON_BLOCK,
            "&7&lSilver Singularity",
            ""
    );
    public static final SlimefunItemStack TIN_SINGULARITY = new SlimefunItemStack(
            "TIN_SINGULARITY",
            Material.IRON_BLOCK,
            "&7&lTin Singularity",
            ""
    );
    public static final SlimefunItemStack LEAD_SINGULARITY = new SlimefunItemStack(
            "LEAD_SINGULARITY",
            Material.IRON_BLOCK,
            "&8&lLead Singularity",
            ""
    );
    public static final SlimefunItemStack ALUMINUM_SINGULARITY = new SlimefunItemStack(
            "ALUMINUM_SINGULARITY",
            Material.IRON_BLOCK,
            "&7&lAluminum Singularity",
            ""
    );
    public static final SlimefunItemStack ZINC_SINGULARITY = new SlimefunItemStack(
            "ZINC_SINGULARITY",
            Material.IRON_BLOCK,
            "&7&lZinc Singularity",
            ""
    );
    public static final SlimefunItemStack IRON_SINGULARITY = new SlimefunItemStack(
            "IRON_SINGULARITY",
            Material.IRON_BLOCK,
            "&7&lIron Singularity",
            ""
    );
    public static final SlimefunItemStack GOLD_SINGULARITY = new SlimefunItemStack(
            "GOLD_SINGULARITY",
            Material.GOLD_BLOCK,
            "&6&lGold Singularity",
            ""
    );
    public static final SlimefunItemStack NETHERITE_SINGULARITY = new SlimefunItemStack(
            "NETHERITE_SINGULARITY",
            Material.NETHERITE_BLOCK,
            "&4&lNetherite Singularity",
            ""
    );
    public static final SlimefunItemStack EMERALD_SINGULARITY = new SlimefunItemStack(
            "EMERALD_SINGULARITY",
            Material.EMERALD_BLOCK,
            "&a&lEmerald Singularity",
            ""
    );
    public static final SlimefunItemStack DIAMOND_SINGULARITY = new SlimefunItemStack(
            "DIAMOND_SINGULARITY",
            Material.DIAMOND_BLOCK,
            "&b&lDiamond Singularity",
            ""
    );
    public static final SlimefunItemStack COAL_SINGULARITY = new SlimefunItemStack(
            "COAL_SINGULARITY",
            Material.COAL_BLOCK,
            "&8&lCoal Singularity",
            ""
    );
    public static final SlimefunItemStack REDSTONE_SINGULARITY = new SlimefunItemStack(
            "REDSTONE_SINGULARITY",
            Material.REDSTONE_BLOCK,
            "&c&lRedstone Singularity",
            ""
    );
    public static final SlimefunItemStack LAPIS_SINGULARITY = new SlimefunItemStack(
            "LAPIS_SINGULARITY",
            Material.LAPIS_BLOCK,
            "&9&lLapis Singularity",
            ""
    );
    public static final SlimefunItemStack QUARTZ_SINGULARITY = new SlimefunItemStack(
            "QUARTZ_SINGULARITY",
            Material.QUARTZ_BLOCK,
            "&f&lQuartz Singularity",
            ""
    );

    //Infinity Singularities

    public static final SlimefunItemStack FORTUNE_SINGULARITY = new SlimefunItemStack(
            "FORTUNE_SINGULARITY",
            Material.NETHER_STAR,
            "&6&lFortune Singularity",
            ""
    );
    public static final SlimefunItemStack METAL_SINGULARITY = new SlimefunItemStack(
            "METAL_SINGULARITY",
            Material.NETHER_STAR,
            "&7&lMetal Singularity",
            ""
    );
    public static final SlimefunItemStack MAGIC_SINGULARITY = new SlimefunItemStack(
            "MAGIC_SINGULARITY",
            Material.NETHER_STAR,
            "&d&lMagic Singularity",
            ""
    );
    public static final SlimefunItemStack EARTH_SINGULARITY = new SlimefunItemStack(
            "EARTH_SINGULARITY",
            Material.NETHER_STAR,
            "&a&lEarth Singularity",
            ""
    );

    //Ingots

    public static final SlimefunItemStack MAGSTEEL = new SlimefunItemStack(
        "MAGSTEEL",
        Material.BRICK,
        "&4&lMagSteel Ingot",
        ""
    );
    public static final SlimefunItemStack MAGNONIUM_INGOT = new SlimefunItemStack(
        "MAGNONIUM_INGOT",
        Material.NETHER_BRICK,
        "&5&lMagnonium Ingot",
        ""
    );
    public static final SlimefunItemStack INFINITE_INGOT = new SlimefunItemStack(
        "INFINITE_INGOT",
        Material.NETHERITE_INGOT,
        "&b&lInfinity Ingot",
        ""
    );

    //Materials

    public static final SlimefunItemStack VOID_DUST = new SlimefunItemStack(
        "VOID_DUST",
        Material.GUNPOWDER,
        "&5&lVoid Dust",
        "&8From the depths of the end...",
        ""
    );

    public static final SlimefunItemStack MACHINE_PLATE = new SlimefunItemStack(
        "MACHINE_PLATE",
        Material.PAPER,
        "&7&lMachine Plate",
        "&7Machine Component",
        ""
    );
    public static final SlimefunItemStack MACHINE_CIRCUIT = new SlimefunItemStack(
        "MACHINE_CIRCUIT",
        Material.GOLD_INGOT,
        "&6&lMachine Circuit",
        "&7Machine Component",
        ""
    );
    public static final SlimefunItemStack INFINITE_MACHINE_CIRCUIT = new SlimefunItemStack(
        "INFINITE_MACHINE_CIRCUIT",
        Material.DIAMOND,
        "&b&lInfinite &6&lMachine Circuit",
        "&7Machine Component",
        ""
    );
    public static final SlimefunItemStack MACHINE_CORE = new SlimefunItemStack(
        "MACHINE_CORE",
        Material.IRON_BLOCK,
        "&7&lMachine Core",
        "&7Machine Component",
        ""
    );
    public static final SlimefunItemStack INFINITE_MACHINE_CORE = new SlimefunItemStack(
        "INFINITE_MACHINE_CORE",
        Material.DIAMOND_BLOCK,
        "&b&lInfinite &7&lMachine Core",
        "&7Machine Component",
        ""
    );

    //Gear

    public static final SlimefunItemStack VOID_FLAME = new SlimefunItemStack(
        "VOID_FLAME",
        Material.ENCHANTED_BOOK,
        "&d&lVoid &c&lFire",
        ""
    );

    public static final SlimefunItemStack MAGNONIUM_CROWN = new SlimefunItemStack(
        "MAGNONIUM_CROWN",
        Material.NETHERITE_HELMET,
        "&5&lMagnonium Crown",
        ""
    );
    public static final SlimefunItemStack MAGNONIUM_CHESTPLATE = new SlimefunItemStack(
        "MAGNONIUM_CHESTPLATE",
        Material.NETHERITE_CHESTPLATE,
        "&5&lMagnonium Chestplate",
        ""
    );
    public static final SlimefunItemStack MAGNONIUM_LEGGINGS = new SlimefunItemStack(
        "MAGNONIUM_LEGGINGS",
        Material.NETHERITE_LEGGINGS,
        "&5&lMagnonium Leggings",
        ""
    );
    public static final SlimefunItemStack MAGNONIUM_BOOTS = new SlimefunItemStack(
        "MAGNONIUM_BOOTS",
        Material.NETHERITE_BOOTS,
        "&5&lMagnonium Boots",
        ""
    );
    public static final SlimefunItemStack MAGNONIUM_BLADE = new SlimefunItemStack(
        "MAGNONIUM_BLADE",
        Material.NETHERITE_SWORD,
        "&5&lMagnonium Blade",
        ""
    );
    public static final SlimefunItemStack MAGNONIUM_PICKAXE = new SlimefunItemStack(
        "MAGNONIUM_PICKAXE",
        Material.NETHERITE_PICKAXE,
        "&5&lMagnonium Pickaxe",
        ""
    );

    public static final SlimefunItemStack INFINITY_CROWN = new SlimefunItemStack(
        "INFINITY_CROWN",
        Material.NETHERITE_HELMET,
        "&b&lInfinity Crown",
        ""
    );
    public static final SlimefunItemStack INFINITY_CHESTPLATE = new SlimefunItemStack(
        "INFINITY_CHESTPLATE",
        Material.NETHERITE_CHESTPLATE,
        "&b&lInfinity Chestplate",
        ""
    );
    public static final SlimefunItemStack INFINITY_LEGGINGS = new SlimefunItemStack(
        "INFINITY_LEGGINGS",
        Material.NETHERITE_LEGGINGS,
        "&b&lInfinity Leggings",
        ""
    );
    public static final SlimefunItemStack INFINITY_BOOTS = new SlimefunItemStack(
        "INFINITY_BOOTS",
        Material.NETHERITE_BOOTS,
        "&b&lInfinity Boots",
        ""
    );
    public static final SlimefunItemStack INFINITY_BLADE = new SlimefunItemStack(
        "INFINITY_BLADE",
        Material.NETHERITE_SWORD,
        "&b&lInfinity Blade",
        ""
    );
    public static final SlimefunItemStack INFINITY_PICKAXE = new SlimefunItemStack(
        "INFINITY_PICKAXE",
        Material.NETHERITE_PICKAXE,
        "&b&lInfinity Pickaxe",
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
        VOID_FLAME.addUnsafeEnchantment(fire, 10);
        INFINITY_CROWN.addUnsafeEnchantment(prot, 40);
        INFINITY_CHESTPLATE.addUnsafeEnchantment(prot, 40);
        INFINITY_LEGGINGS.addUnsafeEnchantment(prot, 40);
        INFINITY_BOOTS.addUnsafeEnchantment(prot, 40);
        INFINITY_PICKAXE.addUnsafeEnchantment(eff, 40);
        INFINITY_PICKAXE.addUnsafeEnchantment(fort, 20);
        INFINITY_BLADE.addUnsafeEnchantment(sharp, 20);
        INFINITY_BLADE.addUnsafeEnchantment(loot, 20);

        //add unbreakables

        ItemMeta hat = INFINITY_CROWN.getItemMeta();
        assert hat != null;
        hat.setUnbreakable(true);
        INFINITY_CROWN.setItemMeta(hat);

        ItemMeta shirt = INFINITY_CHESTPLATE.getItemMeta();
        assert shirt != null;
        shirt.setUnbreakable(true);
        INFINITY_CHESTPLATE.setItemMeta(shirt);

        ItemMeta pants = INFINITY_LEGGINGS.getItemMeta();
        assert pants != null;
        pants.setUnbreakable(true);
        INFINITY_LEGGINGS.setItemMeta(pants);

        ItemMeta shoes = INFINITY_BOOTS.getItemMeta();
        assert shoes != null;
        shoes.setUnbreakable(true);
        INFINITY_BOOTS.setItemMeta(shoes);

        ItemMeta pick = INFINITY_PICKAXE.getItemMeta();
        assert pick != null;
        pick.setUnbreakable(true);
        INFINITY_PICKAXE.setItemMeta(pick);

        ItemMeta blade = INFINITY_BLADE.getItemMeta();
        assert blade != null;
        blade.setUnbreakable(true);
        INFINITY_BLADE.setItemMeta(blade);
    }

    private Items() { }
}