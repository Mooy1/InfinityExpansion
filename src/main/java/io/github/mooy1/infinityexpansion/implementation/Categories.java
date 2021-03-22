package io.github.mooy1.infinityexpansion.implementation;

import io.github.mooy1.infinitylib.core.PluginUtils;
import io.github.mooy1.infinitylib.slimefun.utils.MultiCategory;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

/**
 * Categories for this addon
 *
 * @author Mooy1
 */
public final class Categories {
    
    public static final Category INFINITY_CATEGORY = new InfinityCategory(PluginUtils.getKey("infinity_recipes"),
            new CustomItem(Material.RESPAWN_ANCHOR, "&bInfinity &7Recipes"), 3
    );
    public static final Category MAIN_MATERIALS = new Category(PluginUtils.getKey("main_materials"), 
            new CustomItem(Material.NETHER_STAR, "&bInfinity &7Materials")
    );
    public static final Category BASIC_MACHINES = new Category(PluginUtils.getKey("basic_machines"),
            new CustomItem(Material.LOOM, "&9Basic &7Machines")
    );
    public static final Category ADVANCED_MACHINES = new Category(PluginUtils.getKey("advanced_machines"),
            new CustomItem(Material.BLAST_FURNACE, "&cAdvanced &7Machines")
    );
    public static final Category STORAGE = new Category(PluginUtils.getKey("storage"),
            new CustomItem(Material.BEEHIVE, "&6Storage")
    );
    public static final Category MOB_SIMULATION = new Category(PluginUtils.getKey("mob_simulation"),
            new CustomItem(Material.BEACON, "&bMob Simulation")
    );
    public static final Category INFINITY_MATERIALS = new Category(PluginUtils.getKey("infinity_materials"),
            new CustomItem(Material.NETHERITE_BLOCK, "&bInfinity &aMaterials")
    );
    public static final MultiCategory MAIN_CATEGORY = new MultiCategory(PluginUtils.getKey("main"),
            new CustomItem(Material.NETHER_STAR, "&bInfinity &7Expansion"), 3,
            MAIN_MATERIALS, BASIC_MACHINES, ADVANCED_MACHINES, STORAGE, MOB_SIMULATION, INFINITY_MATERIALS, INFINITY_CATEGORY
    );
    
    public static final Category INFINITY_CHEAT = new Category(PluginUtils.getKey("infinity_cheat"),
            new CustomItem(Material.RESPAWN_ANCHOR, "&bInfinity &7Recipes &c- INCORRECT RECIPES")
    ) {
        @Override
        public boolean isHidden(@Nonnull Player p) {
            return true;
        }
    };
    
}