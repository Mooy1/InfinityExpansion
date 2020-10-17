package me.mooy1.infinityexpansion.machines;

import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mooy1.infinityexpansion.Items;
import me.mooy1.infinityexpansion.setup.Categories;
import me.mooy1.infinityexpansion.utils.PresetUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
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
import java.util.ArrayList;
import java.util.List;

public class VirtualFarm extends SlimefunItem implements InventoryBlock, EnergyNetComponent, RecipeDisplayItem {

    public static final int ENERGY1 = 18;
    public static final int ENERGY2 = 180;
    public static final int ENERGY3 = 3600;
    public static final int SPEED1 = 1;
    public static final int SPEED2 = 4;
    public static final int SPEED3 = 16;
    private final Type type;

    private static final int[] OUTPUT_SLOTS = {
            37, 38, 39, 40, 41, 42, 43
    };
    private static final int[] OUTPUT_BORDER = {
            27, 28, 29, 30, 31, 32, 33, 34, 35,
            36, 44,
            45, 46, 47 ,48, 49 ,50, 51, 52 ,53
    };
    private static final int[] BACKGROUND = {
            0, 4, 8,
            9, 13, 17,
            18, 22, 26
    };
    private static final int[] INPUT_SLOTS = {
            PresetUtils.slot1 + 28
    };
    private static final int STATUS_SLOT = PresetUtils.slot3 + 26;

    public VirtualFarm(Type type) {
        super(type.getCategory(), type.getItem(), type.getRecipeType(), type.getRecipe());
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
                        blockMenuPreset.addItem(i + 27, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
                    }
                    for (int i : PresetUtils.slotChunk1) {
                        blockMenuPreset.addItem(i + 28, PresetUtils.borderItemInput, ChestMenuUtils.getEmptyClickHandler());
                    }
                    for (int i : PresetUtils.slotChunk3) {
                        blockMenuPreset.addItem(i + 26, PresetUtils.borderItemStatus, ChestMenuUtils.getEmptyClickHandler());
                    }
                    for (int i : OUTPUT_BORDER) {
                        blockMenuPreset.addItem(i - 27, PresetUtils.borderItemOutput, ChestMenuUtils.getEmptyClickHandler());
                    }
                    blockMenuPreset.addItem(STATUS_SLOT, PresetUtils.loadingItemBarrier,
                            ChestMenuUtils.getEmptyClickHandler());
                });
    }

    @Override
    public void preRegister() {
        this.addItemHandler(new BlockTicker() {
            public void tick(Block b, SlimefunItem sf, Config data) { VirtualFarm.this.tick(b); }

            public boolean isSynchronized() { return false; }
        });
    }

    public void tick(Block b) {
        @Nullable final BlockMenu inv = BlockStorage.getInventory(b.getLocation());
        if (inv == null) return;

        int energy = type.getEnergy();

        boolean playerWatching = inv.toInventory() != null && !inv.toInventory().getViewers().isEmpty();

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
        return type.getEnergy();
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        List<ItemStack> items = new ArrayList<>();

        for (int i = 0 ; i < INPUTS.length ; i++) {
            items.add(INPUTS[i]);
            items.add(OUTPUTS1[i]);
        }

        return items;
    }

    private static final ItemStack[] INPUTS = {
            new ItemStack(Material.WHEAT_SEEDS),
            new ItemStack(Material.CARROT),
            new ItemStack(Material.POTATO),
            new ItemStack(Material.BEETROOT_SEEDS),
            new ItemStack(Material.PUMPKIN_SEEDS),
            new ItemStack(Material.MELON_SEEDS),
            new ItemStack(Material.SUGAR_CANE),
            new ItemStack(Material.COCOA_BEANS),
            new ItemStack(Material.CACTUS),
            new ItemStack(Material.BAMBOO),
            new ItemStack(Material.CHORUS_FLOWER)
    };

    private static final ItemStack[] OUTPUTS1 = {
            new ItemStack(Material.WHEAT, 1),
            new ItemStack(Material.CARROT, 2),
            new ItemStack(Material.POTATO, 2),
            new ItemStack(Material.BEETROOT, 1),
            new ItemStack(Material.PUMPKIN, 1),
            new ItemStack(Material.MELON_SLICE, 4),
            new ItemStack(Material.SUGAR_CANE, 2),
            new ItemStack(Material.COCOA_BEANS, 2),
            new ItemStack(Material.CACTUS, 2),
            new ItemStack(Material.BAMBOO, 4),
            new ItemStack(Material.CHORUS_FRUIT, 4)
    };

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
        BASIC(ENERGY1, SPEED1, Categories.BASIC_MACHINES, Items.BASIC_VIRTUAL_FARM, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.MAGSTEEL, Items.MAGSTEEL, Items.MAGSTEEL,
                Items.MAGNONIUM_INGOT, SlimefunItems.GEO_MINER, Items.MAGNONIUM_INGOT,
                Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        }),
        ADVANCED(ENERGY2, SPEED2, Categories.ADVANCED_MACHINES, Items.ADVANCED_VIRTUAL_FARM, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.MAGSTEEL, Items.MAGSTEEL, Items.MAGSTEEL,
                Items.MAGNONIUM_INGOT, SlimefunItems.GEO_MINER, Items.MAGNONIUM_INGOT,
                Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        }),
        INFINITY(ENERGY3, SPEED3, Categories.INFINITY_MACHINES, Items.INFINITY_VIRTUAL_FARM, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.INFINITE_INGOT, Items.VOID_INGOT, Items.INFINITE_INGOT,
                Items.VOID_INGOT, Items.VOID_HARVESTER, Items.VOID_INGOT,
                Items.INFINITE_MACHINE_CIRCUIT, Items.INFINITE_MACHINE_CORE, Items.INFINITE_MACHINE_CIRCUIT
        });

        private final int energy;
        private final int speed;
        @Nonnull
        private final Category category;
        private final SlimefunItemStack item;
        private final RecipeType recipeType;
        private final ItemStack[] recipe;
    }
}
