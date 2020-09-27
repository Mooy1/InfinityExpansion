package me.mooy1.mooyaddon;

import me.mooy1.mooyaddon.Gear.InfinityGear;
import me.mooy1.mooyaddon.Gear.MagnoniumGear;
import me.mooy1.mooyaddon.Gear.VoidFlame;
import me.mooy1.mooyaddon.Machines.FastAutoDisenchanter;
import me.mooy1.mooyaddon.Machines.FastAutoEnchanter;
import me.mooy1.mooyaddon.Materials.*;

import javax.annotation.Nonnull;

public final class MooyItemSetup {

    private MooyItemSetup() { }

    public static void setup(@Nonnull MooyAddon plugin) {

        //add compressed cobblestones

        for (CompressedCobblestone.Compression compression : CompressedCobblestone.Compression.values()) {
            new CompressedCobblestone(compression).register(plugin);
        }

        //add machines

        new FastAutoEnchanter().register(plugin);
        new FastAutoDisenchanter().register(plugin);

        //add materials

        for (Cores.Core core : Cores.Core.values()) {
            new Cores(core).register(plugin);
        }

        for (Ingots.Type type : Ingots.Type.values()) {
            new Ingots(type).register(plugin);
        }

        //add gear

        new VoidFlame().register(plugin);

        for (MagnoniumGear.MagnoniumTool magnoniumTool : MagnoniumGear.MagnoniumTool.values()) {
            new MagnoniumGear(magnoniumTool).register(plugin);
        }


        //add geominer recipe

        new VoidDust().register(plugin);
    }
}