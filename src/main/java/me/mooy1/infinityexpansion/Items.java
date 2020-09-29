package me.mooy1.infinityexpansion;

import io.github.thebusybiscuit.slimefun4.utils.HeadTexture;
import me.mooy1.infinityexpansion.Machines.InfinityPanel;
import me.mooy1.infinityexpansion.Machines.Quarry;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder;
import org.bukkit.inventory.meta.ItemMeta;

public final class Items {

    //Machines

    public static final SlimefunItemStack QUARRY = new SlimefunItemStack(
            "QUARRY",
            Material.STONE_BRICKS,
            "&7&lQuarry",
            "",
            LoreBuilder.speed(Quarry.Tier.BASIC.getSpeed()),
            LoreBuilder.powerPerSecond(Quarry.Tier.BASIC.getEnergyConsumption()),
            ""
    );
    public static final SlimefunItemStack ADVANCED_QUARRY = new SlimefunItemStack(
            "ADVANCED_QUARRY",
            Material.STONE_BRICKS,
            "&c&lAdvanced &7&lQuarry",
            "",
            LoreBuilder.speed(Quarry.Tier.ADVANCED.getSpeed()),
            LoreBuilder.powerPerSecond(Quarry.Tier.ADVANCED.getEnergyConsumption()),
            ""
    );
    public static final SlimefunItemStack VOID_QUARRY = new SlimefunItemStack(
            "VOID_QUARRY",
            Material.STONE_BRICKS,
            "&d&lVoid &7&lQuarry",
            "",
            LoreBuilder.speed(Quarry.Tier.VOID.getSpeed()),
            LoreBuilder.powerPerSecond(Quarry.Tier.VOID.getEnergyConsumption()),
            ""
    );
    public static final SlimefunItemStack INFINITY_QUARRY = new SlimefunItemStack(
            "INFINITY_QUARRY",
            Material.STONE_BRICKS,
            "&b&lInfinity &7&lQuarry",
            "",
            LoreBuilder.speed(Quarry.Tier.INFINITY.getSpeed()),
            LoreBuilder.powerPerSecond(Quarry.Tier.INFINITY.getEnergyConsumption()),
            ""
    );
    public static final SlimefunItemStack ADVANCED_ENCHANTER = new SlimefunItemStack(
            "ADVANCED_ENCHANTER",
            Material.ENCHANTING_TABLE,
            "&c&lAdvanced Enchanter",
            "",
            LoreBuilder.speed(10),
            LoreBuilder.powerPerSecond(999),
            ""
    );
    public static final SlimefunItemStack ADVANCED_DISENCHANTER = new SlimefunItemStack(
            "ADVANCED_DISENCHANTER",
            Material.ENCHANTING_TABLE,
            "&c&lAdvanced Disenchanter",
            "",
            LoreBuilder.speed(10),
            LoreBuilder.powerPerSecond(999),
            ""
    );
    public static final SlimefunItemStack INFINITY_ENCHANTER = new SlimefunItemStack(
            "INFINITY_ENCHANTER",
            Material.ENCHANTING_TABLE,
            "&c&lInfinity Enchanter",
            "",
            LoreBuilder.speed(100),
            LoreBuilder.powerPerSecond(99999),
            ""

    );
    public static final SlimefunItemStack INFINITY_DISENCHANTER = new SlimefunItemStack(
            "INFINITY_DISENCHANTER",
            Material.ENCHANTING_TABLE,
            "&c&lInfinity Disenchanter",
            "",
            LoreBuilder.speed(100),
            LoreBuilder.powerPerSecond(99999),
            ""
    );
    public static final SlimefunItemStack INFINITY_FORGE = new SlimefunItemStack(
            "INFINITY_FORGE",
            Material.NETHERITE_BLOCK,
            "&c&lInfinity Forge",
            ""
    );
    public static final SlimefunItemStack INFINITE_CAPACITOR = new SlimefunItemStack(
            "INFINITE_CAPACITOR",
            HeadTexture.CAPACITOR_25,
            "&c&lInfinite Capacitor",
            "",
            "&8\u21E8 &e\u26A1 &bInfinite &7J Capacity",
            ""
    );
    public static final SlimefunItemStack CELESTIAL_PANEL = new SlimefunItemStack(
            "CELESTIAL_PANEL",
            Material.WHITE_GLAZED_TERRACOTTA,
            "&c&lCelestial Panel",
            "&7Generates during the day",
            "",
            LoreBuilder.powerBuffer(InfinityPanel.Panel.CELESTIAL.getCapacity()),
            LoreBuilder.powerPerSecond(InfinityPanel.Panel.CELESTIAL.getDayGenerationRate()*5/3),
            ""
    );
    public static final SlimefunItemStack VOID_PANEL = new SlimefunItemStack(
            "VOID_PANEL",
            Material.LIGHT_GRAY_GLAZED_TERRACOTTA,
            "&c&lVoid Panel",
            "&7Generates during the night",
            "",
            LoreBuilder.powerBuffer(InfinityPanel.Panel.VOID.getCapacity()),
            LoreBuilder.powerPerSecond(InfinityPanel.Panel.VOID.getNightGenerationRate()*5/3),
            ""
    );
    public static final SlimefunItemStack INFINITY_PANEL = new SlimefunItemStack(
            "INFINITY_PANEL",
            Material.LIGHT_BLUE_GLAZED_TERRACOTTA,
            "&c&lInfinity Panel",
            "Always generates",
            "",
            LoreBuilder.powerBuffer(InfinityPanel.Panel.INFINITY.getCapacity()),
            LoreBuilder.powerPerSecond(InfinityPanel.Panel.INFINITY.getDayGenerationRate()*5/3),
            ""
    );

