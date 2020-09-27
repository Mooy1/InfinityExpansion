package me.mooy1.mooyaddon;

import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;

public final class MooyItems {

    //Categories

    public static final Category MOOYMACHINES = new Category(new NamespacedKey(MooyAddon.getInstance(),
            "MOOYMACHINES"),
            new CustomItem(Material.BLAST_FURNACE, "&aMooy Addon")
    );
    public static final Category MOOYMATERIALS = new Category(new NamespacedKey(MooyAddon.getInstance(),
            "MOOYMATERIALS"),
            new CustomItem(Material.EMERALD_BLOCK, "&aMooy Addon")
    );
    public static final Category MOOYGEAR = new Category(new NamespacedKey(MooyAddon.getInstance(),
            "MOOYGEAR"),
            new CustomItem(Material.NETHERITE_CHESTPLATE, "&aMooy Addon")
    );

    //Machines

    public static final SlimefunItemStack INFINITY_AUTO_ENCHANTER = new SlimefunItemStack(
            "INFINITY_AUTO_ENCHANTER",
            Material.ENCHANTING_TABLE,
            "&cInfinity Auto Enchanter",
            ""

    );
    public static final SlimefunItemStack INFINITY_AUTO_DISENCHANTER = new SlimefunItemStack(
            "INFINITY_AUTO_DISENCHANTER",
            Material.ENCHANTING_TABLE,
            "&cInfinity Auto Disenchanter",
            ""

    );

    //Compressed Cobblestones

    public static final SlimefunItemStack COMPRESSED_COBBLESTONE_1 = new SlimefunItemStack(
            "COMPRESSED_COBBLESTONE_1",
            Material.ANDESITE,
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
            Material.STONE,
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
            Material.POLISHED_ANDESITE,
            "&75x Compressed Cobblestone",
            "",
            "&859,049 cobblestone combined",
            ""
    );
    public static final SlimefunItemStack COMPRESSED_COBBLESTONE_6 = new SlimefunItemStack(
            "COMPRESSED_COBBLESTONE_6",
            Material.COBBLESTONE,
            "&76x Compressed Cobblestone",
            "",
            "&8531,441 cobblestone combined",
            ""
    );
    public static final SlimefunItemStack COMPRESSED_COBBLESTONE_7 = new SlimefunItemStack(
            "COMPRESSED_COBBLESTONE_7",
            Material.COBBLESTONE,
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
            "IRON_CORE",
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
            Material.IRON_INGOT,
            "&dInfinity Ingot",
            ""
    );

    public static final SlimefunItemStack VOID_DUST = new SlimefunItemStack(
            "VOID_DUST",
            Material.GUNPOWDER,
            "&5Void Dust",
            "",
            "&8From the depths of the end...",
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

    //enchant list

    private static final Enchantment prot = Enchantment.PROTECTION_ENVIRONMENTAL;
    private static final Enchantment sharp = Enchantment.DAMAGE_ALL;
    private static final Enchantment eff = Enchantment.DIG_SPEED;
    private static final Enchantment unb = Enchantment.DURABILITY;
    private static final Enchantment flame = Enchantment.FIRE_ASPECT;

    //add enchants

    static {
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
        VOID_FLAME.addUnsafeEnchantment(flame, 10);
    }

    private MooyItems() { }
}