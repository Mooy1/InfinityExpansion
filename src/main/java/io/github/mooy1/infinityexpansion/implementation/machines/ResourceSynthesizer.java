package io.github.mooy1.infinityexpansion.implementation.machines;

import io.github.mooy1.infinityexpansion.implementation.template.Machine;
import io.github.mooy1.infinityexpansion.lists.Categories;
import io.github.mooy1.infinityexpansion.lists.Items;
import io.github.mooy1.infinityexpansion.utils.PresetUtils;
import io.github.mooy1.infinityexpansion.utils.StackUtils;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Creates special resources from the combination of singularities
 *
 * @author Mooy1
 */
public class ResourceSynthesizer extends Machine implements EnergyNetComponent, RecipeDisplayItem {

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
                Items.MAGSTEEL_PLATE, Items.MAGSTEEL_PLATE, Items.MAGSTEEL_PLATE,
                Items.MACHINE_PLATE, SlimefunItems.REINFORCED_FURNACE, Items.MACHINE_PLATE,
                Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        });

        registerBlockHandler(getId(), (p, b, stack, reason) -> {
            BlockMenu inv = BlockStorage.getInventory(b);

            if (inv != null) {
                inv.dropItems(b.getLocation(), OUTPUT_SLOTS);
                inv.dropItems(b.getLocation(), INPUT_SLOTS);
            }

            return true;
        });
    }

    @Override
    public void setupInv(@Nonnull BlockMenuPreset blockMenuPreset) {
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
    }

    @Override
    public int[] getTransportSlots(@Nonnull ItemTransportFlow flow) {
        if (flow == ItemTransportFlow.INSERT) {
            return INPUT_SLOTS;
        } else if (flow == ItemTransportFlow.WITHDRAW) {
            return OUTPUT_SLOTS;
        } else {
            return new int[0];
        }
    }
    
    @Override
    public void tick(@Nonnull Block b, @Nonnull Location l, @Nonnull BlockMenu inv) {
        boolean playerWatching = inv.toInventory() != null && !inv.toInventory().getViewers().isEmpty();

        if (getCharge(b.getLocation()) < ENERGY) { //not enough energy

            if (playerWatching) {
                inv.replaceExistingItem(STATUS_SLOT, PresetUtils.notEnoughEnergy);
            }

        } else {
            ItemStack input1 = inv.getItemInSlot(INPUT_SLOT1);
            ItemStack input2 = inv.getItemInSlot(INPUT_SLOT2);

            if (input1 == null || input2 == null) { //no input

                if (playerWatching) {
                    inv.replaceExistingItem(STATUS_SLOT, PresetUtils.inputAnItem);
                }

            } else { //start
                String id1 = StackUtils.getIDFromItem(input1);
                String id2 = StackUtils.getIDFromItem(input2);

                ItemStack recipe = null;

                for (int i = 0; i < RECIPES.length; i += 3) {
                    if (((Objects.equals(id1, StackUtils.getIDFromItem(RECIPES[i])))
                            && (Objects.equals(id2, StackUtils.getIDFromItem(RECIPES[i + 1]))))
                            ||
                            ((Objects.equals(id1, StackUtils.getIDFromItem(RECIPES[i + 1])))
                                    && (Objects.equals(id2, StackUtils.getIDFromItem(RECIPES[i]))))) {
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
                    } else if ((Objects.equals(StackUtils.getIDFromItem(outputSlot), StackUtils.getIDFromItem(recipe))
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

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        final List<ItemStack> items = new ArrayList<>();

        for (int i = 0; i < RECIPES.length; i += 3) {
            items.add(RECIPES[i]);
            items.add(RECIPES[i + 2]);
            items.add(RECIPES[i + 1]);
            items.add(RECIPES[i + 2]);
        }

        return items;
    }
}
