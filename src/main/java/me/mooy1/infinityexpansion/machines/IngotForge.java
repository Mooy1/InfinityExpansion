package me.mooy1.infinityexpansion.machines;

import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mooy1.infinityexpansion.Categories;
import me.mooy1.infinityexpansion.InfinityExpansion;
import me.mooy1.infinityexpansion.Items;
import me.mooy1.infinityexpansion.utils.PresetUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.interfaces.InventoryBlock;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class IngotForge extends SlimefunItem implements InventoryBlock, EnergyNetComponent {

    public static int ENERGY = 1_000_000;

    private final int[] INPUT_SLOTS = {
            10, 11, 12, 13, 14, 15, 16,
            19, 20, 21, 22, 23, 24, 25,
            28, 29, 30, 31, 32, 33, 34,
    };
    private final int[] INPUT_BORDER = {
            0, 1, 2, 3,    5, 6, 7, 8,
            9,                      17,
            18,                     26,
            27,                     35,
            36,        40,          44,
            45,        49,          53,
    };
    private final int[] OUTPUT_SLOTS = {
            51
    };
    private final int[] OUTPUT_BORDER = {
            41, 42, 43,
            50,     52
    };
    private final int RECIPE_SLOT = 4;
    private final int STATUS_SLOT = 47;
    private final int[] STATUS_BORDER = {
            37, 38, 39,
            46,     48,

    };



    public IngotForge() {
        super(Categories.INFINITY_MACHINES, Items.INGOT_FORGE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.MAGSTEEL_PLATE, Items.MAGSTEEL_PLATE, Items.MAGSTEEL_PLATE,
                SlimefunItems.LARGE_CAPACITOR, new ItemStack(Material.SMITHING_TABLE), SlimefunItems.LARGE_CAPACITOR,
                Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
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
        createPreset(this, Items.INGOT_FORGE.getDisplayName(),
                blockMenuPreset -> {

                    for (int i : OUTPUT_BORDER) {
                        blockMenuPreset.addItem(i, PresetUtils.borderItemOutput, ChestMenuUtils.getEmptyClickHandler());
                    }

                    for (int i : INPUT_BORDER) {
                        blockMenuPreset.addItem(i, PresetUtils.borderItemInput, ChestMenuUtils.getEmptyClickHandler());
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
            new NamespacedKey(InfinityExpansion.getInstance(), "ingot_forge"), Items.INGOT_FORGE
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
