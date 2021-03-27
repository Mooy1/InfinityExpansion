package io.github.mooy1.infinityexpansion.implementation.machines;

import io.github.mooy1.infinityexpansion.categories.Categories;
import io.github.mooy1.infinityexpansion.implementation.abstracts.AbstractMachine;
import io.github.mooy1.infinityexpansion.implementation.materials.Items;
import io.github.mooy1.infinityexpansion.implementation.materials.Singularity;
import io.github.mooy1.infinitylib.items.StackUtils;
import io.github.mooy1.infinitylib.slimefun.presets.LorePreset;
import io.github.mooy1.infinitylib.slimefun.presets.MenuPreset;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates special resources from the combination of singularities
 *
 * @author Mooy1
 */
public final class ResourceSynthesizer extends AbstractMachine implements RecipeDisplayItem {

    public static final int ENERGY = 1_000_000;
    
    public static final SlimefunItemStack ITEM = new SlimefunItemStack(
            "RESOURCE_SYNTHESIZER",
            Material.LODESTONE,
            "&6Resource Synthesizer",
            "&7Creates resources by combining 2 Singularities",
            "",
            LorePreset.energy(ResourceSynthesizer.ENERGY) + "per use"
    );
    
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
            Singularity.IRON, Singularity.COAL, new SlimefunItemStack(SlimefunItems.REINFORCED_ALLOY_INGOT, 32),
            Singularity.IRON, Singularity.REDSTONE, new SlimefunItemStack(SlimefunItems.REDSTONE_ALLOY, 32),
            Singularity.DIAMOND, Singularity.COAL, new SlimefunItemStack(SlimefunItems.CARBONADO, 16),
            Singularity.GOLD, Singularity.EMERALD, new SlimefunItemStack(SlimefunItems.BLISTERING_INGOT_3, 16),
            Singularity.COPPER, Singularity.ZINC, new SlimefunItemStack(SlimefunItems.ELECTRO_MAGNET, 64),
            Singularity.IRON, Singularity.QUARTZ, new SlimefunItemStack(SlimefunItems.SOLAR_PANEL, 64)
    };

    public ResourceSynthesizer() {
        super(Categories.ADVANCED_MACHINES, ITEM, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                Items.ADAMANTITE, Items.ADAMANTITE, Items.ADAMANTITE,
                Items.MACHINE_PLATE, SlimefunItems.REINFORCED_FURNACE, Items.MACHINE_PLATE,
                Items.MACHINE_PLATE, Items.MACHINE_CORE, Items.MACHINE_PLATE
        });
    }

    @Override
    protected void onBreak(@Nonnull BlockBreakEvent e, @Nonnull BlockMenu inv, @Nonnull Location l) {
        inv.dropItems(l, OUTPUT_SLOTS);
        inv.dropItems(l, INPUT_SLOTS);
    }

    @Override
    protected int getStatusSlot() {
        return STATUS_SLOT;
    }

    @Override
    protected int getEnergyConsumption() {
        return ENERGY;
    }
    
    @Override
    protected void setupMenu(@Nonnull BlockMenuPreset blockMenuPreset) {
        super.setupMenu(blockMenuPreset);
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
    }

    @Nonnull
    @Override
    protected int[] getTransportSlots(@Nonnull DirtyChestMenu menu, @Nonnull ItemTransportFlow flow, ItemStack item) {
        if (flow == ItemTransportFlow.INSERT) {
            return INPUT_SLOTS;
        } else if (flow == ItemTransportFlow.WITHDRAW) {
            return OUTPUT_SLOTS;
        } else {
            return new int[0];
        }
    }

    @Override
    public void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {

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

    @Override
    protected boolean process(@Nonnull BlockMenu inv, @Nonnull Block b, @Nonnull Config data) {

        ItemStack input1 = inv.getItemInSlot(INPUT_SLOT1);
        ItemStack input2 = inv.getItemInSlot(INPUT_SLOT2);

        if (input1 == null || input2 == null) { //no input

            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, MenuPreset.inputAnItem);
            }
            return false;

        }

        String id1 = StackUtils.getID(input1);

        if (id1 == null) return false;

        String id2 = StackUtils.getID(input2);

        if (id2 == null) return false;

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
            return false;

        }

        recipe = recipe.clone();

        if (inv.fits(recipe, OUTPUT_SLOTS)) { //no item

            inv.pushItem(recipe, OUTPUT_SLOTS);
            inv.consumeItem(INPUT_SLOT1, 1);
            inv.consumeItem(INPUT_SLOT2, 1);

            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE, "&aResource Synthesized!"));
            }
            return true;

        } else { //not enough room

            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, MenuPreset.notEnoughRoom);
            }
            return false;

        }
    }

}
