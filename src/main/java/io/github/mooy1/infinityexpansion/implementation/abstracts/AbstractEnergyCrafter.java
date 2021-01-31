package io.github.mooy1.infinityexpansion.implementation.abstracts;

import io.github.mooy1.infinitylib.abstracts.AbstractTicker;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public abstract class AbstractEnergyCrafter extends AbstractTicker implements EnergyNetComponent {
    
    protected final int energy;
    protected final int statusSlot;
    
    public AbstractEnergyCrafter(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int energy, int statusSlot) {
        super(category, item, recipeType, recipe);
        this.energy = energy;
        this.statusSlot = statusSlot;
    }
    
    public abstract void update(@Nonnull BlockMenu blockMenu);
    
    @Override
    public final void tick(@Nonnull BlockMenu blockMenu, @Nonnull Block block, @Nonnull Config config) {
        if (blockMenu.hasViewer()) {
            int charge = getCharge(block.getLocation());
            if (charge < this.energy) { //not enough energy
                blockMenu.replaceExistingItem(this.statusSlot, new CustomItem(
                        Material.RED_STAINED_GLASS_PANE,
                        "&cNot enough energy!",
                        "",
                        "&aCharge: " + charge + "/" + this.energy + " J",
                        ""
                ));
            } else {
                update(blockMenu);
            }
        }
    }

    @Nonnull
    @Override
    public final EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }

    @Override
    public final int getCapacity() {
        return this.energy;
    }

    @Nonnull
    @Override
    protected final int[] getTransportSlots(@Nonnull DirtyChestMenu menu, @Nonnull ItemTransportFlow flow, ItemStack item) {
        return new int[0];
    }

}
