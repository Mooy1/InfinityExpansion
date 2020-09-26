package me.mooy.mooyaddon;

import java.io.File;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.blocks.UnplaceableBlock;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.cscorelib2.config.Config;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;

import me.mooy.mooyaddon.MooyItems;
import me.mooy.mooyaddon.Items.*;

public class MooyAddon extends JavaPlugin implements SlimefunAddon {

    public static MooyAddon instance;

    @Override
    public void onEnable() {

        Config cfg = new Config(this);

        if (cfg.getBoolean("options.auto-update")) {
            // You could start an Auto-Updater for example
        }

        RegisterItems();

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

    public void RegisterItems() {

        new CompressedCobblestone(CompressedCobblestone.Compression.ONE).register(this);
        new CompressedCobblestone(CompressedCobblestone.Compression.TWO).register(this);
        new CompressedCobblestone(CompressedCobblestone.Compression.THREE).register(this);
        new CompressedCobblestone(CompressedCobblestone.Compression.FOUR).register(this);
        new CompressedCobblestone(CompressedCobblestone.Compression.FIVE).register(this);
        new CompressedCobblestone(CompressedCobblestone.Compression.SIX).register(this);
        new CompressedCobblestone(CompressedCobblestone.Compression.SEVEN).register(this);
        new CompressedCobblestone(CompressedCobblestone.Compression.EIGHT).register(this);
        new CompressedCobblestone(CompressedCobblestone.Compression.NINE).register(this);

    }

    public static MooyAddon getInstance() {
        return instance;
    }
}
