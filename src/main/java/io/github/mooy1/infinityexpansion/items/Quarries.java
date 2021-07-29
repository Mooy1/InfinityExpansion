package io.github.mooy1.infinityexpansion.items;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.experimental.UtilityClass;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.categories.Categories;
import io.github.mooy1.infinityexpansion.items.blocks.InfinityWorkbench;
import io.github.mooy1.infinityexpansion.items.quarries.Oscillator;
import io.github.mooy1.infinityexpansion.items.quarries.Quarry;
import io.github.mooy1.infinitylib.presets.LorePreset;
import io.github.thebusybiscuit.slimefun4.api.MinecraftVersion;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

@UtilityClass
public final class Quarries {

    public static final SlimefunItemStack BASIC_QUARRY = new SlimefunItemStack(
            "BASIC_QUARRY",
            Material.CHISELED_SANDSTONE,
            "&9Basic Quarry",
            "&7Automatically mines overworld ores",
            "",
            LorePreset.speed(1),
            LorePreset.energyPerSecond(300)
    );
    public static final SlimefunItemStack ADVANCED_QUARRY = new SlimefunItemStack(
            "ADVANCED_QUARRY",
            Material.CHISELED_RED_SANDSTONE,
            "&cAdvanced Quarry",
            "&7Automatically mines overworld and nether ores",
            "",
            LorePreset.speed(2),
            LorePreset.energyPerSecond(900)
    );
    public static final SlimefunItemStack VOID_QUARRY = new SlimefunItemStack(
            "VOID_QUARRY",
            Material.CHISELED_NETHER_BRICKS,
            "&8Void Quarry",
            "&7Automatically mines overworld and nether ores",
            "",
            LorePreset.speed(6),
            LorePreset.energyPerSecond(3600)
    );
    public static final SlimefunItemStack INFINITY_QUARRY = new SlimefunItemStack(
            "INFINITY_QUARRY",
            Material.CHISELED_POLISHED_BLACKSTONE,
            "&bInfinity Quarry",
            "&7Automatically mines overworld and nether ores",
            "",
            LorePreset.speed(64),
            LorePreset.energyPerSecond(36000)
    );

    public static void setup(InfinityExpansion plugin) {
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("quarry-options.resources");
        Objects.requireNonNull(section);
        List<Material> outputs = new ArrayList<>();

        boolean coal = section.getBoolean("coal");

        if (coal) {
            outputs.add(Material.COAL);
            outputs.add(Material.COAL);
        }

        if (section.getBoolean("iron")) {
            outputs.add(Material.IRON_INGOT);
        }

        if (section.getBoolean("gold")) {
            outputs.add(Material.GOLD_INGOT);
        }

        if (SlimefunPlugin.getMinecraftVersion().isAtLeast(MinecraftVersion.MINECRAFT_1_17) && section.getBoolean("copper")) {
            outputs.add(Material.COPPER_INGOT);
            outputs.add(Material.COPPER_INGOT);
        }

        if (section.getBoolean("redstone")) {
            new Oscillator(Material.REDSTONE).register(plugin);

            outputs.add(Material.REDSTONE);
        }

        if (section.getBoolean("lapis")) {
            new Oscillator(Material.LAPIS_LAZULI).register(plugin);

            outputs.add(Material.LAPIS_LAZULI);
        }

        if (section.getBoolean("emerald")) {
            new Oscillator(Material.EMERALD).register(plugin);

            outputs.add(Material.EMERALD);
        }

        if (section.getBoolean("diamond")) {
            new Oscillator(Material.DIAMOND).register(plugin);

            outputs.add(Material.DIAMOND);
        }

        new Quarry(Categories.ADVANCED_MACHINES, BASIC_QUARRY, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Materials.MAGSTEEL_PLATE, SlimefunItems.CARBONADO_EDGED_CAPACITOR, Materials.MAGSTEEL_PLATE,
                new ItemStack(Material.IRON_PICKAXE), SlimefunItems.GEO_MINER, new ItemStack(Material.IRON_PICKAXE),
                Materials.MACHINE_CIRCUIT, Materials.MACHINE_CORE, Materials.MACHINE_CIRCUIT
        }, 300, 1, 6, outputs.toArray(new Material[0])).register(plugin);

        if (section.getBoolean("quartz")) {
            new Oscillator(Material.QUARTZ).register(plugin);

            outputs.add(Material.QUARTZ);
        }

        if (section.getBoolean("netherite")) {
            outputs.add(Material.NETHERITE_INGOT);
        }

        if (section.getBoolean("netherrack")) {
            outputs.add(Material.NETHERRACK);
            outputs.add(Material.NETHERRACK);
        }

        new Quarry(Categories.ADVANCED_MACHINES, ADVANCED_QUARRY, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Materials.MACHINE_PLATE, SlimefunItems.ENERGIZED_CAPACITOR, Materials.MACHINE_PLATE,
                new ItemStack(Material.DIAMOND_PICKAXE), BASIC_QUARRY, new ItemStack(Material.DIAMOND_PICKAXE),
                Materials.MACHINE_CIRCUIT, Materials.MACHINE_CORE, Materials.MACHINE_CIRCUIT
        }, 900, 2, 4, outputs.toArray(new Material[0])).register(plugin);

        if (coal) {
            outputs.add(Material.COAL);
        }

        new Quarry(Categories.ADVANCED_MACHINES, VOID_QUARRY, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Materials.VOID_INGOT, SlimefunExtension.VOID_CAPACITOR, Materials.VOID_INGOT,
                new ItemStack(Material.NETHERITE_PICKAXE), ADVANCED_QUARRY, new ItemStack(Material.NETHERITE_PICKAXE),
                Materials.MACHINE_CIRCUIT, Materials.MACHINE_CORE, Materials.MACHINE_CIRCUIT
        }, 3600, 6, 2, outputs.toArray(new Material[0])).register(plugin);

        if (coal) {
            outputs.add(Material.COAL);
        }

        new Quarry(Categories.INFINITY_CHEAT, INFINITY_QUARRY, InfinityWorkbench.TYPE, new ItemStack[] {
                null, Materials.MACHINE_PLATE, Materials.MACHINE_PLATE, Materials.MACHINE_PLATE, Materials.MACHINE_PLATE, null,
                Materials.MACHINE_PLATE, Gear.PICKAXE, Materials.INFINITE_CIRCUIT, Materials.INFINITE_CIRCUIT, Gear.PICKAXE, Materials.MACHINE_PLATE,
                Materials.MACHINE_PLATE, VOID_QUARRY, Materials.INFINITE_CORE, Materials.INFINITE_CORE, VOID_QUARRY, Materials.MACHINE_PLATE,
                Materials.VOID_INGOT, null, Materials.INFINITE_INGOT, Materials.INFINITE_INGOT, null, Materials.VOID_INGOT,
                Materials.VOID_INGOT, null, Materials.INFINITE_INGOT, Materials.INFINITE_INGOT, null, Materials.VOID_INGOT,
                Materials.VOID_INGOT, null, Materials.INFINITE_INGOT, Materials.INFINITE_INGOT, null, Materials.VOID_INGOT
        }, 36000, 64, 1, outputs.toArray(new Material[0])).register(plugin);
    }

}
