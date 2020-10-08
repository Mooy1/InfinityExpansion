package me.mooy1.infinityexpansion;

import me.mooy1.infinityexpansion.utils.LoreUtils;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

public final class Categories {

    public static final Category INFINITY_BASICS = new Category(new NamespacedKey(InfinityExpansion.getInstance(),
        "INFINITY_BASICS"),
        new CustomItem(Material.NETHER_STAR, "&b&lInfinity &7&lExpansion",
                "lore",
                "&7Version: &8" + InfinityExpansion.getInstance().getPluginVersion(),
                "&7Timings: &8" + LoreUtils.CUSTOM_TICKER_DELAY + " &7TPS: &8" + LoreUtils.roundHundreds(LoreUtils.SERVER_TICK_RATIO)
        ), 2
    );
    public static final Category INFINITY_MACHINES = new Category(new NamespacedKey(InfinityExpansion.getInstance(),
        "INFINITY_MACHINES"),
        new CustomItem(Material.SMITHING_TABLE, "&b&lInfinity &7&lMachines",
                "lore",
                "1",
                "2",
                "3",
                "4",
                "5",
                "6",
                "7"
        ), 2
    );
    public static final Category INFINITY_MATERIALS = new Category(new NamespacedKey(InfinityExpansion.getInstance(),
        "INFINITY_MATERIALS"),
        new CustomItem(Material.NETHERITE_INGOT,
                "&b&lInfinity &a&lMaterials"
        ), 2
    );
    public static final Category INFINITY_GEAR = new Category(new NamespacedKey(InfinityExpansion.getInstance(),
        "INFINITY_GEAR"),
        new CustomItem(Material.NETHERITE_CHESTPLATE,
                "&b&lInfinity &9&lGear"
        ), 2
    );

    private Categories() { }
}