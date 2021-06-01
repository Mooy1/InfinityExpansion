package io.github.mooy1.infinityexpansion.items.generators;

import java.util.Objects;

import javax.annotation.Nonnull;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinitylib.presets.LorePreset;
import io.github.mooy1.infinitylib.presets.MenuPreset;
import io.github.mooy1.infinitylib.slimefun.AbstractContainer;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetProvider;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;

/**
 * Solar panels and some other basic generators
 *
 * @author Mooy1
 *
 * Thanks to panda for some stuff to work off of
 */
public final class EnergyGenerator extends AbstractContainer implements EnergyNetProvider {
    
    private final GenerationType type;
    private final int generation;

    public EnergyGenerator(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                           int generation, GenerationType type) {
        super(category, item, recipeType, recipe);
        this.type = type;
        this.generation = generation;
    }

    @Override
    public void setupMenu(@Nonnull BlockMenuPreset blockMenuPreset) {
        for (int i = 0 ; i < 9 ; i++) {
            blockMenuPreset.addItem(i, MenuPreset.STATUS_ITEM, ChestMenuUtils.getEmptyClickHandler());
        }
        blockMenuPreset.addItem(4, MenuPreset.LOADING, ChestMenuUtils.getEmptyClickHandler());
    }

    @Nonnull
    @Override
    public int[] getTransportSlots(@Nonnull DirtyChestMenu dirtyChestMenu, @Nonnull ItemTransportFlow itemTransportFlow, ItemStack itemStack) {
        return new int[0];
    }

    @Override
    public int getGeneratedOutput(@Nonnull Location l, @Nonnull Config data) {
        BlockMenu inv = BlockStorage.getInventory(l);

        int gen = this.type.generate(Objects.requireNonNull(l.getWorld()), l.getBlock(), this.generation);

        if (inv.hasViewer()) {
            if (gen == 0) {
                inv.replaceExistingItem(4, new CustomItem(
                        Material.GREEN_STAINED_GLASS_PANE,
                        "&cNot generating",
                        "&7Stored: &6" + LorePreset.format(getCharge(l)) + " J"
                ));
            } else {
                inv.replaceExistingItem(4, new CustomItem(
                        Material.GREEN_STAINED_GLASS_PANE,
                        "&aGeneration",
                        "&7Type: &6" + this.type.getName(),
                        "&7Generating: &6" + LorePreset.formatEnergy(gen) + " J/s ",
                        "&7Stored: &6" + LorePreset.format(getCharge(l)) + " J"
                ));
            }
        }

        return gen;
    }

    @Override
    public int getCapacity() {
        return this.generation * 100;
    }

    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.GENERATOR;
    }
    
}
