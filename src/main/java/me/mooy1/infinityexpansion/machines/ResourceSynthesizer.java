package me.mooy1.infinityexpansion.machines;

import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mooy1.infinityexpansion.setup.Categories;
import me.mooy1.infinityexpansion.Items;
import me.mooy1.infinityexpansion.utils.IDUtils;
import me.mooy1.infinityexpansion.utils.PresetUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.interfaces.InventoryBlock;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ResourceSynthesizer extends SlimefunItem implements EnergyNetComponent, InventoryBlock, RecipeDisplayItem {

    public static final int ENERGY = 1_000_000;

    private static final int[] OUTPUT_SLOTS = {
            PresetUtils.slot2 + 27
    };
    private static final int OUTPUT_SLOT = OUTPUT_SLOTS[0];
    private static final int[] BACKGROUND = {
            27, 29, 33, 35,
            36, 44,
            45, 46, 47, 51, 52, 53
    };
    private static final int[] OUTPUT_BORDER = {
            28, 34, 37, 38, 42, 43
    };
    private static final int[] INPUT_SLOTS = {
            PresetUtils.slot1, PresetUtils.slot3
    };
    private static final int INPUT_SLOT1 = INPUT_SLOTS[0];
    private static final int INPUT_SLOT2 = INPUT_SLOTS[1];
    private static final int STATUS_SLOT = PresetUtils.slot2;

    private static final ItemStack[] RECIPES = {
            Items.IRON_SINGULARITY, Items.COAL_SINGULARITY, new SlimefunItemStack(SlimefunItems.REINFORCED_ALLOY_INGOT, 32),
            Items.IRON_SINGULARITY, Items.REDSTONE_SINGULARITY, new SlimefunItemStack(SlimefunItems.REDSTONE_ALLOY, 32),
            Items.DIAMOND_SINGULARITY, Items.COAL_SINGULARITY, new SlimefunItemStack(SlimefunItems.COMPRESSED_CARBON, 16),
            Items.GOLD_SINGULARITY, Items.EMERALD_SINGULARITY, new SlimefunItemStack(SlimefunItems.BLISTERING_INGOT_3, 4),
            Items.COPPER_SINGULARITY, Items.IRON_SINGULARITY, new SlimefunItemStack(SlimefunItems.ELECTRO_MAGNET, 32),
            Items.IRON_SINGULARITY, Items.QUARTZ_SINGULARITY, new SlimefunItemStack(SlimefunItems.SOLAR_PANEL, 64)
    };

    public ResourceSynthesizer() {
        super(Categories.ADVANCED_MACHINES, Items.RESOURCE_SYNTHESIZER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                Items.MAGSTEEL, Items.MAGSTEEL, Items.MAGSTEEL,
                Items.MACHINE_PLATE, SlimefunItems.REINFORCED_FURNACE, Items.MACHINE_PLATE,
                Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        });

        setupInv();

        registerBlockHandler(getId(), (p, b, stack, reason) -> {
            BlockMenu inv = BlockStorage.getInventory(b);

            if (inv != null) {
                inv.dropItems(b.getLocation(), getOutputSlots());
                inv.dropItems(b.getLocation(), getInputSlots());
            }

            return true;
        });
    }

    private void setupInv() {
        createPreset(this, Items.RESOURCE_SYNTHESIZER.getDisplayName(),
                blockMenuPreset -> {
                    for (int i : BACKGROUND) {
                        blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
                    }
                    for (int i : PresetUtils.slotChunk1) {
                        blockMenuPreset.addItem(i, PresetUtils.borderItemInput, ChestMenuUtils.getEmptyClickHandler());
                    }
                    for (int i : PresetUtils.slotChunk3) {
                        blockMenuPreset.addItem(i, PresetUtils.borderItemInput, ChestMenuUtils.getEmptyClickHandler());
                    }
                    for (int i : OUTPUT_BORDER) {
                        blockMenuPreset.addItem(i, PresetUtils.borderItemOutput, ChestMenuUtils.getEmptyClickHandler());
                    }
                    for (int i : PresetUtils.slotChunk2) {
                        blockMenuPreset.addItem(i, PresetUtils.borderItemStatus, ChestMenuUtils.getEmptyClickHandler());
                    }
                    for (int i : PresetUtils.slotChunk2) {
                        blockMenuPreset.addItem(i + 27, PresetUtils.borderItemOutput, ChestMenuUtils.getEmptyClickHandler());
                    }
                    blockMenuPreset.addItem(STATUS_SLOT, PresetUtils.loadingItemBarrier,
                            ChestMenuUtils.getEmptyClickHandler());
                });
    }

    @Override
    public void preRegister() {
        this.addItemHandler(new BlockTicker() {
            public void tick(Block b, SlimefunItem sf, Config data) { ResourceSynthesizer.this.tick(b); }

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

        } else {
            ItemStack input1 = inv.getItemInSlot(INPUT_SLOT1);
            ItemStack input2 = inv.getItemInSlot(INPUT_SLOT2);

            if (input1 == null || input2  == null) { //no input

                if (playerWatching) {
                    inv.replaceExistingItem(STATUS_SLOT, PresetUtils.inputAnItem);
                }

            } else { //start
                String id1 = IDUtils.getIDFromItem(input1);
                String id2 = IDUtils.getIDFromItem(input2);

                ItemStack recipe = null;

                for (int i = 0; i < RECIPES.length; i += 3) {
                    if (((id1.equals(IDUtils.getIDFromItem(RECIPES[i])))
                            && (id2.equals(IDUtils.getIDFromItem(RECIPES[i + 1]))))
                            ||
                            ((id1.equals(IDUtils.getIDFromItem(RECIPES[i + 1])))
                            && (id2.equals(IDUtils.getIDFromItem(RECIPES[i]))))) {
                        recipe = RECIPES[i + 2];
                    }
                }

                if (recipe == null) { //invalid recipe

                    if (playerWatching) {
                        inv.replaceExistingItem(STATUS_SLOT, PresetUtils.invalidRecipe);
                    }

                } else { //start

                    ItemStack outputSlot = inv.getItemInSlot(OUTPUT_SLOT);

                    if (outputSlot == null) { //no item

                        inv.consumeItem(INPUT_SLOT1, 1);
                        inv.consumeItem(INPUT_SLOT2, 1);
                        inv.replaceExistingItem(OUTPUT_SLOT, recipe);
                        removeCharge(b.getLocation(), ENERGY);

                        if (playerWatching) {
                            inv.replaceExistingItem(STATUS_SLOT,
                                    new CustomItem(Material.LIME_STAINED_GLASS_PANE, "&aResource Synthesized!"));
                        }
                    } else if ((IDUtils.getIDFromItem(outputSlot).equals(IDUtils.getIDFromItem(recipe))
                            && outputSlot.getAmount() + recipe.getAmount() <= recipe.getMaxStackSize())) { //already an item

                        outputSlot.setAmount(recipe.getAmount() + outputSlot.getAmount());

                        inv.consumeItem(INPUT_SLOT1, 1);
                        inv.consumeItem(INPUT_SLOT2, 1);
                        inv.replaceExistingItem(OUTPUT_SLOT, outputSlot);
                        removeCharge(b.getLocation(), ENERGY);

                        if (playerWatching) {
                            inv.replaceExistingItem(STATUS_SLOT,
                                    new CustomItem(Material.LIME_STAINED_GLASS_PANE, "&aResource Synthesized!"));
                        }

                    } else { //not enough room

                        if (playerWatching) {
                            inv.replaceExistingItem(STATUS_SLOT, PresetUtils.notEnoughRoom);
                        }

                    }
                }
            }
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

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        final List<ItemStack> items = new ArrayList<>();

        for (int i = 0; i < RECIPES.length; i += 3) {
            items.add(RECIPES[i]);
            items.add(RECIPES[i + 2]);
            items.add(RECIPES[i + 1]);
            items.add(null);
        }

        return items;
    }
}
