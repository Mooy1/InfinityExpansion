package me.mooy1.infinityexpansion.machines;

import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import me.mooy1.infinityexpansion.Categories;
import me.mooy1.infinityexpansion.Items;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.interfaces.InventoryBlock;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class PoweredBedrock extends SlimefunItem implements EnergyNetComponent, InventoryBlock {

    public static final int ENERGY = 60_000;

    public PoweredBedrock() {
        super (Categories.INFINITY_MACHINES, Items.POWERED_BEDROCK, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.COMPRESSED_COBBLESTONE_7, Items.VOID_INGOT, Items.COMPRESSED_COBBLESTONE_7,
                Items.VOID_INGOT, Items.INFINITE_MACHINE_CIRCUIT, Items.VOID_INGOT,
                Items.COMPRESSED_COBBLESTONE_7, Items.VOID_INGOT,Items.COMPRESSED_COBBLESTONE_7
        });
    }

    @Override
    public void preRegister() {
        this.addItemHandler(new BlockTicker() {
            public void tick(Block b, SlimefunItem sf, Config data) { PoweredBedrock.this.tick(b); }

            public boolean isSynchronized() { return true; }
        });
    }

    public void tick(Block b) {
        if (b.getType() == Material.AIR) {
            return;
        }
        Location l = b.getLocation();

        if (getCharge(l) >= ENERGY) {
            b.setType(Material.BEDROCK);
        } else {
            b.setType(Material.NETHERITE_BLOCK);
        }
        removeCharge(l, ENERGY);
    }

    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }

    @Override
    public int getCapacity() {
        return ENERGY * 2;
    }

    @Override
    public int[] getInputSlots() {
        return new int[0];
    }

    @Override
    public int[] getOutputSlots() {
        return new int[0];
    }
}