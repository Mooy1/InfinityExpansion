package io.github.mooy1.infinityexpansion.items;

import lombok.experimental.UtilityClass;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.categories.Categories;
import io.github.mooy1.infinityexpansion.items.blocks.AdvancedAnvil;
import io.github.mooy1.infinityexpansion.items.blocks.InfinityWorkbench;
import io.github.mooy1.infinityexpansion.items.blocks.StrainerBase;
import io.github.mooy1.infinitylib.presets.LorePreset;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

@UtilityClass
public final class Blocks {

    public static final SlimefunItemStack STRAINER_BASE = new SlimefunItemStack(
            "STRAINER_BASE",
            Material.SANDSTONE_WALL,
            "&7Strainer Base"
    );
    public static final SlimefunItemStack ADVANCED_ANVIL = new SlimefunItemStack(
            "ADVANCED_ANVIL",
            Material.SMITHING_TABLE,
            "&cAdvanced Anvil",
            "&7Combines tools and gear enchants and sometimes upgrades them",
            "&bWorks with Slimefun items",
            "",
            LorePreset.energy(100000) + "per use"
    );
    public static final SlimefunItemStack INFINITY_FORGE = new SlimefunItemStack(
            "INFINITY_FORGE",
            Material.RESPAWN_ANCHOR,
            "&6Infinity Workbench",
            "&7Used to craft infinity items",
            "",
            LorePreset.energy(10000000) + "per item"
    );
    
    public static void setup(InfinityExpansion plugin) {
        new StrainerBase(Categories.BASIC_MACHINES, STRAINER_BASE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                new ItemStack(Material.STICK), new ItemStack(Material.STRING), new ItemStack(Material.STICK),
                new ItemStack(Material.STICK), new ItemStack(Material.STRING), new ItemStack(Material.STICK),
                Materials.MAGSTEEL, Materials.MAGSTEEL, Materials.MAGSTEEL,
        }, 48).register(plugin);
        new AdvancedAnvil(Categories.MAIN_MATERIALS, ADVANCED_ANVIL, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Materials.MACHINE_PLATE, Materials.MACHINE_PLATE, Materials.MACHINE_PLATE,
                Materials.MACHINE_PLATE, new ItemStack(Material.ANVIL), Materials.MACHINE_PLATE,
                Materials.MACHINE_CIRCUIT, Materials.MACHINE_CORE, Materials.MACHINE_CIRCUIT
        }, 100000).register(plugin);
        new InfinityWorkbench(Categories.MAIN_MATERIALS, INFINITY_FORGE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Materials.VOID_INGOT, Materials.MACHINE_PLATE, Materials.VOID_INGOT,
                SlimefunItems.ENERGIZED_CAPACITOR, new ItemStack(Material.CRAFTING_TABLE), SlimefunItems.ENERGIZED_CAPACITOR,
                Materials.VOID_INGOT, Materials.MACHINE_PLATE, Materials.VOID_INGOT
        }, 10000000).register(plugin);
    }
    
}
