package me.mooy1.infinityexpansion;

import io.github.thebusybiscuit.slimefun4.utils.HeadTexture;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder;
import org.bukkit.inventory.meta.ItemMeta;

public final class Items {

    //Categories

    public static final Category MOOYMACHINES = new Category(new NamespacedKey(InfinityExpansion.getInstance(),
            "MOOYMACHINES"),
            new CustomItem(Material.SMITHING_TABLE, "&aInfinity Machines")
    );
    public static final Category MOOYMATERIALS = new Category(new NamespacedKey(InfinityExpansion.getInstance(),
            "MOOYMATERIALS"),
            new CustomItem(Material.NETHERITE_BLOCK, "&aInfinity Materials")
    );
    public static final Category MOOYGEAR = new Category(new NamespacedKey(InfinityExpansion.getInstance(),
            "MOOYGEAR"),
            new CustomItem(Material.NETHERITE_CHESTPLATE, "&aInfinity Gear")
    );

    //Machines

    public static final SlimefunItemStack ADVANCED_ENCHANTER = new SlimefunItemStack(
            "ADVANCED_ENCHANTER",
            Material.ENCHANTING_TABLE,
            "&cAdvanced Enchanter",
            "",
            LoreBuilder.speed(10),
            LoreBuilder.powerPerSecond(999),
            ""

    );
    public static final SlimefunItemStack ADVANCED_DISENCHANTER = new SlimefunItemStack(
            "ADVANCED_DISENCHANTER",
            Material.ENCHANTING_TABLE,
            "&cAdvanced Disenchanter",
            "",
            LoreBuilder.speed(10),
            LoreBuilder.powerPerSecond(999),
            ""
    );
    public static final SlimefunItemStack INFINITY_ENCHANTER = new SlimefunItemStack(
            "INFINITY_ENCHANTER",
            Material.ENCHANTING_TABLE,
            "&cInfinity Enchanter",
            "",
            LoreBuilder.speed(100),
            LoreBuilder.powerPerSecond(99999),
            ""

    );
    public static final SlimefunItemStack INFINITY_DISENCHANTER = new SlimefunItemStack(
            "INFINITY_DISENCHANTER",
            Material.ENCHANTING_TABLE,
            "&cInfinity Disenchanter",
            "",
            LoreBuilder.speed(100),
            LoreBuilder.powerPerSecond(99999),
            ""
    );
    public static final SlimefunItemStack INFINITY_FORGE = new SlimefunItemStack(
            "INFINITY_FORGE",
            Material.NETHERITE_BLOCK,
            "&cInfinity Forge",
            ""
    );
    public static final SlimefunItemStack INFINITE_CAPACITOR = new SlimefunItemStack(
            "INFINITE_CAPACITOR",
            HeadTexture.CAPACITOR_25,
            "&cInfinite Capacitor",
            "",
            "&8\u21E8 &e\u26A1 &bInfinite &7J Capacity",
            ""
    );
    public static final SlimefunItemStack CELESTIAL_PANEL = new SlimefunItemStack(
            "CELESTIAL_PANEL",
            Material.WHITE_GLAZED_TERRACOTTA,
            "&cCelestial Panel",
            "&7Only works during the day",
            "",
            LoreBuilder.powerPerSecond(6000) + " (Day)",
            ""
    );
    public static final SlimefunItemStack VOID_PANEL = new SlimefunItemStack(
            "VOID_PANEL",
            Material.ORANGE_GLAZED_TERRACOTTA,
            "&cVoid Panel",
            "&7Doesn't work during the day",
            "",
            LoreBuilder.powerPerSecond(12000) + " (Night)",
            ""
    );
    public static final SlimefunItemStack INFINITE_PANEL = new SlimefunItemStack(
            "INFINITE_PANEL",
            Material.LIGHT_BLUE_GLAZED_TERRACOTTA,
            "&cInfinite Panel",
            "",
            LoreBuilder.powerPerSecond(1000000) + " (Day)",
            LoreBuilder.powerPerSecond(1000000) + " (Night)",
            ""
    );

    //Compressed Cobblestones

