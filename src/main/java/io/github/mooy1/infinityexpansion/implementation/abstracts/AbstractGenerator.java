package io.github.mooy1.infinityexpansion.implementation.abstracts;

import io.github.mooy1.infinitylib.slimefun.abstracts.TickingContainer;
import io.github.mooy1.infinitylib.slimefun.presets.MenuPreset;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetProvider;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNet;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public abstract class AbstractGenerator extends TickingContainer implements EnergyNetProvider {
    
    public AbstractGenerator(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
    }

    @Override
    public final void tick(@Nonnull BlockMenu inv, @Nonnull Block block, @Nonnull Config config) {
        if (inv.hasViewer() && !SlimefunPlugin.getNetworkManager().getNetworkFromLocation(block.getLocation(), EnergyNet.class).isPresent()) {
            inv.replaceExistingItem(getStatus(), MenuPreset.connectToEnergyNet);
        }
    }
    
    public abstract int getStatus();

}
