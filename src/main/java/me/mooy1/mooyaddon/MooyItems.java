package me.mooy1.mooyaddon;

import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

public final class MooyItems {

    //Categories

    public static final Category MAIN = new Category(new NamespacedKey(MooyAddon.getInstance(),
            "mooyaddon_main"),
            new CustomItem(Material.EMERALD_BLOCK, "&aMooy Addon")
    );

    //Compressed Cobblestones

    public static final SlimefunItemStack COMPRESSED_COBBLESTONE_1 = new SlimefunItemStack(
            "COMPRESSED_COBBLESTONE_1",
            Material.STONE,
            "&71x Compressed Cobblestone",
            "",
            "&89 cobblestone combined",
            ""
    );
    public static final SlimefunItemStack COMPRESSED_COBBLESTONE_2 = new SlimefunItemStack(
            "COMPRESSED_COBBLESTONE_2",
            Material.COBBLESTONE,
            "&72x Compressed Cobblestone",
            "",
            "&881 cobblestone combined",
            ""
    );
    public static final SlimefunItemStack COMPRESSED_COBBLESTONE_3 = new SlimefunItemStack(
            "COMPRESSED_COBBLESTONE_3",
            Material.GRAVEL,
            "&73x Compressed Cobblestone",
            "",
            "&8243 cobblestone combined",
            ""
    );
    public static final SlimefunItemStack COMPRESSED_COBBLESTONE_4 = new SlimefunItemStack(
            "COMPRESSED_COBBLESTONE_4",
            Material.ANDESITE,
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
            Material.GRAY_GLAZED_TERRACOTTA,
            "&76x Compressed Cobblestone",
            "",
            "&8531,441 cobblestone combined",
            ""
    );
    public static final SlimefunItemStack COMPRESSED_COBBLESTONE_7 = new SlimefunItemStack(
            "COMPRESSED_COBBLESTONE_7",
            Material.OBSIDIAN,
            "&77x Compressed Cobblestone",
            "",
            "&84,782,969 cobblestone combined",
            ""
    );
    public static final SlimefunItemStack COMPRESSED_COBBLESTONE_8 = new SlimefunItemStack(
            "COMPRESSED_COBBLESTONE_8",
            Material.CRYING_OBSIDIAN,
            "&78x Compressed Cobblestone",
            "",
            "&843,046,721 cobblestone combined",
            ""
    );
    public static final SlimefunItemStack COMPRESSED_COBBLESTONE_9 = new SlimefunItemStack(
            "COMPRESSED_COBBLESTONE_9",
            Material.PURPLE_GLAZED_TERRACOTTA,
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
    public static final SlimefunItemStack MAGNESIUM_CORE = new SlimefunItemStack(
            "MAGNESIUM_CORE",
            Material.NETHER_BRICKS,
            "&dMagnesium Core",
            "",
            "&7Lots and Lots of Magnesium",
            ""
    );
    public static final SlimefunItemStack MAGNONIUM_ALLOY = new SlimefunItemStack(
            "MAGNONIUM_ALLOY",
            Material.NETHER_BRICK,
            "&dMagnonium Alloy",
            ""
    );

    //Void

    public static final SlimefunItemStack VOID_DUST = new SlimefunItemStack(
            "VOID_DUST",
            Material.GUNPOWDER,
            "&5Void Dust",
            "",
            "&8From the depths of the end...",
            ""
    );

    //Magnonium Gear

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

    private MooyItems() { }
}