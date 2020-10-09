package me.mooy1.infinityexpansion.machines;

import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
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
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AdvancedAnvil extends SlimefunItem implements EnergyNetComponent, InventoryBlock {

    private final Type type;

    public static int ADVANCED_ENERGY = 100_000;
    public static int INFINITY_ENERGY = 1_000_000;
    
    private final int[] INPUT_SLOTS= {
            PresetUtils.slot1, PresetUtils.slot2
    };
    private final int INPUT_SLOT1 = INPUT_SLOTS[0];
    private final int INPUT_SLOT2 = INPUT_SLOTS[1];
    private final int[] OUTPUT_SLOTS = {
            PresetUtils.slot3
    };
    private final int STATUS_SLOT = PresetUtils.slot2 + 27;
    private final int[] BACKROUND = {
            27, 28, 29, 33, 34, 35,
            36, 37, 38, 42, 43, 44,
            45, 46, 47, 51, 52, 53
    };

    public AdvancedAnvil(Type type) {
        super(Categories.INFINITY_MACHINES, type.getItem(), type.getRecipetype(), type.getRecipe());
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
                    for (int i : PresetUtils.slotChunk3) {
                        blockMenuPreset.addItem(i, PresetUtils.borderItemOutput, ChestMenuUtils.getEmptyClickHandler());
                    }
                    for (int i : BACKROUND) {
                        blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
                    }
                    for (int i : PresetUtils.slotChunk1) {
                        blockMenuPreset.addItem(i, PresetUtils.borderItemInput, ChestMenuUtils.getEmptyClickHandler());
                    }
                    for (int i : PresetUtils.slotChunk2) {
                        blockMenuPreset.addItem(i, PresetUtils.borderItemInput, ChestMenuUtils.getEmptyClickHandler());
                    }
                    for (int i : PresetUtils.slotChunk2) {
                        blockMenuPreset.addItem(i + 27, PresetUtils.borderItemStatus, ChestMenuUtils.getEmptyClickHandler());
                    }
                    blockMenuPreset.addItem(STATUS_SLOT, PresetUtils.loadingItemBarrier,
                            ChestMenuUtils.getEmptyClickHandler());
                    blockMenuPreset.addItem(STATUS_SLOT, PresetUtils.loadingItemRed,
                            ChestMenuUtils.getEmptyClickHandler());
                });
    }

    @Override
    public void preRegister() {
        this.addItemHandler(new BlockTicker() {
            public void tick(Block b, SlimefunItem sf, Config data) { AdvancedAnvil.this.tick(b); }

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

        } else {

        }
    }
    

    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }

    @Override
    public int getCapacity() {
        if (this.type == Type.ADVANCED) {
            return ADVANCED_ENERGY;

        } else if (this.type == Type.INFINITY) {
            return INFINITY_ENERGY;

        } else {
            return 0;
        }
    }

    private int getEnergyConsumption(Type type) {
        if (type == Type.ADVANCED) {
            return ADVANCED_ENERGY;
        } else if (type == Type.INFINITY) {
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

        ADVANCED(Items.ADVANCED_ANVIL, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.MAGSTEEL_PLATE, Items.MAGSTEEL_PLATE, Items.MAGSTEEL_PLATE,
                Items.MACHINE_PLATE, new ItemStack(Material.ANVIL), Items.MACHINE_PLATE,
                Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        }),
        INFINITY(Items.INFINITY_ANVIL, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.INFINITE_INGOT, Items.INFINITE_INGOT, Items.INFINITE_INGOT,
                Items.VOID_INGOT, Items.ADVANCED_ANVIL, Items.VOID_INGOT,
                Items.INFINITE_MACHINE_CIRCUIT, Items.INFINITE_MACHINE_CORE, Items.INFINITE_MACHINE_CIRCUIT
        });

        @Nonnull
        private final SlimefunItemStack item;
        private final RecipeType recipetype;
        private final ItemStack[] recipe;
    }
}
