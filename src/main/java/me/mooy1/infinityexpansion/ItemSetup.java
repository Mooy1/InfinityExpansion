package me.mooy1.infinityexpansion;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.electric.Capacitor;
import me.mooy1.infinityexpansion.gear.InfinityGear;
import me.mooy1.infinityexpansion.gear.MagnoniumGear;
import me.mooy1.infinityexpansion.gear.VoidFlame;
import me.mooy1.infinityexpansion.machines.AdvancedDisenchanter;
import me.mooy1.infinityexpansion.machines.AdvancedEnchanter;
import me.mooy1.infinityexpansion.machines.InfinityDisenchanter;
import me.mooy1.infinityexpansion.machines.InfinityEnchanter;
import me.mooy1.infinityexpansion.machines.InfinityForge;
import me.mooy1.infinityexpansion.machines.InfinityPanel;
import me.mooy1.infinityexpansion.machines.Quarry;
import me.mooy1.infinityexpansion.materials.*;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public final class ItemSetup {

    private ItemSetup() { }

    public static void setup(@Nonnull InfinityExpansion plugin) {

        //add machines

        for (MachineMaterials.Material material : MachineMaterials.Material.values()) {
            new MachineMaterials(material).register(plugin);
        }

        for (Quarry.Tier tier : Quarry.Tier.values()) {
            new Quarry(tier).register(plugin);
        }

        new AdvancedEnchanter().register(plugin);
        new AdvancedDisenchanter().register(plugin);
        new InfinityForge().register(plugin);
        new InfinityEnchanter().register(plugin);
        new InfinityDisenchanter().register(plugin);

        for (InfinityPanel.Panel panel : InfinityPanel.Panel.values()) {
            new InfinityPanel(panel).register(plugin);
        }

        //add materials

        for (CompressedCobblestone.Compression compression : CompressedCobblestone.Compression.values()) {
            new CompressedCobblestone(compression).register(plugin);
        }

        for (Cores.Core core : Cores.Core.values()) {
            new Cores(core).register(plugin);
        }

        for (Ingots.Type type : Ingots.Type.values()) {
            new Ingots(type).register(plugin);
        }

        for (SFIngotBlocks.Block block: SFIngotBlocks.Block.values()) {
            new SFIngotBlocks(block).register(plugin);
        }

        //add gear

        for (InfinityGear.InfinityTool infinityTool : InfinityGear.InfinityTool.values()) {
            new InfinityGear(infinityTool).register(plugin);
        }

        new VoidFlame().register(plugin);

        for (MagnoniumGear.MagnoniumTool magnoniumTool : MagnoniumGear.MagnoniumTool.values()) {
            new MagnoniumGear(magnoniumTool).register(plugin);
        }

        //add geominer recipe

        new VoidDust().register(plugin);

        //Slimefun constructors

        new Capacitor(Categories.INFINITY_MACHINES, 1_600_000_000, Items.INFINITY_CAPACITOR,
            RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            Items.INFINITE_INGOT, Items.INFINITE_INGOT, Items.INFINITE_INGOT,
            Items.INFINITE_INGOT, SlimefunItems.ENERGIZED_CAPACITOR, Items.INFINITE_INGOT,
            Items.INFINITE_INGOT, Items.INFINITE_INGOT, Items.INFINITE_INGOT
        }).register(plugin);

    }
}