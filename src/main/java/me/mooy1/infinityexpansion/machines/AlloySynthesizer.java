package me.mooy1.infinityexpansion.machines;

import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mooy1.infinityexpansion.Categories;
import me.mooy1.infinityexpansion.Items;
import me.mooy1.infinityexpansion.utils.PresetUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.interfaces.InventoryBlock;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AlloySynthesizer extends SlimefunItem implements EnergyNetComponent, InventoryBlock {

    private final Type type;
    public static int BASIC_SPEED = 1;
    public static int INFINITY_SPEED = 4;
    public static int BASIC_ENERGY = 1000;
    public static int INFINITY_ENERGY = 100000;

    private final int[] OUTPUT_SLOTS = {
            16, 25, 34
    };
    private final int[] BACKGROUND = {
            0, 1, 2, 3, 4, 5,
            36, 37, 38, 39, 40, 41
    };
    private final int[] OUTPUT_BORDER = {
            6, 7, 8,
            15, 17,
            24, 26,
            33, 35,
            42, 43, 44
    };
    private final int[] INPUT_SLOTS = {
            PresetUtils.slot1 + 9
    };
    private final int INPUT_SLOT = INPUT_SLOTS[0];
    private final int STATUS_SLOT = PresetUtils.slot2 + 9;

    public AlloySynthesizer(Type type) {
        super(Categories.INFINITY_MACHINES, type.getItem(), RecipeType.ENHANCED_CRAFTING_TABLE, type.getRecipe());
        this.type = type;

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
        createPreset(this, type.getItem().getDisplayName(),
                blockMenuPreset -> {
                    for (int i : BACKGROUND) {
                        blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
                    }
                    for (int i : OUTPUT_BORDER) {
                        blockMenuPreset.addItem(i, PresetUtils.borderItemOutput, ChestMenuUtils.getEmptyClickHandler());
                    }
                    for (int i : PresetUtils.slotChunk2) {
                        blockMenuPreset.addItem(i + 9, PresetUtils.borderItemStatus, ChestMenuUtils.getEmptyClickHandler());
                    }
                    for (int i : PresetUtils.slotChunk1) {
                        blockMenuPreset.addItem(i + 9, PresetUtils.borderItemInput, ChestMenuUtils.getEmptyClickHandler());
                    }
                    blockMenuPreset.addItem(STATUS_SLOT, PresetUtils.loadingItemBarrier,
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
        int energy = getEnergyConsumption(type);

        if (getCharge(b.getLocation()) < energy) { //not enough energy

            if (playerWatching) {
                inv.replaceExistingItem(STATUS_SLOT, PresetUtils.notEnoughEnergy);
            }

        } else { //start

        }
    }

    private int getEnergyConsumption(Type type) {
        if (type == Type.BASIC) {
            return BASIC_ENERGY;
        } else if (type == Type.INFINITY) {
            return INFINITY_ENERGY;
        } else {
            return 0;
        }
    }

    private int getSpeed() {
        if (this.type == Type.BASIC) {
            return BASIC_SPEED;
        } else if (this.type == Type.INFINITY) {
            return INFINITY_SPEED;
        } else {
            return 0;
        }
    }

    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }

    @Override
    public int getCapacity() {
        if (this.type == Type.BASIC) {
            return BASIC_ENERGY;
        } else if (this.type == Type.INFINITY) {
            return INFINITY_ENERGY;
        } else {
            return 0;
        }
    }

    @Override
    public int[] getInputSlots() {
        return INPUT_SLOTS;
    }

    @Override
    public int[] getOutputSlots() {
        return OUTPUT_SLOTS;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)

    public enum Type {
        BASIC(Items.ALLOY_SYNTHESIZER, new ItemStack[] {
            Items.MAGSTEEL, Items.MAGSTEEL, Items.MAGSTEEL,
            Items.MACHINE_PLATE, SlimefunItems.REINFORCED_FURNACE, Items.MACHINE_PLATE,
            Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        }),
        INFINITY(Items.INFINITY_ALLOY_SYNTHESIZER, new ItemStack[] {
            Items.INFINITE_INGOT, Items.MACHINE_PLATE, Items.INFINITE_INGOT,
            Items.MACHINE_PLATE, Items.ALLOY_SYNTHESIZER, Items.MACHINE_PLATE,
            Items.INFINITE_MACHINE_CIRCUIT, Items.INFINITE_MACHINE_CORE, Items.INFINITE_MACHINE_CIRCUIT
        });

        @Nonnull
        private final SlimefunItemStack item;
        private final ItemStack[] recipe;
    }
}
