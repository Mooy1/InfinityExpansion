package io.github.mooy1.infinityexpansion.implementation.machines;

import io.github.mooy1.infinityexpansion.lists.Categories;
import io.github.mooy1.infinityexpansion.lists.Items;
import io.github.mooy1.infinitylib.items.StackUtils;
import io.github.mooy1.infinitylib.objects.AbstractContainer;
import io.github.mooy1.infinitylib.presets.MenuPreset;
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
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates special resources from the combination of singularities
 *
 * @author Mooy1
 */
public class ResourceSynthesizer extends AbstractContainer implements EnergyNetComponent, RecipeDisplayItem {

    public static final int ENERGY = 1_000_000;

    private static final int[] OUTPUT_SLOTS = {
            MenuPreset.slot2 + 27
    };
    private static final int[] BACKGROUND = {
            27, 29, 33, 35,
            36, 44,
            45, 46, 47, 51, 52, 53
    };
    private static final int[] OUTPUT_BORDER = {
            28, 34, 37, 38, 42, 43
    };
    private static final int[] INPUT_SLOTS = {
            MenuPreset.slot1, MenuPreset.slot3
    };
    private static final int INPUT_SLOT1 = INPUT_SLOTS[0];
    private static final int INPUT_SLOT2 = INPUT_SLOTS[1];
    private static final int STATUS_SLOT = MenuPreset.slot2;

    private static final SlimefunItemStack[] RECIPES = {
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
        for (int i : MenuPreset.slotChunk1) {
            blockMenuPreset.addItem(i, MenuPreset.borderItemInput, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : MenuPreset.slotChunk3) {
            blockMenuPreset.addItem(i, MenuPreset.borderItemInput, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : OUTPUT_BORDER) {
            blockMenuPreset.addItem(i, MenuPreset.borderItemOutput, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : MenuPreset.slotChunk2) {
            blockMenuPreset.addItem(i, MenuPreset.borderItemStatus, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : MenuPreset.slotChunk2) {
            blockMenuPreset.addItem(i + 27, MenuPreset.borderItemOutput, ChestMenuUtils.getEmptyClickHandler());
        }
        blockMenuPreset.addItem(STATUS_SLOT, MenuPreset.loadingItemBarrier,
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
    public void tick(@Nonnull Block b, @Nonnull BlockMenu inv) {
        if (getCharge(b.getLocation()) < ENERGY) { //not enough energy

            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, MenuPreset.notEnoughEnergy);
            }

        } else {
            ItemStack input1 = inv.getItemInSlot(INPUT_SLOT1);
            ItemStack input2 = inv.getItemInSlot(INPUT_SLOT2);

            if (input1 == null || input2 == null) { //no input

                if (inv.hasViewer()) {
                    inv.replaceExistingItem(STATUS_SLOT, MenuPreset.inputAnItem);
                }

            } else { //start
                String id1 = StackUtils.getItemID(input1, false);
                
                if (id1 == null) return;
                
                String id2 = StackUtils.getItemID(input2, false);

                if (id2 == null) return;
                
                ItemStack recipe = null;

                for (int i = 0; i < RECIPES.length; i += 3) {
                    if ((id1.equals(RECIPES[i].getItemId()) && id2.equals(RECIPES[i + 1].getItemId()) || (id2.equals(RECIPES[i].getItemId()) && id1.equals(RECIPES[i + 1].getItemId())))) {
                        recipe = RECIPES[i + 2];
                    }
                }

                if (recipe == null) { //invalid recipe

                    if (inv.hasViewer()) {
                        inv.replaceExistingItem(STATUS_SLOT, MenuPreset.invalidRecipe);
                    }

                } else { //start
                    
                    recipe = recipe.clone();
                    
                    if (inv.fits(recipe, OUTPUT_SLOTS)) { //no item

                        inv.pushItem(recipe, OUTPUT_SLOTS);
                        inv.consumeItem(INPUT_SLOT1, 1);
                        inv.consumeItem(INPUT_SLOT2, 1);
                        removeCharge(b.getLocation(), ENERGY);

                        if (inv.hasViewer()) {
                            inv.replaceExistingItem(STATUS_SLOT,
                                    new CustomItem(Material.LIME_STAINED_GLASS_PANE, "&aResource Synthesized!"));
                        }
                        
                    } else { //not enough room

                        if (inv.hasViewer()) {
                            inv.replaceExistingItem(STATUS_SLOT, MenuPreset.notEnoughRoom);
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
        return ENERGY * 2;
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
