package io.github.mooy1.infinityexpansion.implementation.machines;

import io.github.mooy1.infinityexpansion.implementation.blocks.InfinityWorkbench;
import io.github.mooy1.infinityexpansion.implementation.materials.CompressedItem;
import io.github.mooy1.infinityexpansion.implementation.materials.InfinityItem;
import io.github.mooy1.infinityexpansion.implementation.materials.MachineItem;
import io.github.mooy1.infinityexpansion.setup.categories.Categories;
import io.github.mooy1.infinitylib.presets.LorePreset;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * A block that becomes bedrock when powered, for decoration of course
 *
 * @author Mooy1
 */
public final class PoweredBedrock extends SlimefunItem implements EnergyNetComponent {

    public static final int ENERGY = 10_000;
    public static final SlimefunItemStack ITEM = new SlimefunItemStack(
            "POWERED_BEDROCK",
            Material.NETHERITE_BLOCK,
            "&4Powered Bedrock",
            "&7When powered, transforms into a bedrock",
            "&7Will revert once unpowered or broken",
            "",
            LorePreset.energyPerSecond(PoweredBedrock.ENERGY)
    );
    
    public PoweredBedrock() {
        super (Categories.INFINITY_CHEAT, ITEM, InfinityWorkbench.TYPE, new ItemStack[] {
                CompressedItem.COBBLE_5, CompressedItem.COBBLE_5, CompressedItem.COBBLE_5, CompressedItem.COBBLE_5, CompressedItem.COBBLE_5, CompressedItem.COBBLE_5,
                CompressedItem.COBBLE_5, MachineItem.MACHINE_PLATE, CompressedItem.VOID_INGOT, CompressedItem.VOID_INGOT, MachineItem.MACHINE_PLATE, CompressedItem.COBBLE_5,
                CompressedItem.COBBLE_5, CompressedItem.VOID_INGOT, SlimefunItems.ENERGIZED_CAPACITOR, SlimefunItems.ENERGIZED_CAPACITOR, CompressedItem.VOID_INGOT, CompressedItem.COBBLE_5,
                CompressedItem.COBBLE_5, CompressedItem.VOID_INGOT, InfinityItem.CORE, InfinityItem.CIRCUIT, CompressedItem.VOID_INGOT, CompressedItem.COBBLE_5,
                CompressedItem.COBBLE_5, MachineItem.MACHINE_PLATE, CompressedItem.VOID_INGOT, CompressedItem.VOID_INGOT, MachineItem.MACHINE_PLATE, CompressedItem.COBBLE_5,
                CompressedItem.COBBLE_5, CompressedItem.COBBLE_5, CompressedItem.COBBLE_5, CompressedItem.COBBLE_5, CompressedItem.COBBLE_5, CompressedItem.COBBLE_5
        });
    }

    @Override
    public void preRegister() {
        this.addItemHandler(new BlockTicker() {
            public void tick(Block b, SlimefunItem sf, Config data) { PoweredBedrock.this.tick(b); }

            public boolean isSynchronized() { return false; }
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

}
