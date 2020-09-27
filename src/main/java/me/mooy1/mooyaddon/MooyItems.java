package me.mooy1.mooyaddon;

import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;

public final class MooyItems {

    //Categories

    public static final Category MOOYMAIN = new Category(new NamespacedKey(MooyAddon.getInstance(),
            "MOOYMAIN"),
            new CustomItem(Material.EMERALD_BLOCK, "&aMooy Addon")
    );

    //Machines

    public static final SlimefunItemStack FAST_AUTO_ENCHANTER = new SlimefunItemStack(
            "FAST_AUTO_ENCHANTER",
            Material.ENCHANTING_TABLE,
            "&cUltimate Auto Enchanter",
            ""

    );
    public static final SlimefunItemStack FAST_AUTO_DISENCHANTER = new SlimefunItemStack(
            "FAST_AUTO_DISENCHANTER",
            Material.ENCHANTING_TABLE,
            "&cUltimate Auto Disenchanter",
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

    //Magnonium

    public static final SlimefunItemStack MAGNESIUM_BLOCK = new SlimefunItemStack(
            "MAGNESIUM_BLOCK",
            Material.NETHER_BRICKS,
            "&dMagnesium Block",
            ""
    );
    public static final SlimefunItemStack MAGNONIUM_ALLOY = new SlimefunItemStack(
            "MAGNONIUM_ALLOY",
            Material.NETHER_BRICK,
            "&dMagnonium Alloy",
            ""
    );

    //Void items

    public static final SlimefunItemStack VOID_DUST = new SlimefunItemStack(
            "VOID_DUST",
            Material.GUNPOWDER,
            "&5Void Dust",
            "",
            "&8From the depths of the end...",
            ""
    );
    public static final SlimefunItemStack VOID_FLAME = new SlimefunItemStack(
            "VOID_FLAME",
            Material.ENCHANTED_BOOK,
            "&5Void Flame",
            ""
    );

    //Magnonium me.mooy1.mooyaddon.Items.Gear

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