    //Compressed Cobblestones

    public static final SlimefunItemStack COMPRESSED_COBBLESTONE_1 = new SlimefunItemStack(
            "COMPRESSED_COBBLESTONE_1",
            Material.COBBLESTONE,
            "&7&l1x Compressed Cobblestone",
            "",
            "&89 cobblestone combined",
            ""
    );
    public static final SlimefunItemStack COMPRESSED_COBBLESTONE_2 = new SlimefunItemStack(
            "COMPRESSED_COBBLESTONE_2",
            Material.ANDESITE,
            "&7&l2x Compressed Cobblestone",
            "",
            "&881 cobblestone combined",
            ""
    );
    public static final SlimefunItemStack COMPRESSED_COBBLESTONE_3 = new SlimefunItemStack(
            "COMPRESSED_COBBLESTONE_3",
            Material.ANDESITE,
            "&7&l3x Compressed Cobblestone",
            "",
            "&8243 cobblestone combined",
            ""
    );
    public static final SlimefunItemStack COMPRESSED_COBBLESTONE_4 = new SlimefunItemStack(
            "COMPRESSED_COBBLESTONE_4",
            Material.STONE,
            "&7&l4x Compressed Cobblestone",
            "",
            "&86,561 cobblestone combined",
            ""
    );
    public static final SlimefunItemStack COMPRESSED_COBBLESTONE_5 = new SlimefunItemStack(
            "COMPRESSED_COBBLESTONE_5",
            Material.STONE,
            "&7&l5x Compressed Cobblestone",
            "",
            "&859,049 cobblestone combined",
            ""
    );
    public static final SlimefunItemStack COMPRESSED_COBBLESTONE_6 = new SlimefunItemStack(
            "COMPRESSED_COBBLESTONE_6",
            Material.POLISHED_ANDESITE,
            "&7&l6x Compressed Cobblestone",
            "",
            "&8531,441 cobblestone combined",
            ""
    );
    public static final SlimefunItemStack COMPRESSED_COBBLESTONE_7 = new SlimefunItemStack(
            "COMPRESSED_COBBLESTONE_7",
            Material.POLISHED_ANDESITE,
            "&7&l7x Compressed Cobblestone",
            "",
            "&84,782,969 cobblestone combined",
            ""
    );
    public static final SlimefunItemStack COMPRESSED_COBBLESTONE_8 = new SlimefunItemStack(
            "COMPRESSED_COBBLESTONE_8",
            Material.OBSIDIAN,
            "&7&l8x Compressed Cobblestone",
            "",
            "&843,046,721 cobblestone combined",
            ""
    );
    public static final SlimefunItemStack COMPRESSED_COBBLESTONE_9 = new SlimefunItemStack(
            "COMPRESSED_COBBLESTONE_9",
            Material.CRYING_OBSIDIAN,
            "&7&l9x Compressed Cobblestone",
            "",
            "&8387,420,489 cobblestone combined",
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
    public static final SlimefunItemStack SILVER_CORE= new SlimefunItemStack(
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

    //Ingots

    public static final SlimefunItemStack MAGNONIUM_INGOT = new SlimefunItemStack(
            "MAGNONIUM_INGOT",
            Material.NETHER_BRICK,
            "&5&lMagnonium Ingot",
            ""
    );
    public static final SlimefunItemStack INFINITY_INGOT = new SlimefunItemStack(
            "INFINITY_INGOT",
            Material.NETHERITE_INGOT,
            "&b&lInfinity Ingot",
            ""
    );

    //Materials

    public static final SlimefunItemStack VOID_DUST = new SlimefunItemStack(
            "VOID_DUST",
            Material.GUNPOWDER,
            "&5&lVoid Dust",
            "",
            "&8From the depths of the end...",
            ""
    );

    public static final SlimefunItemStack MACHINE_PLATE = new SlimefunItemStack(
            "MACHINE_PLATE",
            Material.IRON_INGOT,
            "&7&lMachine Plate",
            ""
    );
    public static final SlimefunItemStack MACHINE_CIRCUIT = new SlimefunItemStack(
            "MACHINE_CIRCUIT",
            Material.GOLD_INGOT,
            "&6&lMachine Circuit",
            ""
    );
    public static final SlimefunItemStack INFINITE_MACHINE_CIRCUIT = new SlimefunItemStack(
            "INFINITE_MACHINE_CIRCUIT",
            Material.DIAMOND,
            "&b&lInfinite &6&lMachine Circuit",
            ""
    );
    public static final SlimefunItemStack MACHINE_CORE = new SlimefunItemStack(
            "MACHINE_CORE",
            Material.IRON_BLOCK,
            "&7&lMachine Core",
            ""
    );
    public static final SlimefunItemStack INFINITE_MACHINE_CORE = new SlimefunItemStack(
            "INFINITE_MACHINE_CORE",
            Material.DIAMOND_BLOCK,
            "&b&lInfinite &7&lMachine Core",
            ""
    );

    //Gear

    public static final SlimefunItemStack VOID_FLAME = new SlimefunItemStack(
            "VOID_FLAME",
            Material.BOOK,
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
        hat.setUnbreakable(true);
        INFINITY_CROWN.setItemMeta(hat);

        ItemMeta shirt = INFINITY_CHESTPLATE.getItemMeta();
        shirt.setUnbreakable(true);
        INFINITY_CHESTPLATE.setItemMeta(shirt);

        ItemMeta pants = INFINITY_LEGGINGS.getItemMeta();
        pants.setUnbreakable(true);
        INFINITY_LEGGINGS.setItemMeta(pants);

        ItemMeta shoes = INFINITY_BOOTS.getItemMeta();
        shoes.setUnbreakable(true);
        INFINITY_BOOTS.setItemMeta(shoes);

        ItemMeta pick = INFINITY_PICKAXE.getItemMeta();
        pick.setUnbreakable(true);
        INFINITY_PICKAXE.setItemMeta(pick);

        ItemMeta blade = INFINITY_BLADE.getItemMeta();
        blade.setUnbreakable(true);
        INFINITY_BLADE.setItemMeta(blade);
    }

    private Items() { }
}