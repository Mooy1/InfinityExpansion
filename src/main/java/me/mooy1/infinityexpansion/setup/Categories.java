package me.mooy1.infinityexpansion.setup;

import me.mooy1.infinityexpansion.InfinityExpansion;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

public final class Categories {

    public static final Category INFINITY_BASICS = new Category(new NamespacedKey(InfinityExpansion.getInstance(),
        "INFINITY_BASICS"),
        new CustomItem(Material.NETHER_STAR, "&b&lInfinity &7&lExpansion"), 2
    );

    public static final Category INFINITY_MACHINES = new Category(new NamespacedKey(InfinityExpansion.getInstance(),
        "INFINITY_MACHINES"),
        new CustomItem(Material.SMITHING_TABLE, "&b&lInfinity &7&lMachines"), 2
    );

    public static final Category INFINITY_MATERIALS = new Category(new NamespacedKey(InfinityExpansion.getInstance(),
        "INFINITY_MATERIALS"),
        new CustomItem(Material.NETHERITE_INGOT, "&b&lInfinity &a&lMaterials"), 2
    );

    public static final Category INFINITY_GEAR = new Category(new NamespacedKey(InfinityExpansion.getInstance(),
        "INFINITY_GEAR"),
        new CustomItem(Material.NETHERITE_CHESTPLATE, "&b&lInfinity &9&lGear"), 2
    );

    public static final Category HIDDEN_RECIPES = new HiddenCategory(new NamespacedKey(InfinityExpansion.getInstance(),
        "HIDDEN_RECIPES"),
        new CustomItem(Material.BARRIER, "&c( ͡° ͜ʖ ͡°)"),  10
    );

    private Categories() { }
}