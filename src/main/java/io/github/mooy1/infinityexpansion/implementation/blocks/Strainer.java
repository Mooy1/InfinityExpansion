package io.github.mooy1.infinityexpansion.implementation.blocks;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.implementation.materials.SmelteryItem;
import io.github.mooy1.infinityexpansion.setup.categories.Categories;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nullable;

/**
 * Items to be used in the Strainer Base
 *
 * @author Mooy1
 */
public final class Strainer extends SlimefunItem implements NotPlaceable {
    
    public static void setup(InfinityExpansion plugin) {
        new Strainer(BASIC, new ItemStack[] {
                new ItemStack(Material.STICK), new ItemStack(Material.STRING), new ItemStack(Material.STICK),
                new ItemStack(Material.STRING), new ItemStack(Material.STICK), new ItemStack(Material.STRING),
                new ItemStack(Material.STICK), new ItemStack(Material.STRING), new ItemStack(Material.STICK),
        }, 1).register(plugin);
        new Strainer(ADVANCED, new ItemStack[] {
                SmelteryItem.MAGSTEEL, new ItemStack(Material.STRING), SmelteryItem.MAGSTEEL,
                new ItemStack(Material.STRING),BASIC, new ItemStack(Material.STRING),
                SmelteryItem.MAGSTEEL, new ItemStack(Material.STRING), SmelteryItem.MAGSTEEL
        }, 4).register(plugin);
        new Strainer(REINFORCED, new ItemStack[] {
                SlimefunItems.REINFORCED_ALLOY_INGOT, new ItemStack(Material.STRING), SlimefunItems.REINFORCED_ALLOY_INGOT,
                new ItemStack(Material.STRING), ADVANCED, new ItemStack(Material.STRING),
                SlimefunItems.REINFORCED_ALLOY_INGOT, new ItemStack(Material.STRING), SlimefunItems.REINFORCED_ALLOY_INGOT
        }, 20).register(plugin);
    }
    
    public static final SlimefunItemStack BASIC = new SlimefunItemStack(
            "BASIC_STRAINER",
            Material.FISHING_ROD,
            "&9Basic Strainer",
            "&7Collects materials from flowing water",
            "",
            LoreBuilder.speed(1)
    );
    public static final SlimefunItemStack ADVANCED = new SlimefunItemStack(
            "ADVANCED_STRAINER",
            Material.FISHING_ROD,
            "&cAdvanced Strainer",
            "&7Collects materials from flowing water",
            "",
            LoreBuilder.speed(4)
    );
    public static final SlimefunItemStack REINFORCED = new SlimefunItemStack(
            "REINFORCED_STRAINER",
            Material.FISHING_ROD,
            "&fReinforced Strainer",
            "&7Collects materials from flowing water",
            "",
            LoreBuilder.speed(20)
    );
    
    private static final NamespacedKey KEY = new NamespacedKey(InfinityExpansion.getInstance(), "strainer_speed");
    
    private Strainer(SlimefunItemStack item, ItemStack[] recipe, int speed) {
        super(Categories.BASIC_MACHINES, item, RecipeType.ENHANCED_CRAFTING_TABLE, recipe);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.getPersistentDataContainer().set(KEY, PersistentDataType.INTEGER, speed);
            item.setItemMeta(meta); 
        }
    }

    /**
     * This method gets the speed of strainer from an item
     *
     * @return speed
     */
    public static int getStrainer(@Nullable ItemStack item) {
        if (item != null) {
            ItemMeta meta = item.getItemMeta();

            if (meta != null) {
                return meta.getPersistentDataContainer().getOrDefault(Strainer.KEY, PersistentDataType.INTEGER, 0);
            }
        }

        return 0;
    }

}
