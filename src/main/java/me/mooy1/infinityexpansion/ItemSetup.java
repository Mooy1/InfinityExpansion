package me.mooy1.infinityexpansion;

import io.github.thebusybiscuit.slimefun4.implementation.items.electric.Capacitor;
import me.mooy1.infinityexpansion.Gear.InfinityGear;
import me.mooy1.infinityexpansion.Gear.MagnoniumGear;
import me.mooy1.infinityexpansion.Gear.VoidFlame;
import me.mooy1.infinityexpansion.Machines.*;
import me.mooy1.infinityexpansion.Materials.*;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public final class ItemSetup {

    private ItemSetup() { }

    public static void setup(@Nonnull InfinityExpansion plugin) {

        //Slimefun constructors

        new Capacitor(Items.MOOYMACHINES, 2147483647, Items.INFINITY_CAPACITOR, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.INFINITY_INGOT, Items.INFINITY_INGOT, Items.INFINITY_INGOT,
                Items.INFINITY_INGOT, Items.INFINITE_MACHINE_CORE, Items.INFINITY_INGOT,
                Items.INFINITY_INGOT, Items.INFINITY_INGOT, Items.INFINITY_INGOT,
        });

        //add machines

        new AdvancedEnchanter().register(plugin);
        new AdvancedDisenchanter().register(plugin);
        new InfinityEnchanter().register(plugin);
        new InfinityDisenchanter().register(plugin);
        new InfinityForge().register(plugin);

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

        for (MachineMaterials.Material material : MachineMaterials.Material.values()) {
            new MachineMaterials(material).register(plugin);
        }

        //add gear

        new VoidFlame().register(plugin);

        for (MagnoniumGear.MagnoniumTool magnoniumTool : MagnoniumGear.MagnoniumTool.values()) {
            new MagnoniumGear(magnoniumTool).register(plugin);
        }

        for (InfinityGear.InfinityTool infinityTool : InfinityGear.InfinityTool.values()) {
            new InfinityGear(infinityTool).register(plugin);
        }


        //add geominer recipe

        new VoidDust().register(plugin);
    }
}