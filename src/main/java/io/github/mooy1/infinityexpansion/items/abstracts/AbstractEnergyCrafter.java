package io.github.mooy1.infinityexpansion.items.abstracts;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinitylib.machines.TickingMenuBlock;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;

@ParametersAreNonnullByDefault
public abstract class AbstractEnergyCrafter extends TickingMenuBlock implements EnergyNetComponent {

    protected final int energy;
    protected final int statusSlot;

    public AbstractEnergyCrafter(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int energy, int statusSlot) {
        super(category, item, recipeType, recipe);
        this.energy = energy;
        this.statusSlot = statusSlot;
    }

    @Override
    protected final void tick(Block block, BlockMenu blockMenu) {
        if (blockMenu.hasViewer()) {
            int charge = getCharge(block.getLocation());
            if (charge < this.energy) { //not enough energy
                blockMenu.replaceExistingItem(this.statusSlot, new CustomItemStack(
                        Material.RED_STAINED_GLASS_PANE,
                        "&cNot enough energy!",
                        "",
                        "&aCharge: " + charge + "/" + this.energy + " J",
                        ""
                ));
            }
            else {
                update(blockMenu);
            }
        }
    }

    public abstract void update(BlockMenu blockMenu);

    @Nonnull
    @Override
    public final EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }

    @Override
    public final int getCapacity() {
        return this.energy;
    }

    @Override
    protected final int[] getInputSlots(DirtyChestMenu menu, ItemStack item) {
        return new int[0];
    }

}
