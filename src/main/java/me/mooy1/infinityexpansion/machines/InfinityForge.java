package me.mooy1.infinityexpansion.machines;

import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mooy1.infinityexpansion.setup.Categories;
import me.mooy1.infinityexpansion.InfinityExpansion;
import me.mooy1.infinityexpansion.Items;
import me.mooy1.infinityexpansion.utils.PresetUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.interfaces.InventoryBlock;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class InfinityForge extends SlimefunItem implements InventoryBlock, EnergyNetComponent {

    public static int ENERGY = 10_000_000;

    private final int[] INPUT_SLOTS = {
        0, 1, 2, 3, 4, 5,
        9, 10, 11, 12, 13, 14,
        18, 19, 20, 21, 22, 23,
        27, 28, 29, 30, 31, 32,
        36, 37, 38, 39, 40, 41,
        45, 46, 47, 48, 49, 50
    };

    private final int[] OUTPUT_SLOTS = {
        43
    };
    private final int[] OUTPUT_BORDER = {
            33, 34, 35,
            42,     44,
            51, 52, 53
    };

    private final int STATUS_SLOT = 25;
    private final int[] STATUS_BORDER = {
            6,     8,
            15, 16,17,
            24,    26,
    };
    private final int RECIPE_SLOT = 7;

    public InfinityForge() {
        super(Categories.INFINITY_MACHINES, Items.INFINITY_FORGE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            Items.VOID_INGOT, Items.VOID_INGOT, Items.VOID_INGOT,
                SlimefunItems.ENERGIZED_CAPACITOR, Items.INGOT_FORGE, SlimefunItems.ENERGIZED_CAPACITOR,
            Items.MAGNONIUM_INGOT, Items.INFINITE_INGOT, Items.MAGNONIUM_INGOT
        });

        setupInv();

        registerBlockHandler(getID(), (p, b, stack, reason) -> {
            BlockMenu inv = BlockStorage.getInventory(b);
            Location l = b.getLocation();

            if (inv != null) {
                inv.dropItems(l, getOutputSlots());
                inv.dropItems(l, getInputSlots());
            }

            return true;
        });
    }

    private void setupInv() {
        createPreset(this, Items.INFINITY_FORGE.getDisplayName(),
                blockMenuPreset -> {

                    for (int i : OUTPUT_BORDER) {
                        blockMenuPreset.addItem(i, PresetUtils.borderItemOutput, ChestMenuUtils.getEmptyClickHandler());
                    }

                    for (int i : STATUS_BORDER) {
                        blockMenuPreset.addItem(i, PresetUtils.borderItemStatus, ChestMenuUtils.getEmptyClickHandler());
                    }

                    blockMenuPreset.addItem(STATUS_SLOT, PresetUtils.loadingItemBarrier,
                            ChestMenuUtils.getEmptyClickHandler());
                    blockMenuPreset.addItem(RECIPE_SLOT, PresetUtils.recipesItem,
                            ChestMenuUtils.getEmptyClickHandler());
                });
    }

    public static final RecipeType RECIPE_TYPE = new RecipeType(
            new NamespacedKey(InfinityExpansion.getInstance(), "infinity_forge"), Items.INFINITY_FORGE
    );

    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }

    @Override
    public int getCapacity() {
        return ENERGY;
    }

    @Override
    public int[] getInputSlots() {
        return INPUT_SLOTS;
    }

    @Override
    public int[] getOutputSlots() {
        return OUTPUT_SLOTS;
    }
}
