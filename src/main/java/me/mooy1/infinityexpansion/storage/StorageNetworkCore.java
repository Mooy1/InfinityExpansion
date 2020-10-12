package me.mooy1.infinityexpansion.storage;

import me.mooy1.infinityexpansion.Items;
import me.mooy1.infinityexpansion.setup.Categories;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.interfaces.InventoryBlock;
import org.bukkit.inventory.ItemStack;

public class StorageNetworkCore extends SlimefunItem implements InventoryBlock {
    public StorageNetworkCore() {
        super(Categories.INFINITY_STORAGE, Items.STORAGE_NETWORK_CORE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.MACHINE_PLATE, Items.MACHINE_CIRCUIT, Items.MACHINE_PLATE,
                Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT,
                Items.MACHINE_PLATE, Items.MACHINE_CIRCUIT, Items.MACHINE_PLATE,
        });
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
