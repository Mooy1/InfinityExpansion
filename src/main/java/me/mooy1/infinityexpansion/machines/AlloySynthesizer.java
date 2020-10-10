package me.mooy1.infinityexpansion.machines;

import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mooy1.infinityexpansion.setup.Categories;
import me.mooy1.infinityexpansion.Items;
import me.mooy1.infinityexpansion.utils.PresetUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.interfaces.InventoryBlock;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AlloySynthesizer extends SlimefunItem implements EnergyNetComponent, InventoryBlock {

    public static int ENERGY = 1_000_000;

    private final int[] OUTPUT_SLOTS = {
            16, 25, 34, 43
    };
    private final int[] BACKGROUND = {
            0, 1, 2, 3, 4, 5,
            45, 46, 47, 48, 49, 50
    };
    private final int[] OUTPUT_BORDER = {
            6, 7, 8,
            15,     17,
            24,     26,
            33,     35,
            42,     44,
            51, 52, 53
    };
    private final int[] INPUT_BORDER = {
            9, 10, 11,
            18, 20,
            27, 29,
            36, 37, 38
    };
    private final int[] STATUS_BORDER = {
            12, 13, 14,
            21, 23,
            30, 32,
            39, 40, 41
    };
    private final int[] INPUT_SLOTS = {
            19, 28
    };
    private final int INPUT_SLOT1 = INPUT_SLOTS[0];
    private final int INPUT_SLOT2 = INPUT_SLOTS[1];
    private final int RECIPE_SLOT = PresetUtils.slot2 + 9;
    private final int STATUS_SLOT = PresetUtils.slot2 + 18;

    private final ItemStack[] recipes = {
            Items.IRON_SINGULARITY, Items.COAL_SINGULARITY, SlimefunItems.REINFORCED_ALLOY_INGOT,
            Items.IRON_SINGULARITY, Items.REDSTONE_SINGULARITY, SlimefunItems.REDSTONE_ALLOY,
            Items.DIAMOND_SINGULARITY, Items.COAL_SINGULARITY,

    };

    public AlloySynthesizer() {
        super(Categories.INFINITY_MACHINES, Items.ALLOY_SYNTHESIZER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                Items.MAGSTEEL, Items.MAGSTEEL, Items.MAGSTEEL,
                Items.MACHINE_PLATE, SlimefunItems.REINFORCED_FURNACE, Items.MACHINE_PLATE,
                Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        });

        setupInv();

        registerBlockHandler(getID(), (p, b, stack, reason) -> {
            BlockMenu inv = BlockStorage.getInventory(b);

            if (inv != null) {
                inv.dropItems(b.getLocation(), getOutputSlots());
                inv.dropItems(b.getLocation(), getInputSlots());
            }

            return true;
        });
    }

    private void setupInv() {
        createPreset(this, Items.ALLOY_SYNTHESIZER.getDisplayName(),
                blockMenuPreset -> {
                    for (int i : BACKGROUND) {
                        blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
                    }
                    for (int i : OUTPUT_BORDER) {
                        blockMenuPreset.addItem(i, PresetUtils.borderItemOutput, ChestMenuUtils.getEmptyClickHandler());
                    }
                    for (int i : STATUS_BORDER) {
                        blockMenuPreset.addItem(i, PresetUtils.borderItemStatus, ChestMenuUtils.getEmptyClickHandler());
                    }
                    for (int i : INPUT_BORDER) {
                        blockMenuPreset.addItem(i, PresetUtils.borderItemInput, ChestMenuUtils.getEmptyClickHandler());
                    }
                    blockMenuPreset.addItem(STATUS_SLOT, PresetUtils.loadingItemBarrier,
                            ChestMenuUtils.getEmptyClickHandler());
                    blockMenuPreset.addItem(RECIPE_SLOT, PresetUtils.recipesItem,
                            ChestMenuUtils.getEmptyClickHandler());
                });
    }

    @Override
    public void preRegister() {
        this.addItemHandler(new BlockTicker() {
            public void tick(Block b, SlimefunItem sf, Config data) { AlloySynthesizer.this.tick(b); }

            public boolean isSynchronized() { return false; }
        });
    }

    public void tick(Block b) {
        @Nullable final BlockMenu inv = BlockStorage.getInventory(b.getLocation());
        if (inv == null) return;

        boolean playerWatching = inv.toInventory() != null && !inv.toInventory().getViewers().isEmpty();

        if (getCharge(b.getLocation()) < ENERGY) { //not enough energy

            if (playerWatching) {
                inv.replaceExistingItem(STATUS_SLOT, PresetUtils.notEnoughEnergy);
            }

        } else { //start


        }
    }

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
