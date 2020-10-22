package me.mooy1.infinityexpansion.lists;

import me.mooy1.infinityexpansion.InfinityExpansion;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

public class RecipeTypes {

    public static final RecipeType INFINITY_WORKBENCH = new RecipeType(
            new NamespacedKey(InfinityExpansion.getInstance(), "infinity_workbench"), new CustomItem(
            Material.RESPAWN_ANCHOR,
            "&bInfinity &6Workbench",
            "&7Used to craft infinity items"
    ));
    public static final RecipeType VOID_HARVESTER = new RecipeType(
            new NamespacedKey(InfinityExpansion.getInstance(), "void_harvester"), new CustomItem(
            Material.OBSIDIAN,
            "&8Void &7Harvester",
            "&7Slowly harvests &8Void &7Bits from nothing..."
    ));
    public static final RecipeType SINGULARITY_CONSTRUCTOR = new RecipeType(
            new NamespacedKey(InfinityExpansion.getInstance(), "singularity_constructor"), new CustomItem(
            Material.QUARTZ_BRICKS,
            "&fSingularity &7Constructor",
            "&7Condenses large amounts of resources"
    ));
    public static final RecipeType STORAGE_FORGE = new RecipeType(
            new NamespacedKey(InfinityExpansion.getInstance(), "storage_forge"), Items.STORAGE_FORGE
    );

    private RecipeTypes() {}
}
