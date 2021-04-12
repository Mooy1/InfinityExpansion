package io.github.mooy1.infinityexpansion.implementation.abstracts;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinitylib.slimefun.abstracts.TickingContainer;
import io.github.mooy1.infinitylib.slimefun.presets.MenuPreset;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;

/**
 * A slimefun item with a menu and ticker, which will process if it has enough energy
 * 
 * @author Mooy1
 */
public abstract class AbstractMachine extends TickingContainer implements EnergyNetComponent {

    public AbstractMachine(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
    }
    
    protected abstract boolean process(@Nonnull BlockMenu menu, @Nonnull Block b, @Nonnull Config data);

    @Override
    protected final void tick(@Nonnull BlockMenu menu, @Nonnull Block b, @Nonnull Config data) {
        if (getCharge(b.getLocation()) < getEnergyConsumption()) {
            if (menu.hasViewer()) {
                menu.replaceExistingItem(getStatusSlot(), MenuPreset.notEnoughEnergy);
            }
        } else if (process(menu, b, data)) {
            removeCharge(b.getLocation(), getEnergyConsumption());
        }
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    protected void setupMenu(@Nonnull BlockMenuPreset preset) {
        preset.addItem(getStatusSlot(), MenuPreset.loadingItemRed, ChestMenuUtils.getEmptyClickHandler());
    }
    
    protected abstract int getStatusSlot();
    
    protected abstract int getEnergyConsumption();
    
    @Override
    public int getCapacity() {
        return getEnergyConsumption() * 2;
    }

    @Nonnull
    @Override
    public final EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }
    
}