    public static final SlimefunItemStack COMPRESSED_COBBLESTONE_1 = new SlimefunItemStack(
            "COMPRESSED_COBBLESTONE_1",
            Material.COBBLESTONE,
            "&71x Compressed Cobblestone",
            "",
            "&89 cobblestone combined",
            ""
    );
    public static final SlimefunItemStack COMPRESSED_COBBLESTONE_2 = new SlimefunItemStack(
            "COMPRESSED_COBBLESTONE_2",
            Material.ANDESITE,
            "&72x Compressed Cobblestone",
            "",
            "&881 cobblestone combined",
            ""
    );
    public static final SlimefunItemStack COMPRESSED_COBBLESTONE_3 = new SlimefunItemStack(
            "COMPRESSED_COBBLESTONE_3",
            Material.ANDESITE,
            "&73x Compressed Cobblestone",
            "",
            "&8243 cobblestone combined",
            ""
    );
    public static final SlimefunItemStack COMPRESSED_COBBLESTONE_4 = new SlimefunItemStack(
            "COMPRESSED_COBBLESTONE_4",
            Material.STONE,
            "&74x Compressed Cobblestone",
            "",
            "&86,561 cobblestone combined",
            ""
    );
    public static final SlimefunItemStack COMPRESSED_COBBLESTONE_5 = new SlimefunItemStack(
            "COMPRESSED_COBBLESTONE_5",
            Material.STONE,
            "&75x Compressed Cobblestone",
            "",
            "&859,049 cobblestone combined",
            ""
    );
    public static final SlimefunItemStack COMPRESSED_COBBLESTONE_6 = new SlimefunItemStack(
            "COMPRESSED_COBBLESTONE_6",
            Material.POLISHED_ANDESITE,
            "&76x Compressed Cobblestone",
            "",
            "&8531,441 cobblestone combined",
            ""
    );
    public static final SlimefunItemStack COMPRESSED_COBBLESTONE_7 = new SlimefunItemStack(
            "COMPRESSED_COBBLESTONE_7",
            Material.POLISHED_ANDESITE,
            "&77x Compressed Cobblestone",
            "",
            "&84,782,969 cobblestone combined",
            ""
    );
    public static final SlimefunItemStack COMPRESSED_COBBLESTONE_8 = new SlimefunItemStack(
            "COMPRESSED_COBBLESTONE_8",
            Material.OBSIDIAN,
            "&78x Compressed Cobblestone",
            "",
            "&843,046,721 cobblestone combined",
            ""
    );
    public static final SlimefunItemStack COMPRESSED_COBBLESTONE_9 = new SlimefunItemStack(
            "COMPRESSED_COBBLESTONE_9",
            Material.CRYING_OBSIDIAN,
            "&79x Compressed Cobblestone",
            "",
            "&8387,420,489 cobblestone combined",
            ""
    );

    //Cores

    public static final SlimefunItemStack MAGNESIUM_CORE = new SlimefunItemStack(
            "MAGNESIUM_CORE",
            Material.NETHER_BRICKS,
            "&dMagnesium Core",
            ""
    );
    public static final SlimefunItemStack COPPER_CORE = new SlimefunItemStack(
            "COPPER_CORE",
            Material.BRICKS,
            "&dCopper Core",
            ""
    );
    public static final SlimefunItemStack SILVER_CORE= new SlimefunItemStack(
            "SILVER_CORE",
            Material.IRON_BLOCK,
            "&dSilver Core",
            ""
    );
    public static final SlimefunItemStack TIN_CORE = new SlimefunItemStack(
            "TIN_CORE",
            Material.IRON_BLOCK,
            "&dTin Core",
            ""
    );
    public static final SlimefunItemStack LEAD_CORE = new SlimefunItemStack(
            "LEAD_CORE",
            Material.IRON_BLOCK,
            "&dLead Core",
            ""
    );
    public static final SlimefunItemStack ALUMINUM_CORE = new SlimefunItemStack(
            "ALUMINUM_CORE",
            Material.IRON_BLOCK,
            "&dAluminum Core",
            ""
    );
    public static final SlimefunItemStack ZINC_CORE = new SlimefunItemStack(
            "ZINC_CORE",
            Material.IRON_BLOCK,
            "&dZinc Core",
            ""
    );
    public static final SlimefunItemStack IRON_CORE = new SlimefunItemStack(
            "IRON_CORE",
            Material.IRON_BLOCK,
            "&dIron Core",
            ""
    );
    public static final SlimefunItemStack GOLD_CORE = new SlimefunItemStack(
            "GOLD_CORE",
            Material.GOLD_BLOCK,
            "&dGold Core",
            ""
    );
    public static final SlimefunItemStack NETHERITE_CORE = new SlimefunItemStack(
            "NETHERITE_CORE",
            Material.IRON_BLOCK,
            "&dNetherite Core",
            ""
    );
    public static final SlimefunItemStack EMERALD_CORE = new SlimefunItemStack(
            "EMERALD_CORE",
            Material.EMERALD_BLOCK,
            "&dEmerald Core",
            ""
    );
    public static final SlimefunItemStack DIAMOND_CORE = new SlimefunItemStack(
            "DIAMOND_CORE",
            Material.DIAMOND_BLOCK,
            "&dDiamond Core",
            ""
    );
    public static final SlimefunItemStack COAL_CORE = new SlimefunItemStack(
            "COAL_CORE",
            Material.COAL_BLOCK,
            "&dCoal Core",
            ""
    );
    public static final SlimefunItemStack REDSTONE_CORE = new SlimefunItemStack(
            "REDSTONE_CORE",
            Material.REDSTONE_BLOCK,
            "&dRedstone Core",
            ""
    );
    public static final SlimefunItemStack LAPIS_CORE = new SlimefunItemStack(
            "LAPIS_CORE",
            Material.LAPIS_BLOCK,
            "&dLapis Core",
            ""
    );

