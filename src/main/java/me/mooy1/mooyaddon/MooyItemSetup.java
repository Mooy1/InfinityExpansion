package me.mooy1.mooyaddon;

import me.mooy1.mooyaddon.Items.*;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.blocks.UnplaceableBlock;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

final class MooyItemSetup {

    protected static final MooyItemSetup INSTANCE = new MooyItemSetup();
    private final SlimefunAddon plugin = MooyAddon.getInstance();
    private boolean initialised;

    private MooyItemSetup() { }

    public void init() {
        if (initialised) return;

        initialised = true;

        registerCompressedCobbles();
        registerMagnoniumResource();
        registerMagnoniumTools();
        registerResource();
    }

    //add compressed cobblestones

    private void registerCompressedCobbles() {
        for (CompressedCobblestone.Compression compression : CompressedCobblestone.Compression.values()) {
            new CompressedCobblestone(compression).register(plugin);
        }
    }

    //add Magnonium resources

    private void registerMagnoniumResource() {
        for (MagnoniumResource.Type type : MagnoniumResource.Type.values()) {
            new MagnoniumResource(type).register(plugin);
        }
    }

    //add Magnonium tools

    private void registerMagnoniumTools() {
        for (MagnoniumTools.Tool tool : MagnoniumTools.Tool.values()) {
            new MagnoniumTools(tool).register(plugin);
        }
    }

    //add geominer resource

    private void registerResource() {
        new VoidDust().register(plugin);
    }
}