package io.github.mooy1.infinityexpansion.implementation.machines;

import javax.annotation.Nonnull;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinitylib.slimefun.abstracts.AbstractTicker;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

/**
 * A block that becomes bedrock when powered, for decoration of course
 *
 * @author Mooy1
 */
public final class PoweredBedrock extends AbstractTicker implements EnergyNetComponent {
    
    private final int energy;
    
    public PoweredBedrock(Category category, SlimefunItemStack item, RecipeType type, ItemStack[] recipe, int energy) {
        super (category, item, type, recipe);
        this.energy = energy;
    }
    
    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }

    @Override
    public int getCapacity() {
        return energy * 2;
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull Config config) {
        if ((InfinityExpansion.inst().getGlobalTick() & 3) == 0  || block.getType() == Material.AIR) {
            return;
        }
        Location l = block.getLocation();
        if (getCharge(l) < energy) {
            if (block.getType() != Material.NETHERITE_BLOCK) {
                block.setType(Material.NETHERITE_BLOCK);
            }
        } else {
            if (block.getType() != Material.BEDROCK) {
                block.setType(Material.BEDROCK);
            }
        }
        removeCharge(l, energy);
    }

    @Override
    protected boolean synchronised() {
        return true;
    }

}