    //Ingots

    public static final SlimefunItemStack MAGNONIUM_INGOT = new SlimefunItemStack(
            "MAGNONIUM_INGOT",
            Material.NETHER_BRICK,
            "&dMagnonium Ingot",
            ""
    );
    public static final SlimefunItemStack INFINITY_INGOT = new SlimefunItemStack(
            "INFINITY_INGOT",
            Material.NETHERITE_INGOT,
            "&dInfinity Ingot",
            ""
    );

    //Materials

    public static final SlimefunItemStack VOID_DUST = new SlimefunItemStack(
            "VOID_DUST",
            Material.GUNPOWDER,
            "&5Void Dust",
            "",
            "&8From the depths of the end...",
            ""
    );

    public static final SlimefunItemStack MACHINE_PLATE = new SlimefunItemStack(
            "MACHINE_PLATE",
            Material.IRON_INGOT,
            "&5Machine Plate",
            ""
    );
    public static final SlimefunItemStack MACHINE_CIRCUIT = new SlimefunItemStack(
            "MACHINE_CIRCUIT",
            Material.GOLD_INGOT,
            "&5Machine Circuit",
            ""
    );
    public static final SlimefunItemStack INFINITE_MACHINE_CIRCUIT = new SlimefunItemStack(
            "INFINITE_MACHINE_CIRCUIT",
            Material.DIAMOND,
            "&5Infinite Machine Circuit",
            ""
    );
    public static final SlimefunItemStack MACHINE_CORE = new SlimefunItemStack(
            "MACHINE_CORE",
            Material.IRON_BLOCK,
            "&5Machine Core",
            ""
    );
    public static final SlimefunItemStack INFINITE_MACHINE_CORE = new SlimefunItemStack(
            "INFINITE_MACHINE_CORE",
            Material.DIAMOND_BLOCK,
            "&5Infinite Machine Core",
            ""
    );

    //Gear

    public static final SlimefunItemStack VOID_FLAME = new SlimefunItemStack(
            "VOID_FLAME",
            Material.ENCHANTED_BOOK,
            "&5Void Flame",
            ""
    );

    public static final SlimefunItemStack MAGNONIUM_CROWN = new SlimefunItemStack(
            "MAGNONIUM_CROWN",
            Material.NETHERITE_HELMET,
            "&dMagnonium Crown",
            ""
    );
    public static final SlimefunItemStack MAGNONIUM_CHESTPLATE = new SlimefunItemStack(
            "MAGNONIUM_CHESTPLATE",
            Material.NETHERITE_CHESTPLATE,
            "&dMagnonium Chestplate",
            ""
    );
    public static final SlimefunItemStack MAGNONIUM_LEGGINGS = new SlimefunItemStack(
            "MAGNONIUM_LEGGINGS",
            Material.NETHERITE_LEGGINGS,
            "&dMagnonium Leggings",
            ""
    );
    public static final SlimefunItemStack MAGNONIUM_BOOTS = new SlimefunItemStack(
            "MAGNONIUM_BOOTS",
            Material.NETHERITE_BOOTS,
            "&dMagnonium Boots",
            ""
    );
    public static final SlimefunItemStack MAGNONIUM_BLADE = new SlimefunItemStack(
            "MAGNONIUM_BLADE",
            Material.NETHERITE_SWORD,
            "&dMagnonium Blade",
            ""
    );
    public static final SlimefunItemStack MAGNONIUM_PICKAXE = new SlimefunItemStack(
            "MAGNONIUM_PICKAXE",
            Material.NETHERITE_PICKAXE,
            "&dMagnonium Pickaxe",
            ""
    );

    public static final SlimefunItemStack INFINITY_CROWN = new SlimefunItemStack(
            "INFINITY_CROWN",
            Material.NETHERITE_HELMET,
            "&cInfinity Crown",
            ""
    );
    public static final SlimefunItemStack INFINITY_CHESTPLATE = new SlimefunItemStack(
            "INFINITY_CHESTPLATE",
            Material.NETHERITE_CHESTPLATE,
            "&cInfinity Chestplate",
            ""
    );
    public static final SlimefunItemStack INFINITY_LEGGINGS = new SlimefunItemStack(
            "INFINITY_LEGGINGS",
            Material.NETHERITE_LEGGINGS,
            "&cInfinity Leggings",
            ""
    );
    public static final SlimefunItemStack INFINITY_BOOTS = new SlimefunItemStack(
            "INFINITY_BOOTS",
            Material.NETHERITE_BOOTS,
            "&cInfinity Boots",
            ""
    );
    public static final SlimefunItemStack INFINITY_BLADE = new SlimefunItemStack(
            "INFINITY_BLADE",
            Material.NETHERITE_SWORD,
            "&cInfinity Blade",
            ""
    );
    public static final SlimefunItemStack INFINITY_PICKAXE = new SlimefunItemStack(
            "INFINITY_PICKAXE",
            Material.NETHERITE_PICKAXE,
            "&cInfinity Pickaxe",
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