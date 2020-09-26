package me.mooy.mooyaddon;

import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.cscorelib2.config.Config;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;

public class MooyAddon extends JavaPlugin implements SlimefunAddon {

    @Override
    public void onEnable() {
        // Read something from your config.yml
        Config cfg = new Config(this);

        if (cfg.getBoolean("options.auto-update")) {
            // You could start an Auto-Updater for example
        }

        ItemStack mainCategoryItem = new CustomItem(Material.EMERALD_BLOCK, "&4Mooy Addon", "", "&a> Click to open");
        NamespacedKey mainCategoryId = new NamespacedKey(this, "mooyaddon_main");
        Category main = new Category(mainCategoryId, mainCategoryItem);

        ItemStack cobble = new ItemStack(Material.COBBLESTONE);

        SlimefunItemStack compressedCobbleItem = new SlimefunItemStack(
                "COMPRESSED_COBBLESTONE_1",
                Material.ANDESITE,
                "&7Compressed Cobblestone",
                "",
                "&89 cobblestone in 1"
        );

        ItemStack[] compressedCobbleRecipe = {
                cobble, cobble, cobble,
                cobble, cobble, cobble,
                cobble, cobble, cobble
        };
        SlimefunItem compressedCobble = new SlimefunItem(
                main,
                compressedCobbleItem,
                RecipeType.ENHANCED_CRAFTING_TABLE,
                compressedCobbleRecipe
        );
        compressedCobble.register(this);
    }

    @Override
    public void onDisable() {
        // Logic for disabling the plugin...
    }

    @Override
    public String getBugTrackerURL() {
        return "https://github.com/Mooy1/MooyAddon/issues";
    }

    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

}
