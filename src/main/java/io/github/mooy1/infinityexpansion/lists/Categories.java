package io.github.mooy1.infinityexpansion.lists;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.setup.HiddenCategory;
import io.github.mooy1.infinityexpansion.setup.InfinityCategory;
import io.github.mooy1.infinityexpansion.setup.MainCategory;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

/**
 * Categories for this addon
 *
 * @author Mooy1
 */
public final class Categories {
    
    private static final InfinityExpansion instance = InfinityExpansion.getInstance();
    
    public static final MainCategory MAIN = new MainCategory(new NamespacedKey(instance, "main"), 
            new CustomItem(Material.NETHER_STAR, "&bInfinity &7Expansion"), -1
    );

    public static final Category MAIN_MATERIALS = new HiddenCategory(new NamespacedKey(instance, "main_materials"),
            new CustomItem(Material.NETHER_STAR, "&bInfinity &7Expansion"), 2
    );

    public static final Category BASIC_MACHINES = new HiddenCategory(new NamespacedKey(instance, "basic_machines"),
            new CustomItem(Material.LOOM, "&9Basic &7Powered Machines"), 2
    );

    public static final Category ADVANCED_MACHINES = new HiddenCategory(new NamespacedKey(instance, "advanced_machines"),
            new CustomItem(Material.BLAST_FURNACE, "&cAdvanced &7Powered Machines"), 2
    );

    public static final Category STORAGE_TRANSPORT = new HiddenCategory(new NamespacedKey(instance, "storage_transport"),
            new CustomItem(Material.ENDER_CHEST, "&6Storage and Transport"), 2
    );
    
    public static final Category MOB_SIMULATION = new HiddenCategory(new NamespacedKey(instance, "mob_simulation"),
            new CustomItem(Material.SPAWNER, "&bMob Simulation"), 2
    );
    
    public static final Category INFINITY_MATERIALS = new HiddenCategory(new NamespacedKey(instance, "infinity_materials"),
            new CustomItem(Material.NETHERITE_BLOCK, "&bInfinity &aMaterials"), 2
    );

    public static final Category INFINITY_RECIPES = new InfinityCategory(new NamespacedKey(instance, "infinity_recipes"),
            new CustomItem(Material.SMITHING_TABLE, "&bInfinity &7Recipes"), 2
    );
    
    public static final Category INFINITY_CHEAT = new HiddenCategory(new NamespacedKey(instance, "infinity_cheat"),
            new CustomItem(Material.SMITHING_TABLE, "&bInfinity &7Recipes &c- NOT REAL RECIPES"), 2
    );
    
}