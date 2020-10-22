package me.mooy1.infinityexpansion.lists;

import me.mooy1.infinityexpansion.InfinityExpansion;
import me.mooy1.infinityexpansion.setup.CheatCategory;
import me.mooy1.infinityexpansion.setup.InfinityCategory;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

public final class Categories {

    public static final Category BASIC_MACHINES = new Category(new NamespacedKey(InfinityExpansion.getInstance(),
            "BASIC_MACHINES"),
            new CustomItem(Material.LOOM, "&9Basic &7Powered Machines"), 1
    );

    public static final Category INFINITY_MAIN = new Category(new NamespacedKey(InfinityExpansion.getInstance(),
            "INFINITY_MAIN"),
            new CustomItem(Material.NETHER_STAR, "&bInfinity &7Expansion"), 2
    );

    public static final Category ADVANCED_MACHINES = new Category(new NamespacedKey(InfinityExpansion.getInstance(),
            "ADVANCED_MACHINES"),
            new CustomItem(Material.BLAST_FURNACE, "&cAdvanced &7Powered Machines"), 2
    );

    public static final Category STORAGE_TRANSPORT = new Category(new NamespacedKey(InfinityExpansion.getInstance(),
            "STORAGE_TRANSPORT"),
            new CustomItem(Material.ENDER_CHEST, "&6Item Storage and Transport"), 2
    );

    public static final Category INFINITY_MATERIALS = new Category(new NamespacedKey(InfinityExpansion.getInstance(),
            "INFINITY_MATERIALS"),
            new CustomItem(Material.NETHERITE_BLOCK, "&bInfinity &aMaterials"), 3
    );

    public static final Category INFINITY_RECIPES = new InfinityCategory();
    public static final Category INFINITY_CHEAT = new CheatCategory();

    private Categories() { }
}