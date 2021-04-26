package io.github.mooy1.infinityexpansion.implementation.generators;

import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.AllArgsConstructor;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinityexpansion.utils.Util;
import io.github.mooy1.infinitylib.slimefun.abstracts.AbstractContainer;
import io.github.mooy1.infinitylib.slimefun.presets.LorePreset;
import io.github.mooy1.infinitylib.slimefun.presets.MenuPreset;
import io.github.mooy1.infinitylib.slimefun.utils.TickerUtils;
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
    
    private final Type type;
    private final int generation;

    public EnergyGenerator(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int generation, Type type) {
        super(category, item, recipeType, recipe);
        this.type = type;
        this.generation = generation;
    }

    @Override
    public void setupMenu(@Nonnull BlockMenuPreset blockMenuPreset) {
        for (int i = 0 ; i < 9 ; i++) {
            blockMenuPreset.addItem(i, MenuPreset.borderItemStatus, ChestMenuUtils.getEmptyClickHandler());
        }
        blockMenuPreset.addItem(4, MenuPreset.loadingItemRed, ChestMenuUtils.getEmptyClickHandler());
    }

    @Nonnull
    @Override
    public int[] getTransportSlots(@Nonnull DirtyChestMenu dirtyChestMenu, @Nonnull ItemTransportFlow itemTransportFlow, ItemStack itemStack) {
        return new int[0];
    }

    @Override
    public int getGeneratedOutput(@Nonnull Location l, @Nonnull Config data) {
        @Nullable final BlockMenu inv = BlockStorage.getInventory(l);
        if (inv == null) return 0;

        Type type = getGeneration(l.getBlock(), Objects.requireNonNull(l.getWorld()));
        
        if (type == null) {
            if (inv.hasViewer()) {
                inv.replaceExistingItem(4, new CustomItem(
                        Material.GREEN_STAINED_GLASS_PANE,
                        "&cNot generating",
                        "&7Stored: &6" + LorePreset.format(getCharge(l)) + " J"
                ));
            }
            return 0;
        } else {
            int gen = type.more ? this.generation << 1 : this.generation;
            if (inv.hasViewer()) {
                inv.replaceExistingItem(4, new CustomItem(
                        Material.GREEN_STAINED_GLASS_PANE,
                        "&aGeneration",
                        "&7Type: &6" + type.status,
                        "&7Generating: &6" + LorePreset.format(gen * TickerUtils.TPS) + " J/s ",
                        "&7Stored: &6" + LorePreset.format(getCharge(l)) + " J"
                ));
            }
            return gen;
        }
    }

    private Type getGeneration(@Nonnull Block block, @Nonnull World world) {
        if (this.type == Type.WATER) {

            // don't check water log every tick
            if (Util.isWaterLogged(block)) {
                return Type.WATER;
            }

        } else if (this.type == Type.INFINITY) {

            return Type.INFINITY;

        } else if (world.getEnvironment() == World.Environment.NETHER) {

            if (this.type == Type.GEOTHERMAL) {
                return Type.NETHER;
            }

            if (this.type == Type.LUNAR) {
                return Type.LUNAR;
            }

        } else if (world.getEnvironment() == World.Environment.THE_END) {

            if (this.type == Type.LUNAR) {
                return Type.LUNAR;
            }

        } else if (world.getEnvironment() == World.Environment.NORMAL) {

            if (this.type == Type.GEOTHERMAL) {
                return Type.GEOTHERMAL;
            }

            if (world.getTime() >= 13000 || block.getLocation().add(0, 1, 0).getBlock().getLightFromSky() != 15) {

                if (this.type == Type.LUNAR) {
                    return Type.LUNAR;
                }

            } else if (this.type == Type.SOLAR) {
                return Type.SOLAR;
            }
        }

        return null;
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

    @AllArgsConstructor
    public enum Type {
        WATER("Hydroelectric", false),
        GEOTHERMAL("Geothermal", false),
        SOLAR("Day", false),
        LUNAR("Night", false),
        INFINITY("Infinity", false),
        
        NETHER("Nether (2x)", true);
        
        private final String status;
        private final boolean more;
        
    }
    
}
