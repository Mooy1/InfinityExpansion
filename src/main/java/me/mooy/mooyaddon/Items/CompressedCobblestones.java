package me.mooy.mooyaddon.Items;

import java.io.File;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.blocks.UnplaceableBlock;
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

import me.mooy.mooyaddon.MooyItems;
import me.mooy.mooyaddon.MooyAddon;

public class CompressedCobblestones {

    public CompressedCobblestones() {
        super(MooyItems.main, MooyItems.COMPRESSED_COBBLESTONE_1, RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[]{
                        new ItemStack(Material.COBBLESTONE),
                        new ItemStack(Material.COBBLESTONE),
                        new ItemStack(Material.COBBLESTONE),
                        new ItemStack(Material.COBBLESTONE),
                        new ItemStack(Material.COBBLESTONE),
                        new ItemStack(Material.COBBLESTONE),
                        new ItemStack(Material.COBBLESTONE),
                        new ItemStack(Material.COBBLESTONE),
                        new ItemStack(Material.COBBLESTONE),
        });
    }
}
