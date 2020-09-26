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

import javax.print.attribute.standard.Compression;

public class CompressedCobblestone extends SlimefunItem {

    private final Compression compression;

    public CompressedCobblestone(Compression compression) {
        super(MooyItems.main,
                MooyItems.COMPRESSED_COBBLESTONE_1,
                RecipeType.ENHANCED_CRAFTING_TABLE,
                compression.recipe);
        this.compression = compression;
    }

    public static ItemStack cobble = new ItemStack(Material.COBBLESTONE);
    public static ItemStack cobble1 = MooyItems.COMPRESSED_COBBLESTONE_1;
    public static ItemStack cobble2 = MooyItems.COMPRESSED_COBBLESTONE_2;
    public static ItemStack cobble3 = MooyItems.COMPRESSED_COBBLESTONE_3;
    public static ItemStack cobble4 = MooyItems.COMPRESSED_COBBLESTONE_4;
    public static ItemStack cobble5 = MooyItems.COMPRESSED_COBBLESTONE_5;
    public static ItemStack cobble6 = MooyItems.COMPRESSED_COBBLESTONE_6;
    public static ItemStack cobble7 = MooyItems.COMPRESSED_COBBLESTONE_7;
    public static ItemStack cobble8 = MooyItems.COMPRESSED_COBBLESTONE_8;

    public enum Compression {
        ONE(new ItemStack[] {
                SlimefunItems.GILDED_IRON, SlimefunItems.MAGNESIUM_INGOT, SlimefunItems.GILDED_IRON,
                SlimefunItems.ELECTRIC_MOTOR, SlimefunItems.COMPOSTER, SlimefunItems.ELECTRIC_MOTOR,
                new ItemStack(Material.IRON_HOE), SlimefunItems.MEDIUM_CAPACITOR, new ItemStack(Material.IRON_HOE)}
        ),
        TWO(new ItemStack[] {SlimefunItems.HARDENED_METAL_INGOT, SlimefunItems.BLISTERING_INGOT_3, SlimefunItems.HARDENED_METAL_INGOT,
                SlimefunItems.ELECTRIC_MOTOR, cobble, SlimefunItems.ELECTRIC_MOTOR, cobble,
                new ItemStack(Material.DIAMOND_HOE), SlimefunItems.LARGE_CAPACITOR, new ItemStack(Material.DIAMOND_HOE)}
        );

        private final ItemStack[] recipe;

        Compression(ItemStack[] recipe) {
            this.recipe = recipe;
        }
    }
}