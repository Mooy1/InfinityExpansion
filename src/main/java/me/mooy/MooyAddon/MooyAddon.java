package me.mooy.mooyaddon;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
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

        Config cfg = new Config(this);

        if (cfg.getBoolean("options.auto-update")) {
            // You could start an Auto-Updater for example
        }

        AddItems();

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

    public void AddItems() {

        ItemStack mainCategoryItem = new CustomItem(Material.EMERALD_BLOCK, "&4Mooy Addon", "", "&a> Click to open");
        NamespacedKey mainCategoryId = new NamespacedKey(this, "mooyaddon_main");
        Category main = new Category(mainCategoryId, mainCategoryItem);

        ItemStack material1 = new ItemStack(Material.COBBLESTONE);

        SlimefunItem COMPRESSED_COBBLESTONE_1 = new SlimefunItem(
                main,
                new SlimefunItemStack(
                        "COMPRESSED_COBBLESTONE_1",
                        Material.ANDESITE,
                        "&71x Compressed Cobblestone",
                        "",
                        "&89 cobblestone combined",
                        ""
                ),
                RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[]{
                        material1, material1, material1,
                        material1, material1, material1,
                        material1, material1, material1,
                }
        );

        ItemStack material2 = new ItemStack(Material.COBBLESTONE);

        SlimefunItem COMPRESSED_COBBLESTONE_2 = new SlimefunItem(
                main,
                new SlimefunItemStack(
                        "COMPRESSED_COBBLESTONE_2",
                        Material.ANDESITE,
                        "&72x Compressed Cobblestone",
                        "",
                        "&881 cobblestone combined",
                        ""
                ),
                RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[]{
                        material2, material2, material2,
                        material2, material2, material2,
                        material2, material2, material2,
                }
        );

        ItemStack material3 = new ItemStack(Material.COBBLESTONE);

        SlimefunItem COMPRESSED_COBBLESTONE_3 = new SlimefunItem(
                main,
                new SlimefunItemStack(
                        "COMPRESSED_COBBLESTONE_3",
                        Material.ANDESITE,
                        "&73x Compressed Cobblestone",
                        "",
                        "&8243 cobblestone combined",
                        ""
                ),
                RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[]{
                        material3, material3, material3,
                        material3, material3, material3,
                        material3, material3, material3,
                }
        );

        ItemStack material4 = new ItemStack(Material.COBBLESTONE);

        SlimefunItem COMPRESSED_COBBLESTONE_4 = new SlimefunItem(
                main,
                new SlimefunItemStack(
                        "COMPRESSED_COBBLESTONE_4",
                        Material.ANDESITE,
                        "&74x Compressed Cobblestone",
                        "",
                        "&86,561 cobblestone combined",
                        ""
                ),
                RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[]{
                        material4, material4, material4,
                        material4, material4, material4,
                        material4, material4, material4,
                }
        );

        ItemStack material5 = new ItemStack(Material.COBBLESTONE);

        SlimefunItem COMPRESSED_COBBLESTONE_5 = new SlimefunItem(
                main,
                new SlimefunItemStack(
                        "COMPRESSED_COBBLESTONE_5",
                        Material.ANDESITE,
                        "&75x Compressed Cobblestone",
                        "",
                        "&859,049 cobblestone combined",
                        ""
                ),
                RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[]{
                        material5, material5, material5,
                        material5, material5, material5,
                        material5, material5, material5,
                }
        );

        ItemStack material6 = new ItemStack(Material.COBBLESTONE);

        SlimefunItem COMPRESSED_COBBLESTONE_6 = new SlimefunItem(
                main,
                new SlimefunItemStack(
                        "COMPRESSED_COBBLESTONE_6",
                        Material.ANDESITE,
                        "&76x Compressed Cobblestone",
                        "",
                        "&8531,441 cobblestone combined",
                        ""
                ),
                RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[]{
                        material6, material6, material6,
                        material6, material6, material6,
                        material6, material6, material6,
                }
        );

        ItemStack material7 = new ItemStack(Material.COBBLESTONE);

        SlimefunItem COMPRESSED_COBBLESTONE_7 = new SlimefunItem(
                main,
                new SlimefunItemStack(
                        "COMPRESSED_COBBLESTONE_7",
                        Material.ANDESITE,
                        "&77x Compressed Cobblestone",
                        "",
                        "&84,782,969 cobblestone combined",
                        ""
                ),
                RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[]{
                        material7, material7, material7,
                        material7, material7, material7,
                        material7, material7, material7,
                }
        );

        ItemStack material8 = new ItemStack(Material.COBBLESTONE);

        SlimefunItem COMPRESSED_COBBLESTONE_8 = new SlimefunItem(
                main,
                new SlimefunItemStack(
                        "COMPRESSED_COBBLESTONE_8",
                        Material.ANDESITE,
                        "&78x Compressed Cobblestone",
                        "",
                        "&843,046,721 cobblestone combined",
                        ""
                ),
                RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[]{
                        material8, material8, material8,
                        material8, material8, material8,
                        material8, material8, material8,
                }
        );

        ItemStack material9 = new ItemStack(Material.COBBLESTONE);

        SlimefunItem COMPRESSED_COBBLESTONE_9 = new SlimefunItem(
                main,
                new SlimefunItemStack(
                        "COMPRESSED_COBBLESTONE_9",
                        Material.ANDESITE,
                        "&79x Compressed Cobblestone",
                        "",
                        "&8387,420,489 cobblestone combined",
                        ""
                ),
                RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[]{
                        material9, material9, material9,
                        material9, material9, material9,
                        material9, material9, material9,
                }
        );

        COMPRESSED_COBBLESTONE_1.register(this);
        COMPRESSED_COBBLESTONE_2.register(this);
        COMPRESSED_COBBLESTONE_3.register(this);
        COMPRESSED_COBBLESTONE_4.register(this);
        COMPRESSED_COBBLESTONE_5.register(this);
        COMPRESSED_COBBLESTONE_6.register(this);
        COMPRESSED_COBBLESTONE_7.register(this);
        COMPRESSED_COBBLESTONE_8.register(this);
        COMPRESSED_COBBLESTONE_9.register(this);

    }
}
