package me.mooy1.mooyaddon.Materials;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mooy1.mooyaddon.MooyItems;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.annotation.Nonnull;

public class MachineMaterials extends SlimefunItem{

    private final Material material;

    public MachineMaterials(Material material) {
        super(MooyItems.MOOYGEAR, material.getItem(), RecipeType.ENHANCED_CRAFTING_TABLE, material.getRecipe());
        this.material = material;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Material {

        CIRCUIT(MooyItems.MACHINE_CIRCUIT, new ItemStack[] {
                SlimefunItems.COPPER_INGOT, new ItemStack(org.bukkit.Material.GOLD_INGOT), SlimefunItems.COPPER_INGOT,
                SlimefunItems.COPPER_INGOT, SlimefunItems.BASIC_CIRCUIT_BOARD, SlimefunItems.COPPER_INGOT,
                SlimefunItems.COPPER_INGOT, new ItemStack(org.bukkit.Material.GOLD_INGOT), SlimefunItems.COPPER_INGOT
        }),
        CORE(MooyItems.MACHINE_CORE, new ItemStack[] {
                SlimefunItems.STEEL_PLATE, SlimefunItems.STEEL_PLATE, SlimefunItems.STEEL_PLATE,
                MooyItems.MACHINE_CIRCUIT, new ItemStack(org.bukkit.Material.IRON_BLOCK), MooyItems.MACHINE_CIRCUIT,
                SlimefunItems.STEEL_PLATE, SlimefunItems.STEEL_PLATE, SlimefunItems.STEEL_PLATE
        }),
        ICIRCUIT(MooyItems.INFINITE_MACHINE_CIRCUIT, new ItemStack[] {
                MooyItems.INFINITY_INGOT, SlimefunItems.GOLD_24K, MooyItems.INFINITY_INGOT,
                MooyItems.INFINITY_INGOT, MooyItems.MACHINE_CIRCUIT, MooyItems.INFINITY_INGOT,
                MooyItems.INFINITY_INGOT, SlimefunItems.GOLD_24K, MooyItems.INFINITY_INGOT
        }),
        ICORE(MooyItems.INFINITE_MACHINE_CORE, new ItemStack[] {
                SlimefunItems.REINFORCED_PLATE, new ItemStack(org.bukkit.Material.NETHER_STAR), SlimefunItems.REINFORCED_PLATE,
                MooyItems.INFINITE_MACHINE_CIRCUIT, MooyItems.MACHINE_CORE, MooyItems.INFINITE_MACHINE_CIRCUIT,
                SlimefunItems.REINFORCED_PLATE, new ItemStack(org.bukkit.Material.NETHER_STAR), SlimefunItems.REINFORCED_PLATE
        });

        @Nonnull
        private final SlimefunItemStack item;
        private final ItemStack[] recipe;
    }
}