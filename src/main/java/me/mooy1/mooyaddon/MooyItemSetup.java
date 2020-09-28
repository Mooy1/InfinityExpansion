package me.mooy1.mooyaddon;

import me.mooy1.mooyaddon.Gear.InfinityGear;
import me.mooy1.mooyaddon.Gear.MagnoniumGear;
import me.mooy1.mooyaddon.Gear.VoidFlame;
import me.mooy1.mooyaddon.Machines.*;
import me.mooy1.mooyaddon.Materials.*;

import javax.annotation.Nonnull;

public final class MooyItemSetup {

    private MooyItemSetup() { }

    public static void setup(@Nonnull MooyAddon plugin) {

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