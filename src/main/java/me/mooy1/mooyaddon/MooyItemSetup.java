package me.mooy1.mooyaddon;

import me.mooy1.mooyaddon.Items.*;

import javax.annotation.Nonnull;

public final class MooyItemSetup {

    private MooyItemSetup() { }

    public static void setup(@Nonnull MooyAddon plugin) {
        //add compressed cobblestones

        for (CompressedCobblestone.Compression compression : CompressedCobblestone.Compression.values()) {
            new CompressedCobblestone(compression).register(plugin);
        }

        //add Magnonium resources

        for (MagnoniumResource.Type type : MagnoniumResource.Type.values()) {
            new MagnoniumResource(type).register(plugin);
        }

        //add Magnonium tools

        for (MagnoniumTools.Tool tool : MagnoniumTools.Tool.values()) {
            new MagnoniumTools(tool).register(plugin);
        }

        //add geominer resource

        new VoidDust().register(plugin);
    }
}